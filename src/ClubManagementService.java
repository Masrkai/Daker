import java.util.ArrayList;
import java.util.List;

/**
 * Service layer for all club, member, and sport operations.
 * Both CLI and GUI call the same methods, ensuring identical behaviour.
 */
public class ClubManagementService {
    private final DatabaseHandler db = DatabaseHandler.getInstance();

    // --- Club operations ---
    public boolean addClub(String name, String manager, String location) {
        ClubSystem.Club club = new ClubSystem.Club(name, new ArrayList<>(), manager, location, new ArrayList<>());
        return db.addClub(club);
    }

    public List<ClubSystem.Club> getAllClubs() {
        return db.getAllClubs();
    }

    // --- Member operations ---
    public boolean addMemberToClub(String clubName, int id, String name, String phone, int children) {
        ClubSystem.Member member = new ClubSystem.Member(id, name, phone, children);
        return db.addMemberToClub(clubName, member);
    }

    public boolean removeMemberFromClub(String clubName, int memberId) {
        return db.removeMemberFromClub(clubName, memberId);
    }

    public List<ClubSystem.Member> getAllMembers() {
        return db.getAllMembers();
    }

    // --- Sport operations ---
    public boolean addSport(String name, int id, int teams) {
        ClubSystem.Sport sport = new ClubSystem.Sport(name, id, teams);
        return db.addSport(sport);
    }

    public List<ClubSystem.Sport> getAllSports() {
        return db.getAllSports();
    }

    // --- Statistics ---
    public SystemStatistics getStatistics() {
        List<ClubSystem.Club> clubs = db.getAllClubs();
        List<ClubSystem.Member> members = db.getAllMembers();
        List<ClubSystem.Sport> sports = db.getAllSports();

        int totalClubs = clubs.size();
        int totalMembers = db.getTotalMemberCount();
        int totalSports = sports.size();
        int totalChildren = db.getTotalChildren();
        int totalTeams = db.getTotalTeams();

        double avgChildren = members.isEmpty() ? 0 : (double) totalChildren / members.size();
        double avgTeams = sports.isEmpty() ? 0 : (double) totalTeams / sports.size();

        return new SystemStatistics(totalClubs, totalMembers, totalSports,
                                    totalChildren, avgChildren, totalTeams, avgTeams);
    }

    // Statistics container
    public record SystemStatistics(int totalClubs, int totalMembers, int totalSports,
                                   int totalChildren, double avgChildrenPerMember,
                                   int totalTeams, double avgTeamsPerSport) {
    }
}