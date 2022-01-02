package de.smava.homework.loan.persistence;

import de.smava.homework.loan.util.SearchCriteria;
import de.smava.homework.loan.util.TriFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class EntitySpecification<T> implements Specification<T> {
    private final SearchCriteria criteria;

    private final Map<String, TriFunction<Root<T>, CriteriaBuilder, SearchCriteria, Predicate>> predicates = new HashMap<String, TriFunction<Root<T>, CriteriaBuilder, SearchCriteria, Predicate>>() {{
        put(">", (root, builder, criteria) -> builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString()));
        put(">=", (root, builder, criteria) -> builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
        put("<", (root, builder, criteria) -> builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString()));
        put("<=", (root, builder, criteria) -> builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
        put(":", (root, builder, criteria) -> {
            if (root.get(criteria.getKey()).getJavaType().equals(String.class)) {
                return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        });
        // TODO: add more if necessary, but please follow the open-closed principle!
    }};

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return predicates.containsKey(criteria.getOperation()) ? predicates.get(criteria.getOperation()).apply(root, builder, criteria) : null;
    }
}
