import java.sql.*;
import java.util.*;

public class DB {

    private static final String URL = "jdbc:mysql://localhost:3306/school";
    private static final String USER = "root";
    private static final String PASS = "Shourya@123";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // ===================== SUBJECTS =====================

    public static ArrayList<String> fetchSubjects() {
        ArrayList<String> subjects = new ArrayList<>();
        String sql = "SELECT name FROM subjects";

        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                subjects.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjects;
    }

    public static void viewSubjects() {
        String sql = "SELECT id, name FROM subjects";

        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n--- Subjects ---");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + ". " + rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addSubject(String name) {
        String sql = "INSERT INTO subjects (name) VALUES (?)";

        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.executeUpdate();
            System.out.println("Subject added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateSubject(int id, String newName) {
        String sql = "UPDATE subjects SET name=? WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, newName);
            pst.setInt(2, id);
            pst.executeUpdate();
            System.out.println("Subject updated successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteSubject(int id) {
        String sql = "DELETE FROM subjects WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Subject deleted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ===================== STUDENTS =====================

    public static void saveStudent(Student s) {
        String sql = "INSERT INTO students VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, s.rollNo);
            pst.setString(2, s.name);

            // save marks (max 5 for now)
            for (int i = 0; i < 5; i++) {
                pst.setInt(3 + i, i < s.marks.length ? s.marks[i] : 0);
            }

            pst.setInt(8, s.total);
            pst.setDouble(9, s.percentage);
            pst.setString(10, String.valueOf(s.grade));

            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Student> fetchAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Student s = new Student();
                s.rollNo = rs.getInt("rollNo");
                s.name = rs.getString("name");

                s.marks = new int[Student.subjects.size()];

                for (int i = 0; i < s.marks.length; i++) {
                    s.marks[i] = rs.getInt("mark" + (i + 1));
                }

                s.total = rs.getInt("total");
                s.percentage = rs.getDouble("percentage");
                s.grade = rs.getString("grade").charAt(0);

                students.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    // Delete student
    public static void deleteStudent(int rollNo) {
        String sql = "DELETE FROM students WHERE rollNo=?";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, rollNo);
            int rows = pst.executeUpdate();

            if (rows > 0)
                System.out.println("Student deleted successfully!");
            else
                System.out.println("No student found with this roll number.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update student marks
    public static void updateStudentMarks(int rollNo, int[] marks, int total, double percentage, char grade) {
        String sql = "UPDATE students SET mark1=?, mark2=?, mark3=?, mark4=?, mark5=?, total=?, percentage=?, grade=? WHERE rollNo=?";

        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            for (int i = 0; i < 5; i++) {
                pst.setInt(1 + i, i < marks.length ? marks[i] : 0);
            }
            pst.setInt(6, total);
            pst.setDouble(7, percentage);
            pst.setString(8, String.valueOf(grade));
            pst.setInt(9, rollNo);

            int rows = pst.executeUpdate();
            if (rows > 0)
                System.out.println("Student marks updated successfully!");
            else
                System.out.println("No student found with this roll number.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
