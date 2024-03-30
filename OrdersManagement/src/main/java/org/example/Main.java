package org.example;

import BusinessLogic.ClientBLL;
import BusinessLogic.ProductBLL;
import Connection.ConnectionFactory;
import Model.Client;
import Model.Product;
import Presentation.GUI;
import Start.Reflection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Main class represents the entry point of the application.
 */
public class Main {
    protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    /**
     * The main method is the entry point of the application.
     *
     * @param args The command line arguments.
     * @throws SQLException if a database access error occurs.
     */
    public static void main(String[] args) throws SQLException {
        ConnectionFactory c = new ConnectionFactory();

        /*ResultSet resultSet = c.selectQuery("SELECT * FROM produs");
        ArrayList<Product> produse = mapToProductList(resultSet);
        System.out.println(produse);*/

        //ProductBLL productBLL = new ProductBLL(c);
        //productBLL.insert(new Product(4, "nume2" , "categ",45.9f));
        //productBLL.insert(new Product(15, "sampon" , "head",15.15f));
        //////productBLL.delete(2);
        //productBLL.update(new Product(5, "Cami", "sampon", 100));

        //clientBLL.insert(new Client(1, "Ari" , "arimarcu@gmail.com", "adresamea"));

        /*ClientBLL clientBLL = new ClientBLL(c);
        Client client1 = null;
        try {
            client1 = clientBLL.findClientById(10);
        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
        }
        Reflection.retrieveProperties(client1);*/

        GUI gui = new GUI();
    }
}
