# ClubManagementService.java

Service layer that encapsulates all business operations for clubs, members, and sports. It is the single entry point for both the CLI (`Main.java`) and the GUI (`ClubManagementGUI.java`), guaranteeing consistent behaviour regardless of the user interface.

## Responsibility

- Translates high‑level actions (e.g., “add a club”) into calls on the persistence layer (`DatabaseHandler`).
- Provides the data to the presentation layers in ready‑to‑use `List` form.
- Shields the UI from direct database access and implementation details.

## Initialisation

- Creates a private instance of `DatabaseHandler` via its singleton method.
- The database is initialised automatically the first time `DatabaseHandler.getInstance()` is called.

## Public Methods

### Club Operations

| Method                                                  | Returns      | Description                                                                 |
|---------------------------------------------------------|-------------------|-----------------------------------------------------------------------------|
| `addClub(String name, String manager, String location)` | `boolean`    | Creates a club with an empty member list and no branches, then persists it. |
| `getAllClubs()`                                         | `List<Club>` | Retrieves all clubs (with their branches and members) from the database.    |

### Member Operations

| Method                                                                              | Returns        | Description                                                    |
|-------------------------------------------------------------------------------------|-------------------------|----------------------------------------------------------------|
| `addMemberToClub(String clubName, int id, String name, String phone, int children)` | `boolean`      | Adds a new member to the specified club.                       |
| `removeMemberFromClub(String clubName, int memberId)`                               | `boolean`      | Removes the member with the given ID from the club.            |
| `getAllMembers()`                                                                   | `List<Member>` | Returns all members stored in the database (across all clubs). |

### Sport Operations

| Method                                     | Returns       | Description                           |
|--------------------------------------------|---------------|---------------------------------------|
| `addSport(String name, int id, int teams)` | `boolean`     | Persists a new sport.                 |
| `getAllSports()`                           | `List<Sport>` | Fetches all sports from the database. |

### Statistics

| Method            | Returns            | Description                                                                                             |
|-----------------------------|--------------------------------|---------------------------------------------------------------------------------------------------------|
| `getStatistics()` | `SystemStatistics` | Computes aggregated data: total clubs, members, sports, children, teams, and per‑member/sport averages. |

The `SystemStatistics` record contains:

- `totalClubs`, `totalMembers`, `totalSports`
- `totalChildren`, `avgChildrenPerMember`
- `totalTeams`, `avgTeamsPerSport`

## Design Rationale

- **Single Responsibility:** The service layer owns business logic; the database layer owns persistence. This separation makes the code easier to maintain and test.
- **Shared State:** Both CLI and GUI obtain data through the same `ClubManagementService` instance, so changes made in one interface are immediately visible in the other (uses the same underlying database).
- **Thin Wrapper:** The service mostly delegates calls directly to `DatabaseHandler`. This keeps it simple while still providing a clean API for the UI.

\newpage