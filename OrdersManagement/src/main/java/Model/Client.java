package Model;
/**
 * The Client class represents a client entity.
 */
public class Client {
    private int idClient;
    private String nume;
    private String email;
    private String adresa;
    /**
     * Constructs a Client object with the specified ID, name, email, and address.
     *
     * @param idClient The ID of the client.
     * @param nume     The name of the client.
     * @param email    The email address of the client.
     * @param adresa   The address of the client.
     */
    public Client(int idClient, String nume, String email, String adresa) {
        this.idClient = idClient;
        this.nume = nume;
        this.email = email;
        this.adresa = adresa;
    }
    /**
     * Constructs a Client object with the specified name, email, and address.
     *
     * @param nume   The name of the client.
     * @param email  The email address of the client.
     * @param adresa The address of the client.
     */
    public Client(String nume, String email, String adresa) {
        this.nume = nume;
        this.email = email;
        this.adresa = adresa;
    }
    /**
     * Returns the ID of the client.
     *
     * @return The ID of the client.
     */
    public int getIdClient() {
        return idClient;
    }
    /**
     * Sets the ID of the client.
     *
     * @param idClient The ID of the client.
     */
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
    /**
     * Returns the name of the client.
     *
     * @return The name of the client.
     */
    public String getNume() {
        return nume;
    }
    /**
     * Sets the name of the client.
     *
     * @param nume The name of the client.
     */
    public void setNume(String nume) {
        this.nume = nume;
    }
    /**
     * Returns the email address of the client.
     *
     * @return The email address of the client.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the email address of the client.
     *
     * @param email The email address of the client.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Returns the address of the client.
     *
     * @return The address of the client.
     */
    public String getAdresa() {
        return adresa;
    }
    /**
     * Sets the address of the client.
     *
     * @param adresa The address of the client.
     */
    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
}
