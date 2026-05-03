import java.util.List;

/**
 * BinarySearch.java
 *
 * Provides binary search functionality for Club and Member collections.
 *
 * IMPORTANT PREREQUISITE: The input lists MUST be sorted before calling
 * these search functions. Binary search requires sorted data to work correctly.
 *
 * Time Complexity: O(log n) for each search operation.
 */
public class BinarySearch {

    /**
     * Searches for a club by name using binary search.
     *
     * @param clubs The list of clubs, sorted by name in ascending order
     * @param name  The name of the club to search for
     * @return The Club record if found, null otherwise
     */
    public static ClubSystem.Club binarySearchClub(List<ClubSystem.Club> clubs, String name) {
        if (clubs == null || clubs.isEmpty() || name == null) {
            return null;
        }

        int left = 0;
        int right = clubs.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            ClubSystem.Club midClub = clubs.get(mid);
            int comparison = midClub.name().compareToIgnoreCase(name);

            if (comparison == 0) {
                return midClub;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null;
    }

    /**
     * Searches for a member by ID using binary search.
     *
     * @param members The list of members, sorted by ID in ascending order
     * @param id      The ID of the member to search for
     * @return The Member record if found, null otherwise
     */
    public static ClubSystem.Member binarySearchMember(List<ClubSystem.Member> members, int id) {
        if (members == null || members.isEmpty()) {
            return null;
        }

        int left = 0;
        int right = members.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            ClubSystem.Member midMember = members.get(mid);
            int midId = midMember.id();

            if (midId == id) {
                return midMember;
            } else if (midId < id) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null;
    }

    /**
     * Recursive implementation of binary search for clubs by name.
     *
     * @param clubs The list of clubs, sorted by name in ascending order
     * @param name  The name of the club to search for
     * @return The Club record if found, null otherwise
     */
    public static ClubSystem.Club binarySearchClubRecursive(List<ClubSystem.Club> clubs, String name) {
        if (clubs == null || clubs.isEmpty() || name == null) {
            return null;
        }
        return binarySearchClubRecursiveHelper(clubs, name, 0, clubs.size() - 1);
    }

    /**
     * Helper method for recursive club binary search.
     */
    private static ClubSystem.Club binarySearchClubRecursiveHelper(List<ClubSystem.Club> clubs, String name, int left,
            int right) {
        if (left > right) {
            return null;
        }

        int mid = left + (right - left) / 2;
        ClubSystem.Club midClub = clubs.get(mid);
        int comparison = midClub.name().compareToIgnoreCase(name);

        if (comparison == 0) {
            return midClub;
        } else if (comparison < 0) {
            return binarySearchClubRecursiveHelper(clubs, name, mid + 1, right);
        } else {
            return binarySearchClubRecursiveHelper(clubs, name, left, mid - 1);
        }
    }

    /**
     * Recursive implementation of binary search for members by ID.
     *
     * @param members The list of members, sorted by ID in ascending order
     * @param id      The ID of the member to search for
     * @return The Member record if found, null otherwise
     */
    public static ClubSystem.Member binarySearchMemberRecursive(List<ClubSystem.Member> members, int id) {
        if (members == null || members.isEmpty()) {
            return null;
        }
        return binarySearchMemberRecursiveHelper(members, id, 0, members.size() - 1);
    }

    /**
     * Helper method for recursive member binary search.
     */
    private static ClubSystem.Member binarySearchMemberRecursiveHelper(List<ClubSystem.Member> members, int id,
            int left, int right) {
        if (left > right) {
            return null;
        }

        int mid = left + (right - left) / 2;
        ClubSystem.Member midMember = members.get(mid);
        int midId = midMember.id();

        if (midId == id) {
            return midMember;
        } else if (midId < id) {
            return binarySearchMemberRecursiveHelper(members, id, mid + 1, right);
        } else {
            return binarySearchMemberRecursiveHelper(members, id, left, mid - 1);
        }
    }
}