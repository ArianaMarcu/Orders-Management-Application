package BusinessLogic;

import Connection.ConnectionFactory;
import Model.Product;

import java.sql.ResultSet;
import java.util.ArrayList;
/**
 * The ProductBLL class provides business logic methods for managing products in the database.
 */
public class ProductBLL {
    private ConnectionFactory connectionFactory = null;
    /**
     * Constructor for the ProductBLL class.
     *
     * @param connection The connection factory used to establish a database connection.
     */

    public ProductBLL(ConnectionFactory connection) {
        connectionFactory = connection;
    }
    /**
     * Inserts a product into the database.
     *
     * @param product The product object to be inserted.
     */
    public void insert(Product product) {
        String sql = "INSERT INTO produs(nume, categorie, pret, cantitate)values('" + product.getNumeProdus() + "','" +
                product.getCategorie() + "'," + product.getPret() + "," + product.getCantitate() + ")";
        ConnectionFactory.executeQuery(sql);
    }
    /**
     * Deletes a product from the database based on the specified ID.
     *
     * @param id The ID of the product to be deleted.
     */
    public void delete(int id) {
        String sql = "DELETE FROM produs where id=" + id;
        ConnectionFactory.executeQuery(sql);
    }
    /**
     * Updates the information of a product in the database.
     *
     * @param p The product object containing the updated information.
     */
    public void update(Product p) {
        String sql = "UPDATE produs SET nume= '" + p.getNumeProdus() + "',categorie='" + p.getCategorie() + "',pret=" +
                p.getPret() + ",cantitate=" + p.getCantitate() + " where id=" + p.getIdProdus();
        ConnectionFactory.executeQuery(sql);
    }
    /**
     * Retrieves all products from the database.
     *
     * @return A ResultSet containing the product records.
     */

    public ResultSet view() {
        String sql = "SELECT * FROM produs";
        ResultSet resultSet = ConnectionFactory.selectQuery(sql);
        return resultSet;
    }
    /**
     * Retrieves the names of all products from the database.
     *
     * @return An ArrayList containing the names of all products.
     */
    public ArrayList<String> readProductNames() {
        ArrayList<String> productNames = new ArrayList<>();
        String sql = "SELECT nume from produs";
        ResultSet resultSet = ConnectionFactory.selectQuery(sql);
        try {
            while (resultSet.next())
                productNames.add(resultSet.getString("nume"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return productNames;
    }
    /**
     * Retrieves the ID of a product based on its name.
     *
     * @param nume The name of the product.
     * @return The ID of the product.
     */
    public int getIdByName(String nume) {
        String sql = "SELECT id FROM produs WHERE nume='" + nume + "'";
        ResultSet rs = ConnectionFactory.selectQuery(sql);
        try {
            while (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }
    /**
     * Retrieves a product from the database based on the specified ID.
     *
     * @param id The ID of the product to retrieve.
     * @return The product object.
     */
    public Product getById(int id) {
        String sql = "SELECT * FROM produs where id=" + id;
        ResultSet rs = ConnectionFactory.selectQuery(sql);
        Product product = null;
        try {
            while (rs.next()) {
                product = new Product(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getString("categorie"),
                        rs.getFloat("pret"),
                        rs.getInt("cantitate"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return product;
    }
    /**
     * Updates the quantity of a product in the database.
     *
     * @param idProdus  The ID of the product.
     * @param newValue  The new value for the quantity.
     */
    public void updateCantitate(int idProdus, int newValue)
    {
        String sql = "UPDATE produs SET cantitate=" + newValue + " where id=" + idProdus;
        connectionFactory.executeQuery(sql);
    }
}
