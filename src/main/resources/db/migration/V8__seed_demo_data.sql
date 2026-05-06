-- =====================================================
-- Seed habitats
-- =====================================================

INSERT INTO habitats (biome, location, min_temp_c, max_temp_c)
SELECT 'Volcanic Rift', 'Sector A-1', 35, 90
WHERE NOT EXISTS (
    SELECT 1 FROM habitats WHERE biome = 'Volcanic Rift' AND location = 'Sector A-1'
);

INSERT INTO habitats (biome, location, min_temp_c, max_temp_c)
SELECT 'Frozen Tundra', 'Sector B-4', -50, 5
WHERE NOT EXISTS (
    SELECT 1 FROM habitats WHERE biome = 'Frozen Tundra' AND location = 'Sector B-4'
);

INSERT INTO habitats (biome, location, min_temp_c, max_temp_c)
SELECT 'Crystal Marsh', 'Sector C-2', 10, 35
WHERE NOT EXISTS (
    SELECT 1 FROM habitats WHERE biome = 'Crystal Marsh' AND location = 'Sector C-2'
);

INSERT INTO habitats (biome, location, min_temp_c, max_temp_c)
SELECT 'Sky Canopy', 'Sector D-7', 15, 45
WHERE NOT EXISTS (
    SELECT 1 FROM habitats WHERE biome = 'Sky Canopy' AND location = 'Sector D-7'
);

-- =====================================================
-- Seed creatures
-- =====================================================

INSERT INTO creatures (name, species, danger_level, condition, notes, status, habitat_id)
VALUES
    ('Shadowfang', 'Ash Drake', 5, 'WATCH', 'Aggressive near feeding time.', 'ACTIVE',
     (SELECT id FROM habitats WHERE biome = 'Volcanic Rift' LIMIT 1)),

    ('Emberclaw', 'Lava Stalker', 4, 'STABLE', 'Responds well to thermal enrichment.', 'ACTIVE',
     (SELECT id FROM habitats WHERE biome = 'Volcanic Rift' LIMIT 1)),

    ('Frostmaw', 'Glacier Beast', 5, 'CRITICAL', 'Requires constant temperature monitoring.', 'ACTIVE',
     (SELECT id FROM habitats WHERE biome = 'Frozen Tundra' LIMIT 1)),

    ('Snowveil', 'Arctic Phantom', 3, 'WATCH', 'Avoids direct light exposure.', 'ACTIVE',
     (SELECT id FROM habitats WHERE biome = 'Frozen Tundra' LIMIT 1)),

    ('Mireblink', 'Marsh Sprite', 2, 'STABLE', 'Highly curious around staff.', 'ACTIVE',
     (SELECT id FROM habitats WHERE biome = 'Crystal Marsh' LIMIT 1)),

    ('Glassjaw', 'Crystal Croaker', 3, 'QUARANTINE', 'Possible spore contamination.', 'ACTIVE',
     (SELECT id FROM habitats WHERE biome = 'Crystal Marsh' LIMIT 1)),

    ('Stormwing', 'Tempest Roc', 4, 'WATCH', 'Wing injuries improving.', 'ACTIVE',
     (SELECT id FROM habitats WHERE biome = 'Sky Canopy' LIMIT 1)),

    ('Cloudsnap', 'Aerial Serpent', 2, 'STABLE', 'Calm during morning checks.', 'ACTIVE',
     (SELECT id FROM habitats WHERE biome = 'Sky Canopy' LIMIT 1))
ON CONFLICT (habitat_id, name) DO NOTHING;

-- =====================================================
-- Seed roles
-- =====================================================

INSERT INTO roles (name)
VALUES
    ('ADMIN'),
    ('CARETAKER'),
    ('VETERINARIAN'),
    ('SECURITY'),
    ('RESEARCHER')
ON CONFLICT (name) DO NOTHING;

-- =====================================================
-- Seed users
-- =====================================================

INSERT INTO users (full_name, email, phone)
VALUES
    ('Avery Stone', 'avery.stone@neonark.org', '555-0101'),
    ('Mira Chen', 'mira.chen@neonark.org', '555-0102'),
    ('Jonah Reyes', 'jonah.reyes@neonark.org', '555-0103'),
    ('Seth Fairburn', 'seth.fairburn@neonark.org', '555-0104')
ON CONFLICT (email) DO NOTHING;

-- =====================================================
-- Seed user roles
-- =====================================================

INSERT INTO user_roles (user_id, role_id)
VALUES
    ((SELECT id FROM users WHERE email = 'avery.stone@neonark.org'), (SELECT id FROM roles WHERE name = 'ADMIN')),
    ((SELECT id FROM users WHERE email = 'mira.chen@neonark.org'), (SELECT id FROM roles WHERE name = 'VETERINARIAN')),
    ((SELECT id FROM users WHERE email = 'jonah.reyes@neonark.org'), (SELECT id FROM roles WHERE name = 'CARETAKER')),
    ((SELECT id FROM users WHERE email = 'seth.fairburn@neonark.org'), (SELECT id FROM roles WHERE name = 'SECURITY'))
ON CONFLICT (user_id, role_id) DO NOTHING;

-- =====================================================
-- Seed feeding schedules
-- =====================================================

INSERT INTO feeding_schedules (creature_id, feeding_time)
SELECT (SELECT id FROM creatures WHERE name = 'Shadowfang' LIMIT 1), '08:00'
WHERE NOT EXISTS (
    SELECT 1 FROM feeding_schedules
    WHERE creature_id = (SELECT id FROM creatures WHERE name = 'Shadowfang' LIMIT 1)
      AND feeding_time = '08:00'
);

INSERT INTO feeding_schedules (creature_id, feeding_time)
SELECT (SELECT id FROM creatures WHERE name = 'Frostmaw' LIMIT 1), '08:00'
WHERE NOT EXISTS (
    SELECT 1 FROM feeding_schedules
    WHERE creature_id = (SELECT id FROM creatures WHERE name = 'Frostmaw' LIMIT 1)
      AND feeding_time = '08:00'
);

INSERT INTO feeding_schedules (creature_id, feeding_time)
SELECT (SELECT id FROM creatures WHERE name = 'Stormwing' LIMIT 1), '14:00'
WHERE NOT EXISTS (
    SELECT 1 FROM feeding_schedules
    WHERE creature_id = (SELECT id FROM creatures WHERE name = 'Stormwing' LIMIT 1)
      AND feeding_time = '14:00'
);

INSERT INTO feeding_schedules (creature_id, feeding_time)
SELECT (SELECT id FROM creatures WHERE name = 'Mireblink' LIMIT 1), '18:30'
WHERE NOT EXISTS (
    SELECT 1 FROM feeding_schedules
    WHERE creature_id = (SELECT id FROM creatures WHERE name = 'Mireblink' LIMIT 1)
      AND feeding_time = '18:30'
);

-- =====================================================
-- Seed observations
-- observations now uses user_id, not author_name
-- =====================================================

INSERT INTO observations (creature_id, user_id, note, observed_at)
SELECT
    (SELECT id FROM creatures WHERE name = 'Shadowfang' LIMIT 1),
    (SELECT id FROM users WHERE email = 'mira.chen@neonark.org' LIMIT 1),
    'Displayed heightened alertness during morning feeding.',
    '2026-05-01 08:15:00'
WHERE NOT EXISTS (
    SELECT 1 FROM observations WHERE note = 'Displayed heightened alertness during morning feeding.'
);

INSERT INTO observations (creature_id, user_id, note, observed_at)
SELECT
    (SELECT id FROM creatures WHERE name = 'Frostmaw' LIMIT 1),
    (SELECT id FROM users WHERE email = 'avery.stone@neonark.org' LIMIT 1),
    'Temperature fluctuation noted near enclosure gate.',
    '2026-05-01 09:30:00'
WHERE NOT EXISTS (
    SELECT 1 FROM observations WHERE note = 'Temperature fluctuation noted near enclosure gate.'
);

INSERT INTO observations (creature_id, user_id, note, observed_at)
SELECT
    (SELECT id FROM creatures WHERE name = 'Stormwing' LIMIT 1),
    (SELECT id FROM users WHERE email = 'jonah.reyes@neonark.org' LIMIT 1),
    'Wing mobility improved after habitat adjustment.',
    '2026-05-02 14:45:00'
WHERE NOT EXISTS (
    SELECT 1 FROM observations WHERE note = 'Wing mobility improved after habitat adjustment.'
);

INSERT INTO observations (creature_id, user_id, note, observed_at)
SELECT
    (SELECT id FROM creatures WHERE name = 'Glassjaw' LIMIT 1),
    (SELECT id FROM users WHERE email = 'seth.fairburn@neonark.org' LIMIT 1),
    'Quarantine protocols maintained. No new symptoms observed.',
    '2026-05-03 11:20:00'
WHERE NOT EXISTS (
    SELECT 1 FROM observations WHERE note = 'Quarantine protocols maintained. No new symptoms observed.'
);