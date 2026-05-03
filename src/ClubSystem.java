import java.util.ArrayList;
import java.util.List;

public class ClubSystem {

    // ============================
    // RECORD DEFINITIONS
    // ============================

    /**
     * Represents a sports club with its details and members.
     *
     * @param name     The name of the club
     * @param branches List of branch locations
     * @param manager  Name of the club manager
     * @param location Primary location of the club
     * @param members  List of members belonging to this club
     */
    public record Club(
            String name,
            List<String> branches,
            String manager,
            String location,
            List<Member> members) {
    }

    /**
     * Represents a member of a club.
     *
     * @param id               Unique identifier for the member
     * @param name             Name of the member
     * @param phone            Phone number of the member
     * @param numberOfChildren Number of children the member has
     */
    public record Member(
            int id,
            String name,
            String phone,
            int numberOfChildren) {
    }

    /**
     * Represents a sport offered by the club system.
     *
     * @param name          Name of the sport
     * @param id            Unique identifier for the sport
     * @param numberOfTeams Number of teams for this sport
     */
    public record Sport(
            String name,
            int id,
            int numberOfTeams) {
    }

    // ============================
    // INITIALIZATION FUNCTIONS
    // ============================

    /**
     * Creates and returns a list of predefined clubs with sample data.
     *
     * @return A list of initialized Club records
     */
    public static List<Club> initializeClubs() {
        List<Club> clubs = new ArrayList<>();

        // Club 1: Lions Club
        List<String> lionsBranches = new ArrayList<>();
        lionsBranches.add("Downtown");
        lionsBranches.add("Uptown");

        List<Member> lionsMembers = new ArrayList<>();
        lionsMembers.add(new Member(101, "Alice Johnson", "555-0101", 2));
        lionsMembers.add(new Member(102, "Bob Smith", "555-0102", 0));
        lionsMembers.add(new Member(103, "Charlie Brown", "555-0103", 3));

        clubs.add(new Club(
                "Lions",
                lionsBranches,
                "John Doe",
                "New York",
                lionsMembers));

        // Club 2: Tigers Club
        List<String> tigersBranches = new ArrayList<>();
        tigersBranches.add("Westside");
        tigersBranches.add("Eastside");
        tigersBranches.add("Northside");

        List<Member> tigersMembers = new ArrayList<>();
        tigersMembers.add(new Member(201, "Diana Prince", "555-0201", 1));
        tigersMembers.add(new Member(202, "Evan Wright", "555-0202", 2));

        clubs.add(new Club(
                "Tigers",
                tigersBranches,
                "Jane Smith",
                "Los Angeles",
                tigersMembers));

        // Club 3: Eagles Club
        List<String> eaglesBranches = new ArrayList<>();
        eaglesBranches.add("Central");

        List<Member> eaglesMembers = new ArrayList<>();
        eaglesMembers.add(new Member(301, "Frank Miller", "555-0301", 0));
        eaglesMembers.add(new Member(302, "Grace Lee", "555-0302", 1));
        eaglesMembers.add(new Member(303, "Henry Davis", "555-0303", 4));
        eaglesMembers.add(new Member(304, "Ivy Wilson", "555-0304", 2));

        clubs.add(new Club(
                "Eagles",
                eaglesBranches,
                "Robert Brown",
                "Chicago",
                eaglesMembers));

        // Club 4: Sharks Club
        List<String> sharksBranches = new ArrayList<>();
        sharksBranches.add("Beachfront");
        sharksBranches.add("Harbor");

        List<Member> sharksMembers = new ArrayList<>();
        sharksMembers.add(new Member(401, "Jack Taylor", "555-0401", 1));

        clubs.add(new Club(
                "Sharks",
                sharksBranches,
                "Emily Davis",
                "Miami",
                sharksMembers));

        // Club 5: Bears Club
        List<String> bearsBranches = new ArrayList<>();
        bearsBranches.add("Mountain View");
        bearsBranches.add("Valley");
        bearsBranches.add("Riverside");

        List<Member> bearsMembers = new ArrayList<>();
        bearsMembers.add(new Member(501, "Karen White", "555-0501", 2));
        bearsMembers.add(new Member(502, "Leo Martin", "555-0502", 3));

        clubs.add(new Club(
                "Bears",
                bearsBranches,
                "Michael Wilson",
                "Denver",
                bearsMembers));

        return clubs;
    }

    /**
     * Creates and returns a list of predefined members with sample data.
     * These members are also included in their respective clubs.
     *
     * @return A list of all initialized Member records
     */
    public static List<Member> initializeMembers() {
        List<Member> members = new ArrayList<>();

        // Lions members
        members.add(new Member(101, "Alice Johnson", "555-0101", 2));
        members.add(new Member(102, "Bob Smith", "555-0102", 0));
        members.add(new Member(103, "Charlie Brown", "555-0103", 3));

        // Tigers members
        members.add(new Member(201, "Diana Prince", "555-0201", 1));
        members.add(new Member(202, "Evan Wright", "555-0202", 2));

        // Eagles members
        members.add(new Member(301, "Frank Miller", "555-0301", 0));
        members.add(new Member(302, "Grace Lee", "555-0302", 1));
        members.add(new Member(303, "Henry Davis", "555-0303", 4));
        members.add(new Member(304, "Ivy Wilson", "555-0304", 2));

        // Sharks members
        members.add(new Member(401, "Jack Taylor", "555-0401", 1));

        // Bears members
        members.add(new Member(501, "Karen White", "555-0501", 2));
        members.add(new Member(502, "Leo Martin", "555-0502", 3));

        return members;
    }

    /**
     * Creates and returns a list of predefined sports with sample data.
     *
     * @return A list of initialized Sport records
     */
    public static List<Sport> initializeSports() {
        List<Sport> sports = new ArrayList<>();

        sports.add(new Sport("Basketball", 1, 8));
        sports.add(new Sport("Football", 2, 12));
        sports.add(new Sport("Swimming", 3, 6));
        sports.add(new Sport("Tennis", 4, 4));
        sports.add(new Sport("Volleyball", 5, 6));
        sports.add(new Sport("Baseball", 6, 10));
        sports.add(new Sport("Soccer", 7, 11));

        return sports;
    }

    // ============================
    // UTILITY FUNCTIONS
    // ============================

    /**
     * Displays all clubs with their details in a formatted manner.
     *
     * @param clubs The list of clubs to display
     */
    public static void displayClubs(List<Club> clubs) {
        if (clubs == null || clubs.isEmpty()) {
            System.out.println("No clubs to display.");
            return;
        }

        System.out.println("=".repeat(60));
        System.out.println("                    CLUB LIST");
        System.out.println("=".repeat(60));

        for (Club club : clubs) {
            System.out.println("\nClub Name:    " + club.name());
            System.out.println("Manager:      " + club.manager());
            System.out.println("Location:     " + club.location());
            System.out.println("Branches:     " + String.join(", ", club.branches()));
            System.out.println("Members:      " + club.members().size());
            System.out.println("-".repeat(40));

            for (Member member : club.members()) {
                System.out.printf("  [%d] %-20s | Phone: %-12s | Children: %d%n",
                        member.id(), member.name(), member.phone(), member.numberOfChildren());
            }
        }
        System.out.println("=".repeat(60));
    }

    /**
     * Displays all members in a formatted manner.
     *
     * @param members The list of members to display
     */
    public static void displayMembers(List<Member> members) {
        if (members == null || members.isEmpty()) {
            System.out.println("No members to display.");
            return;
        }

        System.out.println("=".repeat(60));
        System.out.println("                   MEMBER LIST");
        System.out.println("=".repeat(60));
        System.out.printf("%-6s %-20s %-15s %-10s%n", "ID", "Name", "Phone", "Children");
        System.out.println("-".repeat(60));

        for (Member member : members) {
            System.out.printf("%-6d %-20s %-15s %-10d%n",
                    member.id(), member.name(), member.phone(), member.numberOfChildren());
        }
        System.out.println("=".repeat(60));
    }

    /**
     * Displays all sports in a formatted manner.
     *
     * @param sports The list of sports to display
     */
    public static void displaySports(List<Sport> sports) {
        if (sports == null || sports.isEmpty()) {
            System.out.println("No sports to display.");
            return;
        }

        System.out.println("=".repeat(50));
        System.out.println("                  SPORT LIST");
        System.out.println("=".repeat(50));
        System.out.printf("%-6s %-20s %-15s%n", "ID", "Name", "Teams");
        System.out.println("-".repeat(50));

        for (Sport sport : sports) {
            System.out.printf("%-6d %-20s %-15d%n",
                    sport.id(), sport.name(), sport.numberOfTeams());
        }
        System.out.println("=".repeat(50));
    }

    /**
     * Adds a new member to a specific club.
     *
     * @param clubs    The list of clubs
     * @param clubName The name of the club to add the member to
     * @param member   The member to add
     * @return true if the member was added successfully, false if club not found
     */
    public static boolean addMemberToClub(List<Club> clubs, String clubName, Member member) {
        if (clubs == null || clubName == null || member == null) {
            return false;
        }

        for (Club club : clubs) {
            if (club.name().equalsIgnoreCase(clubName)) {
                // Since records are immutable, we need to create a new Club with updated
                // members
                List<Member> updatedMembers = new ArrayList<>(club.members());
                updatedMembers.add(member);

                Club updatedClub = new Club(
                        club.name(),
                        club.branches(),
                        club.manager(),
                        club.location(),
                        updatedMembers);

                // Replace the old club with the updated one
                int index = clubs.indexOf(club);
                clubs.set(index, updatedClub);
                return true;
            }
        }

        return false;
    }

    /**
     * Adds a new club to the list.
     *
     * @param clubs The list of clubs
     * @param club  The club to add
     * @return true if added successfully
     */
    public static boolean addClub(List<Club> clubs, Club club) {
        if (clubs == null || club == null) {
            return false;
        }
        return clubs.add(club);
    }

    /**
     * Adds a new sport to the list.
     *
     * @param sports The list of sports
     * @param sport  The sport to add
     * @return true if added successfully
     */
    public static boolean addSport(List<Sport> sports, Sport sport) {
        if (sports == null || sport == null) {
            return false;
        }
        return sports.add(sport);
    }

    /**
     * Removes a member from a specific club by member ID.
     *
     * @param clubs    The list of clubs
     * @param clubName The name of the club
     * @param memberId The ID of the member to remove
     * @return true if the member was removed, false otherwise
     */
    public static boolean removeMemberFromClub(List<Club> clubs, String clubName, int memberId) {
        if (clubs == null || clubName == null) {
            return false;
        }

        for (Club club : clubs) {
            if (club.name().equalsIgnoreCase(clubName)) {
                List<Member> updatedMembers = new ArrayList<>(club.members());
                boolean removed = updatedMembers.removeIf(m -> m.id() == memberId);

                if (removed) {
                    Club updatedClub = new Club(
                            club.name(),
                            club.branches(),
                            club.manager(),
                            club.location(),
                            updatedMembers);
                    int index = clubs.indexOf(club);
                    clubs.set(index, updatedClub);
                }

                return removed;
            }
        }

        return false;
    }

    /**
     * Finds a member by ID across all clubs.
     *
     * @param clubs    The list of clubs to search through
     * @param memberId The ID of the member to find
     * @return The Member if found, null otherwise
     */
    public static Member findMemberById(List<Club> clubs, int memberId) {
        if (clubs == null) {
            return null;
        }

        for (Club club : clubs) {
            for (Member member : club.members()) {
                if (member.id() == memberId) {
                    return member;
                }
            }
        }

        return null;
    }

    /**
     * Counts the total number of members across all clubs.
     *
     * @param clubs The list of clubs
     * @return The total member count
     */
    public static int getTotalMemberCount(List<Club> clubs) {
        if (clubs == null) {
            return 0;
        }

        int count = 0;
        for (Club club : clubs) {
            count += club.members().size();
        }
        return count;
    }

    /**
     * Gets all members from all clubs as a flat list.
     *
     * @param clubs The list of clubs
     * @return A list containing all members from all clubs
     */
    public static List<Member> getAllMembers(List<Club> clubs) {
        List<Member> allMembers = new ArrayList<>();
        if (clubs == null) {
            return allMembers;
        }

        for (Club club : clubs) {
            allMembers.addAll(club.members());
        }
        return allMembers;
    }
}