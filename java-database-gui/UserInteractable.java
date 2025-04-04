import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


public class UserInteractable{

	public static String infoCollection(){
		String connection = "";
		try{

			Scanner scanner = new Scanner(System.in);
			System.out.print("Please enter the name of your database: ");
			String db = scanner.nextLine();

			System.out.print("Please enter the port of your database: ");
			String port = scanner.nextLine();
			
			System.out.print("Please enter the user name: ");
			String user = scanner.nextLine();

			System.out.print("Please enter the password for that user: ");
			String pwd = scanner.nextLine();

			Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
			
			connection = "jdbc:mysql://localhost:" + port + "/" + db + "?" + "user=" + user + "&password=" + pwd;
			//Statement stmt = conn.createStatement();
			//ResultSet rs = stmt.executeQuery("SELECT * FROM book;");
			//ResultSetMetaData meta = rs.getMetaData();

			//String result = DisplayFormatting.tableDisplay(rs, meta);
			//System.out.println(result);
			//return connection;

		}
                catch(Exception e){
                        System.out.println("Unknown Error:" + e.getMessage());
                }
		return connection;
	}
}
