# Database Schema

The SQLite database `example.db` contains four core tables plus an association table.

## PlantUML Diagram

```plantuml
@startuml
!define table(x) class x << (T,#FFAAAA) >>
!define primary_key(x) <b>x</b>
!define foreign_key(x) <i>x</i>

table(clubs) {
  primary_key(id) : INTEGER
  name : TEXT NOT NULL UNIQUE
  manager : TEXT NOT NULL
  location : TEXT NOT NULL
}

table(branches) {
  primary_key(id) : INTEGER
  foreign_key(club_id) : INTEGER NOT NULL
  name : TEXT NOT NULL
}

table(members) {
  primary_key(id) : INTEGER
  foreign_key(club_id) : INTEGER NOT NULL
  name : TEXT NOT NULL
  phone : TEXT
  number_of_children : INTEGER DEFAULT 0
}

table(sports) {
  primary_key(id) : INTEGER
  name : TEXT NOT NULL UNIQUE
  number_of_teams : INTEGER NOT NULL DEFAULT 0
}

table(club_sports) {
  primary_key(club_id) : INTEGER NOT NULL
  primary_key(sport_id) : INTEGER NOT NULL
}

clubs ||--o{ branches : "has"
clubs ||--o{ members : "has"
clubs ||--o{ club_sports : ""
sports ||--o{ club_sports : ""
@enduml
```

## Table Descriptions

### clubs

- `id` – Auto‑increment primary key.
- `name` – Unique club name (e.g., "Lions").
- `manager`, `location` – Text fields.

### branches

- Linked to `clubs` via `club_id` (foreign key with CASCADE delete).
- Multiple branches per club.

### members

- `id` is a user‑provided primary key (not auto‑incremented).
- `club_id` foreign key to clubs.
- `number_of_children` defaults to 0.

### sports

- `id` is user‑provided.
- `name` unique.
- `number_of_teams` integer.

### club_sports

- Many‑to‑many relationship between clubs and sports.
- Composite primary key (`club_id`, `sport_id`).
- No extra attributes.

## SQL Files

- **`schema.sql`**: Contains `CREATE TABLE IF NOT EXISTS` statements for all tables.
- **`data.sql`**: Inserts sample clubs, branches, members, sports, and club‑sport associations.
