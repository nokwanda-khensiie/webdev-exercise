package wethinkcode.persistence;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Exercise 3.1
 */
public class Tables {
    private final Connection connection;

    /**
     * Create an instance of the Tables object using the provided database connection
     * @param connection The JDBC connection to use
     */
    public Tables(Connection connection) {
        this.connection = connection;
    }

    /**
     * 3.1 Complete this method
     *
     * Create the Genres table
     * @return true if the table was successfully created, otherwise false
     */
    public boolean createGenres() {

        return createTable("CREATE TABLE Genres (\n" +
                "    code TEXT NOT NULL PRIMARY KEY,\n" +
                "    description TEXT NOT NULL\n" +
                ");");
    }

    /**
     * 3.1 Complete this method
     *
     * Create the Books table
     * @return true if the table was successfully created, otherwise false
     */


    public boolean createBooks() {
        createGenres();
        return createTable("""
                CREATE TABLE Books (
                    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    genre_code TEXT NOT NULL,
                    FOREIGN KEY (genre_code) REFERENCES Genres(code)
                );""");
    }

    /**
     * 3.1 Complete this method
     *
     * Execute a SQL statement containing an SQL command to create a table.
     * If the SQL statement is not a create statement, it should return false.
     *
     * @param sql the SQL statement containing the create command
     * @return true if the command was successfully executed, else false
     */
    protected boolean createTable(String sql) {
        if (!sql.contains("CREATE")){
            return false;
        }
        try(final Statement table = connection.createStatement()){
            table.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
}
