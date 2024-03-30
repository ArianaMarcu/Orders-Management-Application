package DataAccess;

import Model.Client;

import java.util.logging.Logger;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import Connection.ConnectionFactory;
/**
 * The ClientDAO class handles data access operations related to clients.
 */
public class ClientDAO extends AbstractDAO<Client> {
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO person (nume,email,adresa)"
            + " VALUES (?,?,?)";
    private final static String findStatementString = "SELECT * FROM person where id = ?";
    /**
     * Retrieves a client from the database based on the specified client ID.
     *
     * @param clientId The ID of the client to retrieve.
     * @return The client object.
     */
    public Client findById(int clientId) {
        Client toReturn = null;

        Connection dbConnection = ConnectionFactory.init();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1, clientId);
            rs = findStatement.executeQuery();
            rs.next();

            String name = rs.getString("nume");
            String address = rs.getString("email");
            String email = rs.getString("adresa");
            toReturn = new Client(clientId, name, address, email);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }/*

    public static int insert(Client c) {
        Connection dbConnection = ConnectionFactory.init();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, c.getNume());
            insertStatement.setString(2, c.getEmail());
            insertStatement.setString(3, c.getAdresa());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }*/

}
