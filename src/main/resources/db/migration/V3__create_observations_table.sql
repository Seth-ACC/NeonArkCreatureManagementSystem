CREATE TABLE observations (
              id BIGSERIAL PRIMARY KEY,

              creature_id BIGINT NOT NULL,
              author_name VARCHAR(100) NOT NULL,
              note TEXT NOT NULL,
              observed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

              CONSTRAINT fk_observations_creature
                  FOREIGN KEY (creature_id)
                      REFERENCES creatures(id)
);