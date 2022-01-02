package de.smava.homework.loan;

import org.mockserver.client.MockServerClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("test")
@SpringBootTest(classes = LoanApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {AbstractIntegrationTest.Initializer.class})
@Sql(scripts = {"/sql-scripts/clear_data.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public abstract class AbstractIntegrationTest {

    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer(DockerImageName.parse("postgres:12.3-alpine")) {{
        start();
    }};

    private static final MockServerContainer mockServer = new MockServerContainer(DockerImageName.parse("mockserver/mockserver:mockserver-5.10.0")) {{
        start();
    }};

    protected MockServerClient mockServerClient = new MockServerClient(mockServer.getContainerIpAddress(), mockServer.getServerPort());

    public static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + POSTGRESQL_CONTAINER.getJdbcUrl(),
                    "spring.datasource.username=" + POSTGRESQL_CONTAINER.getUsername(),
                    "spring.datasource.password=" + POSTGRESQL_CONTAINER.getPassword(),
                    "spring.datasource.driverClassName=" + POSTGRESQL_CONTAINER.getDriverClassName(),

                    "customer.ribbon.listOfServers=" + mockServer.getEndpoint()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}