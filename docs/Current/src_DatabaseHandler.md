# DatabaseHandler.java

Singleton class that manages all SQLite operations for `example.db`. It automatically creates and populates the database on first run using `schema.sql` and `data.sql`.

## Connection

- JDBC URL: `jdbc:sqlite:example.db`
- Auto‑commit mode is enabled.

## Initialisation

- On first start, the class checks if the `clubs` table exists.
- If not, it executes `schema.sql` and `data.sql` from the classpath (`/db/schema.sql`, `/db/data.sql`).

## Public Methods

| Method                                                | Returns        | Description                               |
|-------------------------------------------------------|----------------|-------------------------------------------|
| `getAllClubs()`                                       | `List<Club>`   | Fetches clubs with branches and members.  |
| `addClub(Club)`                                       | `boolean`      | Inserts club and its branches.            |
| `getAllMembers()`                                     | `List<Member>` | Returns all members (regardless of club). |
| `addMemberToClub(String clubName, Member)`            | `boolean`      | Inserts a member linked to a club.        |
| `removeMemberFromClub(String clubName, int memberId)` | `boolean`      | Deletes the member.                       |
| `getAllSports()`                                      | `List<Sport>`  | Retrieves all sports.                     |
| `addSport(Sport)`                                     | `boolean`      | Inserts a sport.                          |
| `getTotalMemberCount()`                               | `int`          | Aggregate query `COUNT(*)`.               |
| `getTotalChildren()`                                  | `int`          | `SUM(number_of_children)`.                |
| `getTotalTeams()`                                     | `int`          | `SUM(number_of_teams)`.                   |
| `close()`                                             | void           | Closes the connection.                    |

## Design

- Uses **Prepared Statements** to prevent SQL injection.
- The Singleton pattern ensures only one database connection is used throughout the application.
- The `club_sports` table is created but currently not exposed via any public method (exists for future use).

\newpage