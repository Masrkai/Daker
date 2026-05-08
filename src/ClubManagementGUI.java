import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClubManagementGUI extends JFrame {

    // Persistence Files
    private final String CLUBS_FILE = "clubs_data.dat";
    private final String MEMBERS_FILE = "members_data.dat";
    private final String SPORTS_FILE = "sports_data.dat";

    private List<ClubSystem.Club> clubs;
    private List<ClubSystem.Member> allMembers;
    private List<ClubSystem.Sport> sports;

    private JTable clubTable, memberTable, sportTable;
    private DefaultTableModel clubModel, memberModel, sportModel;

    public ClubManagementGUI() {
        loadData(); // Load data from files on startup

        setTitle("Club Management System");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Style
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185)); // Blue Header
        headerPanel.setPreferredSize(new Dimension(1100, 65));
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

        refreshTables();
    }

    // --- CLUBS PANEL (Full Functionality) ---
    private JPanel createClubPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = { "Club Name", "Manager", "Location", "Branches Count", "Members Count" };
        clubModel = new DefaultTableModel(columns, 0);
        clubTable = new JTable(clubModel);
        
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton addBtn = createStyledButton("Add"); 
        JButton delBtn = createStyledButton("Delete");
        JButton sortBtn = createStyledButton("Sort (Bubble)");
        JButton searchBtn = createStyledButton("Search (Binary)");

        addBtn.addActionListener(e -> {
            JTextField nameF = new JTextField(), managerF = new JTextField(), locF = new JTextField();
            Object[] msg = { "Club Name:", nameF, "Manager:", managerF, "Location:", locF };
            if (JOptionPane.showConfirmDialog(null, msg, "Add New Club", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                if(!nameF.getText().isEmpty()){
                    clubs.add(new ClubSystem.Club(nameF.getText(), new ArrayList<>(), managerF.getText(), locF.getText(), new ArrayList<>()));
                    saveData(); refreshTables();
                }
            }
        });

        delBtn.addActionListener(e -> {
            int row = clubTable.getSelectedRow();
            if (row != -1) {
                clubs.remove(row); saveData(); refreshTables();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            }
        });

        sortBtn.addActionListener(e -> {
            SortingAlgorithms.bubbleSortClubs(clubs);
            saveData(); refreshTables();
            JOptionPane.showMessageDialog(this, "Clubs sorted alphabetically.");
        });

        searchBtn.addActionListener(e -> {
            if (clubs.isEmpty()) {
                JOptionPane.showMessageDialog(this, "List is empty.");
                return;
            }
            SortingAlgorithms.bubbleSortClubs(clubs); // Must sort before binary search
            String name = JOptionPane.showInputDialog("Enter Club Name to find:");
            if (name != null && !name.isEmpty()) {
                ClubSystem.Club result = BinarySearch.binarySearchClub(clubs, name);
                if (result != null) {
                    JOptionPane.showMessageDialog(this, "Found: " + result.name() + "\nManager: " + result.manager() + "\nLocation: " + result.location());
                } else {
                    JOptionPane.showMessageDialog(this, "Club not found.");
                }
            }
        });

        controlPanel.add(addBtn); controlPanel.add(delBtn); controlPanel.add(sortBtn); controlPanel.add(searchBtn);
        panel.add(new JScrollPane(clubTable), BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);
        return panel;
    }

    // --- MEMBERS PANEL (Full Functionality) ---
    private JPanel createMemberPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = { "ID", "Name", "Phone", "Children" };
        memberModel = new DefaultTableModel(columns, 0);
        memberTable = new JTable(memberModel);
        
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addBtn = createStyledButton("Add"); 
        JButton delBtn = createStyledButton("Delete");
        JButton sortBtn = createStyledButton("Sort (Selection)");

        addBtn.addActionListener(e -> {
            JTextField idF = new JTextField(), nameF = new JTextField(), phoneF = new JTextField(), childF = new JTextField();
            Object[] msg = { "ID:", idF, "Name:", nameF, "Phone:", phoneF, "Children:", childF };
            if (JOptionPane.showConfirmDialog(null, msg, "Add Member", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                try {
                    allMembers.add(new ClubSystem.Member(Integer.parseInt(idF.getText()), nameF.getText(), phoneF.getText(), Integer.parseInt(childF.getText())));
                    saveData(); refreshTables();
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Error: ID and Children must be numbers."); }
            }
        });

        delBtn.addActionListener(e -> {
            int row = memberTable.getSelectedRow();
            if (row != -1) { allMembers.remove(row); saveData(); refreshTables(); }
        });

        sortBtn.addActionListener(e -> {
            SortingAlgorithms.selectionSortMembers(allMembers);
            saveData(); refreshTables();
        });

        controlPanel.add(addBtn); controlPanel.add(delBtn); controlPanel.add(sortBtn);
        panel.add(new JScrollPane(memberTable), BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);
        return panel;
    }

    // --- SPORTS PANEL (Full Functionality) ---
    private JPanel createSportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = { "ID", "Sport Name", "Teams" };
        sportModel = new DefaultTableModel(columns, 0);
        sportTable = new JTable(sportModel);
        
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addBtn = createStyledButton("Add"); 
        JButton delBtn = createStyledButton("Delete");
        JButton sortBtn = createStyledButton("Sort (Merge)");

        addBtn.addActionListener(e -> {
            JTextField idF = new JTextField(), nameF = new JTextField(), teamF = new JTextField();
            Object[] msg = { "Sport ID:", idF, "Sport Name:", nameF, "Teams Count:", teamF };
            if (JOptionPane.showConfirmDialog(null, msg, "Add Sport", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                try {
                    sports.add(new ClubSystem.Sport(nameF.getText(), Integer.parseInt(idF.getText()), Integer.parseInt(teamF.getText())));
                    saveData(); refreshTables();
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Input Error!"); }
            }
        });

        delBtn.addActionListener(e -> {
            int row = sportTable.getSelectedRow();
            if (row != -1) { sports.remove(row); saveData(); refreshTables(); }
        });

        sortBtn.addActionListener(e -> {
            sports = SortingAlgorithms.mergeSortSports(new ArrayList<>(sports));
            saveData(); refreshTables();
        });

        controlPanel.add(addBtn); controlPanel.add(delBtn); controlPanel.add(sortBtn);
        panel.add(new JScrollPane(sportTable), BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);
        return panel;
    }

    // --- HELPER: BUTTON STYLING (Visible Black Text) ---
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(140, 35));
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(Color.BLACK); // Explicitly Black Text
        btn.setBackground(new Color(225, 225, 225)); // Classic Light Gray
        btn.setFocusPainted(false);
        return btn;
    }

    // --- PERSISTENCE: SAVE/LOAD ---
    private void saveData() {
        try {
            writeObject(CLUBS_FILE, clubs);
            writeObject(MEMBERS_FILE, allMembers);
            writeObject(SPORTS_FILE, sports);
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void writeObject(String file, Object obj) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(obj); oos.close();
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        try {
            clubs = (new File(CLUBS_FILE).exists()) ? (List<ClubSystem.Club>) readObject(CLUBS_FILE) : new ArrayList<>(ClubSystem.initializeClubs());
            allMembers = (new File(MEMBERS_FILE).exists()) ? (List<ClubSystem.Member>) readObject(MEMBERS_FILE) : new ArrayList<>(ClubSystem.initializeMembers());
            sports = (new File(SPORTS_FILE).exists()) ? (List<ClubSystem.Sport>) readObject(SPORTS_FILE) : new ArrayList<>(ClubSystem.initializeSports());
        } catch (Exception e) {
            clubs = new ArrayList<>(ClubSystem.initializeClubs());
            allMembers = new ArrayList<>(ClubSystem.initializeMembers());
            sports = new ArrayList<>(ClubSystem.initializeSports());
        }
    }

    private Object readObject(String file) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Object obj = ois.readObject(); ois.close(); return obj;
    }

    private void refreshTables() {
        clubModel.setRowCount(0);
        for (ClubSystem.Club c : clubs) clubModel.addRow(new Object[]{c.name(), c.manager(), c.location(), c.branches().size(), c.members().size()});
        memberModel.setRowCount(0);
        for (ClubSystem.Member m : allMembers) memberModel.addRow(new Object[]{m.id(), m.name(), m.phone(), m.numberOfChildren()});
        sportModel.setRowCount(0);
        for (ClubSystem.Sport s : sports) sportModel.addRow(new Object[]{s.id(), s.name(), s.numberOfTeams()});
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClubManagementGUI().setVisible(true));
    }
}
