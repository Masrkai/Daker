# ClubManagementGUI.java

A Swing‑based graphical interface that displays clubs, members, and sports in sortable tables.

## Structure

- **JTabbedPane** with three tabs:
  - **Clubs** – table and controls for Bubble Sort & Binary Search.
  - **Members** – table with Selection Sort & Binary Search.
  - **Sports** – table with Merge Sort.
- Each tab has a `JTable` backed by a `DefaultTableModel`.
- Data is initially loaded from `ClubSystem.initialize…()` methods (hard‑coded samples), not from the database.

## Controls

| Tab      | Buttons | Action |
|----------|---------|--------|
| Clubs    | "Sort by Name (Bubble Sort)" | Sorts the clubs list in‑place and refreshes the table. |
|          | "Search by Name (Binary Search)" | Sorts first, then prompts for a name; shows result in a dialog. |
| Members  | "Sort by ID (Selection Sort)" | Sorts members by ID. |
|          | "Search by ID (Binary Search)" | Sorts first, prompts for an ID, displays the found member. |
| Sports   | "Sort by Name (Merge Sort)" | Sorts sports and updates the table. |

## Important Notes

- The GUI does **not** interact with the database; it uses the static sample data for demonstration.
- Sorting is performed directly on the lists obtained from `ClubSystem.initialize…()`, so changes are reflected immediately in the GUI.
- The Merge Sort returns a new list; the `sports` field is reassigned to the new sorted list.
