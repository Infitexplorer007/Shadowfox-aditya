import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StudentManagement1 extends JFrame {

    // Text fields
    private JTextField txtId, txtName, txtAge, txtCourse;

    // Table
    private JTable table;
    private DefaultTableModel model;

    // Data list
    private ArrayList<Student> studentList = new ArrayList<>();

    public StudentManagement1() {
        setTitle("Student Information System");
        setSize(850, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ---------- FORM PANEL ----------
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Student ID:"), gbc);

        gbc.gridx = 1;
        txtId = new JTextField(15);
        panel.add(txtId, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 3;
        txtName = new JTextField(15);
        panel.add(txtName, gbc);

        // Row 1
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Age:"), gbc);

        gbc.gridx = 1;
        txtAge = new JTextField(15);
        panel.add(txtAge, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Course:"), gbc);

        gbc.gridx = 3;
        txtCourse = new JTextField(15);
        panel.add(txtCourse, gbc);

        // ---------- BUTTONS ----------
        gbc.gridy = 2;

        gbc.gridx = 0;
        JButton btnAdd = new JButton("Add");
        panel.add(btnAdd, gbc);

        gbc.gridx = 1;
        JButton btnUpdate = new JButton("Update");
        panel.add(btnUpdate, gbc);

        gbc.gridx = 2;
        JButton btnDelete = new JButton("Delete");
        panel.add(btnDelete, gbc);

        gbc.gridx = 3;
        JButton btnClear = new JButton("Clear");
        panel.add(btnClear, gbc);

        // ---------- TABLE ----------
        model = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Course"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // ---------- BUTTON ACTIONS ----------
        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearFields());

        // ---------- TABLE CLICK ----------
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtId.setText(model.getValueAt(row, 0).toString());
                txtName.setText(model.getValueAt(row, 1).toString());
                txtAge.setText(model.getValueAt(row, 2).toString());
                txtCourse.setText(model.getValueAt(row, 3).toString());
            }
        });
    }

    // ---------- CRUD METHODS ----------

    private void addStudent() {
        if (txtId.getText().isEmpty() || txtName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }

        Student s = new Student(
                txtId.getText(),
                txtName.getText(),
                txtAge.getText(),
                txtCourse.getText()
        );

        studentList.add(s);
        model.addRow(new Object[]{s.id, s.name, s.age, s.course});
        clearFields();
    }

    private void updateStudent() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            model.setValueAt(txtId.getText(), row, 0);
            model.setValueAt(txtName.getText(), row, 1);
            model.setValueAt(txtAge.getText(), row, 2);
            model.setValueAt(txtCourse.getText(), row, 3);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to update");
        }
    }

    private void deleteStudent() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            model.removeRow(row);
            studentList.remove(row);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to delete");
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAge.setText("");
        txtCourse.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementSystem().setVisible(true));
    }
}

// ---------- STUDENT CLASS ----------
class Student {
    String id, name, age, course;

    Student(String id, String name, String age, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }
}
