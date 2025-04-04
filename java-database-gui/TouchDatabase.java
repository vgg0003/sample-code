import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;

public class TouchDatabase{

	public static String runCommand(String command, String connection){
		//System.out.println(command);
		String result = "";
		try{
                        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
                        //String db = "databases1";
                        //String port = "3306";
                        //String pwd = "vgenge";
                        Connection conn = DriverManager.getConnection(connection);
                        Statement stmt=conn.createStatement();
                        ResultSet rs=stmt.executeQuery(command);
			ResultSetMetaData meta = rs.getMetaData();
			
			
			result = DisplayFormatting.tableDisplay(rs, meta);
                        

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


		
	
}
