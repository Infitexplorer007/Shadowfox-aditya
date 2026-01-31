import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentManagementSystem extends JFrame {

    // Text fields
    private JTextField txtId, txtName, txtAge, txtCourse;

    // Table
    private JTable table;
    private DefaultTableModel model;

    // Data storage
    private final ArrayList<Student> studentList = new ArrayList<>();

    public StudentManagementSystem() {
        setTitle("Student Information System");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Student ID:"));
        txtId = new JTextField();
        panel.add(txtId);

        panel.add(new JLabel("Name:"));
        txtName = new JTextField();
        panel.add(txtName);

        panel.add(new JLabel("Age:"));
        txtAge = new JTextField();
        panel.add(txtAge);

        panel.add(new JLabel("Course:"));
        txtCourse = new JTextField();
        panel.add(txtCourse);

        // Buttons
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");

        panel.add(btnAdd);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnClear);

        // Table
        model = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Course"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Button actions
        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearFields());

        // Table click event
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtId.setText(model.getValueAt(row, 0).toString());
                txtName.setText(model.getValueAt(row, 1).toString());
                txtAge.setText(model.getValueAt(row, 2).toString());
                txtCourse.setText(model.getValueAt(row, 3).toString());
            }
        });
    }

    // Add student
    private void addStudent() {
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

    // Update student
    private void updateStudent() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            model.setValueAt(txtId.getText(), row, 0);
            model.setValueAt(txtName.getText(), row, 1);
            model.setValueAt(txtAge.getText(), row, 2);
            model.setValueAt(txtCourse.getText(), row, 3);
            clearFields();
        }
    }

    // Delete student
    private void deleteStudent() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            model.removeRow(row);
            studentList.remove(row);
            clearFields();
        }
    }

    // Clear input fields
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

// Student class
class Student {
    String id, name, age, course;

    Student(String id, String name, String age, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }
}
