import java.sql.*;

public class Teacherlogin {

    public static boolean login(String username, String password) {
        try {
            Connection con = DB.getConnection();

            String sql = "SELECT * FROM teachers WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch (Exception e) {
            System.out.println("Teacher Login Error: " + e.getMessage());
            return false;
        }
    }
}
