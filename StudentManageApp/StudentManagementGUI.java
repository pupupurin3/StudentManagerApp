/*=============================================================================
 |   Assignment:  Program #1:  Student Management System
 |       Author:  Noah Matsukuma
 |      Partner:  n/a
 |
 |  Course Name:  AP CSA
 |   Instructor:  Jonathan Virak
 |     Due Date:  3/3/2025
 |
 |  Description:  This program provides a graphical user interface for managing
 |                student information, including adding, viewing, searching, 
 |                and editing student details.
 |
 |     Language:  Java version 8
 | Ex. Packages:  javax.swing, java.awt, java.util.List, java.util.stream.Collectors
 |                
 | Deficiencies:  I hope there isn't any
 *===========================================================================*/

/*+----------------------------------------------------------------------
 ||
 ||  Class StudentManagementGUI 
 ||
 ||         Author:  Noah Matsukuma
 ||
 ||        Purpose:  This class provides the main graphical user interface
 ||                  for the Student Management System. It allows users to
 ||                  add, view, search, and edit student information.
 ||
 ||  Inherits From:  JFrame
 ||
 ||     Interfaces:  None
 |+-----------------------------------------------------------------------
 ||
 ||      Constants:  None
 ||
 |+-----------------------------------------------------------------------
 ||
 ||   Constructors:  
 ||      StudentManagementGUI() - Initializes the GUI components and sets up
 ||                               the event listeners.
 ||
 ||  Class Methods:  None
 ||
 ||  Inst. Methods:  
 ||      private void showAddStudentForm() - Displays the form for adding a new student.
 ||      private void showViewStudentForm() - Displays the form for viewing a student's information.
 ||      private void showStudentInfoForm(Student student) - Displays the form with a student's information.
 ||      private void displayAllStudents() - Displays all students in the table.
 ||      private void filterAndSortStudents() - Filters and sorts the students based on search criteria.
 ||      private void displayFilteredStudents(List<Student> students) - Displays the filtered students in the table.
 ||
 ++-----------------------------------------------------------------------*/

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class StudentManagementGUI extends JFrame {
    private StudentManager manager;
    private JTextArea displayArea;
    private JPanel formPanel;
    private JTextField searchField;
    private JComboBox<String> filterComboBox;
    private JComboBox<String> sortComboBox;
    private JScrollPane displayScrollPane;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JScrollPane tableScrollPane;

    public StudentManagementGUI() {
        manager = new StudentManager();
        setTitle("Student Management System");
        setSize(1000, 600); // Increase the size of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1));
        JButton addButton = new JButton("Add Student");
        JButton searchButton = new JButton("Search Students");
        JButton viewButton = new JButton("View Student Info");
        JButton exitButton = new JButton("Exit");

        // Add buttons to panel
        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(exitButton);

        // Add button panel to frame
        add(buttonPanel, BorderLayout.WEST);

        // Create search bar, filter combo box, and sort combo box
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        filterComboBox = new JComboBox<>(new String[]{"ID", "First Name", "Last Name", "GPA", "Grade Level"});
        sortComboBox = new JComboBox<>(new String[]{"ID", "First Name", "Last Name", "GPA", "Grade Level"});
        JPanel comboBoxPanel = new JPanel(new GridLayout(2, 2));
        comboBoxPanel.add(new JLabel("Filter by:"));
        comboBoxPanel.add(filterComboBox);
        comboBoxPanel.add(new JLabel("Sort by:"));
        comboBoxPanel.add(sortComboBox);
        searchPanel.add(new JLabel("Search: "), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(comboBoxPanel, BorderLayout.EAST);
        add(searchPanel, BorderLayout.NORTH);

        // Create display area
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayScrollPane = new JScrollPane(displayArea);
        add(displayScrollPane, BorderLayout.CENTER);

        // Create form panel
        formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(6, 2, 10, 10)); // Add spacing between components

        // Create table for displaying students
        tableModel = new DefaultTableModel(new Object[]{"ID", "First Name", "Last Name", "GPA", "Grade Level"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the table non-editable
            }
        };
        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.setGridColor(Color.WHITE); // Set grid lines to white
        tableScrollPane = new JScrollPane(studentTable);

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddStudentForm();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayAllStudents();
                remove(formPanel);
                add(tableScrollPane, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    int studentID = (int) tableModel.getValueAt(selectedRow, 0);
                    Student student = manager.findStudentByID(studentID);
                    if (student != null) {
                        showStudentInfoForm(student);
                    } else {
                        JOptionPane.showMessageDialog(StudentManagementGUI.this, "Error: Student ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    showViewStudentForm();
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                filterAndSortStudents();
            }

            public void removeUpdate(DocumentEvent e) {
                filterAndSortStudents();
            }

            public void changedUpdate(DocumentEvent e) {
                filterAndSortStudents();
            }
        });

        sortComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filterAndSortStudents();
            }
        });

        // Display all students initially
        displayAllStudents();
    }

    /*---------------------------------------------------------------------
        |  Method showAddStudentForm
        *-------------------------------------------------------------------*/
    private void showAddStudentForm() {
        remove(displayScrollPane);
        remove(tableScrollPane); // Remove the table
        add(formPanel, BorderLayout.CENTER);
        formPanel.removeAll();

        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField();
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField();
        JLabel idLabel = new JLabel("Student ID:");
        JTextField idField = new JTextField();
        JLabel gpaLabel = new JLabel("GPA:");
        JTextField gpaField = new JTextField();
        JLabel gradeLevelLabel = new JLabel("Grade Level:");
        JTextField gradeLevelField = new JTextField();
        JButton submitButton = new JButton("Submit");

        // Set preferred size for text fields to make them smaller in height
        Dimension textFieldSize = new Dimension(200, 25); // Adjusted height to ensure text entry
        firstNameField.setPreferredSize(textFieldSize);
        lastNameField.setPreferredSize(textFieldSize);
        idField.setPreferredSize(textFieldSize);
        gpaField.setPreferredSize(textFieldSize);
        gradeLevelField.setPreferredSize(textFieldSize);

        formPanel.add(firstNameLabel);
        formPanel.add(firstNameField);
        formPanel.add(lastNameLabel);
        formPanel.add(lastNameField);
        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(gpaLabel);
        formPanel.add(gpaField);
        formPanel.add(gradeLevelLabel);
        formPanel.add(gradeLevelField);
        formPanel.add(new JLabel()); // Empty cell
        formPanel.add(submitButton);

        formPanel.revalidate();
        formPanel.repaint();

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                int id = Integer.parseInt(idField.getText());
                if (String.valueOf(id).length() < 9) {
                    JOptionPane.showMessageDialog(StudentManagementGUI.this, "Error: Student ID must be at least 9 digits.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (manager.findStudentByID(id) != null) {
                    JOptionPane.showMessageDialog(StudentManagementGUI.this, "Error: Student ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double gpa = Double.parseDouble(gpaField.getText());
                int gradeLevel = Integer.parseInt(gradeLevelField.getText());
                manager.addStudent(new Student(firstName, lastName, id, gpa, gradeLevel));
                JOptionPane.showMessageDialog(StudentManagementGUI.this, "Student added.", "Success", JOptionPane.INFORMATION_MESSAGE);
                formPanel.removeAll();
                formPanel.revalidate();
                formPanel.repaint();
                showAddStudentForm(); // Ensure form is shown again
            }
        });
    }

    /*---------------------------------------------------------------------
        |  Method showViewStudentForm
        *-------------------------------------------------------------------*/
    private void showViewStudentForm() {
        remove(displayScrollPane);
        remove(tableScrollPane); // Remove the table
        add(formPanel, BorderLayout.CENTER);
        formPanel.removeAll();

        JLabel idLabel = new JLabel("Student ID:");
        JTextField idField = new JTextField();
        JButton submitButton = new JButton("View");

        // Set preferred size for text fields to make them smaller in height
        Dimension textFieldSize = new Dimension(200, 25); // Adjusted height to ensure text entry
        idField.setPreferredSize(textFieldSize);

        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(new JLabel()); // Empty cell
        formPanel.add(submitButton);

        formPanel.revalidate();
        formPanel.repaint();

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                Student student = manager.findStudentByID(id);
                if (student != null) {
                    showStudentInfoForm(student);
                } else {
                    JOptionPane.showMessageDialog(StudentManagementGUI.this, "Error: Student ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /*---------------------------------------------------------------------
        |  Method showStudentInfoForm
        *-------------------------------------------------------------------*/
    private void showStudentInfoForm(Student student) {
        remove(displayScrollPane);
        remove(tableScrollPane); // Remove the table
        add(formPanel, BorderLayout.CENTER);
        formPanel.removeAll();

        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField(student.getFirstName());
        firstNameField.setEditable(false);
        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField(student.getLastName());
        lastNameField.setEditable(false);
        JLabel idLabel = new JLabel("Student ID:");
        JTextField idField = new JTextField(String.valueOf(student.getStudentID()));
        idField.setEditable(false);
        JLabel gpaLabel = new JLabel("GPA:");
        JTextField gpaField = new JTextField(String.valueOf(student.getGpa()));
        gpaField.setEditable(false);
        JLabel gradeLevelLabel = new JLabel("Grade Level:");
        JTextField gradeLevelField = new JTextField(String.valueOf(student.getGradeLevel()));
        gradeLevelField.setEditable(false);
        JButton editButton = new JButton("Edit Student Info");
        JButton removeButton = new JButton("Remove Student");

        formPanel.add(firstNameLabel);
        formPanel.add(firstNameField);
        formPanel.add(lastNameLabel);
        formPanel.add(lastNameField);
        formPanel.add(idLabel);
        formPanel.add(idField);
        formPanel.add(gpaLabel);
        formPanel.add(gpaField);
        formPanel.add(gradeLevelLabel);
        formPanel.add(gradeLevelField);
        formPanel.add(editButton);
        formPanel.add(removeButton);

        formPanel.revalidate();
        formPanel.repaint();

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                firstNameField.setEditable(true);
                lastNameField.setEditable(true);
                gpaField.setEditable(true);
                gradeLevelField.setEditable(true);
                formPanel.remove(editButton);
                formPanel.remove(removeButton);
                JButton confirmButton = new JButton("Confirm Changes");
                JButton cancelButton = new JButton("Cancel");

                formPanel.add(confirmButton);
                formPanel.add(cancelButton);

                formPanel.revalidate();
                formPanel.repaint();

                confirmButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String firstName = firstNameField.getText();
                        String lastName = lastNameField.getText();
                        double gpa = Double.parseDouble(gpaField.getText());
                        int gradeLevel = Integer.parseInt(gradeLevelField.getText());
                        student.setFirstName(firstName);
                        student.setLastName(lastName);
                        student.setGpa(gpa);
                        student.setGradeLevel(gradeLevel);
                        manager.saveStudentsToFile();
                        JOptionPane.showMessageDialog(StudentManagementGUI.this, "Student info updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        showStudentInfoForm(student);
                    }
                });

                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        showStudentInfoForm(student);
                    }
                });
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String confirmId = JOptionPane.showInputDialog(StudentManagementGUI.this, "Enter Student ID to confirm removal:");
                if (confirmId != null && confirmId.equals(String.valueOf(student.getStudentID()))) {
                    if (manager.removeStudentByID(student.getStudentID())) {
                        JOptionPane.showMessageDialog(StudentManagementGUI.this, "Student removed.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        displayAllStudents();
                    } else {
                        JOptionPane.showMessageDialog(StudentManagementGUI.this, "Error: Student ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(StudentManagementGUI.this, "Error: Student ID does not match.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /*---------------------------------------------------------------------
        |  Method displayAllStudents
        *-------------------------------------------------------------------*/
    private void displayAllStudents() {
        remove(formPanel);
        add(tableScrollPane, BorderLayout.CENTER);
        tableModel.setRowCount(0); // Clear existing rows
        for (Student student : manager.getAllStudents()) {
            tableModel.addRow(new Object[]{student.getStudentID(), student.getFirstName(), student.getLastName(), student.getGpa(), student.getGradeLevel()});
        }
    }

    /*---------------------------------------------------------------------
        |  Method filterAndSortStudents
        *-------------------------------------------------------------------*/
    private void filterAndSortStudents() {
        String searchText = searchField.getText().toLowerCase();
        String filterCriteria = (String) filterComboBox.getSelectedItem();
        String sortCriteria = (String) sortComboBox.getSelectedItem();
        List<Student> filteredStudents = manager.getAllStudents().stream()
                .filter(student -> {
                    switch (filterCriteria) {
                        case "ID":
                            return String.valueOf(student.getStudentID()).contains(searchText);
                        case "First Name":
                            return student.getFirstName().toLowerCase().contains(searchText);
                        case "Last Name":
                            return student.getLastName().toLowerCase().contains(searchText);
                        case "GPA":
                            return String.valueOf(student.getGpa()).contains(searchText);
                        case "Grade Level":
                            return String.valueOf(student.getGradeLevel()).contains(searchText);
                        default:
                            return false;
                    }
                })
                .sorted((s1, s2) -> {
                    switch (sortCriteria) {
                        case "ID":
                            return Integer.compare(s1.getStudentID(), s2.getStudentID());
                        case "First Name":
                            return s1.getFirstName().compareToIgnoreCase(s2.getFirstName());
                        case "Last Name":
                            return s1.getLastName().compareToIgnoreCase(s2.getLastName());
                        case "GPA":
                            return Double.compare(s1.getGpa(), s2.getGpa());
                        case "Grade Level":
                            return Integer.compare(s1.getGradeLevel(), s2.getGradeLevel());
                        default:
                            return 0;
                    }
                })
                .collect(Collectors.toList());

        displayFilteredStudents(filteredStudents);
    }

    /*---------------------------------------------------------------------
        |  Method displayFilteredStudents
        *-------------------------------------------------------------------*/
    private void displayFilteredStudents(List<Student> students) {
        tableModel.setRowCount(0); // Clear existing rows
        for (Student student : students) {
            tableModel.addRow(new Object[]{student.getStudentID(), student.getFirstName(), student.getLastName(), student.getGpa(), student.getGradeLevel()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                StudentManagementGUI gui = new StudentManagementGUI();
                gui.setVisible(true);
            }
        });
    }
}