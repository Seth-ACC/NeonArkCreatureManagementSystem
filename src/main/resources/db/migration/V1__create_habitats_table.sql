CREATE TABLE habitats (
      id SERIAL PRIMARY KEY,
      biome VARCHAR(100) NOT NULL,
      location VARCHAR(100),
      min_temp_c INT,
      max_temp_c INT
);