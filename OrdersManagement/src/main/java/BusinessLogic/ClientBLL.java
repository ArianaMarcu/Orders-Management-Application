package BusinessLogic;

import BusinessLogic.Validators.EmailValidator;
import Connection.ConnectionFactory;
import DataAccess.ClientDAO;
import Model.Client;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import BusinessLogic.Validators.EmailValidator;
import BusinessLogic.Validators.Validator;
import DataAccess.ClientDAO;
import Model.Client;
import Model.Product;

import javax.swing.*;
/**
 * The ClientBLL class handles business logic operations related to clients.
 */
public class ClientBLL {
    private ConnectionFactory connectionFactory = null;
    private ClientDAO clientDAO;

    /**
     * Constructor for the ClientBLL class.
     *
     * @param connection The connection factory used to establish a database connection.
     */
    public ClientBLL(ConnectionFactory connection) {
        clientDAO = new ClientDAO();
        connectionFactory = connection;
    }

    /**
     * Inserts a client into the database.
     *
     * @param client The client object to be inserted.
     */
    public void insert(Client client) {
        String sql = "INSERT INTO person(nume, email, adresa) VALUES ('" + client.getNume() + "','" +
                client.getEmail() + "','" + client.getAdresa() + "')";
        connectionFactory.executeQuery(sql);
    }

    /**
     * Deletes a client from the database based on the specified ID.
     *
     * @param id The ID of the client to be deleted.
     */
    public void delete(int id) {
        String sql = "DELETE FROM person WHERE id=" + id;
        connectionFactory.executeQuery(sql);
    }

    /**
     * Updates the information of a client in the database.
     *
     * @param c The client object containing the updated information.
     */
    public void edit(Client c) {
        String sql = "UPDATE person SET nume= '" + c.getNume() + "',email='" + c.getEmail() + "',adresa='" +
                c.getAdresa() + "' WHERE id=" + c.getIdClient();
        connectionFactory.executeQuery(sql);
    }

    /**
     * Retrieves all clients from the database.
     *
     * @return A ResultSet containing the client records.
     */
    public ResultSet view() {
        String sql = "SELECT * FROM person";
        ResultSet resultSet = connectionFactory.selectQuery(sql);
        return resultSet;
    }

    /**
     * Retrieves the names of all clients from the database.
     *
     * @return An ArrayList containing the names of all clients.
     */
    public ArrayList<String> readClientNames() {
        ArrayList<String> clientNames = new ArrayList<>();
        String sql = "SELECT nume FROM person";
        ResultSet rs = connectionFactory.selectQuery(sql);
        try {
            while (rs.next()) {
                clientNames.add(rs.getString("nume"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return clientNames;
    }

    /**
     * Retrieves the ID of a client based on their name.
     *
     * @param nume The name of the client.
     * @return The ID of the client.
     */
    public int getIdByName(String nume) {
        String sql = "SELECT id FROM person WHERE nume='" + nume + "'";
        ResultSet rs = connectionFactory.selectQuery(sql);
        try {
            while (rs.next())
                return rs.getInt("id");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    /**
     * Finds a client in the database based on the specified ID.
     *
     * @param id The ID of the client to find.
     * @return The client object.
     * @throws NoSuchElementException if the client with the specified ID is not found.
     */
    public Client findClientById(int id) {
        Client c = clientDAO.findById(id);
        if (c == null) {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return c;
    }
}

