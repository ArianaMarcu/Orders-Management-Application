package Connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * The ConnectionFactory class provides methods for establishing and managing the database connection.
 */
public class ConnectionFactory {
    //The class contains the name of the driver(initialized through reflection), the database location(DBURL)
    //the user and the password for accessing the MySQL Server
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://127.0.0.1:3306/ordersmanagement";
    private static final String USER = "root";
    private static final String PASS = "Anca73#@Adi74";
    public static ConnectionFactory singleInstance = new ConnectionFactory();
    public static Connection connection;
    /**
     * Constructs a new ConnectionFactory instance and initializes the database connection.
     */
    public ConnectionFactory()
    {
        init();
    }  //The connection to the DB will be placed in a Singleton object
    /**
     * Initializes the database connection.
     *
     * @return The established database connection.
     */
    public static Connection init()
    {
        try{
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        }catch(Exception e){
            LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
            System.out.println(e.getMessage());
        }
        return connection;
    }
    /**
     * Retrieves the database connection.
     *
     * @return The database connection.
     */
    public static Connection getConnection() {
        return singleInstance.init();
    }
    /**
     * Prepares a SQL statement for execution.
     *
     * @param sql The SQL query or statement.
     * @return The prepared statement.
     * @throws SQLException if a database access error occurs.
     */
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
    /**
     * Executes a SQL query or statement.
     *
     * @param s The SQL query or statement to execute.
     */
    public static void executeQuery(String s){
        try{
            Statement statement = connection.createStatement();
            statement.execute(s);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * Executes a SELECT SQL query and returns the result set.
     *
     * @param s The SELECT SQL query to execute.
     * @return The result set of the executed query.
     */
    public static ResultSet selectQuery(String s){
        try{
            Statement statement = connection.createStatement();
            return statement.executeQuery(s);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    /**
     * Closes the database connection.
     *
     * @param connection The database connection to close.
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
            }
        }
    }
    /**
     * Closes the statement.
     *
     * @param statement The statement to close.
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
            }
        }
    }
    /**
     * Closes the result set.
     *
     * @param resultSet The result set to close.
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
            }
        }
    }


}
