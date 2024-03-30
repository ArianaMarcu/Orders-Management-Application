package Presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import BusinessLogic.ClientBLL;
import BusinessLogic.ProductBLL;
import Connection.ConnectionFactory;
import DataAccess.ClientDAO;
import Model.Client;
import Model.Product;
/**
 * The ProductGUI class represents the graphical user interface for managing products.
 */
public class ProductGUI extends JFrame
{
    ProductBLL productBLL = null;
    JTable table = new JTable();
    /**
     * Constructs a new ProductGUI object.
     *
     * @param c the ConnectionFactory object to establish the database connection
     */
    public ProductGUI(ConnectionFactory c)
    {
        productBLL = new ProductBLL(c);
        init();
    }
    /**
     * Initializes the GUI components.
     */
    private void init()
    {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton addProduct = new JButton("Add new product");
        addProduct.setBounds(180, 124, 150, 50);
        add(addProduct);
        addProduct.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                ProductGUI.this.addNewProduct();
            }
        });
        this.add(addProduct);

        JButton editProduct = new JButton("Edit a product");
        editProduct.setBounds(390, 124, 150, 50);
        editProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProductGUI.this.editProduct();
            }
        });
        this.add(editProduct);

        JButton deleteProduct = new JButton("Delete a product");
        deleteProduct.setBounds(600, 124, 150, 50);
        deleteProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProductGUI.this.deleteProduct();
            }
        });
        this.add(deleteProduct);

        JButton viewProducts = new JButton("View all products");
        viewProducts.setBounds(690, 284, 150, 100);
        viewProducts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProductGUI.this.viewAll();
            }
        });
        this.add(viewProducts);


        //setVisible(true);
        this.setLayout((LayoutManager) null);
        this.setTitle("Produs");
        this.setLocationRelativeTo((Component) null);
        this.setVisible(true);
        this.setBounds(42, 288, 896, 504);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    /**
     * Displays all the products in a table.
     */
    private void viewAll() {
        JFrame viewProducts = new JFrame("Product Table");
        viewProducts.setVisible(true);
        viewProducts.setBounds(250, 350, 500, 400);
        viewProducts.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(table);
        viewProducts.add(scrollPane, BorderLayout.CENTER);

        ArrayList<Product> productList = fetchProductDataFromDatabase();
        populateTable(productList);

        viewProducts.setVisible(true);
    }
    /**
     * Fetches the product data from the database.
     *
     * @return an ArrayList of Product objects containing the fetched data
     */
    private ArrayList<Product> fetchProductDataFromDatabase() {
        ArrayList<Product> productList = new ArrayList<>();
        try {
            ResultSet resultSet = productBLL.view();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nume = resultSet.getString("nume");
                String categorie = resultSet.getString("categorie");
                float pret = resultSet.getFloat("pret");
                int cantitate = resultSet.getInt("cantitate");
                Product product = new Product(id, nume, categorie, pret, cantitate);
                productList.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }
    /**
     * Populates the table with the provided product data.
     *
     * @param productList the ArrayList of Product objects containing the data to populate the table
     */
    private void populateTable(ArrayList<Product> productList) {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"id", "nume", "categorie", "pret", "cantitate"});
        for (Product product : productList)
        {
            model.addRow(new Object[]{product.getIdProdus(), product.getNumeProdus(), product.getCategorie(), product.getPret(), product.getCantitate()});
        }
        table.setModel(model);
    }
    /**
     * Deletes a product from the database.
     */
    private void deleteProduct()
    {
        JFrame deleteAProduct = new JFrame("Delete product");
        deleteAProduct.setVisible(true);
        deleteAProduct.setBounds(250, 350, 500, 400);
        deleteAProduct.setLayout((LayoutManager) null);

        JLabel label = new JLabel("Id product: ");
        JTextField IDtext = new JTextField();

        label.setBounds(80, 40, 100, 20);
        IDtext.setBounds(150, 40, 100, 20);

        deleteAProduct.add(label);
        deleteAProduct.add(IDtext);

        JButton deleteP = new JButton("Delete");
        deleteP.setBounds(180, 220, 100, 50);
        deleteP.addActionListener(e -> {
            productBLL.delete(Integer.parseInt(IDtext.getText()));
            deleteAProduct.setVisible(false);
        });
        deleteAProduct.add(deleteP);
    }
    /**
     * Edits the data of a product in the database.
     */
    private void editProduct()
    {
        JFrame editAProduct = new JFrame("Update product data");
        editAProduct.setVisible(true);
        editAProduct.setBounds(250, 350, 500, 400);
        editAProduct.setLayout((LayoutManager) null);

        JLabel label1 = new JLabel("Id produs: ");
        JLabel label2 = new JLabel("Nume: ");
        JLabel label3 = new JLabel("Categorie: ");
        JLabel label4 = new JLabel("Pret: ");
        JLabel label5 = new JLabel("Cantitate: ");

        label1.setBounds(80, 40, 100, 20);
        label2.setBounds(80, 80, 100, 20);
        label3.setBounds(80, 140, 100, 20);
        label4.setBounds(80, 200, 100, 20);
        label5.setBounds(80, 290, 100, 20);

        editAProduct.add(label1);
        editAProduct.add(label2);
        editAProduct.add(label3);
        editAProduct.add(label4);
        editAProduct.add(label5);

        JTextField IdJT = new JTextField();
        JTextField NameJT = new JTextField();
        JTextField CategoryJT = new JTextField();
        JTextField PriceJT = new JTextField();
        JTextField QuantityJT = new JTextField();

        IdJT.setBounds(180, 40, 100, 20);
        NameJT.setBounds(180, 80, 100, 20);
        CategoryJT.setBounds(180, 140, 100, 20);
        PriceJT.setBounds(180, 200, 100, 20);
        QuantityJT.setBounds(180, 290, 100, 20);

        editAProduct.add(IdJT);
        editAProduct.add(NameJT);
        editAProduct.add(CategoryJT);
        editAProduct.add(PriceJT);
        editAProduct.add(QuantityJT);

        JButton UpdatedProduct = new JButton("Update");
        UpdatedProduct.setBounds(180, 220, 100, 50);

        UpdatedProduct.addActionListener(e -> {
            Product toUpdate = new Product(Integer.parseInt(IdJT.getText()), NameJT.getText(), CategoryJT.getText(),Float.parseFloat(PriceJT.getText()), Integer.parseInt(QuantityJT.getText()));
            productBLL.update(toUpdate);
            editAProduct.setVisible(false);
        });
        editAProduct.add(UpdatedProduct);
    }
    /**
     * Adds a new product to the database.
     */
    private void addNewProduct()
    {
        JFrame addAProduct = new JFrame("Add product");
        addAProduct.setVisible(true);
        addAProduct.setBounds(250, 350, 500, 400);
        addAProduct.setLayout((LayoutManager) null);

        JLabel label1 = new JLabel("Nume: ");
        JLabel label2 = new JLabel("Categorie: ");
        JLabel label3 = new JLabel("Pret: ");
        JLabel label4 = new JLabel("Cantitate: ");

        label1.setBounds(80, 80, 100, 20);
        label2.setBounds(80, 140, 100, 20);
        label3.setBounds(80, 200, 100, 20);
        label4.setBounds(80, 290, 100, 20);

        addAProduct.add(label1);
        addAProduct.add(label2);
        addAProduct.add(label3);
        addAProduct.add(label4);

        JTextField NameJT = new JTextField();
        JTextField CategoryJT = new JTextField();
        JTextField PriceJT = new JTextField();
        JTextField QuantityJT = new JTextField();

        NameJT.setBounds(180, 80, 100, 20);
        CategoryJT.setBounds(180, 140, 100, 20);
        PriceJT.setBounds(180, 200, 100, 20);
        QuantityJT.setBounds(180, 290, 100, 20);

        addAProduct.add(NameJT);
        addAProduct.add(CategoryJT);
        addAProduct.add(PriceJT);
        addAProduct.add(QuantityJT);

        JButton addTheProduct = new JButton("Add");
        addTheProduct.setBounds(180, 220, 100, 50);
        addTheProduct.addActionListener(e -> {
            Product toAdd = new Product(NameJT.getText(), CategoryJT.getText(), Float.parseFloat(PriceJT.getText()), Integer.parseInt(QuantityJT.getText()));
            productBLL.insert(toAdd);
            addAProduct.setVisible(false);
        });
        addAProduct.add(addTheProduct);
    }

}
