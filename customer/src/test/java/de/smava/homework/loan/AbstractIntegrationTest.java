package de.smava.homework.loan;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("test")
@SpringBootTest(classes = CustomerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {AbstractIntegrationTest.Initializer.class})
@Sql(scripts = {"/sql-scripts/clear_data.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public abstract class AbstractIntegrationTest {

    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer(DockerImageName.parse("postgres:12.3-alpine")) {{
        start();
    }};

    public static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + POSTGRESQL_CONTAINER.getJdbcUrl(),
                    "spring.datasource.username=" + POSTGRESQL_CONTAINER.getUsername(),
                    "spring.datasource.password=" + POSTGRESQL_CONTAINER.getPassword(),
                    "spring.datasource.driverClassName=" + POSTGRESQL_CONTAINER.getDriverClassName()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}