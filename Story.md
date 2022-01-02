## Search functionality

It's needed a basic `search` functionality which needs to filter by some criteria/constraints on `loan` domain and returns the `customers`' data (yet another domain) which are the owners of the filtered loans.

We can use several options:
 * GraphQL
 * Reverse AJAX, WebSockets, Server-Sent (the server will push the result to the client)
 * classical REST API: the biggest challenge here is the delay/timeout. Since the criteria might be complex, then we can think about some further steps to improve the performance:
     * using a search engine (elasticsearch/lucene/solr)
     * data duplication: the `loan` service will contain a table of customers, which is in a read-only mode from API/application, but will be updated only `onMessageReceived` on a specific topic/queue from `customer` service. this will speed up a bit the second query(getCustomersInfo), because the loan will search directly in its DB, instead of sending REST requests to customer service. another benefit of this approach is you can add DB indexes on the columns which will be used for search criteria
     * using distributed cache for data which are created/updated not very frequently
     * query by primary keys/indexed columns whenever it's possible.
    
Every approach has its pros and cons, so it's advisable to collect some frequent use cases and perform some tests/benchmarks to choose wisely. Sometimes it results to be successful a mixed/hybrid approach of two or more options.

In this exercise/homework I decided to implement(it was not required) a very simple solution by combining `querying by primary keys`(getCustomerByID/IDs), `caching not frequented updated data`(customers' data), and a custom,dynamic and pageable query (I used Spring Data `Specification` interface`, but a similar result can be achieved with [querydsl](http://www.querydsl.com/) as well).
The flow is the following:
* The user performs a `GET /search?page=0&size=10&sort=amount,ASC&criteria=amount>=1000,amount<=2000,status:CREATED,duration>=12`. Note:
   * The `criteria` query param is a string which contains a list of search criteria comma separated. each search criteria is a composite of `{key}{operation}{value}`. the key is the column name of `loan` table, the value is the criteria to compare with a specific operation. Currently, the operations supported are `>`, `>=`, `<`, `<=`, and `:` (`:` represents equals operation)
   * if you don't specify the `page`, `size`, and `sort`, they are initialized with the default values, but it's highly suggested using them in every request to have a correct `Slice`/`Page` result, so the user can go back and forth with the chunks
* the `criteria` is parsed by <code>SearchCriteriaParser</code> to a List<SearchCriteria> 
* The <code>SearchService</code> transforms/builds an SQL WHERE `Specification` from the `List<SearchCriteria>` created from the previous step, executes the query to `loan` table applying the page and search criteria
* The previous step returns a `Slice`/`Page` result, which is mapped to only `List<Long>` customerIDs
* Finally, the service retrieves the Customers' information calling the `customer` service by IDs (FeignClient is used for svc2svc communication)
* The result is cached, so the other requests can be faster.
