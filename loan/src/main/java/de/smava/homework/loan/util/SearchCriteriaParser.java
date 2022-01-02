package de.smava.homework.loan.util;

import de.smava.homework.loan.persistence.LoanEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SearchCriteriaParser {
    private static final List<String> SUPPORTED_OPERATIONS = Arrays.asList(":", "<", "<=", ">", ">=");
    private static final Pattern SEARCH_CRITERIA_PATTERN = Pattern.compile("(\\w+?)(" + String.join("|", SUPPORTED_OPERATIONS) + ")(\\w+?),");

    public List<SearchCriteria> parse(String searchCriteria) {
        if (!StringUtils.hasText(searchCriteria)) {
            return Collections.emptyList();
        }

        List<SearchCriteria> params = new ArrayList<>();
        Matcher matcher = SEARCH_CRITERIA_PATTERN.matcher(searchCriteria + ",");
        while (matcher.find() && SUPPORTED_OPERATIONS.contains(matcher.group(2))) {
            params.add(new SearchCriteria(mapKey(matcher.group(1)), matcher.group(2), mapValue(matcher.group(1), matcher.group(3))));
        }
        return params;
    }

    private String mapKey(String key) {
        switch (key) {
            case "minAmount":
            case "maxAmount":
                return "amount";
            default:
                return key;
        }
    }

    private Object mapValue(String key, String value) {
        switch (key) {
            case "status":
                return LoanEntity.Status.valueOf(value.toUpperCase());
            default:
                return value;
        }
    }
}
