ALTER TABLE observations
    ADD COLUMN user_id BIGINT;

ALTER TABLE observations
    ADD CONSTRAINT fk_observations_user
        FOREIGN KEY (user_id)
            REFERENCES users(id);