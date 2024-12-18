package Model;

import java.sql.*;
import java.util.List;

public class QueryLogic {

    Connection con;
    List<Book> books;
    List<Author> authors;
    List<Review> reviews;

    public QueryLogic(Connection con) {
        this.con = con;
    }



    public static void selectAllFromAuthorQuery(Connection con) throws SQLException {
        try (Statement stmt = con.createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery("SELECT * FROM T_Author");

            // Get the attribute names
            ResultSetMetaData metaData = rs.getMetaData();
            int ccount = metaData.getColumnCount();
            for (int c = 1; c <= ccount; c++) {
                System.out.print(metaData.getColumnName(c) + "\t");
            }
            System.out.println();

            // Get the attribute values
            while (rs.next()) {
                // NB! This is an example, -not- the preferred way to retrieve data.
                // You should use methods that return a specific data type, like
                // rs.getInt(), rs.getString() or such.
                // It's also advisable to store each tuple (row) in an object of
                // custom type (e.g. Employee).
                for (int c = 1; c <= ccount; c++) {
                    System.out.print(rs.getString(c) + "\t");
                }
                System.out.println();
            }
        }
    }
}
