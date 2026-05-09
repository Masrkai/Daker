# ClubManagementGUI.java

A Swing‑based graphical interface that displays clubs, members, and sports in sortable tables. It uses the same `ClubManagementService` as the CLI, meaning all data is read from (and written to) the SQLite database.

## Structure

- **JTabbedPane** with three tabs:
  - **Clubs** – table and controls for Bubble Sort, Binary Search, add club, and member management.
  - **Members** – table with Selection Sort, Binary Search, and refresh.
  - **Sports** – table with Merge Sort and add sport.
- Each tab has a `JTable` backed by a `DefaultTableModel`.
- Data is loaded from the service (**not** from hard‑coded sample data). The service fetches the persisted data from the database.

## Controls

| Tab     | Buttons                             | Action                                                                                         |
|-------------|-------------------------------------|------------------------------------------------------------------------------------------------|
| Clubs   | "Add Club"                          | Opens a dialog; adds a club to the database and refreshes the table.                           |
|         | "Sort (Bubble)"                     | Sorts the clubs list in‑place using Bubble Sort and refreshes the view.                        |
|         | "Search Club"                       | Sorts clubs first, then prompts for a name and displays the result via `BinarySearch`.         |
|         | "Add Member to Club"                | Adds a member to the selected club (persisted to database) and refreshes all tables.           |
|         | "Remove Member"                     | Removes a member by ID from the selected club and refreshes.                                   |
| Members | "Sort by ID"                        | Sorts all members by ID using Selection Sort, then refreshes the table.                        |
|         | "Search by ID"                      | Sorts first, then prompts for an ID and shows the found member.                                |
|         | "Refresh"                           | Reloads members from the database via the service.                                             |
| Sports  | "Add Sport"                         | Adds a new sport (persisted) and refreshes.                                                    |
|         | "Sort (Merge)"                      | Applies Merge Sort to the sports list, updates the view.                                       |

## Important Notes

- The GUI **now uses `ClubManagementService`** just like the CLI. All add/remove operations are persisted in the SQLite database.
- Sorting is performed on the in‑memory lists obtained from the service; no sorting changes are written to the database.
- The Merge Sort returns a new list; the `sports` field is reassigned to the sorted list before refreshing the table.
- A "Show Statistics" button at the bottom opens a dialog with aggregate system statistics, computed via the service.

\newpage