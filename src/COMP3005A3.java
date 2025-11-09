import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.time.LocalDate;


public class COMP3005A3 {

    private final String url = "jdbc:postgresql://localhost:5432/a3";
    private final String user = "postgres";
    private final String password = "password";

    public void getAllStudents() {
        String SQL = "SELECT * FROM students";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            var rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getInt("student_id") + "\t" +
                                   rs.getString("first_name") + "\t" +
                                   rs.getString("last_name") + "\t" +
                                   rs.getString("email") + "\t" +
                                   rs.getDate("enrollment_date"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Add a user
    public void addStudent(String first_name, String last_name, String email, String enrollment_date) {
        String SQL = "INSERT INTO students(first_name,last_name,email, enrollment_date) VALUES(?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
        PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            System.out.println(enrollment_date.split("-")[0]);
            int year = Integer.parseInt(enrollment_date.split("-")[0]);
            int month = Integer.parseInt(enrollment_date.split("-")[1]);
            int day = Integer.parseInt(enrollment_date.split("-")[2]);
           

            LocalDate date = LocalDate.of(year, month, day);

            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, email);
            pstmt.setObject(4, date);
            pstmt.executeUpdate();
            System.out.println("Student added successfully!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Update user's email based on name
    public void updateStudentEmail(int student_id, String new_email) {
        String SQL = "UPDATE students SET email = ? WHERE student_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, student_id);
            pstmt.setString(2, new_email);
            pstmt.executeUpdate();
            System.out.println("Student email updated!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Delete user based on name
    public void deleteStudent(int student_id) {
        String SQL = "DELETE FROM students WHERE student_id=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, student_id);
            pstmt.executeUpdate();
            System.out.println("Student deleted!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        COMP3005A3 dbOps = new COMP3005A3();
        Scanner scanner = new Scanner(System.in);
        while (true){

            dbOps.getAllStudents();
            
            System.out.println("Select an option:");
            System.out.println("1. Add Student");
            System.out.println("2. Update Student Email"); 
            System.out.println("3. Delete Student");
            System.out.println("4. Exit");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                System.out.println("Enter a First Name:");
                String first_name = scanner.nextLine();
                System.out.println("Enter a Last Name:");
                String last_name = scanner.nextLine();
                System.out.println("Enter an Email:");
                String email = scanner.nextLine();
                System.out.println("Enter an Enrollment Date (YYYY-MM-DD):");
                String enrollment_date = scanner.nextLine();
                dbOps.addStudent(first_name, last_name, email, enrollment_date);
            }
        
            else if (choice == 2){
                System.out.println("Enter Student ID to update email:");
                int student_id = Integer.parseInt(scanner.nextLine());
                System.out.println("Enter new Email:");
                String new_email = scanner.nextLine();
                dbOps.updateStudentEmail(student_id, new_email);
            }
        
            else if (choice == 3){
                System.out.println("Enter Student ID to delete:");
                int student_id = Integer.parseInt(scanner.nextLine());
                dbOps.deleteStudent(student_id);
            }

            else if (choice == 4){
                break;
            }
        }
        scanner.close();
    }
}

