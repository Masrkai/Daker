import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main.java
 *
 * Command-line interface for the Club Management System.
 * All business logic is delegated to ClubManagementService.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ClubManagementService service = new ClubManagementService();

    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("     WELCOME TO THE CLUB MANAGEMENT SYSTEM");
        System.out.println("=".repeat(60));

        // Load initial data
        List<ClubSystem.Club> clubs = service.getAllClubs();
        List<ClubSystem.Member> members = service.getAllMembers();
        List<ClubSystem.Sport> sports = service.getAllSports();

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
                case 1 -> {
                    clubs = service.getAllClubs();
                    members = service.getAllMembers();
                    sports = service.getAllSports();
                    displayAllData(clubs, members, sports);
                }
                case 2 -> sortAndDisplayClubs(clubs);
                case 3 -> sortAndDisplayMembers(members);
                case 4 -> sortAndDisplaySports(sports);
                case 5 -> searchClubByName(clubs);
                case 6 -> searchMemberById(members);
                case 7 -> {
                    addNewClub(clubs);
                    clubs = service.getAllClubs(); // refresh after addition
                }
                case 8 -> {
                    addNewMember(clubs);
                    clubs = service.getAllClubs(); // refresh to show new member
                    members = service.getAllMembers();
                }
                case 9 -> {
                    addNewSport(sports);
                    sports = service.getAllSports();
                }
                case 10 -> {
                    removeMember(clubs);
                    clubs = service.getAllClubs();
                    members = service.getAllMembers();
                }
                case 11 -> displayStatistics(clubs, members, sports);
                case 0 -> {
                    running = false;
                    DatabaseHandler.getInstance().close();
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

    // --- Sorting (still calling algorithm classes directly, no duplication to remove) ---
    private static void sortAndDisplayClubs(List<ClubSystem.Club> clubs) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("       SORTING CLUBS BY NAME (Bubble Sort)");
        System.out.println("=".repeat(60));
        printAlgorithmInfo("Bubble Sort", "O(n²) worst/average, O(n) best", "O(1)",
                "Repeatedly steps through list, compares adjacent elements and swaps them if wrong order.");

        System.out.println("\n--- Before Sorting ---");
        clubs.forEach(c -> System.out.println("  " + c.name()));

        long start = System.nanoTime();
        SortingAlgorithms.bubbleSortClubs(clubs);
        long end = System.nanoTime();

        System.out.println("\n--- After Sorting ---");
        clubs.forEach(c -> System.out.println("  " + c.name()));
        System.out.printf("%nSorting completed in %.3f ms%n", (end - start) / 1_000_000.0);
    }

    private static void sortAndDisplayMembers(List<ClubSystem.Member> members) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("       SORTING MEMBERS BY ID (Selection Sort)");
        System.out.println("=".repeat(60));
        printAlgorithmInfo("Selection Sort", "O(n²) all cases", "O(1)",
                "Divides list into sorted/unsorted portions, repeatedly selects minimum.");

        System.out.println("\n--- Before Sorting ---");
        members.forEach(m -> System.out.println("  ID: " + m.id() + " - " + m.name()));

        long start = System.nanoTime();
        SortingAlgorithms.selectionSortMembers(members);
        long end = System.nanoTime();

        System.out.println("\n--- After Sorting ---");
        members.forEach(m -> System.out.println("  ID: " + m.id() + " - " + m.name()));
        System.out.printf("%nSorting completed in %.3f ms%n", (end - start) / 1_000_000.0);
    }

    private static void sortAndDisplaySports(List<ClubSystem.Sport> sports) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("       SORTING SPORTS BY NAME (Merge Sort)");
        System.out.println("=".repeat(60));
        printAlgorithmInfo("Merge Sort", "O(n log n) all cases", "O(n)",
                "Divide-and-conquer algorithm that divides list into halves, sorts and merges.");

        System.out.println("\n--- Before Sorting ---");
        sports.forEach(s -> System.out.println("  " + s.name()));

        long start = System.nanoTime();
        List<ClubSystem.Sport> sorted = SortingAlgorithms.mergeSortSports(sports);
        long end = System.nanoTime();
        sports.clear();
        sports.addAll(sorted); // update original list

        System.out.println("\n--- After Sorting ---");
        sports.forEach(s -> System.out.println("  " + s.name()));
        System.out.printf("%nSorting completed in %.3f ms%n", (end - start) / 1_000_000.0);
    }

    // --- Searching ---
    private static void searchClubByName(List<ClubSystem.Club> clubs) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("         SEARCH CLUB BY NAME (Binary Search)");
        System.out.println("=".repeat(60));
        System.out.println("Prerequisite: Clubs sorted by name. Time Complexity: O(log n)");

        SortingAlgorithms.bubbleSortClubs(clubs);
        System.out.print("\nEnter club name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) return;

        long start = System.nanoTime();
        ClubSystem.Club found = BinarySearch.binarySearchClub(clubs, name);
        long end = System.nanoTime();

        if (found != null) {
            System.out.println("\n✓ Found: " + found.name() + " | Manager: " + found.manager() +
                    " | Location: " + found.location());
        } else {
            System.out.println("\n✗ Club not found.");
        }
        System.out.printf("Search completed in %.3f ms%n", (end - start) / 1_000_000.0);
    }

    private static void searchMemberById(List<ClubSystem.Member> members) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("         SEARCH MEMBER BY ID (Binary Search)");
        System.out.println("=".repeat(60));
        System.out.println("Prerequisite: Members sorted by ID. Time Complexity: O(log n)");

        SortingAlgorithms.selectionSortMembers(members);
        System.out.print("\nEnter member ID: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
            return;
        }

        long start = System.nanoTime();
        ClubSystem.Member found = BinarySearch.binarySearchMember(members, id);
        long end = System.nanoTime();

        if (found != null) {
            System.out.println("\n✓ Found: " + found.name() + " (ID: " + found.id() +
                    "), Phone: " + found.phone() + ", Children: " + found.numberOfChildren());
        } else {
            System.out.println("\n✗ Member not found.");
        }
        System.out.printf("Search completed in %.3f ms%n", (end - start) / 1_000_000.0);
    }

    // --- Add / Remove operations (now via service) ---
    private static void addNewClub(List<ClubSystem.Club> clubs) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("              ADD NEW CLUB");
        System.out.println("=".repeat(60));

        System.out.print("Enter club name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) return;

        System.out.print("Enter manager: ");
        String manager = scanner.nextLine().trim();
        System.out.print("Enter location: ");
        String location = scanner.nextLine().trim();

        if (service.addClub(name, manager, location)) {
            clubs.clear();
            clubs.addAll(service.getAllClubs());
            System.out.println("\n✓ Club added successfully.");
        } else {
            System.out.println("\n✗ Failed to add club.");
        }
    }

    private static void addNewMember(List<ClubSystem.Club> clubs) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           ADD NEW MEMBER TO CLUB");
        System.out.println("=".repeat(60));
        System.out.println("Available clubs:");
        for (ClubSystem.Club c : clubs) System.out.println("  - " + c.name());

        System.out.print("\nEnter club name: ");
        String clubName = scanner.nextLine().trim();
        System.out.print("Enter member ID: ");
        int id;
        try { id = Integer.parseInt(scanner.nextLine().trim()); } catch (NumberFormatException e) { return; }
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Enter children count: ");
        int children;
        try { children = Integer.parseInt(scanner.nextLine().trim()); } catch (NumberFormatException e) { children = 0; }

        if (service.addMemberToClub(clubName, id, name, phone, children)) {
            clubs.clear();
            clubs.addAll(service.getAllClubs());
            System.out.println("\n✓ Member added.");
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
        if (name.isEmpty()) return;
        System.out.print("Enter sport ID: ");
        int id;
        try { id = Integer.parseInt(scanner.nextLine().trim()); } catch (NumberFormatException e) { return; }
        System.out.print("Enter teams count: ");
        int teams;
        try { teams = Integer.parseInt(scanner.nextLine().trim()); } catch (NumberFormatException e) { teams = 0; }

        if (service.addSport(name, id, teams)) {
            sports.clear();
            sports.addAll(service.getAllSports());
            System.out.println("\n✓ Sport added.");
        } else {
            System.out.println("\n✗ Failed to add sport.");
        }
    }

    private static void removeMember(List<ClubSystem.Club> clubs) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           REMOVE MEMBER FROM CLUB");
        System.out.println("=".repeat(60));
        System.out.println("Available clubs:");
        for (ClubSystem.Club c : clubs) System.out.println("  - " + c.name());

        System.out.print("\nEnter club name: ");
        String clubName = scanner.nextLine().trim();
        System.out.print("Enter member ID: ");
        int id;
        try { id = Integer.parseInt(scanner.nextLine().trim()); } catch (NumberFormatException e) { return; }

        if (service.removeMemberFromClub(clubName, id)) {
            clubs.clear();
            clubs.addAll(service.getAllClubs());
            System.out.println("\n✓ Member removed.");
        } else {
            System.out.println("\n✗ Failure: member or club not found.");
        }
    }

    // --- Statistics ---
    private static void displayStatistics(List<ClubSystem.Club> clubs,
                                          List<ClubSystem.Member> members,
                                          List<ClubSystem.Sport> sports) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("              SYSTEM STATISTICS");
        System.out.println("=".repeat(60));

        ClubManagementService.SystemStatistics stats = service.getStatistics();

        System.out.println("Data Overview:");
        System.out.println("  Total Clubs:   " + stats.totalClubs());
        System.out.println("  Total Members: " + stats.totalMembers());
        System.out.println("  Total Sports:  " + stats.totalSports());

        System.out.println("\nClub Details:");
        for (ClubSystem.Club c : clubs) {
            System.out.printf("  %-15s | Location: %-15s | Members: %d%n",
                    c.name(), c.location(), c.members().size());
        }

        System.out.println("\nMember Statistics:");
        System.out.println("  Total Children: " + stats.totalChildren());
        System.out.printf("  Average Children per Member: %.2f%n", stats.avgChildrenPerMember());

        System.out.println("\nSport Statistics:");
        System.out.println("  Total Teams: " + stats.totalTeams());
        System.out.printf("  Average Teams per Sport: %.2f%n", stats.avgTeamsPerSport());

        System.out.println("\nAlgorithm Performance Summary:");
        System.out.println("  Bubble Sort (Clubs):     O(n²) time, O(1) space");
        System.out.println("  Selection Sort (Members):  O(n²) time, O(1) space");
        System.out.println("  Merge Sort (Sports):     O(n log n) time, O(n) space");
        System.out.println("  Binary Search:           O(log n) time, O(1) space");
    }

    private static void printAlgorithmInfo(String name, String time, String space, String desc) {
        System.out.println("Algorithm: " + name);
        System.out.println("Time Complexity: " + time);
        System.out.println("Space Complexity: " + space);
        System.out.println("Description: " + desc);
        System.out.println("-".repeat(60));
    }
}