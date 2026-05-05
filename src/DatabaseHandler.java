import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseHandler.java
 *
 * Handles all SQLite operations for example.db.
 * Reads schema.sql and data.sql on first run to initialize the database.
 * Provides CRUD operations for clubs, members, and sports.
 */
public class DatabaseHandler {
    private static final String DB_URL = "jdbc:sqlite:example.db";
    private static DatabaseHandler instance;
    private Connection conn;

    private DatabaseHandler() {
        connect();
        initializeDatabase();
    }

    public static synchronized DatabaseHandler getInstance() {
        if (instance == null) instance = new DatabaseHandler();
        return instance;
    }

    private void connect() {
        try {
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }
    }

    /** Reads schema.sql and data.sql to build and populate example.db */
    private void initializeDatabase() {
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet tables = meta.getTables(null, null, "clubs", null);
            if (tables.next()) { tables.close(); return; }
            tables.close();

            executeSqlFile("/db/schema.sql");
            executeSqlFile("/db/data.sql");
            System.out.println("Database initialized from schema.sql and data.sql");
        } catch (SQLException e) {
            throw new RuntimeException("Database initialization failed", e);
        }
    }

    private void executeSqlFile(String resourcePath) {
        try (java.io.InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is == null) throw new java.io.FileNotFoundException("Classpath resource not found: " + resourcePath);
            try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(is))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().startsWith("--") || line.trim().isEmpty()) continue;
                    sb.append(line);
                    if (line.trim().endsWith(";")) {
                        conn.createStatement().execute(sb.toString());
                        sb.setLength(0);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute " + resourcePath, e);
        }
    }

    // ==================== CLUBS ====================

    public List<ClubSystem.Club> getAllClubs() {
        List<ClubSystem.Club> clubs = new ArrayList<>();
        String sql = "SELECT * FROM clubs";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String manager = rs.getString("manager");
                String location = rs.getString("location");
                List<String> branches = getBranchesForClub(id);
                List<ClubSystem.Member> members = getMembersForClub(id);
                clubs.add(new ClubSystem.Club(name, branches, manager, location, members));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return clubs;
    }

    private List<String> getBranchesForClub(int clubId) {
        List<String> branches = new ArrayList<>();
        String sql = "SELECT name FROM branches WHERE club_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, clubId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) branches.add(rs.getString("name"));
        } catch (SQLException e) { e.printStackTrace(); }
        return branches;
    }

    public boolean addClub(ClubSystem.Club club) {
        String sql = "INSERT INTO clubs (name, manager, location) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, club.name());
            ps.setString(2, club.manager());
            ps.setString(3, club.location());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                int clubId = keys.getInt(1);
                for (String branch : club.branches()) {
                    try (PreparedStatement ps2 = conn.prepareStatement("INSERT INTO branches (club_id, name) VALUES (?, ?)")) {
                        ps2.setInt(1, clubId);
                        ps2.setString(2, branch);
                        ps2.executeUpdate();
                    }
                }
            }
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ==================== MEMBERS ====================

    public List<ClubSystem.Member> getAllMembers() {
        List<ClubSystem.Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                members.add(new ClubSystem.Member(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getInt("number_of_children")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return members;
    }

    private List<ClubSystem.Member> getMembersForClub(int clubId) {
        List<ClubSystem.Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members WHERE club_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, clubId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                members.add(new ClubSystem.Member(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phone"),
                    rs.getInt("number_of_children")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return members;
    }

    public boolean addMemberToClub(String clubName, ClubSystem.Member member) {
        String sql = "SELECT id FROM clubs WHERE name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, clubName);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return false;
            int clubId = rs.getInt("id");

            try (PreparedStatement ps2 = conn.prepareStatement(
                "INSERT INTO members (id, club_id, name, phone, number_of_children) VALUES (?, ?, ?, ?, ?)")) {
                ps2.setInt(1, member.id());
                ps2.setInt(2, clubId);
                ps2.setString(3, member.name());
                ps2.setString(4, member.phone());
                ps2.setInt(5, member.numberOfChildren());
                ps2.executeUpdate();
                return true;
            }
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean removeMemberFromClub(String clubName, int memberId) {
        String sql = "DELETE FROM members WHERE id = ? AND club_id = (SELECT id FROM clubs WHERE name = ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, memberId);
            ps.setString(2, clubName);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ==================== SPORTS ====================

    public List<ClubSystem.Sport> getAllSports() {
        List<ClubSystem.Sport> sports = new ArrayList<>();
        String sql = "SELECT * FROM sports";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                sports.add(new ClubSystem.Sport(
                    rs.getString("name"),
                    rs.getInt("id"),
                    rs.getInt("number_of_teams")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return sports;
    }

    public boolean addSport(ClubSystem.Sport sport) {
        String sql = "INSERT INTO sports (id, name, number_of_teams) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sport.id());
            ps.setString(2, sport.name());
            ps.setInt(3, sport.numberOfTeams());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ==================== STATS ====================

    public int getTotalMemberCount() {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM members")) {
            return rs.getInt(1);
        } catch (SQLException e) { return 0; }
    }

    public int getTotalChildren() {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT SUM(number_of_children) FROM members")) {
            return rs.getInt(1);
        } catch (SQLException e) { return 0; }
    }

    public int getTotalTeams() {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT SUM(number_of_teams) FROM sports")) {
            return rs.getInt(1);
        } catch (SQLException e) { return 0; }
    }

    public void close() {
        try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
}