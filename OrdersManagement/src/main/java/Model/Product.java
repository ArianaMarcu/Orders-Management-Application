package Model;

/**
 * The Product class represents a product entity.
 */
public class Product {
    private int idProdus;
    private String numeProdus;
    private String categorie;
    private float pret;
    private int cantitate;

    /**
     * Constructs a Product object with the specified ID, name, category, price, and quantity.
     *
     * @param idProdus     The ID of the product.
     * @param numeProdus   The name of the product.
     * @param categorie    The category of the product.
     * @param pret         The price of the product.
     * @param cantitate    The quantity of the product.
     */
    public Product(int idProdus, String numeProdus, String categorie, float pret, int cantitate) {
        this.idProdus = idProdus;
        this.numeProdus = numeProdus;
        this.categorie = categorie;
        this.pret = pret;
        this.cantitate = cantitate;
    }

    /**
     * Constructs a Product object with the specified name, category, price, and quantity.
     *
     * @param numeProdus   The name of the product.
     * @param categorie    The category of the product.
     * @param pret         The price of the product.
     * @param cantitate    The quantity of the product.
     */
    public Product(String numeProdus, String categorie, float pret, int cantitate) {
        this.numeProdus = numeProdus;
        this.categorie = categorie;
        this.pret = pret;
        this.cantitate = cantitate;
    }

    /**
     * Returns the ID of the product.
     *
     * @return The ID of the product.
     */
    public int getIdProdus() {
        return idProdus;
    }

    /**
     * Sets the ID of the product.
     *
     * @param idProdus The ID of the product.
     */
    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    /**
     * Returns the name of the product.
     *
     * @return The name of the product.
     */
    public String getNumeProdus() {
        return numeProdus;
    }

    /**
     * Sets the name of the product.
     *
     * @param numeProdus The name of the product.
     */
    public void setNumeProdus(String numeProdus) {
        this.numeProdus = numeProdus;
    }

    /**
     * Returns the category of the product.
     *
     * @return The category of the product.
     */
    public String getCategorie() {
        return categorie;
    }

    /**
     * Sets the category of the product.
     *
     * @param categorie The category of the product.
     */
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    /**
     * Returns the price of the product.
     *
     * @return The price of the product.
     */
    public float getPret() {
        return pret;
    }

    /**
     * Sets the price of the product.
     *
     * @param pret The price of the product.
     */
    public void setPret(float pret) {
        this.pret = pret;
    }

    /**
     * Returns the quantity of the product.
     *
     * @return The quantity of the product.
     */
    public int getCantitate() {
        return cantitate;
    }

    /**
     * Sets the quantity of the product.
     *
     * @param cantitate The quantity of the product.
     */
    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    /**
     * Returns a string representation of the Product object.
     *
     * @return A string representation of the Product object.
     */
    @Override
    public String toString() {
        return "Product{" +
                "idProdus=" + idProdus +
                ", numeProdus='" + numeProdus + '\'' +
                ", categorie='" + categorie + '\'' +
                ", pret=" + pret +
                '}';
    }
}
