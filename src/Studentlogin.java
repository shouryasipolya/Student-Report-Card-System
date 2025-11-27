import java.sql.*;

public class Studentlogin {

    public static boolean login(int rollNo) {
        try {
            Connection con = DB.getConnection();

            String sql = "SELECT * FROM students WHERE rollNo=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, rollNo);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch (Exception e) {
            System.out.println("Student Login Error: " + e.getMessage());
            return false;
        }
    }
}
