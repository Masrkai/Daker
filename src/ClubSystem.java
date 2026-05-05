import java.util.ArrayList;
import java.util.List;

public class ClubSystem {

    public record Club(
            String name,
            List<String> branches,
            String manager,
            String location,
            List<Member> members) {
    }

    public record Member(
            int id,
            String name,
            String phone,
            int numberOfChildren) {
    }

    public record Sport(
            String name,
            int id,
            int numberOfTeams) {
    }

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

    // Creates and returns a list of predefined members with sample data.
    // These members are also included in their respective clubs.
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

    // Displays all members in a formatted manner.
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

    // Displays all sports in a formatted manner.
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

    public static boolean addClub(List<Club> clubs, Club club) {
        if (clubs == null || club == null) {
            return false;
        }
        return clubs.add(club);
    }

    public static boolean addSport(List<Sport> sports, Sport sport) {
        if (sports == null || sport == null) {
            return false;
        }
        return sports.add(sport);
    }

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