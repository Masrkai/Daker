-- Clubs table
CREATE TABLE IF NOT EXISTS clubs (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE,
    manager TEXT NOT NULL,
    location TEXT NOT NULL
);

-- Branches table (one-to-many with clubs)
CREATE TABLE IF NOT EXISTS branches (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    club_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    FOREIGN KEY (club_id) REFERENCES clubs(id) ON DELETE CASCADE
);

-- Members table (many-to-one with clubs)
CREATE TABLE IF NOT EXISTS members (
    id INTEGER PRIMARY KEY,
    club_id INTEGER NOT NULL,
    name TEXT NOT NULL,
    phone TEXT,
    number_of_children INTEGER DEFAULT 0,
    FOREIGN KEY (club_id) REFERENCES clubs(id) ON DELETE CASCADE
);

-- Sports table
CREATE TABLE IF NOT EXISTS sports (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    number_of_teams INTEGER NOT NULL DEFAULT 0
);

-- Club-Sport association (many-to-many)
CREATE TABLE IF NOT EXISTS club_sports (
    club_id INTEGER NOT NULL,
    sport_id INTEGER NOT NULL,
    PRIMARY KEY (club_id, sport_id),
    FOREIGN KEY (club_id) REFERENCES clubs(id) ON DELETE CASCADE,
    FOREIGN KEY (sport_id) REFERENCES sports(id) ON DELETE CASCADE
);