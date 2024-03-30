package Presentation;

import Connection.ConnectionFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 * The GUI class represents the main graphical user interface of the application.
 */
public class GUI extends JFrame {
    public JButton butonClient, butonProdus, butonComanda;
    ConnectionFactory connectionFactory = null;
    /**
     * Constructs a new GUI object.
     */
    public GUI()
    {
        connectionFactory = new ConnectionFactory();
        connectionFactory.executeQuery("set SQL_SAFE_UPDATES = 0");
        init();
    }
    /**
     * Initializes the GUI components.
     */
    private void init(){
        setTitle("Main Window");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        butonClient = new JButton("Client");
        butonClient.setBounds(200, 20, 200, 100);
        butonClient.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e){
                new ClientGUI(connectionFactory);
            }
        });
        add(butonClient);

        butonComanda = new JButton("Order");
        butonComanda.setBounds(200, 150, 200, 100);
        butonComanda.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e){
                new OrderGUI(connectionFactory);
            }
        });
        add(butonComanda);

        butonProdus = new JButton("Product");
        butonProdus.setBounds(200, 300, 200, 100);
        butonProdus.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e){
                new ProductGUI(connectionFactory);
            }
        });
        add(butonProdus);

        setVisible(true);
    }
}
