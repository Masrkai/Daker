import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClubManagementGUI extends JFrame {

    private final ClubManagementService service = new ClubManagementService();
    private List<ClubSystem.Club> clubs;
    private List<ClubSystem.Member> allMembers;
    private List<ClubSystem.Sport> sports;

    private JTable clubTable, memberTable, sportTable;
    private DefaultTableModel clubModel, memberModel, sportModel;

    public ClubManagementGUI() {
        loadDataFromService();

        setTitle("Club Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setPreferredSize(new Dimension(1200, 65));
        JLabel headerLabel = new JLabel("CLUB MANAGEMENT SYSTEM");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Clubs", createClubPanel());
        tabbedPane.addTab("Members", createMemberPanel());
        tabbedPane.addTab("Sports", createSportPanel());
        add(tabbedPane, BorderLayout.CENTER);

        // Statistics button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton statsBtn = createStyledButton("Show Statistics");
        statsBtn.addActionListener(e -> displayStatistics());
        bottomPanel.add(statsBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshTables();
    }

    // ------------------------------------------------------------
    // Club Panel
    // ------------------------------------------------------------
    private JPanel createClubPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = { "Club Name", "Manager", "Location", "Members Count" };
        clubModel = new DefaultTableModel(columns, 0);
        clubTable = new JTable(clubModel);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addClubBtn = createStyledButton("Add Club");
        JButton sortBtn = createStyledButton("Sort (Bubble)");
        JButton searchBtn = createStyledButton("Search Club");
        JButton addMemberBtn = createStyledButton("Add Member to Club");
        JButton removeMemberBtn = createStyledButton("Remove Member");

        addClubBtn.addActionListener(e -> addNewClub());
        sortBtn.addActionListener(e -> sortClubs());
        searchBtn.addActionListener(e -> searchClub());
        addMemberBtn.addActionListener(e -> addMemberToClub());
        removeMemberBtn.addActionListener(e -> removeMemberFromClub());

        controlPanel.add(addClubBtn);
        controlPanel.add(sortBtn);
        controlPanel.add(searchBtn);
        controlPanel.add(addMemberBtn);
        controlPanel.add(removeMemberBtn);

        panel.add(new JScrollPane(clubTable), BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void addNewClub() {
        JTextField nameF = new JTextField(), managerF = new JTextField(), locF = new JTextField();
        Object[] msg = { "Club Name:", nameF, "Manager:", managerF, "Location:", locF };
        if (JOptionPane.showConfirmDialog(this, msg, "Add New Club", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            if (!nameF.getText().isEmpty()) {
                if (service.addClub(nameF.getText(), managerF.getText(), locF.getText())) {
                    loadDataFromService();
                    refreshTables();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add club.");
                }
            }
        }
    }

    private void sortClubs() {
        SortingAlgorithms.bubbleSortClubs(clubs);
        refreshTables();
        JOptionPane.showMessageDialog(this, "Clubs sorted alphabetically.");
    }

    private void searchClub() {
        if (clubs.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Club list empty.");
            return;
        }
        SortingAlgorithms.bubbleSortClubs(clubs);
        String name = JOptionPane.showInputDialog("Enter club name:");
        if (name != null && !name.isEmpty()) {
            ClubSystem.Club result = BinarySearch.binarySearchClub(clubs, name);
            if (result != null) {
                JOptionPane.showMessageDialog(this,
                        "Found: " + result.name() + "\nManager: " + result.manager() + "\nLocation: " + result.location());
            } else {
                JOptionPane.showMessageDialog(this, "Club not found.");
            }
        }
    }

    private void addMemberToClub() {
        int row = clubTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a club first.");
            return;
        }
        String clubName = (String) clubModel.getValueAt(row, 0);
        JTextField idF = new JTextField(), nameF = new JTextField(), phoneF = new JTextField(), childF = new JTextField();
        Object[] msg = { "Member ID:", idF, "Name:", nameF, "Phone:", phoneF, "Children:", childF };
        if (JOptionPane.showConfirmDialog(this, msg, "Add Member to " + clubName, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idF.getText());
                int children = Integer.parseInt(childF.getText());
                if (service.addMemberToClub(clubName, id, nameF.getText(), phoneF.getText(), children)) {
                    loadDataFromService();
                    refreshTables();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add member.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID and Children must be numbers.");
            }
        }
    }

    private void removeMemberFromClub() {
        int row = clubTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a club first.");
            return;
        }
        String clubName = (String) clubModel.getValueAt(row, 0);
        String input = JOptionPane.showInputDialog("Enter member ID to remove:");
        if (input != null && !input.isEmpty()) {
            try {
                int id = Integer.parseInt(input);
                if (service.removeMemberFromClub(clubName, id)) {
                    loadDataFromService();
                    refreshTables();
                } else {
                    JOptionPane.showMessageDialog(this, "Member not found or club doesn't exist.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid ID.");
            }
        }
    }

    // ------------------------------------------------------------
    // Member Panel (global view + search)
    // ------------------------------------------------------------
    private JPanel createMemberPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = { "ID", "Name", "Phone", "Children" };
        memberModel = new DefaultTableModel(columns, 0);
        memberTable = new JTable(memberModel);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton sortBtn = createStyledButton("Sort by ID");
        JButton searchBtn = createStyledButton("Search by ID");
        JButton refreshBtn = createStyledButton("Refresh");

        sortBtn.addActionListener(e -> {
            SortingAlgorithms.selectionSortMembers(allMembers);
            refreshTables();
        });
        searchBtn.addActionListener(e -> searchMemberById());
        refreshBtn.addActionListener(e -> {
            loadDataFromService();
            refreshTables();
        });

        controlPanel.add(sortBtn); controlPanel.add(searchBtn); controlPanel.add(refreshBtn);
        panel.add(new JScrollPane(memberTable), BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void searchMemberById() {
        if (allMembers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No members.");
            return;
        }
        SortingAlgorithms.selectionSortMembers(allMembers);
        String input = JOptionPane.showInputDialog("Enter member ID:");
        if (input != null && !input.isEmpty()) {
            try {
                int id = Integer.parseInt(input);
                ClubSystem.Member m = BinarySearch.binarySearchMember(allMembers, id);
                if (m != null) {
                    JOptionPane.showMessageDialog(this,
                            "Found: " + m.name() + " (ID: " + m.id() + ")\nPhone: " + m.phone() +
                            "\nChildren: " + m.numberOfChildren());
                } else {
                    JOptionPane.showMessageDialog(this, "Member not found.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid ID.");
            }
        }
    }

    // ------------------------------------------------------------
    // Sport Panel
    // ------------------------------------------------------------
    private JPanel createSportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = { "ID", "Sport Name", "Teams" };
        sportModel = new DefaultTableModel(columns, 0);
        sportTable = new JTable(sportModel);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addBtn = createStyledButton("Add Sport");
        JButton sortBtn = createStyledButton("Sort (Merge)");

        addBtn.addActionListener(e -> addSport());
        sortBtn.addActionListener(e -> {
            sports = SortingAlgorithms.mergeSortSports(new ArrayList<>(sports));
            refreshTables();
        });

        controlPanel.add(addBtn); controlPanel.add(sortBtn);
        panel.add(new JScrollPane(sportTable), BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void addSport() {
        JTextField idF = new JTextField(), nameF = new JTextField(), teamF = new JTextField();
        Object[] msg = { "Sport ID:", idF, "Name:", nameF, "Teams:", teamF };
        if (JOptionPane.showConfirmDialog(this, msg, "Add Sport", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idF.getText());
                int teams = Integer.parseInt(teamF.getText());
                if (service.addSport(nameF.getText(), id, teams)) {
                    loadDataFromService();
                    refreshTables();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add sport.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID and Teams must be numbers.");
            }
        }
    }

    // ------------------------------------------------------------
    // Statistics
    // ------------------------------------------------------------
    private void displayStatistics() {
        ClubManagementService.SystemStatistics stats = service.getStatistics();

        StringBuilder sb = new StringBuilder();
        sb.append("SYSTEM STATISTICS\n\n");
        sb.append("Total Clubs:   ").append(stats.totalClubs()).append("\n");
        sb.append("Total Members: ").append(stats.totalMembers()).append("\n");
        sb.append("Total Sports:  ").append(stats.totalSports()).append("\n");
        sb.append("Total Children: ").append(stats.totalChildren()).append("\n");
        sb.append(String.format("Avg Children/Member: %.2f\n", stats.avgChildrenPerMember()));
        sb.append("Total Teams: ").append(stats.totalTeams()).append("\n");
        sb.append(String.format("Avg Teams/Sport: %.2f\n", stats.avgTeamsPerSport()));
        sb.append("\nAlgorithm Summary:\n");
        sb.append("Bubble Sort (Clubs): O(n²) time, O(1) space\n");
        sb.append("Selection Sort (Members): O(n²) time, O(1) space\n");
        sb.append("Merge Sort (Sports): O(n log n) time, O(n) space\n");
        sb.append("Binary Search: O(log n) time, O(1) space\n");

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 350));
        JOptionPane.showMessageDialog(this, scrollPane, "Statistics", JOptionPane.INFORMATION_MESSAGE);
    }

    // ------------------------------------------------------------
    // Helpers
    // ------------------------------------------------------------
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(160, 35));
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(225, 225, 225));
        btn.setFocusPainted(false);
        return btn;
    }

    private void loadDataFromService() {
        clubs = service.getAllClubs();
        allMembers = service.getAllMembers();
        sports = service.getAllSports();
    }

    private void refreshTables() {
        // Clubs
        clubModel.setRowCount(0);
        for (ClubSystem.Club c : clubs)
            clubModel.addRow(new Object[]{c.name(), c.manager(), c.location(), c.members().size()});

        // Members
        memberModel.setRowCount(0);
        for (ClubSystem.Member m : allMembers)
            memberModel.addRow(new Object[]{m.id(), m.name(), m.phone(), m.numberOfChildren()});

        // Sports
        sportModel.setRowCount(0);
        for (ClubSystem.Sport s : sports)
            sportModel.addRow(new Object[]{s.id(), s.name(), s.numberOfTeams()});
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClubManagementGUI().setVisible(true));
    }
}