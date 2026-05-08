import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClubManagementGUI extends JFrame {

    // جعلنا القوائم قابلة للتعديل (ArrayList) لضمان عمل الحذف والإضافة
    private List<ClubSystem.Club> clubs = new ArrayList<>(ClubSystem.initializeClubs());
    private List<ClubSystem.Member> allMembers = new ArrayList<>(ClubSystem.initializeMembers());
    private List<ClubSystem.Sport> sports = new ArrayList<>(ClubSystem.initializeSports());

    private JTable clubTable, memberTable, sportTable;
    private DefaultTableModel clubModel, memberModel, sportModel;

    public ClubManagementGUI() {
        setTitle("Club Management System - Algorithms Final Project");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        JLabel headerLabel = new JLabel("Club Management System (Level 2 - CRUD & Algorithms)");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Clubs", createClubPanel());
        tabbedPane.addTab("Members", createMemberPanel());
        tabbedPane.addTab("Sports", createSportPanel());
        add(tabbedPane, BorderLayout.CENTER);

        refreshTables();
    }

    // --- PANEL FOR CLUBS ---
    private JPanel createClubPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = { "Club Name", "Manager", "Location", "Branches Count" };
        clubModel = new DefaultTableModel(columns, 0);
        clubTable = new JTable(clubModel);

        JPanel controlPanel = new JPanel();
        JButton addBtn = new JButton("Add Club");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton sortBtn = new JButton("Sort (Bubble)");
        JButton searchBtn = new JButton("Search (Binary)");

        // Add Club Logic
        addBtn.addActionListener(e -> {
            JTextField nameF = new JTextField();
            JTextField managerF = new JTextField();
            JTextField locF = new JTextField();
            Object[] message = { "Name:", nameF, "Manager:", managerF, "Location:", locF };

            int option = JOptionPane.showConfirmDialog(null, message, "Add New Club", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                // إضافة نادي جديد (بافتراض أن الفروع تبدأ بـ قائمة فارغة)
                clubs.add(new ClubSystem.Club(nameF.getText(), managerF.getText(), locF.getText(), new ArrayList<>()));
                refreshTables();
            }
        });

        // Delete Club Logic
        deleteBtn.addActionListener(e -> {
            int selectedRow = clubTable.getSelectedRow();
            if (selectedRow != -1) {
                clubs.remove(selectedRow);
                refreshTables();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            }
        });

        sortBtn.addActionListener(e -> {
            SortingAlgorithms.bubbleSortClubs(clubs);
            refreshTables();
        });

        searchBtn.addActionListener(e -> {
            SortingAlgorithms.bubbleSortClubs(clubs);
            String name = JOptionPane.showInputDialog("Enter Club Name:");
            if (name != null && !name.isEmpty()) {
                ClubSystem.Club result = BinarySearch.binarySearchClub(clubs, name);
                if (result != null) JOptionPane.showMessageDialog(this, "Found: " + result.name());
                else JOptionPane.showMessageDialog(this, "Not Found.");
            }
        });

        controlPanel.add(addBtn);
        controlPanel.add(deleteBtn);
        controlPanel.add(sortBtn);
        controlPanel.add(searchBtn);
        panel.add(new JScrollPane(clubTable), BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);
        return panel;
    }

    // --- PANEL FOR MEMBERS ---
    private JPanel createMemberPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = { "ID", "Name", "Phone", "Children" };
        memberModel = new DefaultTableModel(columns, 0);
        memberTable = new JTable(memberModel);

        JPanel controlPanel = new JPanel();
        JButton addBtn = new JButton("Add Member");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton sortBtn = new JButton("Sort (Selection)");

        addBtn.addActionListener(e -> {
            JTextField idF = new JTextField();
            JTextField nameF = new JTextField();
            JTextField phoneF = new JTextField();
            JTextField childF = new JTextField();
            Object[] message = { "ID:", idF, "Name:", nameF, "Phone:", phoneF, "Children Count:", childF };

            int option = JOptionPane.showConfirmDialog(null, message, "Add New Member", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    allMembers.add(new ClubSystem.Member(
                            Integer.parseInt(idF.getText()), 
                            nameF.getText(), 
                            phoneF.getText(), 
                            Integer.parseInt(childF.getText())
                    ));
                    refreshTables();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid ID or Children count.");
                }
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = memberTable.getSelectedRow();
            if (row != -1) {
                allMembers.remove(row);
                refreshTables();
            }
        });

        sortBtn.addActionListener(e -> {
            SortingAlgorithms.selectionSortMembers(allMembers);
            refreshTables();
        });

        controlPanel.add(addBtn);
        controlPanel.add(deleteBtn);
        controlPanel.add(sortBtn);
        panel.add(new JScrollPane(memberTable), BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);
        return panel;
    }

    // --- PANEL FOR SPORTS ---
    private JPanel createSportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = { "Sport ID", "Sport Name", "Teams Count" };
        sportModel = new DefaultTableModel(columns, 0);
        sportTable = new JTable(sportModel);

        JPanel controlPanel = new JPanel();
        JButton addBtn = new JButton("Add Sport");
        JButton deleteBtn = new JButton("Delete Selected");
        JButton sortBtn = new JButton("Sort (Merge)");

        addBtn.addActionListener(e -> {
            JTextField idF = new JTextField();
            JTextField nameF = new JTextField();
            JTextField teamsF = new JTextField();
            Object[] message = { "Sport ID:", idF, "Sport Name:", nameF, "Teams Count:", teamsF };

            int option = JOptionPane.showConfirmDialog(null, message, "Add New Sport", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    sports.add(new ClubSystem.Sport(
                            Integer.parseInt(idF.getText()), 
                            nameF.getText(), 
                            Integer.parseInt(teamsF.getText())
                    ));
                    refreshTables();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid inputs.");
                }
            }
        });

        deleteBtn.addActionListener(e -> {
            int row = sportTable.getSelectedRow();
            if (row != -1) {
                sports.remove(row);
                refreshTables();
            }
        });

        sortBtn.addActionListener(e -> {
            sports = SortingAlgorithms.mergeSortSports(new ArrayList<>(sports));
            refreshTables();
        });

        controlPanel.add(addBtn);
        controlPanel.add(deleteBtn);
        controlPanel.add(sortBtn);
        panel.add(new JScrollPane(sportTable), BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);
        return panel;
    }

    private void refreshTables() {
        clubModel.setRowCount(0);
        for (ClubSystem.Club c : clubs) {
            clubModel.addRow(new Object[] { c.name(), c.manager(), c.location(), c.branches().size() });
        }

        memberModel.setRowCount(0);
        for (ClubSystem.Member m : allMembers) {
            memberModel.addRow(new Object[] { m.id(), m.name(), m.phone(), m.numberOfChildren() });
        }

        sportModel.setRowCount(0);
        for (ClubSystem.Sport s : sports) {
            sportModel.addRow(new Object[] { s.id(), s.name(), s.numberOfTeams() });
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new ClubManagementGUI().setVisible(true));
    }
}
