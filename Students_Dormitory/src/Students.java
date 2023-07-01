import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Students extends JFrame {

    private JPanel students_panel;
    private JLabel studentsIcon;
    private JTable students_table;
    private JTextField name_field;
    private JTextField surname_field;
    private JTextField email_field;
    private JTextField password_field;
    private JTextField phone_field;
    private JButton addStudentButton;
    private JButton updateStudentButton;
    private JButton deleteStudentButton;

    public Students() throws HeadlessException, FileNotFoundException {
        initialize();
    }

    private void initialize() throws FileNotFoundException {
        Student student= new Student();

        setSize(1000, 600);
        setResizable(false);
        setLayout(new GridLayout(2, 4));
        setContentPane(students_panel);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Students Management");
        listStudents();
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)students_table.getModel();

                if( name_field.getText().length()<=0||
                        surname_field.getText().length()<=0||
                        email_field.getText().length()<=0||
                        password_field.getText().length()<=0||
                        phone_field.getText().length()<=0){


                    JOptionPane.showMessageDialog(null,"Please, fill all fields");


                }else if(!email_field.getText().contains("@")||email_field.getText().strip().contains(" ")){
                    JOptionPane.showMessageDialog(null,"Email not valid");
                }else if(student.checkExistence(email_field.getText().strip())){
                    JOptionPane.showMessageDialog(null,"Please,use another email");
                }else {
                    try {
                        student.addStudent(new Student(
                                name_field.getText().strip(),
                                surname_field.getText().strip(),
                                email_field.getText().strip().toLowerCase(),
                                password_field.getText().strip(),
                                phone_field.getText().strip()

                        ));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    String [] rowData={
                            String.valueOf(student.students.size()),
                            name_field.getText().strip(),
                            surname_field.getText().strip(),
                            email_field.getText().strip().toLowerCase(),
                            password_field.getText().strip(),
                            phone_field.getText().strip(),

                    };
                    table_model.addRow(rowData);

                }
            }
        });
        deleteStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultTableModel table_model= (DefaultTableModel)students_table.getModel();

                if (students_table.getSelectedRowCount()==1){
                    try {
                        student.emptyRoom(student.students.get(students_table.getSelectedRow()).getEmail());
                        student.deleteStudentFromRestaurant(student.students.get(students_table.getSelectedRow()).getEmail());
                        student.deleteStudentFromLibrary(student.students.get(students_table.getSelectedRow()).getEmail());
                        student.deleteStudentFromGym(student.students.get(students_table.getSelectedRow()).getEmail());
                        student.deleteStudent(student.students.get(students_table.getSelectedRow()).getEmail());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    table_model.removeRow(students_table.getSelectedRow());
                }else{
                    JOptionPane.showMessageDialog(null,"No selected row!!");

                }
            }
        });
        updateStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)students_table.getModel();
                if (students_table.getSelectedRowCount()==1){
                    try {
                        student.updateRoom(student.students.get(students_table.getSelectedRow()).getEmail(),email_field.getText().strip().toLowerCase());
                        student.updateStudentInLibrary(student.students.get(students_table.getSelectedRow()).getEmail(),email_field.getText().strip().toLowerCase());
                        student.updateStudentInGym(student.students.get(students_table.getSelectedRow()).getEmail(),email_field.getText().strip().toLowerCase());
                        student.updateStudentInRestaurant(student.students.get(students_table.getSelectedRow()).getEmail(),email_field.getText().strip().toLowerCase());

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    student.students.get(students_table.getSelectedRow()).setName(name_field.getText().strip());
                    student.students.get(students_table.getSelectedRow()).setSurname(surname_field.getText().strip());
                    student.students.get(students_table.getSelectedRow()).setEmail(email_field.getText().strip().toLowerCase());
                    student.students.get(students_table.getSelectedRow()).setPassword(password_field.getText().strip());
                    student.students.get(students_table.getSelectedRow()).setPhone(phone_field.getText().strip());

                    try {
                        student.WriteStudentData();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    table_model.setValueAt(student.students.get(students_table.getSelectedRow()).getName(),students_table.getSelectedRow(),1);
                    table_model.setValueAt(student.students.get(students_table.getSelectedRow()).getSurname(),students_table.getSelectedRow(),2);
                    table_model.setValueAt(student.students.get(students_table.getSelectedRow()).getEmail().toLowerCase(),students_table.getSelectedRow(),3);
                    table_model.setValueAt(student.students.get(students_table.getSelectedRow()).getPassword(),students_table.getSelectedRow(),4);
                    table_model.setValueAt(student.students.get(students_table.getSelectedRow()).getPhone(),students_table.getSelectedRow(),5);



                }else {
                    JOptionPane.showMessageDialog(null,"No selected row!!");

                }

            }
        });
        students_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DefaultTableModel table_model= (DefaultTableModel)students_table.getModel();



                name_field.setText(table_model.getValueAt(students_table.getSelectedRow(),1).toString());
                surname_field.setText(table_model.getValueAt(students_table.getSelectedRow(),2).toString());
                email_field.setText(table_model.getValueAt(students_table.getSelectedRow(),3).toString());
                password_field.setText(table_model.getValueAt(students_table.getSelectedRow(),4).toString());
                phone_field.setText(table_model.getValueAt(students_table.getSelectedRow(),5).toString());

            }
        });
    }

    public  void listStudents() throws FileNotFoundException {

        students_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Student student= new Student();
        Object[][] students = new String[student.students.size()][6];
        for (int i = 0; i < students.length; i++) {
            students[i][0]=String.valueOf(student.students.get(i).getId());
            students[i][1]=student.students.get(i).getName();
            students[i][2]=student.students.get(i).getSurname();
            students[i][3]=student.students.get(i).getEmail();
            students[i][4]=student.students.get(i).getPassword();
            students[i][5]=student.students.get(i).getPhone();



        }
        students_table.setModel(new DefaultTableModel(
                students,new Object[]{"ID","Name","Surname","Email","Password","Phone"}
                                )
                                {
                                    public boolean isCellEditable(int row , int column){
                                        return false;
                                    }
                                }

        );
    }

    private void createUIComponents() {
        this.studentsIcon= new JLabel(new ImageIcon("photos/admin_dashboard/home page.png"));
    }
}
