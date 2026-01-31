import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InventoryManager extends JFrame {

    JTextField txtItemId, txtItemName, txtPrice, txtQuantity, txtSearch;
    JLabel lblTotalValue, lblLowStock;
    JTable table;
    DefaultTableModel model;

    public InventoryManager() {
        setTitle("Inventory Manager (Swing)");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        /* ===== MENU BAR ===== */
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu help = new JMenu("Help");
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(help);
        setJMenuBar(menuBar);

        /* ===== TOP FORM PANEL ===== */
        JPanel formPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Item Details"));

        txtItemId = new JTextField();
        txtItemName = new JTextField();
        txtPrice = new JTextField();
        txtQuantity = new JTextField();

        formPanel.add(new JLabel("Item ID"));
        formPanel.add(new JLabel("Item Name"));
        formPanel.add(new JLabel("Price"));
        formPanel.add(new JLabel("Quantity"));

        formPanel.add(txtItemId);
        formPanel.add(txtItemName);
        formPanel.add(txtPrice);
        formPanel.add(txtQuantity);

        /* ===== BUTTON PANEL ===== */
        JPanel buttonPanel = new JPanel();

        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(formPanel, BorderLayout.CENTER);
        northPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);

        /* ===== SEARCH BAR ===== */
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtSearch = new JTextField(20);
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(txtSearch);

        add(searchPanel, BorderLayout.WEST);

        /* ===== TABLE ===== */
        model = new DefaultTableModel(
                new String[]{"Item ID", "Item Name", "Quantity", "Price", "Total"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        /* ===== FOOTER ===== */
        JPanel footerPanel = new JPanel(new BorderLayout());

        lblTotalValue = new JLabel("Total Inventory Value: ₹0");
        lblLowStock = new JLabel("Low Stock Items: None");

        JButton btnExport = new JButton("Export Report");

        footerPanel.add(lblTotalValue, BorderLayout.WEST);
        footerPanel.add(lblLowStock, BorderLayout.CENTER);
        footerPanel.add(btnExport, BorderLayout.EAST);

        add(footerPanel, BorderLayout.SOUTH);

        /* ===== BUTTON ACTIONS ===== */

        btnAdd.addActionListener(e -> addItem());

        btnUpdate.addActionListener(e -> updateItem());

        btnDelete.addActionListener(e -> deleteItem());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtItemId.setText(model.getValueAt(row, 0).toString());
                txtItemName.setText(model.getValueAt(row, 1).toString());
                txtQuantity.setText(model.getValueAt(row, 2).toString());
                txtPrice.setText(model.getValueAt(row, 3).toString());
            }
        });

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchItem(txtSearch.getText());
            }
        });

        btnExport.addActionListener(e -> exportReport());
    }

    /* ===== METHODS ===== */

    void addItem() {
        try {
            int id = Integer.parseInt(txtItemId.getText());
            String name = txtItemName.getText();
            int qty = Integer.parseInt(txtQuantity.getText());
            double price = Double.parseDouble(txtPrice.getText());
            double total = qty * price;

            model.addRow(new Object[]{id, name, qty, price, total});
            calculateTotalValue();
            checkLowStock();
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number input");
        }
    }

    void updateItem() {
    int viewRow = table.getSelectedRow();

    if (viewRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select an item to update");
        return;
    }

    int row = table.convertRowIndexToModel(viewRow);

    try {
        int qty = Integer.parseInt(txtQuantity.getText());
        double price = Double.parseDouble(txtPrice.getText());

        model.setValueAt(txtItemId.getText(), row, 0);
        model.setValueAt(txtItemName.getText(), row, 1);
        model.setValueAt(qty, row, 2);
        model.setValueAt(price, row, 3);
        model.setValueAt(qty * price, row, 4);

        calculateTotalValue();
        checkLowStock();

        JOptionPane.showMessageDialog(this, "Item updated successfully!");

    } catch (NumberFormatException | ClassCastException e) {
        JOptionPane.showMessageDialog(this, "Invalid input data");
    }
}


    void deleteItem() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            model.removeRow(row);
            calculateTotalValue();
            checkLowStock();
        }
    }

    void calculateTotalValue() {
        double sum = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            sum += Double.parseDouble(model.getValueAt(i, 4).toString());
        }
        lblTotalValue.setText("Total Inventory Value: ₹" + sum);
    }

    void checkLowStock() {
        StringBuilder lowStock = new StringBuilder("Low Stock Items: ");
        boolean found = false;

        for (int i = 0; i < model.getRowCount(); i++) {
            int qty = Integer.parseInt(model.getValueAt(i, 2).toString());
            if (qty <= 3) {
                lowStock.append(model.getValueAt(i, 1)).append("(Qty: ").append(qty).append(") ");
                found = true;
            }
        }

        lblLowStock.setText(found ? lowStock.toString() : "Low Stock Items: None");
    }

    void searchItem(String text) {
        DefaultTableModel newModel = new DefaultTableModel(
                new String[]{"Item ID", "Item Name", "Quantity", "Price", "Total"}, 0);

        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 1).toString().toLowerCase().contains(text.toLowerCase())) {
                newModel.addRow(new Object[]{
                        model.getValueAt(i, 0),
                        model.getValueAt(i, 1),
                        model.getValueAt(i, 2),
                        model.getValueAt(i, 3),
                        model.getValueAt(i, 4)
                });
            }
        }
        table.setModel(newModel);
    }

    void exportReport() {
        try (FileWriter writer = new FileWriter("Inventory_Report.txt")) {
            for (int i = 0; i < model.getRowCount(); i++) {
                writer.write(
                        model.getValueAt(i, 0) + " | " +
                        model.getValueAt(i, 1) + " | " +
                        model.getValueAt(i, 2) + " | " +
                        model.getValueAt(i, 3) + " | " +
                        model.getValueAt(i, 4) + "\n");
            }
            JOptionPane.showMessageDialog(this, "Report Exported Successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error exporting report: " + e.getMessage(), "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void clearFields() {
        txtItemId.setText("");
        txtItemName.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryManager().setVisible(true));
    }
}
