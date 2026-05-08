# Main.java

Console entry point for the Club Management System. It uses `DatabaseHandler` as the single source of truth for persistent data.

## Workflow

1. Load all clubs, members, and sports from the SQLite database.
2. Display an interactive menu loop.
3. Execute the chosen operation (display, sort, search, add, remove, statistics).
4. Sorting algorithms are called on the in‑memory lists; after sorting, the lists are reflected in the console.
5. Add/Remove operations update the database **and** refresh the in‑memory lists to stay synchronised.

## Menu Options

| Option | Action |
|--------|--------|
| 1 | Display all data (clubs, members, sports). |
| 2 | Sort clubs by name using Bubble Sort. |
| 3 | Sort members by ID using Selection Sort. |
| 4 | Sort sports by name using Merge Sort. |
| 5 | Search for a club by name (auto‑sorts first). |
| 6 | Search for a member by ID (auto‑sorts first). |
| 7 | Add a new club (persisted to DB). |
| 8 | Add a new member to a specific club (persisted to DB). |
| 9 | Add a new sport (persisted to DB). |
| 10 | Remove a member from a club (persisted to DB). |
| 11 | Display system statistics (using DB aggregate functions). |
| 0 | Exit. |

## Sorting & Searching Flow

- Each sort operation prints the list before and after, along with complexity information.
- Each search automatically performs the required sort (Bubble Sort for clubs, Selection Sort for members) and then performs Binary Search. The execution time is displayed.
- The Merge Sort function returns a new sorted list; the global `sports` reference is updated accordingly.

## Design Choices

- Database operations are isolated in `DatabaseHandler` (Singleton).
- After every mutating DB operation (`addClub`, `addMemberToClub`, …), the in‑memory lists are reloaded from the database to guarantee consistency.
- Statistics (total members, children, teams) are queried directly via SQL for efficiency.
# Main.java

Console entry point for the Club Management System. It uses `DatabaseHandler` as the single source of truth for persistent data.

## Workflow

1. Load all clubs, members, and sports from the SQLite database.
2. Display an interactive menu loop.
3. Execute the chosen operation (display, sort, search, add, remove, statistics).
4. Sorting algorithms are called on the in‑memory lists; after sorting, the lists are reflected in the console.
5. Add/Remove operations update the database **and** refresh the in‑memory lists to stay synchronised.

## Menu Options

| Option | Action |
|--------|--------|
| 1 | Display all data (clubs, members, sports). |
| 2 | Sort clubs by name using Bubble Sort. |
| 3 | Sort members by ID using Selection Sort. |
| 4 | Sort sports by name using Merge Sort. |
| 5 | Search for a club by name (auto‑sorts first). |
| 6 | Search for a member by ID (auto‑sorts first). |
| 7 | Add a new club (persisted to DB). |
| 8 | Add a new member to a specific club (persisted to DB). |
| 9 | Add a new sport (persisted to DB). |
| 10 | Remove a member from a club (persisted to DB). |
| 11 | Display system statistics (using DB aggregate functions). |
| 0 | Exit. |

## Sorting & Searching Flow

- Each sort operation prints the list before and after, along with complexity information.
- Each search automatically performs the required sort (Bubble Sort for clubs, Selection Sort for members) and then performs Binary Search. The execution time is displayed.
- The Merge Sort function returns a new sorted list; the global `sports` reference is updated accordingly.

## Design Choices

- Database operations are isolated in `DatabaseHandler` (Singleton).
- After every mutating DB operation (`addClub`, `addMemberToClub`, …), the in‑memory lists are reloaded from the database to guarantee consistency.
- Statistics (total members, children, teams) are queried directly via SQL for efficiency.
