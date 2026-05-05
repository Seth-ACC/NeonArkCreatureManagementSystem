CREATE TABLE creatures (
       id BIGSERIAL PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       species VARCHAR(100) NOT NULL,
       danger_level INT NOT NULL,
       condition VARCHAR(50) NOT NULL,
       notes TEXT,
       status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
       habitat_id BIGINT NOT NULL,

       CONSTRAINT fk_creatures_habitat
           FOREIGN KEY (habitat_id)
               REFERENCES habitats(id),

       CONSTRAINT uq_creature_name_per_habitat
           UNIQUE (habitat_id, name),

       CONSTRAINT chk_creature_danger_level
           CHECK (danger_level BETWEEN 1 AND 5),

       CONSTRAINT chk_creature_status
           CHECK (status IN ('ACTIVE', 'REMOVED')),

       CONSTRAINT chk_creature_condition
           CHECK (condition IN ('STABLE', 'WATCH', 'CRITICAL', 'QUARANTINE'))
);