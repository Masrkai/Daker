import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * SortingAlgorithms.java
 *
 * Provides sorting functionality for Club, Member, and Sport collections.
 *
 * Implemented algorithms:
 * - Bubble Sort: O(n²) time, O(1) space — used for sorting clubs by name
 * - Selection Sort: O(n²) time, O(1) space — used for sorting members by ID
 * - Merge Sort: O(n log n) time, O(n) space — used for sorting sports by name
 */
public class SortingAlgorithms {

    // ============================
    // BUBBLE SORT — Clubs by Name
    // ============================

    /**
     * Sorts a list of clubs by name in ascending order using Bubble Sort.
     *
     * Time Complexity: O(n²) in worst/average case, O(n) in best case (already
     * sorted)
     * Space Complexity: O(1) auxiliary space
     *
     * @param clubs The list of clubs to sort (modified in-place)
     */
    public static void bubbleSortClubs(List<ClubSystem.Club> clubs) {
        if (clubs == null || clubs.size() <= 1) {
            return;
        }

        int n = clubs.size();
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                String name1 = clubs.get(j).name();
                String name2 = clubs.get(j + 1).name();

                if (name1.compareToIgnoreCase(name2) > 0) {
                    // Swap clubs[j] and clubs[j + 1]
                    ClubSystem.Club temp = clubs.get(j);
                    clubs.set(j, clubs.get(j + 1));
                    clubs.set(j + 1, temp);
                    swapped = true;
                }
            }

            // Optimization: if no swaps occurred, the list is already sorted
            if (!swapped) {
                break;
            }
        }
    }

    // ================================
    // SELECTION SORT — Members by ID
    // ================================

    /**
     * Sorts a list of members by ID in ascending order using Selection Sort.
     * Minimizes the number of swaps (at most n swaps).
     *
     * Time Complexity: O(n²) in all cases
     * Space Complexity: O(1) auxiliary space
     *
     * @param members The list of members to sort (modified in-place)
     */
    public static void selectionSortMembers(List<ClubSystem.Member> members) {
        if (members == null || members.size() <= 1) {
            return;
        }

        int n = members.size();

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            // Find the index of the minimum element in the unsorted portion
            for (int j = i + 1; j < n; j++) {
                if (members.get(j).id() < members.get(minIndex).id()) {
                    minIndex = j;
                }
            }

            // Swap the found minimum element with the first element of unsorted portion
            if (minIndex != i) {
                ClubSystem.Member temp = members.get(i);
                members.set(i, members.get(minIndex));
                members.set(minIndex, temp);
            }
        }
    }

    // ==============================
    // MERGE SORT — Sports by Name
    // ==============================

    /**
     * Sorts a list of sports by name in ascending order using Merge Sort.
     * Returns a new sorted list; the original list is not modified.
     *
     * Time Complexity: O(n log n) in all cases
     * Space Complexity: O(n) auxiliary space
     *
     * @param sports The list of sports to sort
     * @return A new list containing the sports sorted by name
     */
    public static List<ClubSystem.Sport> mergeSortSports(List<ClubSystem.Sport> sports) {
        if (sports == null || sports.size() <= 1) {
            return sports == null ? null : new ArrayList<>(sports);
        }

        List<ClubSystem.Sport> workingCopy = new ArrayList<>(sports);
        mergeSortSportsHelper(workingCopy, 0, workingCopy.size() - 1);
        return workingCopy;
    }

    /**
     * Recursive helper for merge sort on sports.
     */
    private static void mergeSortSportsHelper(List<ClubSystem.Sport> sports, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            // Sort first and second halves
            mergeSortSportsHelper(sports, left, mid);
            mergeSortSportsHelper(sports, mid + 1, right);

            // Merge the sorted halves
            mergeSports(sports, left, mid, right);
        }
    }

    /**
     * Merges two sorted subarrays of sports into a single sorted subarray.
     */
    private static void mergeSports(List<ClubSystem.Sport> sports, int left, int mid, int right) {
        // Sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Create temporary arrays
        List<ClubSystem.Sport> leftArray = new ArrayList<>(n1);
        List<ClubSystem.Sport> rightArray = new ArrayList<>(n2);

        // Copy data to temporary arrays
        for (int i = 0; i < n1; i++) {
            leftArray.add(sports.get(left + i));
        }
        for (int j = 0; j < n2; j++) {
            rightArray.add(sports.get(mid + 1 + j));
        }

        // Merge the temporary arrays back into sports[left..right]
        int i = 0; // Initial index of first subarray
        int j = 0; // Initial index of second subarray
        int k = left; // Initial index of merged subarray

        while (i < n1 && j < n2) {
            if (leftArray.get(i).name().compareToIgnoreCase(rightArray.get(j).name()) <= 0) {
                sports.set(k, leftArray.get(i));
                i++;
            } else {
                sports.set(k, rightArray.get(j));
                j++;
            }
            k++;
        }

        // Copy remaining elements of leftArray, if any
        while (i < n1) {
            sports.set(k, leftArray.get(i));
            i++;
            k++;
        }

        // Copy remaining elements of rightArray, if any
        while (j < n2) {
            sports.set(k, rightArray.get(j));
            j++;
            k++;
        }
    }

    // ============================================
    // GENERIC SORTING UTILITIES (Bonus/Extension)
    // ============================================

    /**
     * Generic bubble sort using a custom comparator.
     *
     * @param <T>        The type of elements in the list
     * @param list       The list to sort (modified in-place)
     * @param comparator The comparator defining the sort order
     */
    public static <T> void bubbleSort(List<T> list, Comparator<? super T> comparator) {
        if (list == null || list.size() <= 1) {
            return;
        }

        int n = list.size();
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    T temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    /**
     * Generic selection sort using a custom comparator.
     *
     * @param <T>        The type of elements in the list
     * @param list       The list to sort (modified in-place)
     * @param comparator The comparator defining the sort order
     */
    public static <T> void selectionSort(List<T> list, Comparator<? super T> comparator) {
        if (list == null || list.size() <= 1) {
            return;
        }

        int n = list.size();

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (comparator.compare(list.get(j), list.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                T temp = list.get(i);
                list.set(i, list.get(minIndex));
                list.set(minIndex, temp);
            }
        }
    }

    /**
     * Generic merge sort using a custom comparator.
     * Returns a new sorted list; the original is not modified.
     *
     * @param <T>        The type of elements in the list
     * @param list       The list to sort
     * @param comparator The comparator defining the sort order
     * @return A new list containing the sorted elements
     */
    public static <T> List<T> mergeSort(List<T> list, Comparator<? super T> comparator) {
        if (list == null || list.size() <= 1) {
            return list == null ? null : new ArrayList<>(list);
        }

        List<T> workingCopy = new ArrayList<>(list);
        mergeSortHelper(workingCopy, 0, workingCopy.size() - 1, comparator);
        return workingCopy;
    }

    private static <T> void mergeSortHelper(List<T> list, int left, int right, Comparator<? super T> comparator) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortHelper(list, left, mid, comparator);
            mergeSortHelper(list, mid + 1, right, comparator);
            merge(list, left, mid, right, comparator);
        }
    }

    private static <T> void merge(List<T> list, int left, int mid, int right, Comparator<? super T> comparator) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<T> leftArray = new ArrayList<>(n1);
        List<T> rightArray = new ArrayList<>(n2);

        for (int i = 0; i < n1; i++) {
            leftArray.add(list.get(left + i));
        }
        for (int j = 0; j < n2; j++) {
            rightArray.add(list.get(mid + 1 + j));
        }

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            if (comparator.compare(leftArray.get(i), rightArray.get(j)) <= 0) {
                list.set(k, leftArray.get(i));
                i++;
            } else {
                list.set(k, rightArray.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            list.set(k, leftArray.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            list.set(k, rightArray.get(j));
            j++;
            k++;
        }
    }
}