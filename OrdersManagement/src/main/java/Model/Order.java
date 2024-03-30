package Model;

/**
 * The Order class represents an order entity.
 */
public class Order {
    private int idPerson;
    private int idProdus;
    private int cantitate;

    /**
     * Constructs an Order object with the specified person ID, product ID, and quantity.
     *
     * @param idPerson   The ID of the person associated with the order.
     * @param idProdus   The ID of the product in the order.
     * @param cantitate  The quantity of the product in the order.
     */
    public Order(int idPerson, int idProdus, int cantitate) {
        this.idPerson = idPerson;
        this.idProdus = idProdus;
        this.cantitate = cantitate;
    }

    /**
     * Returns the ID of the person associated with the order.
     *
     * @return The ID of the person associated with the order.
     */
    public int getIdPerson() {
        return idPerson;
    }

    /**
     * Sets the ID of the person associated with the order.
     *
     * @param idPerson The ID of the person associated with the order.
     */
    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    /**
     * Returns the ID of the product in the order.
     *
     * @return The ID of the product in the order.
     */
    public int getIdProdus() {
        return idProdus;
    }

    /**
     * Sets the ID of the product in the order.
     *
     * @param idProdus The ID of the product in the order.
     */
    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    /**
     * Returns the quantity of the product in the order.
     *
     * @return The quantity of the product in the order.
     */
    public int getCantitate() {
        return cantitate;
    }

    /**
     * Sets the quantity of the product in the order.
     *
     * @param cantitate The quantity of the product in the order.
     */
    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }
}
