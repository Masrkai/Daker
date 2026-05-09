# BinarySearch.java

Implements both iterative and recursive binary search for clubs and members. Lists **must** be sorted before calling any search method.

## Club Search

```java
public static Club binarySearchClub(List<Club> clubs, String name)
// Recursive: binarySearchClubRecursive(List<Club>, String)
```

- Searches for a club by `name` (case‑insensitive).
- Time: O(log n), Space: O(1) iterative / O(log n) recursive (due to call stack).

## Member Search

```java
public static Member binarySearchMember(List<Member> members, int id)
// Recursive: binarySearchMemberRecursive(List<Member>, int)
```

- Searches for a member by integer `id`.
- Time: O(log n), Space: O(1) / O(log n) recursive.

## Usage Precondition

In the application, the appropriate sorting algorithm is called automatically before a binary search:

- Club search → `bubbleSortClubs(clubs)`
- Member search → `selectionSortMembers(members)`

This ensures the prerequisite is always met.


\newpage