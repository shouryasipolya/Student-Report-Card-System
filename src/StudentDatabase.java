import java.sql.*;

public class StudentDatabase {

    public static void addStudent(Student s) {
        try {
            Connection con = DB.getConnection();

            String sql = "INSERT INTO students VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, s.rollNo);
            ps.setString(2, s.name);
            ps.setInt(3, s.marks[0]);
            ps.setInt(4, s.marks[1]);
            ps.setInt(5, s.marks[2]);
            ps.setInt(6, s.marks[3]);
            ps.setInt(7, s.marks[4]);
            ps.setInt(8, s.total);
            ps.setDouble(9, s.percentage);
            ps.setString(10, String.valueOf(s.grade));

            ps.executeUpdate();
            System.out.println("Student added successfully!");
        }
        catch (Exception e) {
            System.out.println("Add Student Error: " + e.getMessage());
        }
    }

    public static void displayReport(int rollNo) {
        try {
            Connection con = DB.getConnection();

            String sql = "SELECT * FROM students WHERE rollNo=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, rollNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("\n===== Report Card =====");
                System.out.println("Roll No: " + rs.getInt("rollNo"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("-----------------------");
                System.out.println("Sub 1: " + rs.getInt("sub1"));
                System.out.println("Sub 2: " + rs.getInt("sub2"));
                System.out.println("Sub 3: " + rs.getInt("sub3"));
                System.out.println("Sub 4: " + rs.getInt("sub4"));
                System.out.println("Sub 5: " + rs.getInt("sub5"));
                System.out.println("Total: " + rs.getInt("total"));
                System.out.println("Percentage: " + rs.getDouble("percentage"));
                System.out.println("Grade: " + rs.getString("grade"));
            }
            else {
                System.out.println("No student found with roll number " + rollNo);
            }
        }
        catch (Exception e) {
            System.out.println("Display Error: " + e.getMessage());
        }
    }
}
