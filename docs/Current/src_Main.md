# Main.java

Console entry point for the Club Management System. Uses `ClubManagementService` as the single access layer for all data operations, which in turn persists everything in the SQLite database.

## Workflow

1. Instantiate the service (`ClubManagementService`) – automatically initialises the database via `DatabaseHandler`.
2. Load clubs, members, and sports from the database **through the service**.
3. Display an interactive menu loop.
4. Execute the chosen operation (display, sort, search, add, remove, statistics).
5. All mutation actions (add club, add member, etc.) call the corresponding service methods, which update the database. After every mutation, the in‑memory lists are reloaded from the service to stay synchronised.
6. Sorting algorithms operate on the in‑memory lists; they do not modify the database.

## Menu Options

| Option | Action                                                    |
|--------|-----------------------------------------------------------|
| 1      | Display all data (clubs, members, sports).                |
| 2      | Sort clubs by name using Bubble Sort.                     |
| 3      | Sort members by ID using Selection Sort.                  |
| 4      | Sort sports by name using Merge Sort.                     |
| 5      | Search for a club by name (auto‑sorts first).             |
| 6      | Search for a member by ID (auto‑sorts first).             |
| 7      | Add a new club (persisted to DB via service).             |
| 8      | Add a new member to a specific club (persisted via service). |
| 9      | Add a new sport (persisted to DB via service).            |
| 10     | Remove a member from a club (persisted to DB via service).|
| 11     | Display system statistics (using service‑aggregated data).|
| 0      | Exit.                                                     |

## Sorting & Searching Flow

- Each sort operation prints the list before and after, along with complexity information.
- Each search automatically performs the required sort (Bubble Sort for clubs, Selection Sort for members) and then performs Binary Search. The execution time is displayed.
- The Merge Sort function returns a new sorted list; the global `sports` reference is updated accordingly.

## Design Choices

- All persistence is delegated to `ClubManagementService`, which internally uses the `DatabaseHandler` singleton.
- After every mutating operation, the in‑memory lists are refreshed by calling `service.getAllClubs()`, `service.getAllMembers()`, and `service.getAllSports()` to guarantee consistency.
- Statistics are retrieved via the service’s `getStatistics()` method, which uses database aggregate functions for efficiency.
- The command‑line interface remains completely independent of the GUI, but both share the exact same service layer and database backend.

\newpage