## **ClubSystem/src/ClubSystem.java**

- **Data Structures**:
  - Define `record` types for `Club`, `Member`, and `Sport`.
  - Example:

    ```java
    record Club(String name, List<String> branches, String manager, String location, List<Member> members) {}
    record Member(int id, String name, String phone, int numberOfChildren) {}
    record Sport(String name, int id, int numberOfTeams) {}
    ```

- **Initialization Functions**:
  - `List<Club> initializeClubs()`
  - `List<Member> initializeMembers()`
  - `List<Sport> initializeSports()`

---

## **ClubSystem/src/SortingAlgorithms.java**

- **Sorting Functions**:
  - `void bubbleSortClubs(List<Club> clubs)`
  - `void selectionSortMembers(List<Member> members)`
  - `List<Sport> mergeSortSports(List<Sport> sports)`
- **Comparator Logic**:
  - Use lambdas for custom comparisons (e.g., `Comparator.comparing(Club::name)`).

---

## **ClubSystem/src/BinarySearch.java**

- **Search Functions**:
  - `Club binarySearchClub(List<Club> clubs, String name)`
  - `Member binarySearchMember(List<Member> members, int id)`
- **Prerequisite**:
  - Ensure input lists are sorted before calling these functions.

---

## **ClubSystem/src/Main.java**

- **Entry Point**:
  - `main()` method to:
    - Initialize data using functions from `ClubSystem.java`.
    - Call sorting and searching functions.
    - Handle user input via `Scanner`.
- **Example Workflow**:

  ```java
  public static void main(String[] args) {
      List<Club> clubs = initializeClubs();
      bubbleSortClubs(clubs);
      Club foundClub = binarySearchClub(clubs, "Lions");
      System.out.println(foundClub);
  }
  ```

---

## **ClubSystem/src/GUI/ClubSystemGUI.java** *(Optional)*

- **JavaFX Components**:
  - `Stage`, `Scene`, `TableView`, `Button`, `TextField`.
- **Functions**:
  - `void showClubsTable(List<Club> clubs)`
  - `void setupSearchHandlers()`
- **Data Binding**:
  - Use `ObservableList` for dynamic updates.

---

## **ClubSystem/report.pdf**

- **Content**:
  - Team details.
  - System overview.
  - Sorting algorithm comparisons (time/space complexity).
  - Big(O) analysis for each operation.

---

## **ClubSystem/README.md**

- **Project Structure Overview**.
- **How to Run**:
  - Compile and execute instructions.
  - Example:

    ```
    javac src/*.java
    java src.Main
    ```

- **Dependencies** (if any, e.g., JavaFX for GUI).
