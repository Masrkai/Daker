# Algorithm Complexity Analysis


## Sorting Algorithms

####  Bubble Sort – Sorting Clubs by Name

- **Method:** `SortingAlgorithms.bubbleSortClubs(List<Club> clubs)`
- **Use case:** Sorts the list of clubs alphabetically (case‑insensitive) by their names.
- **Algorithm description:**
  Repeatedly steps through the list, compares adjacent elements, and swaps them if they are in the wrong order. The largest unsorted element “bubbles up” to its correct position after each pass. An optimisation stops early if a full pass makes no swaps, indicating the list is already sorted.

- **Time Complexity:**
  - **Best case:** \(O(n)\) – when the list is already sorted. The early‑exit flag (`swapped == false`) causes only one pass over \(n\) elements.
  - **Average case:** \(O(n^2)\) – on random data, about \(\frac{n(n-1)}{4}\) comparisons and swaps are needed.
  - **Worst case:** \(O(n^2)\) – when the list is sorted in reverse order, requiring \(\frac{n(n-1)}{2}\) comparisons and swaps.

- **Space Complexity:** \(O(1)\)
  The algorithm operates in‑place, only using a few temporary variables.

- **Justification:**
  Bubble Sort was used because clubs are a relatively small dataset and the simplicity of the algorithm serves to illustrate a basic \(O(n^2)\) sort. The in‑place, stable nature is not critical here, but the early‑exit optimisation helps when the list is nearly sorted.

#### Selection Sort – Sorting Members by ID

- **Method:** `SortingAlgorithms.selectionSortMembers(List<Member> members)`
- **Use case:** Sorts the global member list by their numeric ID.
- **Algorithm description:**
  Divides the list into a sorted prefix and an unsorted suffix. In each iteration, it scans the unsorted portion to find the smallest element and swaps it with the first element of the unsorted part.

- **Time Complexity:**
  - **Best, average, and worst case:** \(O(n^2)\)
    The number of comparisons is always \(\frac{n(n-1)}{2}\), regardless of the initial order. Swaps are at most \(n\).

- **Space Complexity:** \(O(1)\)
  Sorting is performed in‑place; only a few index variables are needed.

- **Justification:**
  Selection Sort is another \(O(n^2)\) algorithm that contrasts with Bubble Sort by performing fewer writes (at most \(n-1\) swaps). It was assigned to members because the member list may not benefit from an early exit, and the constant overhead remains low for the expected data sizes.

\newpage

#### Merge Sort – Sorting Sports by Name

- **Method:** `SortingAlgorithms.mergeSortSports(List<Sport> sports)`
- **Use case:** Sorts the sports list by name (case‑insensitive).
- **Algorithm description:**
  A classic divide‑and‑conquer algorithm. It recursively splits the list into two halves until each sub‑list contains one element, then merges the sorted halves back together. The merge step uses temporary arrays to hold the halves.

- **Time Complexity:**
  - **Best, average, and worst case:** \(O(n \log n)\)
    The recurrence \(T(n) = 2T(n/2) + \Theta(n)\) yields this complexity. The number of levels in the recursion tree is \(\log_2 n\), and each level performs a linear merge.

- **Space Complexity:** \(O(n)\)
  Merge Sort requires additional space for the temporary left and right sub‑arrays in the merge step. The recursive call stack adds \(O(\log n)\) space, but the dominant term is the auxiliary array of total size \(n\).

- **Justification:**
  Merge Sort was chosen to demonstrate the superior asymptotic performance of \(O(n \log n)\) sorting. It is particularly suitable for sorting sports because the number of sports is expected to be moderate, and the guaranteed \(O(n \log n)\) time makes it efficient and predictable regardless of the initial ordering. The extra space cost is acceptable.

## Searching Algorithm – Binary Search

- **Methods:**
  - `BinarySearch.binarySearchClub(List<Club> clubs, String name)` – iterative
  - `BinarySearch.binarySearchMember(List<Member> members, int id)` – iterative
  - Recursive variants also implemented.

- **Algorithm description:**
  Binary search works on a **sorted** list. It repeatedly compares the target with the middle element, eliminating half of the remaining search space each time. The iterative version uses a loop while the recursive version calls itself on the appropriate sub‑range.

- **Time Complexity:**
  - **All cases (once sorted):** \(O(\log n)\)
    The search space is halved at each step, so the maximum number of comparisons is \(\lceil \log_2 n \rceil\).

- **Space Complexity:**
  - **Iterative:** \(O(1)\) – only a few variables.
  - **Recursive:** \(O(\log n)\) due to the call stack (though tail recursion can be optimised, Java standard compilation does not guarantee it).

- **Prerequisite:** The list must be sorted. In the application, clubs are sorted by name using Bubble Sort and members by ID using Selection Sort before binary search is invoked. This ensures correctness and maintains the \(O(\log n)\) search time.

### Complexity Summary Table

| Algorithm      | Used for             | Time (Best)     | Time (Average)  | Time (Worst)    | Space                                        |
|----------------|----------------------|-----------------|-----------------|-----------------|----------------------------------------------|
| Bubble Sort    | Clubs (by name)      | \(O(n)\)        | \(O(n^2)\)      | \(O(n^2)\)      | \(O(1)\)                                     |
| Selection Sort | Members (by ID)      | \(O(n^2)\)      | \(O(n^2)\)      | \(O(n^2)\)      | \(O(1)\)                                     |
| Merge Sort     | Sports (by name)     | \(O(n \log n)\) | \(O(n \log n)\) | \(O(n \log n)\) | \(O(n)\)                                     |
| Binary Search  | Club / Member lookup | –               | \(O(\log n)\)   | \(O(\log n)\)   | \(O(1)\) iterative / \(O(\log n)\) recursive |