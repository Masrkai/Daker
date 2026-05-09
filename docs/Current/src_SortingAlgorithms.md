# SortingAlgorithms.java

Provides static sorting methods for clubs, members, sports, and a generic set of algorithms.

## Club Sorting – Bubble Sort

```java
public static void bubbleSortClubs(List<Club> clubs)
```

- Time: O(n²), Space: O(1)
- Uses `compareToIgnoreCase` for case‑insensitive lexicographic order.
- Includes an early‑exit optimisation: if no swaps occur in a pass, the list is already sorted.

## Member Sorting – Selection Sort

```java
public static void selectionSortMembers(List<Member> members)
```

- Time: O(n²), Space: O(1)
- Finds the minimum `id` in the unsorted portion and swaps it with the first unsorted element.
- Always performs exactly n−1 swaps (worst‑case memory writes are better than Bubble Sort).

## Sport Sorting – Merge Sort

```java
public static List<Sport> mergeSortSports(List<Sport> sports)
```

- Time: O(n log n), Space: O(n)
- Returns a **new** sorted list (does not mutate the original, though internally works on a copy).
- Stable sort: preserves relative order of equal elements.

## Generic Implementations

The class also offers generic methods that accept a `Comparator<? super T>`:

| Method                               | Description                        |
|--------------------------------------|------------------------------------|
| `bubbleSort(List<T>, Comparator)`    | In‑place bubble sort.              |
| `selectionSort(List<T>, Comparator)` | In‑place selection sort.           |
| `mergeSort(List<T>, Comparator)`     | Returns a new sorted list, stable. |

These can be used for custom sorting beyond the predefined use cases.

\newpage