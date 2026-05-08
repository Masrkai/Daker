## **1. Core Requirements & Intuition**

### **Data Structures**

- **Clubs**: Use `List<Map<String, Object>>` or `List<Record>` to represent clubs.
  - Each club: `name` (String), `branches` (List<String>), `manager` (String), `location` (String), `members` (List<Map<String, Object>>).
- **Members**: Use `List<Map<String, Object>>` or `List<Record>`.
  - Each member: `id` (int), `name` (String), `phone` (String), `numberOfChildren` (int).
- **Sports**: Use `List<Map<String, Object>>` or `List<Record>`.
  - Each sport: `name` (String), `id` (int), `numberOfTeams` (int).

**Intuition**:

- Use **records** (Java 21) for immutable data structures where applicable.
- Use **functions** to manipulate these structures, avoiding classes.

---

## **2. Sorting Algorithms**

Three sorting functions for:

- Clubs by name
- Members by ID
- Sports by name

### **Algorithm Choices**

| Algorithm          | Pros                          | Cons                          | Best For               |
|--------------------|-------------------------------|-------------------------------|------------------------|
| Bubble Sort        | Simple to implement           | O(n²), inefficient for large data | Small datasets         |
| Selection Sort     | Minimizes swaps               | O(n²), still slow             | Small datasets         |
| Merge Sort         | O(n log n), stable            | Requires extra space          | Large datasets         |

**Suggested Combination**:

1. `bubbleSortClubs(List<Map<String, Object>> clubs)`
2. `selectionSortMembers(List<Map<String, Object>> members)`
3. `mergeSortSports(List<Map<String, Object>> sports)`

**Intuition**:

- Implement each algorithm as a **standalone function**.
- Use **lambdas** or **method references** for comparators.

---

## **3. Searching**

- **Binary Search**: Required for searching clubs by name and members by ID.
  - **Prerequisite**: The list must be **sorted** first.
  - Implement:
    - `binarySearchClub(List<Map<String, Object>> clubs, String name)`
    - `binarySearchMember(List<Map<String, Object>> members, int id)`

**Intuition**:

- Binary search reduces time complexity from **O(n)** to **O(log n)**.
- Use **recursion** or **iteration** for implementation.

---

## **4. Big(O) Analysis**

Calculate the time complexity for:

- Sorting algorithms (e.g., Bubble Sort: O(n²), Merge Sort: O(n log n)).
- Binary search: O(log n).
- Overall program complexity (dominated by the slowest operation, likely sorting).

---

## **5. Input/Output (I/O)**

- **User Input**: Use `Scanner` to take input for clubs, members, and sports.

  ```java
  Scanner scanner = new Scanner(System.in);
  System.out.print("Enter club name: ");
  String name = scanner.nextLine();
  ```

- **Insert Initial Data**: Call functions in `main()` to add predefined data.

  ```java
  public static void main(String[] args) {
      List<Map<String, Object>> clubs = new ArrayList<>();
      Map<String, Object> club1 = Map.of(
          "name", "Lions",
          "branches", List.of("Branch1"),
          "manager", "John Doe",
          "location", "New York",
          "members", new ArrayList<>()
      );
      clubs.add(club1);
  }
  ```

---

## **6. GUI (Bonus)**

- Use **JavaFX** for a simple GUI.
- Example components:
  - `Stage` (window)
  - `TextField` (input)
  - `Button` (actions)
  - `TableView` (display data)

**Intuition**:

- Start with a **console-based** version first, then add GUI later.

---

## **7. Project Structure**

```
ClubSystem/
├── src/
│   ├── ClubSystem.java
│   ├── SortingAlgorithms.java
│   ├── BinarySearch.java
│   ├── Main.java
│   └── GUI/ (optional)
├── report.pdf
└── README.md
```

---

## **8. Report Content**

- **Team Members**: IDs, names, classes.
- **Project Description**:
  - Brief overview of the system.
  - Comparison of sorting algorithms (e.g., Bubble Sort vs. Merge Sort in terms of speed, memory, and use cases).
- **Big(O) Analysis**: Explain the complexity of each part.

---

## **9. Example Code Snippets**

### **Club Representation**

```java
record Club(String name, List<String> branches, String manager, String location, List<Map<String, Object>> members) {}
```

### **Bubble Sort for Clubs**

```java
public static void bubbleSortClubs(List<Map<String, Object>> clubs) {
    for (int i = 0; i < clubs.size() - 1; i++) {
        for (int j = 0; j < clubs.size() - i - 1; j++) {
            String name1 = (String) clubs.get(j).get("name");
            String name2 = (String) clubs.get(j + 1).get("name");
            if (name1.compareTo(name2) > 0) {
                Collections.swap(clubs, j, j + 1);
            }
        }
    }
}
```

### **Binary Search for Members**

```java
public static Map<String, Object> binarySearchMember(List<Map<String, Object>> members, int id) {
    int left = 0, right = members.size() - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        int midId = (int) members.get(mid).get("id");
        if (midId == id) {
            return members.get(mid);
        } else if (midId < id) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    return null;
}
```

---

## **10. Testing**

- Test each sorting function with **small and large datasets**.
- Verify binary search returns correct results.
- Edge cases: Empty lists, duplicate names/IDs.
