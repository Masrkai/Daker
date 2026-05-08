# ClubSystem.java

Defines the core data structures using Java records and provides utility methods for initialisation and in‑memory list manipulation.

## Records

- `Club(String name, List<String> branches, String manager, String location, List<Member> members)`
  Immutable representation of a club, including its branches and members.
- `Member(int id, String name, String phone, int numberOfChildren)`
- `Sport(String name, int id, int numberOfTeams)`

## Factory Methods

| Method | Description |
|--------|-------------|
| `initializeClubs()` | Creates five sample clubs (Lions, Tigers, Eagles, Sharks, Bears) with predefined members. |
| `initializeMembers()` | Returns a flat list of all sample members across clubs. |
| `initializeSports()` | Returns seven sports (Basketball, Football, Swimming, …). |

## Utility Functions

| Function | Complexity | Description |
|----------|------------|-------------|
| `displayClubs(List<Club>)` | O(n) | Prints formatted club details. |
| `displayMembers(List<Member>)` | O(n) | Prints member list. |
| `displaySports(List<Sport>)` | O(n) | Prints sport list. |
| `addMemberToClub(List<Club>, String clubName, Member)` | O(c) | Adds a member to a club (creates a new immutable `Club` instance). |
| `addClub(List<Club>, Club)` | O(1) | Appends a new club. |
| `addSport(List<Sport>, Sport)` | O(1) | Appends a new sport. |
| `removeMemberFromClub(List<Club>, String clubName, int memberId)` | O(c + m) | Removes a member from the matching club. |
| `findMemberById(List<Club>, int memberId)` | O(c × m) | Searches all clubs for a member. |
| `getTotalMemberCount(List<Club>)` | O(c) | Sums member counts. |
| `getAllMembers(List<Club>)` | O(c × m) | Flattens all club members into one list. |

These functions are used by the CLI (`Main.java`) when the database is not involved. The GUI and `Main.java` with the database use the real persistence layer (`DatabaseHandler`).

> **Note:** All mutation functions work with immutable records by creating new instances and replacing the original in the list.
