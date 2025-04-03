import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class TouchDatabase{

	public static String runCommand(String command){
		System.out.println(command);
		String result = "";
		try{
                        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                        String db = "databases1";
                        String port = "3306";
                        String pwd = "vgenge";
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:"+port+"/"+db+"?" + "user=root&password=" +pwd);
                        Statement stmt=conn.createStatement();
                        ResultSet rs=stmt.executeQuery(command);

                        while(rs.next()) {
                                //int id= rs.getInt("bookID");
                                //String name = rs.getString("Title");
                                result = rs.getString(1);
                        }

                }catch (SQLException ex) {
                        System.out.println("SQLException: " + ex.getMessage());
                        System.out.println("SQLState: " + ex.getSQLState());
                        System.out.println("VendorError: " +ex.getErrorCode());
                }
                catch(Exception e){
                        System.out.println("Unknown Error:" + e.getMessage());
                }
	return result;
	}
	
	public static void main(String[] args){
		try{
			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			String db = "databases1";
			String port = "3306";
			String pwd = "vgenge";
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:"+port+"/"+db+"?" + "user=root&password=" +pwd);
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("SELECT * FROM book");

			while(rs.next()) {
				int id= rs.getInt("bookID");
				String name = rs.getString("Title");
				System.out.println(id + " -- " + name);
			}

		}catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " +ex.getErrorCode());
		}
		catch(Exception e){
			System.out.println("Unknown Error:" + e.getMessage());
		}
	}
}
