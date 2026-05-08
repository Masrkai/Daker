# Program Behavior Overview

The system offers two user interfaces:

1. **Console (`Main.java`)** – A text‑based menu that interacts with an SQLite database.
2. **GUI (`ClubManagementGUI.java`)** – A Swing application that uses hard‑coded sample data for sorting/searching demonstrations.

## Common Flow

- Data is represented by three immutable record types: `Club`, `Member`, `Sport`.
- Sorting algorithms operate on the respective in‑memory lists.
- Binary search always requires a pre‑sorted list; the application enforces this by sorting automatically before every search.

## Console Workflow

- The database is the authoritative source of data.
- After every add/remove operation, the in‑memory lists are reloaded from the database.
- Sorting is performed on the in‑memory lists; the database is not modified unless the user explicitly adds/removes entities.
- Statistics are computed using SQL aggregate functions for efficiency.

## GUI Workflow

- Uses static initialisation data from `ClubSystem.initialize…()`.
- No database interaction; ideal for algorithmic demonstration without persistence.
- Sorting and searching are triggered by button clicks; results appear in tables or dialogs.