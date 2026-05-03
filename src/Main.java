import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main.java
 *
 * Entry point for the Club Management System.
 * Provides an interactive console-based menu for managing clubs, members, and
 * sports.
 * Demonstrates sorting algorithms (Bubble Sort, Selection Sort, Merge Sort) and
 * binary search functionality.
 *
 * Big-O Analysis of the overall program:
 * - Sorting dominates the time complexity of most operations.
 * - Bubble Sort: O(n²) worst/average, O(n) best case
 * - Selection Sort: O(n²) all cases
 * - Merge Sort: O(n log n) all cases
 * - Binary Search: O(log n)
 * - Linear search (findMemberById across clubs): O(c * m) where c = clubs, m =
 * members per club
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("     WELCOME TO THE CLUB MANAGEMENT SYSTEM");
        System.out.println("=".repeat(60));

        // Initialize data
        List<ClubSystem.Club> clubs = ClubSystem.initializeClubs();
        List<ClubSystem.Member> members = ClubSystem.initializeMembers();
        List<ClubSystem.Sport> sports = ClubSystem.initializeSports();

        boolean running = true;

        while (running) {
            printMainMenu();
            System.out.print("\nEnter your choice: ");

            String input = scanner.nextLine().trim();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> displayAllData(clubs, members, sports);
                case 2 -> sortAndDisplayClubs(clubs);
                case 3 -> sortAndDisplayMembers(members);
                case 4 -> sortAndDisplaySports(sports);
                case 5 -> searchClubByName(clubs);
                case 6 -> searchMemberById(members);
                case 7 -> addNewClub(clubs);
                case 8 -> addNewMember(clubs);
                case 9 -> addNewSport(sports);
                case 10 -> removeMember(clubs);
                case 11 -> displayStatistics(clubs, members, sports);
                case 0 -> {
                    running = false;
                    System.out.println("\nThank you for using the Club Management System. Goodbye!");
                }
                default -> System.out.println("\nInvalid choice. Please try again.");
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    // ============================
    // MENU DISPLAY
    // ============================

    private static void printMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("              MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("  1. Display All Data (Clubs, Members, Sports)");
        System.out.println("  2. Sort Clubs by Name (Bubble Sort)");
        System.out.println("  3. Sort Members by ID (Selection Sort)");
        System.out.println("  4. Sort Sports by Name (Merge Sort)");
        System.out.println("  5. Search Club by Name (Binary Search)");
        System.out.println("  6. Search Member by ID (Binary Search)");
        System.out.println("  7. Add New Club");
        System.out.println("  8. Add New Member to Club");
        System.out.println("  9. Add New Sport");
        System.out.println(" 10. Remove Member from Club");
        System.out.println(" 11. Display Statistics");
        System.out.println("  0. Exit");
        System.out.println("=".repeat(50));
    }

    // ============================
    // DISPLAY OPERATIONS
    // ============================

    private static void displayAllData(List<ClubSystem.Club> clubs,
            List<ClubSystem.Member> members,
            List<ClubSystem.Sport> sports) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("              DISPLAYING ALL DATA");
        System.out.println("=".repeat(60));

        ClubSystem.displayClubs(clubs);
        System.out.println();
        ClubSystem.displayMembers(members);
        System.out.println();
        ClubSystem.displaySports(sports);
    }

    // ============================
    // SORTING OPERATIONS
    // ============================

    private static void sortAndDisplayClubs(List<ClubSystem.Club> clubs) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("       SORTING CLUBS BY NAME (Bubble Sort)");
        System.out.println("=".repeat(60));
        System.out.println("Algorithm: Bubble Sort");
        System.out.println("Time Complexity: O(n²) worst/average, O(n) best");
        System.out.println("Space Complexity: O(1)");
        System.out.println("Description: Repeatedly steps through the list, compares");
        System.out.println("adjacent elements and swaps them if they are in wrong order.");
        System.out.println("-".repeat(60));

        System.out.println("\n--- Before Sorting ---");
        for (ClubSystem.Club club : clubs) {
            System.out.println("  " + club.name());
        }

        long startTime = System.nanoTime();
        SortingAlgorithms.bubbleSortClubs(clubs);
        long endTime = System.nanoTime();

        System.out.println("\n--- After Sorting ---");
        for (ClubSystem.Club club : clubs) {
            System.out.println("  " + club.name());
        }

        double durationMs = (endTime - startTime) / 1_000_000.0;
        System.out.printf("%nSorting completed in %.3f ms%n", durationMs);
    }

    private static void sortAndDisplayMembers(List<ClubSystem.Member> members) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("       SORTING MEMBERS BY ID (Selection Sort)");
        System.out.println("=".repeat(60));
        System.out.println("Algorithm: Selection Sort");
        System.out.println("Time Complexity: O(n²) all cases");
        System.out.println("Space Complexity: O(1)");
        System.out.println("Description: Divides list into sorted and unsorted portions,");
        System.out.println("repeatedly selects the minimum element from unsorted portion.");
        System.out.println("-".repeat(60));

        System.out.println("\n--- Before Sorting ---");
        for (ClubSystem.Member member : members) {
            System.out.println("  ID: " + member.id() + " - " + member.name());
        }

        long startTime = System.nanoTime();
        SortingAlgorithms.selectionSortMembers(members);
        long endTime = System.nanoTime();

        System.out.println("\n--- After Sorting ---");
        for (ClubSystem.Member member : members) {
            System.out.println("  ID: " + member.id() + " - " + member.name());
        }

        double durationMs = (endTime - startTime) / 1_000_000.0;
        System.out.printf("%nSorting completed in %.3f ms%n", durationMs);
    }

    private static void sortAndDisplaySports(List<ClubSystem.Sport> sports) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("       SORTING SPORTS BY NAME (Merge Sort)");
        System.out.println("=".repeat(60));
        System.out.println("Algorithm: Merge Sort");
        System.out.println("Time Complexity: O(n log n) all cases");
        System.out.println("Space Complexity: O(n)");
        System.out.println("Description: Divide-and-conquer algorithm that divides the list");
        System.out.println("into halves, sorts them, and merges the sorted halves.");
        System.out.println("-".repeat(60));

        System.out.println("\n--- Before Sorting ---");
        for (ClubSystem.Sport sport : sports) {
            System.out.println("  " + sport.name());
        }

        long startTime = System.nanoTime();
        List<ClubSystem.Sport> sortedSports = SortingAlgorithms.mergeSortSports(sports);
        long endTime = System.nanoTime();

        System.out.println("\n--- After Sorting ---");
        for (ClubSystem.Sport sport : sortedSports) {
            System.out.println("  " + sport.name());
        }

        double durationMs = (endTime - startTime) / 1_000_000.0;
        System.out.printf("%nSorting completed in %.3f ms%n", durationMs);
    }

    // ============================
    // SEARCHING OPERATIONS
    // ============================

    private static void searchClubByName(List<ClubSystem.Club> clubs) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("         SEARCH CLUB BY NAME (Binary Search)");
        System.out.println("=".repeat(60));
        System.out.println("Prerequisite: Clubs must be sorted by name first.");
        System.out.println("Time Complexity: O(log n)");
        System.out.println("-".repeat(60));

        // First, ensure clubs are sorted
        SortingAlgorithms.bubbleSortClubs(clubs);

        System.out.print("\nEnter club name to search: ");
        String searchName = scanner.nextLine().trim();

        if (searchName.isEmpty()) {
            System.out.println("Search cancelled: empty input.");
            return;
        }

        long startTime = System.nanoTime();
        ClubSystem.Club found = BinarySearch.binarySearchClub(clubs, searchName);
        long endTime = System.nanoTime();

        if (found != null) {
            System.out.println("\n✓ Club found!");
            System.out.println("  Name:     " + found.name());
            System.out.println("  Manager:  " + found.manager());
            System.out.println("  Location: " + found.location());
            System.out.println("  Branches: " + String.join(", ", found.branches()));
            System.out.println("  Members:  " + found.members().size());
        } else {
            System.out.println("\n✗ Club not found: \"" + searchName + "\"");
        }

        double durationMs = (endTime - startTime) / 1_000_000.0;
        System.out.printf("%nSearch completed in %.3f ms%n", durationMs);
    }

    private static void searchMemberById(List<ClubSystem.Member> members) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("         SEARCH MEMBER BY ID (Binary Search)");
        System.out.println("=".repeat(60));
        System.out.println("Prerequisite: Members must be sorted by ID first.");
        System.out.println("Time Complexity: O(log n)");
        System.out.println("-".repeat(60));

        // First, ensure members are sorted
        SortingAlgorithms.selectionSortMembers(members);

        System.out.print("\nEnter member ID to search: ");
        String input = scanner.nextLine().trim();

        int searchId;
        try {
            searchId = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
            return;
        }

        long startTime = System.nanoTime();
        ClubSystem.Member found = BinarySearch.binarySearchMember(members, searchId);
        long endTime = System.nanoTime();

        if (found != null) {
            System.out.println("\n✓ Member found!");
            System.out.println("  ID:       " + found.id());
            System.out.println("  Name:     " + found.name());
            System.out.println("  Phone:    " + found.phone());
            System.out.println("  Children: " + found.numberOfChildren());
        } else {
            System.out.println("\n✗ Member not found with ID: " + searchId);
        }

        double durationMs = (endTime - startTime) / 1_000_000.0;
        System.out.printf("%nSearch completed in %.3f ms%n", durationMs);
    }

    // ============================
    // ADD OPERATIONS
    // ============================

    private static void addNewClub(List<ClubSystem.Club> clubs) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("              ADD NEW CLUB");
        System.out.println("=".repeat(60));

        System.out.print("Enter club name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Club name cannot be empty.");
            return;
        }

        System.out.print("Enter manager name: ");
        String manager = scanner.nextLine().trim();

        System.out.print("Enter location: ");
        String location = scanner.nextLine().trim();

        System.out.print("Enter branches (comma-separated): ");
        String branchesInput = scanner.nextLine().trim();
        List<String> branches = new ArrayList<>();
        if (!branchesInput.isEmpty()) {
            for (String branch : branchesInput.split(",")) {
                branches.add(branch.trim());
            }
        }

        ClubSystem.Club newClub = new ClubSystem.Club(
                name, branches, manager, location, new ArrayList<>());

        if (ClubSystem.addClub(clubs, newClub)) {
            System.out.println("\n✓ Club \"" + name + "\" added successfully!");
        } else {
            System.out.println("\n✗ Failed to add club.");
        }
    }

    private static void addNewMember(List<ClubSystem.Club> clubs) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           ADD NEW MEMBER TO CLUB");
        System.out.println("=".repeat(60));

        System.out.println("Available clubs:");
        for (int i = 0; i < clubs.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + clubs.get(i).name());
        }

        System.out.print("\nEnter club name: ");
        String clubName = scanner.nextLine().trim();

        boolean clubExists = clubs.stream()
                .anyMatch(c -> c.name().equalsIgnoreCase(clubName));
        if (!clubExists) {
            System.out.println("Club not found: \"" + clubName + "\"");
            return;
        }

        System.out.print("Enter member ID: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
            return;
        }

        System.out.print("Enter member name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine().trim();

        System.out.print("Enter number of children: ");
        int children;
        try {
            children = Integer.parseInt(scanner.nextLine().trim());
            if (children < 0)
                children = 0;
        } catch (NumberFormatException e) {
            children = 0;
        }

        ClubSystem.Member newMember = new ClubSystem.Member(id, name, phone, children);

        if (ClubSystem.addMemberToClub(clubs, clubName, newMember)) {
            System.out.println("\n✓ Member \"" + name + "\" added to \"" + clubName + "\" successfully!");
        } else {
            System.out.println("\n✗ Failed to add member.");
        }
    }

    private static void addNewSport(List<ClubSystem.Sport> sports) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("              ADD NEW SPORT");
        System.out.println("=".repeat(60));

        System.out.print("Enter sport name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Sport name cannot be empty.");
            return;
        }

        System.out.print("Enter sport ID: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
            return;
        }

        System.out.print("Enter number of teams: ");
        int teams;
        try {
            teams = Integer.parseInt(scanner.nextLine().trim());
            if (teams < 0)
                teams = 0;
        } catch (NumberFormatException e) {
            teams = 0;
        }

        ClubSystem.Sport newSport = new ClubSystem.Sport(name, id, teams);

        if (ClubSystem.addSport(sports, newSport)) {
            System.out.println("\n✓ Sport \"" + name + "\" added successfully!");
        } else {
            System.out.println("\n✗ Failed to add sport.");
        }
    }

    // ============================
    // REMOVE OPERATIONS
    // ============================

    private static void removeMember(List<ClubSystem.Club> clubs) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           REMOVE MEMBER FROM CLUB");
        System.out.println("=".repeat(60));

        System.out.println("Available clubs:");
        for (ClubSystem.Club club : clubs) {
            System.out.println("  - " + club.name() + " (" + club.members().size() + " members)");
        }

        System.out.print("\nEnter club name: ");
        String clubName = scanner.nextLine().trim();

        System.out.print("Enter member ID to remove: ");
        int memberId;
        try {
            memberId = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
            return;
        }

        if (ClubSystem.removeMemberFromClub(clubs, clubName, memberId)) {
            System.out.println("\n✓ Member with ID " + memberId + " removed from \"" + clubName + "\" successfully!");
        } else {
            System.out.println("\n✗ Member not found or club does not exist.");
        }
    }

    // ============================
    // STATISTICS
    // ============================

    private static void displayStatistics(List<ClubSystem.Club> clubs,
            List<ClubSystem.Member> members,
            List<ClubSystem.Sport> sports) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("              SYSTEM STATISTICS");
        System.out.println("=".repeat(60));

        System.out.println("Data Overview:");
        System.out.println("  Total Clubs:   " + clubs.size());
        System.out.println("  Total Members: " + members.size());
        System.out.println("  Total Sports:  " + sports.size());

        System.out.println("\nClub Details:");
        int totalMembersAcrossClubs = 0;
        for (ClubSystem.Club club : clubs) {
            int memberCount = club.members().size();
            totalMembersAcrossClubs += memberCount;
            System.out.printf("  %-15s | Location: %-15s | Members: %d%n",
                    club.name(), club.location(), memberCount);
        }

        System.out.println("\nMember Statistics:");
        int totalChildren = 0;
        for (ClubSystem.Member member : members) {
            totalChildren += member.numberOfChildren();
        }
        System.out.println("  Total Children: " + totalChildren);
        System.out.printf("  Average Children per Member: %.2f%n",
                members.isEmpty() ? 0 : (double) totalChildren / members.size());

        System.out.println("\nSport Statistics:");
        int totalTeams = 0;
        for (ClubSystem.Sport sport : sports) {
            totalTeams += sport.numberOfTeams();
        }
        System.out.println("  Total Teams: " + totalTeams);
        System.out.printf("  Average Teams per Sport: %.2f%n",
                sports.isEmpty() ? 0 : (double) totalTeams / sports.size());

        System.out.println("\nAlgorithm Performance Summary:");
        System.out.println("  Bubble Sort (Clubs):     O(n²) time, O(1) space");
        System.out.println("  Selection Sort (Members):  O(n²) time, O(1) space");
        System.out.println("  Merge Sort (Sports):     O(n log n) time, O(n) space");
        System.out.println("  Binary Search:           O(log n) time, O(1) space");
    }
}