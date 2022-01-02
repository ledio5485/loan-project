CREATE TABLE IF NOT EXISTS CUSTOMER
(
    ID         BIGSERIAL PRIMARY KEY NOT NULL,
    USER_ID    NUMERIC UNIQUE        NOT NULL,
    FIRST_NAME TEXT                  NOT NULL,
    LAST_NAME  TEXT                  NOT NULL,
    EMAIL      TEXT                  NOT NULL,
    PHONE      TEXT                  NOT NULL
);
