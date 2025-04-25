import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

/*+----------------------------------------------------------------------
 ||
 ||  Class StudentManager 
 ||
 ||         Author:  Noah Matsukuma
 ||
 ||        Purpose:  This class manages a list of students, allowing for
 ||                   adding, removing, updating, and sorting students. It
 ||                   also handles loading and saving student data to a file.
 ||
 ||  Inherits From:  None
 ||
 ||     Interfaces:  None
 |+-----------------------------------------------------------------------
 ||
 ||      Constants:  
 ||          FILE_NAME -- The name of the file where student data is stored.
 ||
 |+-----------------------------------------------------------------------
 ||
 ||   Constructors:  
 ||          StudentManager() -- Initializes the student manager and loads students from file.
 ||
 ||  Class Methods:  None
 ||
 ||  Inst. Methods:  
 ||          addStudent(Student student) -- void
 ||          getAllStudents() -- ArrayList<Student>
 ||          displayAllStudents() -- void
 ||          findStudentByID(int studentID) -- Student
 ||          removeStudentByID(int studentID) -- boolean
 ||          updateStudentGpa(int studentID, double newGpa) -- boolean
 ||          sortStudentsByGpa() -- void
 ||          sortStudentsByName() -- void
 ||          loadStudentsFromFile() -- void
 ||          saveStudentsToFile() -- void
 ||
 ++-----------------------------------------------------------------------*/

/*---------------------------------------------------------------------
        |  Method StudentManager
        |
        |  Purpose:  Constructor to initialize the student manager and load students from file.
        |
        |  Pre-condition:  None
        |
        |  Post-condition: Initializes the students list and loads students from the file.
        |
        |  Parameters: None
        |
        |  Returns:  None
        *-------------------------------------------------------------------*/
public class StudentManager {
    private ArrayList<Student> students;
    private static final String FILE_NAME = "students.txt";

    // Constructor
    public StudentManager() {
        students = new ArrayList<>();
        loadStudentsFromFile();
    }

    /*---------------------------------------------------------------------
        |  Method addStudent
        |
        |  Purpose:  Adds a new student to the list and saves the list to the file.
        |
        |  Pre-condition:  The student object must be valid.
        |
        |  Post-condition: The student is added to the list and the list is saved to the file.
        |
        |  Parameters:
        |      student -- The student object to be added.
        |
        |  Returns:  None
        *-------------------------------------------------------------------*/
    public void addStudent(Student student) {
        students.add(student);
        saveStudentsToFile();
    }

    /*---------------------------------------------------------------------
        |  Method getAllStudents
        |
        |  Purpose:  Returns the list of all students.
        |
        |  Pre-condition:  None
        |
        |  Post-condition: Returns the list of all students.
        |
        |  Parameters: None
        |
        |  Returns:  ArrayList<Student> -- The list of all students.
        *-------------------------------------------------------------------*/
    public ArrayList<Student> getAllStudents() {
        return students;
    }

    /*---------------------------------------------------------------------
        |  Method displayAllStudents
        |
        |  Purpose:  Displays all students' details.
        |
        |  Pre-condition:  None
        |
        |  Post-condition: Prints the details of all students.
        |
        |  Parameters: None
        |
        |  Returns:  None
        *-------------------------------------------------------------------*/
    public void displayAllStudents() {
        System.out.printf("%-20s %-20s %-25s %-10s %-10s%n", "ID", "First Name", "Last Name", "GPA", "Grade Level");
        for (Student student : students) {
            student.displayStudentDetails();
        }
    }

    /*---------------------------------------------------------------------
        |  Method findStudentByID
        |
        |  Purpose:  Finds a student by their ID.
        |
        |  Pre-condition:  The student ID must be valid.
        |
        |  Post-condition: Returns the student object if found, otherwise null.
        |
        |  Parameters:
        |      studentID -- The ID of the student to be found.
        |
        |  Returns:  Student -- The student object if found, otherwise null.
        *-------------------------------------------------------------------*/
    public Student findStudentByID(int studentID) {
        for (Student student : students) {
            if (student.getStudentID() == studentID) {
                return student;
            }
        }
        return null;
    }

    /*---------------------------------------------------------------------
        |  Method removeStudentByID
        |
        |  Purpose:  Removes a student by their ID.
        |
        |  Pre-condition:  The student ID must be valid.
        |
        |  Post-condition: The student is removed from the list and the list is saved to the file.
        |
        |  Parameters:
        |      studentID -- The ID of the student to be removed.
        |
        |  Returns:  boolean -- True if the student was removed, otherwise false.
        *-------------------------------------------------------------------*/
    public boolean removeStudentByID(int studentID) {
        Student student = findStudentByID(studentID);
        if (student != null) {
            students.remove(student);
            saveStudentsToFile();
            return true;
        }
        return false;
    }

    /*---------------------------------------------------------------------
        |  Method updateStudentGpa
        |
        |  Purpose:  Updates a student's GPA.
        |
        |  Pre-condition:  The student ID must be valid and the new GPA must be a valid value.
        |
        |  Post-condition: The student's GPA is updated and the list is saved to the file.
        |
        |  Parameters:
        |      studentID -- The ID of the student whose GPA is to be updated.
        |      newGpa -- The new GPA value.
        |
        |  Returns:  boolean -- True if the GPA was updated, otherwise false.
        *-------------------------------------------------------------------*/
    public boolean updateStudentGpa(int studentID, double newGpa) {
        Student student = findStudentByID(studentID);
        if (student != null) {
            student.setGpa(newGpa);
            saveStudentsToFile();
            return true;
        }
        return false;
    }

    /*---------------------------------------------------------------------
        |  Method sortStudentsByGpa
        |
        |  Purpose:  Sorts the students by their GPA.
        |
        |  Pre-condition:  None
        |
        |  Post-condition: The students are sorted by their GPA.
        |
        |  Parameters: None
        |
        |  Returns:  None
        *-------------------------------------------------------------------*/
    public void sortStudentsByGpa() {
        students.sort(Comparator.comparingDouble(Student::getGpa));
    }

    /*---------------------------------------------------------------------
        |  Method sortStudentsByName
        |
        |  Purpose:  Sorts the students by their first and last names.
        |
        |  Pre-condition:  None
        |
        |  Post-condition: The students are sorted by their first and last names.
        |
        |  Parameters: None
        |
        |  Returns:  None
        *-------------------------------------------------------------------*/
    public void sortStudentsByName() {
        students.sort(Comparator.comparing(Student::getFirstName).thenComparing(Student::getLastName));
    }

    /*---------------------------------------------------------------------
        |  Method loadStudentsFromFile
        |
        |  Purpose:  Loads students from the file.
        |
        |  Pre-condition:  The file must exist and be readable.
        |
        |  Post-condition: The students are loaded from the file into the list.
        |
        |  Parameters: None
        |
        |  Returns:  None
        *-------------------------------------------------------------------*/
    private void loadStudentsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String firstName = data[0];
                String lastName = data[1];
                int studentID = Integer.parseInt(data[2]);
                double gpa = Double.parseDouble(data[3]);
                int gradeLevel = Integer.parseInt(data[4]);
                students.add(new Student(firstName, lastName, studentID, gpa, gradeLevel));
            }
        } catch (IOException e) {
            System.out.println("Error loading students from file: " + e.getMessage());
        }
    }

    /*---------------------------------------------------------------------
        |  Method saveStudentsToFile
        |
        |  Purpose:  Saves the students to the file.
        |
        |  Pre-condition:  The file must be writable.
        |
        |  Post-condition: The students are saved to the file.
        |
        |  Parameters: None
        |
        |  Returns:  None
        *-------------------------------------------------------------------*/
    public void saveStudentsToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student student : students) {
                pw.println(student.getFirstName() + "," + student.getLastName() + "," + student.getStudentID() + "," + student.getGpa() + "," + student.getGradeLevel());
            }
        } catch (IOException e) {
            System.out.println("Error saving students to file: " + e.getMessage());
        }
    }
}
