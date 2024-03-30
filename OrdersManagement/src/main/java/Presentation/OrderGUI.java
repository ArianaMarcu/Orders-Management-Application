package Presentation;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import BusinessLogic.ClientBLL;
import BusinessLogic.OrderBLL;
import BusinessLogic.ProductBLL;
import Connection.ConnectionFactory;
import DataAccess.ClientDAO;
import Model.Product;
import Model.Order;
/**
 * The OrderGUI class represents the graphical user interface for creating orders.
 */
public class OrderGUI extends JFrame
{
    private JComboBox<String> productComboBox;
    private JComboBox<String> clientComboBox;
    private JTextField quantityTextField;
    private JButton orderButton;
    private OrderBLL orderBLL = null;
    private ProductBLL productBLL = null;
    private ClientBLL clientBLL = null;
    /**
     * Constructs a new OrderGUI object.
     *
     * @param c the ConnectionFactory object to establish the database connection
     */
    public OrderGUI(ConnectionFactory c)
    {
        productBLL = new ProductBLL(c);
        clientBLL = new ClientBLL(c);
        orderBLL = new OrderBLL(c);
        initializeComponents();
        populateComboBoxes(c);
        this.setVisible(true);
    }
    /**
     * Initializes the GUI components.
     */
    private void initializeComponents()
    {
        setTitle("Create Order");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(null);

        productComboBox = new JComboBox<>();
        productComboBox.setPreferredSize(new Dimension(200, 25));

        clientComboBox = new JComboBox<>();
        clientComboBox.setPreferredSize(new Dimension(200, 25));

        quantityTextField = new JTextField();
        quantityTextField.setPreferredSize(new Dimension(200, 25));

        orderButton = new JButton("Place Order");
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                placeOrder();
            }
        });
        setLayout(new FlowLayout());
        add(new JLabel("Product: "));
        add(productComboBox);
        add(new JLabel("Client: "));
        add(clientComboBox);
        add(new JLabel("Quantity: "));
        add(quantityTextField);
        add(orderButton);
    }
    /**
     * Populates the product and client combo boxes with data from the database.
     *
     * @param conn the ConnectionFactory object to establish the database connection
     */
    private void populateComboBoxes(ConnectionFactory conn)
    {
        ArrayList<String> productNames = productBLL.readProductNames();
        ArrayList<String> clientNames = clientBLL.readClientNames();
        for(String productName : productNames)
        {
            productComboBox.addItem(productName);
        }
        for(String clientName: clientNames)
        {
            clientComboBox.addItem(clientName);
        }
    }
    /**
     * Places an order based on the selected product, client, and quantity.
     */
    private void placeOrder()
    {
        String selectedProduct = productComboBox.getSelectedItem().toString();
        String selectedClient = clientComboBox.getSelectedItem().toString();
        int idProdus = productBLL.getIdByName(selectedProduct);
        int idClient = clientBLL.getIdByName(selectedClient);
        Integer cantitate = Integer.parseInt(quantityTextField.getText());
        Product product = productBLL.getById(idProdus);
        if(product.getCantitate() >= cantitate){
            orderBLL.insert(new Order(idProdus, idClient, cantitate));
            productBLL.updateCantitate(idProdus, product.getCantitate() - cantitate);
        }
        else{
            JOptionPane.showMessageDialog(null, "Out of stock");
        }
    }
}
