package Presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import BusinessLogic.ClientBLL;
import BusinessLogic.OrderBLL;
import Connection.ConnectionFactory;
import DataAccess.ClientDAO;
import Model.Client;
import Model.Product;
/**
 * The ClientGUI class represents the graphical user interface for managing clients.
 */
public class ClientGUI extends JFrame
{
    ClientBLL clientBLL = null;
    OrderBLL orderBLL = null;
    JTable table = new JTable();
    /**
     * Constructs a new ClientGUI object.
     *
     * @param c The ConnectionFactory object used for connecting to the database.
     */
    public ClientGUI(ConnectionFactory c)
    {
        clientBLL = new ClientBLL(c);
        orderBLL = new OrderBLL(c);
        initialize();
    }
    /**
     * Initializes the GUI components.
     */
    public void initialize()
    {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton addClient = new JButton("Add new client");
        addClient.setBounds(180, 124, 150, 50);
        add(addClient);
        addClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ClientGUI.this.addNewClient();
            }
        });
        this.add(addClient);

        JButton editClient = new JButton("Edit a client");
        editClient.setBounds(390, 124, 150, 50);
        editClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ClientGUI.this.editNewClient();
            }
        });
        this.add(editClient);

        JButton deleteClient = new JButton("Delete a client");
        deleteClient.setBounds(600, 124, 150, 50);
        deleteClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ClientGUI.this.deleteNewClient();
            }
        });
        this.add(deleteClient);

        JButton viewClients = new JButton("View clients");
        viewClients.setBounds(690, 284, 150, 100);
        viewClients.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ClientGUI.this.viewAll();
            }
        });
        this.add(viewClients);

        //setVisible(true);
        this.setLayout((LayoutManager) null);
        this.setTitle("Client");
        this.setLocationRelativeTo((Component) null);
        this.setVisible(true);
        this.setBounds(42, 288, 896, 504);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    /**
     * Displays all clients in a table.
     */
    private void viewAll() {
        JFrame viewClients = new JFrame("Clients Table");
        viewClients.setVisible(true);
        viewClients.setBounds(250, 350, 500, 400);
        viewClients.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(table);
        viewClients.add(scrollPane, BorderLayout.CENTER);

        ArrayList<Client> clientsList = fetchProductDataFromDatabase();
        populateTable(clientsList);

        viewClients.setVisible(true);
    }
    /**
     * Fetches client data from the database.
     *
     * @return The list of clients retrieved from the database.
     */
    private ArrayList<Client> fetchProductDataFromDatabase() {
        ArrayList<Client> clientList = new ArrayList<>();
        try {
            ResultSet resultSet = clientBLL.view();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nume = resultSet.getString("nume");
                String email = resultSet.getString("email");
                String adresa = resultSet.getString("adresa");

                Client client = new Client(id, nume, email, adresa);
                clientList.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientList;
    }
    /**
     * Populates the table with client data.
     *
     * @param clientList The list of clients to populate the table with.
     */
    private void populateTable(ArrayList<Client> clientList) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"id", "nume", "email", "adresa"});
        for (Client c : clientList)
        {
            model.addRow(new Object[]{c.getIdClient(), c.getNume(), c.getEmail(), c.getAdresa()});
        }
        table.setModel(model);
    }
    /**
     * Deletes a client from the database.
     */
    private void deleteNewClient()
    {
        JFrame deleteNewClient = new JFrame("Delete client");
        deleteNewClient.setVisible(true);
        deleteNewClient.setBounds(250, 350, 500, 400);
        deleteNewClient.setLayout((LayoutManager) null);

        JLabel id_clientJL = new JLabel("Id client: ");
        JTextField id_clientJT = new JTextField();

        id_clientJL.setBounds(80, 40, 100, 20);
        id_clientJT.setBounds(150, 40, 100, 20);

        deleteNewClient.add(id_clientJL);
        deleteNewClient.add(id_clientJT);

        JButton deleteTheClient = new JButton("Delete");
        deleteTheClient.setBounds(180, 220, 100, 50);
        deleteTheClient.addActionListener(e -> {
            deleteClient(Integer.parseInt(id_clientJT.getText()));
            //clientBLL.delete(Integer.parseInt(id_clientJT.getText()));
            deleteNewClient.setVisible(false);
        });
        deleteNewClient.add(deleteTheClient);

    }
    /**
     * Deletes a client from the database.
     *
     * @param idClient The ID of the client to be deleted.
     */
    private void deleteClient(int idClient)
    {
        try{
            Method orderDelete = OrderBLL.class.getDeclaredMethod("delete", int.class);
            orderDelete.invoke(orderBLL, idClient);
           Method method = ClientBLL.class.getDeclaredMethod("delete", int.class);

           //cauta metoda in clasa ClientBLL care are numele delete si are un paramateru de tip int
           if(Modifier.isPublic(method.getModifiers()))
               method.invoke(clientBLL, idClient);
       }catch(Exception e){
           System.out.println(e.getMessage());
       }
    }
    /**
     * Edits a client's data in the database.
     */
    private void editNewClient()
    {
        JFrame editNewClient = new JFrame("Update client data");
        editNewClient.setVisible(true);
        editNewClient.setBounds(250, 350, 500, 400);
        editNewClient.setLayout((LayoutManager) null);

        JLabel id_clientJL = new JLabel("Id client: ");
        JLabel client_nameJL = new JLabel("Name: ");
        JLabel client_emailJL = new JLabel("Email: ");
        JLabel client_addressJL = new JLabel("Address: ");

        id_clientJL.setBounds(80, 40, 100, 20);
        client_nameJL.setBounds(80, 80, 100, 20);
        client_emailJL.setBounds(80, 140, 100, 20);
        client_addressJL.setBounds(80, 200, 100, 20);

        editNewClient.add(id_clientJL);
        editNewClient.add(client_nameJL);
        editNewClient.add(client_emailJL);
        editNewClient.add(client_addressJL);

        JTextField id_clientJT = new JTextField();
        JTextField client_nameJT = new JTextField();
        JTextField client_emailJT = new JTextField();
        JTextField client_addressJT = new JTextField();

        id_clientJT.setBounds(180, 40, 100, 20);
        client_nameJT.setBounds(180, 80, 100, 20);
        client_emailJT.setBounds(180, 140, 100, 20);
        client_addressJT.setBounds(180, 200, 100, 20);

        editNewClient.add(id_clientJT);
        editNewClient.add(client_nameJT);
        editNewClient.add(client_emailJT);
        editNewClient.add(client_addressJT);

        JButton addTheClient = new JButton("Update");
        addTheClient.setBounds(180, 220, 100, 50);

        addTheClient.addActionListener(e -> {
            Client toAdd = new Client(Integer.parseInt(id_clientJT.getText()), client_nameJT.getText(), client_emailJT.getText(), client_addressJT.getText());
            clientBLL.edit(toAdd);
            editNewClient.setVisible(false);
        });
        editNewClient.add(addTheClient);
    }
    /**
     * Adds a new client to the database.
     */
    private void addNewClient()
    {
        JFrame addNewClient = new JFrame("Add client");
        addNewClient.setVisible(true);
        addNewClient.setBounds(250, 350, 500, 400);
        addNewClient.setLayout((LayoutManager) null);

        JLabel client_nameJL = new JLabel("Name: ");
        JLabel client_emailJL = new JLabel("Email: ");
        JLabel client_addressJL = new JLabel("Address: ");

        client_nameJL.setBounds(80, 80, 100, 20);
        client_emailJL.setBounds(80, 140, 100, 20);
        client_addressJL.setBounds(80, 200, 100, 20);

        addNewClient.add(client_nameJL);
        addNewClient.add(client_emailJL);
        addNewClient.add(client_addressJL);

        JTextField client_nameJT = new JTextField();
        JTextField client_emailJT = new JTextField();
        JTextField client_addressJT = new JTextField();

        client_nameJT.setBounds(180, 80, 100, 20);
        client_emailJT.setBounds(180, 140, 100, 20);
        client_addressJT.setBounds(180, 200, 100, 20);

        addNewClient.add(client_nameJT);
        addNewClient.add(client_emailJT);
        addNewClient.add(client_addressJT);

        JButton addTheClient = new JButton("Add");
        addTheClient.setBounds(180, 220, 100, 50);

        addTheClient.addActionListener(e -> {
            Client toAdd = new Client(client_nameJT.getText(), client_emailJT.getText(), client_addressJT.getText());
            clientBLL.insert(toAdd);
            addNewClient.setVisible(false);
        });
        addNewClient.add(addTheClient);

    }
}
