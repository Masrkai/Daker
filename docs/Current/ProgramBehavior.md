# Program Behavior Overview

The system offers two user interfaces:

1. **Console (`Main.java`)** – A text‑based menu.
2. **GUI (`ClubManagementGUI.java`)** – A Swing application.

Both interfaces share the same business logic through `ClubManagementService`, which handles all persistent data operations via an SQLite database. This guarantees identical behaviour regardless of the interface used.

## Common Flow

- Data is represented by three immutable record types: `Club`, `Member`, `Sport`.
- `ClubManagementService` acts as the single point of access for reading and writing data; it internally delegates to `DatabaseHandler`.
- Sorting algorithms operate on the respective in‑memory lists obtained from the service.
- Binary search always requires a pre‑sorted list; the application enforces this by sorting automatically before every search.

## Console Workflow

- The database is the authoritative source of data.
- After every add/remove operation, the in‑memory lists are reloaded from the service.
- Sorting is performed on the in‑memory lists; the database is not modified unless the user explicitly adds/removes entities.
- Statistics are computed using service‑aggregated data.

## GUI Workflow

- Data is loaded from the database via the service on startup and after every mutation.
- Sorting and searching are triggered by button clicks; results appear in tables or dialogs.
- The GUI includes additional controls for adding clubs, members, and sports, all of which are persisted immediately through the service.

\newpage