import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * ClubManagementGUI.java
 * The Graphical User Interface for the Club Management System.
 * This class integrates all algorithms and data structures.
 */
public class ClubManagementGUI extends JFrame {
    
    // Data sources initialized from ClubSystem
    private List<ClubSystem.Club> clubs = ClubSystem.initializeClubs();
    private List<ClubSystem.Member> allMembers = ClubSystem.initializeMembers();
    private List<ClubSystem.Sport> sports = ClubSystem.initializeSports();

    // Tables and Models
    private JTable clubTable, memberTable, sportTable;
    private DefaultTableModel clubModel, memberModel, sportModel;

    public ClubManagementGUI() {
        // Window Setup
        setTitle("Club Management System - Algorithms Final Project");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        JLabel headerLabel = new JLabel("Club Management System (Level 2 - Algorithms)");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Tabs setup
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Clubs (Bubble Sort)", createClubPanel());
        tabbedPane.addTab("Members (Selection Sort)", createMemberPanel());
        tabbedPane.addTab("Sports (Merge Sort)", createSportPanel());
        add(tabbedPane, BorderLayout.CENTER);

        // Initial Data Load
        refreshTables();
    }

    // --- PANEL FOR CLUBS ---
    private JPanel createClubPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"Club Name", "Manager", "Location", "Branches Count"};
        clubModel = new DefaultTableModel(columns, 0);
        clubTable = new JTable(clubModel);

        JPanel controlPanel = new JPanel();
        JButton sortBtn = new JButton("Sort by Name (Bubble Sort)");
        JButton searchBtn = new JButton("Search by Name (Binary Search)");
        
        sortBtn.addActionListener(e -> {
            SortingAlgorithms.bubbleSortClubs(clubs);
            refreshTables();
            JOptionPane.showMessageDialog(this, "Sorted using Bubble Sort: O(n^2)");
        });

        searchBtn.addActionListener(e -> {
            SortingAlgorithms.bubbleSortClubs(clubs); // Prerequisite for Binary Search
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

        controlPanel.add(sortBtn);
        controlPanel.add(searchBtn);
        panel.add(new JScrollPane(clubTable), BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);
        return panel;
    }

    // --- PANEL FOR MEMBERS ---
    private JPanel createMemberPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"ID", "Name", "Phone", "Children"};
        memberModel = new DefaultTableModel(columns, 0);
        memberTable = new JTable(memberModel);

        JPanel controlPanel = new JPanel();
        JButton sortBtn = new JButton("Sort by ID (Selection Sort)");
        JButton searchBtn = new JButton("Search by ID (Binary Search)");

        sortBtn.addActionListener(e -> {
            SortingAlgorithms.selectionSortMembers(allMembers);
            refreshTables();
            JOptionPane.showMessageDialog(this, "Sorted using Selection Sort: O(n^2)");
        });

        searchBtn.addActionListener(e -> {
            SortingAlgorithms.selectionSortMembers(allMembers); // Prerequisite
            String idStr = JOptionPane.showInputDialog("Enter Member ID to find:");
            try {
                if (idStr != null) {
                    int id = Integer.parseInt(idStr);
                    ClubSystem.Member result = BinarySearch.binarySearchMember(allMembers, id);
                    if (result != null) {
                        JOptionPane.showMessageDialog(this, "Found: " + result.name() + "\nPhone: " + result.phone());
                    } else {
                        JOptionPane.showMessageDialog(this, "Member ID not found.");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric ID.");
            }
        });

        controlPanel.add(sortBtn);
        controlPanel.add(searchBtn);
        panel.add(new JScrollPane(memberTable), BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);
        return panel;
    }

    // --- PANEL FOR SPORTS ---
    private JPanel createSportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"Sport ID", "Sport Name", "Teams Count"};
        sportModel = new DefaultTableModel(columns, 0);
        sportTable = new JTable(sportModel);

        JPanel controlPanel = new JPanel();
        JButton sortBtn = new JButton("Sort by Name (Merge Sort)");

        sortBtn.addActionListener(e -> {
            // Note: MergeSort returns a new list in your implementation
            sports = SortingAlgorithms.mergeSortSports(sports);
            refreshTables();
            JOptionPane.showMessageDialog(this, "Sorted using Merge Sort: O(n log n)");
        });

        controlPanel.add(sortBtn);
        panel.add(new JScrollPane(sportTable), BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Update all tables when data changes
    private void refreshTables() {
        // Refresh Clubs
        clubModel.setRowCount(0);
        for (ClubSystem.Club c : clubs) {
            clubModel.addRow(new Object[]{c.name(), c.manager(), c.location(), c.branches().size()});
        }

        // Refresh Members
        memberModel.setRowCount(0);
        for (ClubSystem.Member m : allMembers) {
            memberModel.addRow(new Object[]{m.id(), m.name(), m.phone(), m.numberOfChildren()});
        }

        // Refresh Sports
        sportModel.setRowCount(0);
        for (ClubSystem.Sport s : sports) {
            sportModel.addRow(new Object[]{s.id(), s.name(), s.numberOfTeams()});
        }
    }

    public static void main(String[] args) {
        // Setting a nice look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> {
            new ClubManagementGUI().setVisible(true);
        });
    }
}
