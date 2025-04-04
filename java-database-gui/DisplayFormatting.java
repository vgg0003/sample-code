import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;


public class DisplayFormatting{

public static String columnNameDisplay(ResultSetMetaData meta, int column, int maxLen){
                String name = "";

                try {
                        //System.out.println("The display maxlen: " + Integer.toString(maxLen));
                        name = meta.getColumnName(column);
                        if (name.length() < maxLen){
                                name = " ".repeat(maxLen - name.length()) + name;
                        }
                        //System.out.println("The actual length of the display name: " + Integer.toString(name.length()));

                }catch (SQLException ex) {
                        System.out.println("SQLException: " + ex.getMessage());
                        System.out.println("SQLState: " + ex.getSQLState());
                        System.out.println("VendorError: " +ex.getErrorCode());
                }
                catch(Exception e){
                        System.out.println("Unknown Error:" + e.getMessage());
                }

        return name;
        }

        public static String cellValueDisplay(ResultSet rs, int column, int maxLen){
                String cell = "";
                try {
                        cell = rs.getString(column);
                        if (cell == null){
                                cell = " ".repeat(maxLen - 4) + "NULL";
                        }
                        if (cell.length() < maxLen){
                                cell = " ".repeat(maxLen - cell.length()) + cell;
                        }
                        //System.out.println("maxlen given to cell value display: " + Integer.toString(maxLen));
                        //System.out.println("Actual length of the cell: " + Integer.toString(cell.length()));

                }catch (SQLException ex){
                        System.out.println("SQLException: " + ex.getMessage());
                        System.out.println("SQLState: " + ex.getSQLState());
                        System.out.println("VendorError: " +ex.getErrorCode());
                }
                catch(Exception e){
                        System.out.println("Unknown Error:" + e.getMessage());
                }
                return cell;
        }



        public static String rowValueDisplay(ResultSet rs, ResultSetMetaData meta, int numColumns, int[] maxLens){
                String result = "";
                int i = 1;

                while (i <= numColumns){

                        result += cellValueDisplay(rs, i, maxLens[i-1]);
                        if (i != numColumns) {
                                result += " | ";
                        }
                        i++;
                }


                return (result + "\n");
	}

	public static int getLargestStringInColumn(ResultSet rs, int column){
                int maxLen = 0;
                try {
                        maxLen = rs.getMetaData().getColumnName(column).length() + 3;
                        while(rs.next()){
                                if (rs.getString(column).length() + 3 > maxLen){
                                        maxLen = rs.getString(column).length() + 3;
                                }
                        }

                }catch (SQLException ex){
                        System.out.println("SQLException: " + ex.getMessage());
                        System.out.println("SQLState: " + ex.getSQLState());
                        System.out.println("VendorError: " +ex.getErrorCode());
                }
                catch(Exception e){
                        System.out.println("Unknown Error:" + e.getMessage());
                }
                return maxLen;

        }

        public static String columnAllNameFormat(ResultSet rs, ResultSetMetaData meta, int numColumns, int[] maxLens){
                String names = "";
                int i = 1;

                while (i <= numColumns) {

                        names += columnNameDisplay(meta, i, maxLens[i-1]);
                        if (i != numColumns){
                                names += " | ";
                        }
                        i++;
                }
                return names;


        }

	public static String tableDisplay(ResultSet rs, ResultSetMetaData meta){
                String table = "";
                try{
                        int numColumns = meta.getColumnCount();
                        int[] maxLens = new int[numColumns];
                        int k = 0;
                        //System.out.println(numColumns);
                        while (k < numColumns){
                                //System.out.println(k);
                                maxLens[k] = getLargestStringInColumn(rs, k + 1);
                                //System.out.println(maxLens[k]);
                                k++;
                        }


                        table += columnAllNameFormat(rs, meta, numColumns, maxLens) + "\n";
                        rs.first();
			table += rowValueDisplay(rs, meta, numColumns, maxLens);
                        while (rs.next()){
				//System.out.println(rs.getRow());
                                table += rowValueDisplay(rs, meta, numColumns, maxLens);
                        }


                }catch (SQLException ex){
                System.out.println("SQLException: " + ex.getMessage());
                        System.out.println("SQLState: " + ex.getSQLState());
                        System.out.println("VendorError: " +ex.getErrorCode());
                }
                catch(Exception e){
                        System.out.println("Unknown Error:" + e.getMessage());
                }
                //System.out.println(table);
                return table;
        }



}
