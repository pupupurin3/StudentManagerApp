/*+----------------------------------------------------------------------
 ||
 ||  Class Student 
 ||
 ||         Author:  Noah Matsukuma
 ||
 ||        Purpose:  This class represents a student with attributes such as
 ||                  first name, last name, student ID, GPA, and grade level.
 ||
 ||  Inherits From:  None
 ||
 ||     Interfaces:  None
 |+-----------------------------------------------------------------------
 ||
 ||      Constants:  None
 ||
 |+-----------------------------------------------------------------------
 ||
 ||   Constructors:  
 ||      Student(String firstName, String lastName, int studentID, double gpa, int gradeLevel)
 ||
 ||  Class Methods:  None
 ||
 ||  Inst. Methods:  
 ||      String getFirstName()
 ||      void setFirstName(String firstName)
 ||      String getLastName()
 ||      void setLastName(String lastName)
 ||      int getStudentID()
 ||      void setStudentID(int studentID)
 ||      double getGpa()
 ||      void setGpa(double gpa)
 ||      int getGradeLevel()
 ||      void setGradeLevel(int gradeLevel)
 ||      void displayStudentDetails()
 ||
 ++-----------------------------------------------------------------------*/

public class Student {
    private String firstName;
    private String lastName;
    private int studentID;
    private double gpa;
    private int gradeLevel;

    // Constructor
    /*---------------------------------------------------------------------
    |  Method Student
    |
    |  Purpose:  Initializes a new instance of the Student class with the
    |            provided first name, last name, student ID, GPA, and grade level.
    |
    |  Pre-condition:  None
    |
    |  Post-condition: A new Student object is created with the specified attributes.
    |
    |  Parameters:
    |      firstName -- The first name of the student.
    |      lastName -- The last name of the student.
    |      studentID -- The unique ID of the student.
    |      gpa -- The GPA of the student.
    |      gradeLevel -- The grade level of the student.
    |
    |  Returns:  None
    *-------------------------------------------------------------------*/
    public Student(String firstName, String lastName, int studentID, double gpa, int gradeLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        this.gpa = gpa;
        this.gradeLevel = gradeLevel;
    }

    // Getters and Setters
    /*---------------------------------------------------------------------
    |  Method getFirstName
    |
    |  Purpose:  Returns the first name of the student.
    |
    |  Pre-condition:  None
    |
    |  Post-condition: The first name of the student is returned.
    |
    |  Parameters:  None
    |
    |  Returns:  The first name of the student.
    *-------------------------------------------------------------------*/
    public String getFirstName() {
        return firstName;
    }

    /*---------------------------------------------------------------------
    |  Method setFirstName
    |
    |  Purpose:  Sets the first name of the student.
    |
    |  Pre-condition:  None
    |
    |  Post-condition: The first name of the student is updated.
    |
    |  Parameters:
    |      firstName -- The new first name of the student.
    |
    |  Returns:  None
    *-------------------------------------------------------------------*/
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /*---------------------------------------------------------------------
    |  Method getLastName
    |
    |  Purpose:  Returns the last name of the student.
    |
    |  Pre-condition:  None
    |
    |  Post-condition: The last name of the student is returned.
    |
    |  Parameters:  None
    |
    |  Returns:  The last name of the student.
    *-------------------------------------------------------------------*/
    public String getLastName() {
        return lastName;
    }

    /*---------------------------------------------------------------------
    |  Method setLastName
    |
    |  Purpose:  Sets the last name of the student.
    |
    |  Pre-condition:  None
    |
    |  Post-condition: The last name of the student is updated.
    |
    |  Parameters:
    |      lastName -- The new last name of the student.
    |
    |  Returns:  None
    *-------------------------------------------------------------------*/
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /*---------------------------------------------------------------------
    |  Method getStudentID
    |
    |  Purpose:  Returns the student ID.
    |
    |  Pre-condition:  None
    |
    |  Post-condition: The student ID is returned.
    |
    |  Parameters:  None
    |
    |  Returns:  The student ID.
    *-------------------------------------------------------------------*/
    public int getStudentID() {
        return studentID;
    }

    /*---------------------------------------------------------------------
    |  Method setStudentID
    |
    |  Purpose:  Sets the student ID.
    |
    |  Pre-condition:  None
    |
    |  Post-condition: The student ID is updated.
    |
    |  Parameters:
    |      studentID -- The new student ID.
    |
    |  Returns:  None
    *-------------------------------------------------------------------*/
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    /*---------------------------------------------------------------------
    |  Method getGpa
    |
    |  Purpose:  Returns the GPA of the student.
    |
    |  Pre-condition:  None
    |
    |  Post-condition: The GPA of the student is returned.
    |
    |  Parameters:  None
    |
    |  Returns:  The GPA of the student.
    *-------------------------------------------------------------------*/
    public double getGpa() {
        return gpa;
    }

    /*---------------------------------------------------------------------
    |  Method setGpa
    |
    |  Purpose:  Sets the GPA of the student.
    |
    |  Pre-condition:  None
    |
    |  Post-condition: The GPA of the student is updated.
    |
    |  Parameters:
    |      gpa -- The new GPA of the student.
    |
    |  Returns:  None
    *-------------------------------------------------------------------*/
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    /*---------------------------------------------------------------------
    |  Method getGradeLevel
    |
    |  Purpose:  Returns the grade level of the student.
    |
    |  Pre-condition:  None
    |
    |  Post-condition: The grade level of the student is returned.
    |
    |  Parameters:  None
    |
    |  Returns:  The grade level of the student.
    *-------------------------------------------------------------------*/
    public int getGradeLevel() {
        return gradeLevel;
    }

    /*---------------------------------------------------------------------
    |  Method setGradeLevel
    |
    |  Purpose:  Sets the grade level of the student.
    |
    |  Pre-condition:  None
    |
    |  Post-condition: The grade level of the student is updated.
    |
    |  Parameters:
    |      gradeLevel -- The new grade level of the student.
    |
    |  Returns:  None
    *-------------------------------------------------------------------*/
    public void setGradeLevel(int gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    // Method to display student details
    /*---------------------------------------------------------------------
    |  Method displayStudentDetails
    |
    |  Purpose:  Displays the details of the student in a formatted manner.
    |
    |  Pre-condition:  None
    |
    |  Post-condition: The student details are printed to the console.
    |
    |  Parameters:  None
    |
    |  Returns:  None
    *-------------------------------------------------------------------*/
    public void displayStudentDetails() {
        System.out.printf("%-10d %-15s %-15s %-10.2f %-10d%n", studentID, firstName, lastName, gpa, gradeLevel);
    }
}
