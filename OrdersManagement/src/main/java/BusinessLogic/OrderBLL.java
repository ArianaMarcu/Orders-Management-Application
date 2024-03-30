package BusinessLogic;

import Connection.ConnectionFactory;
import Model.Order;
/**
 * The OrderBLL class handles business logic operations related to orders.
 */
public class OrderBLL {
    private ConnectionFactory connectionFactory = null;

    /**
     * Constructor for the OrderBLL class.
     *
     * @param c The connection factory used to establish a database connection.
     */
    public OrderBLL(ConnectionFactory c) {
        connectionFactory = c;
    }

    /**
     * Inserts an order into the database.
     *
     * @param o The order object to be inserted.
     */
    public void insert(Order o) {
        String sql = "INSERT INTO orders (idPerson, idProdus, cantitate) VALUES (" + o.getIdPerson() + "," +
                o.getIdProdus() + "," + o.getCantitate() + ")";
        connectionFactory.executeQuery(sql);
    }

    /**
     * Deletes orders from the database based on the specified person ID.
     *
     * @param idPerson The ID of the person whose orders are to be deleted.
     */
    public void delete(int idPerson) {
        String sql = "DELETE FROM orders WHERE idPerson=" + idPerson;
        connectionFactory.executeQuery(sql);
    }
}

