import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EmployeeManagement extends JFrame {
    private JPanel EmployeeManagement;
    private JLabel part_icon;
    private JButton add_emp;
    private JButton delete_emp;
    private JButton update_emp;
    private JTable employees_list;
    private JTextField name_field;
    private JTextField surname_field;
    private JTextField email_field;
    private JTextField password_field;
    private JTextField phone_field;
    private JRadioButton chef_emp;
    private JRadioButton trainer_emp;
    private JRadioButton librarian_emp;
    private JLabel emp_name;
    private JLabel emp_surname;
    private JLabel emp_password;
    private JLabel emp_phone;
    private JLabel emp_email;
    ButtonGroup buttonGroup;

    public EmployeeManagement() throws HeadlessException, FileNotFoundException {
        initialize();
    }

    private void initialize() throws FileNotFoundException {
        Admin admin = new Admin();
        Employee employee= new Employee();
        setSize(1000, 600);
        setResizable(false);
        setLayout(new GridLayout(2, 4));
        setContentPane(EmployeeManagement);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Employee Management");

        trainer_emp.setActionCommand("trainer");
        chef_emp.setActionCommand("chef");
        librarian_emp.setActionCommand("librarian");

        buttonGroup= new ButtonGroup();
        buttonGroup.add(chef_emp);
        buttonGroup.add(trainer_emp);
        buttonGroup.add(librarian_emp);
        buttonGroup.setSelected(librarian_emp.getModel(),true);



        listEmployee();
        add_emp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)employees_list.getModel();


                if( name_field.getText().length()<=0||
                        surname_field.getText().length()<=0||
                        email_field.getText().length()<=0||
                        password_field.getText().length()<=0||
                        phone_field.getText().length()<=0){


                    JOptionPane.showMessageDialog(null,"Please, fill all fields");


                }else if(!email_field.getText().contains("@")||email_field.getText().strip().contains(" ")){
                    JOptionPane.showMessageDialog(null,"Email not valid");
                }else if(admin.checkExistence(email_field.getText().strip(),"employee")){
                    JOptionPane.showMessageDialog(null,"Please,use another email");
                }else {

                    if (buttonGroup.getSelection().getActionCommand()=="trainer"){
                        try {
                            admin.addEmployee(new Employee(
                                    name_field.getText().strip(),
                                    surname_field.getText().strip(),
                                    email_field.getText().strip().toLowerCase(),
                                    password_field.getText().strip(),
                                    phone_field.getText().strip(),
                                    buttonGroup.getSelection().getActionCommand()

                            ));
                            String [] rowData={
                                    String.valueOf(admin.employees.size()),
                                    name_field.getText().strip(),
                                    surname_field.getText().strip(),
                                    email_field.getText().strip().toLowerCase(),
                                    password_field.getText().strip(),
                                    phone_field.getText().strip(),
                                    buttonGroup.getSelection().getActionCommand()

                            };
                            table_model.addRow(rowData);

                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }else if (buttonGroup.getSelection().getActionCommand()=="chef"){
                        try {
                            admin.addEmployee(new Employee(
                                    name_field.getText().strip(),
                                    surname_field.getText().strip(),
                                    email_field.getText().strip().toLowerCase(),
                                    password_field.getText().strip(),
                                    phone_field.getText().strip(),
                                    buttonGroup.getSelection().getActionCommand()

                            ));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }else if (buttonGroup.getSelection().getActionCommand()=="librarian"){
                        try {
                            admin.addEmployee(new Employee(
                                    name_field.getText().strip(),
                                    surname_field.getText().strip(),
                                    email_field.getText().strip().toLowerCase(),
                                    password_field.getText().strip(),
                                    phone_field.getText().strip(),
                                    buttonGroup.getSelection().getActionCommand()

                            ));
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Please select a button");

                    }
                }



            }
        });
        employees_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DefaultTableModel table_model= (DefaultTableModel)employees_list.getModel();

                String T_position= table_model.getValueAt(employees_list.getSelectedRow(),6).toString();

                buttonGroup.setSelected(librarian_emp.getModel(),true);

                name_field.setText(table_model.getValueAt(employees_list.getSelectedRow(),1).toString());
                surname_field.setText(table_model.getValueAt(employees_list.getSelectedRow(),2).toString());
                email_field.setText(table_model.getValueAt(employees_list.getSelectedRow(),3).toString());
                password_field.setText(table_model.getValueAt(employees_list.getSelectedRow(),4).toString());
                phone_field.setText(table_model.getValueAt(employees_list.getSelectedRow(),5).toString());

                if (T_position.equals("chef")){
                    buttonGroup.setSelected(chef_emp.getModel(),true);
                }else  if(T_position.equals("trainer")){
                    buttonGroup.setSelected(trainer_emp.getModel(),true);
                }else {
                    buttonGroup.setSelected(librarian_emp.getModel(),true);
                }


            }
        });
        update_emp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)employees_list.getModel();
                if (employees_list.getSelectedRowCount()==1){
                    admin.employees.get(employees_list.getSelectedRow()).setName(name_field.getText().strip());
                    admin.employees.get(employees_list.getSelectedRow()).setSurname(surname_field.getText().strip());
                    admin.employees.get(employees_list.getSelectedRow()).setEmail(email_field.getText().strip().toLowerCase());
                    admin.employees.get(employees_list.getSelectedRow()).setPassword(password_field.getText().strip());
                    admin.employees.get(employees_list.getSelectedRow()).setPhone(phone_field.getText().strip());
                    admin.employees.get(employees_list.getSelectedRow()).setPosition(buttonGroup.getSelection().getActionCommand());

                    try {
                        admin.writeData();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    table_model.setValueAt(admin.employees.get(employees_list.getSelectedRow()).getName(),employees_list.getSelectedRow(),1);
                    table_model.setValueAt(admin.employees.get(employees_list.getSelectedRow()).getSurname(),employees_list.getSelectedRow(),2);
                    table_model.setValueAt(admin.employees.get(employees_list.getSelectedRow()).getEmail(),employees_list.getSelectedRow(),3);
                    table_model.setValueAt(admin.employees.get(employees_list.getSelectedRow()).getPassword(),employees_list.getSelectedRow(),4);
                    table_model.setValueAt(admin.employees.get(employees_list.getSelectedRow()).getPhone(),employees_list.getSelectedRow(),5);
                    table_model.setValueAt(admin.employees.get(employees_list.getSelectedRow()).getPosition(),employees_list.getSelectedRow(),6);



                }else {
                    JOptionPane.showMessageDialog(null,"No selected row!!");

                }

            }
        });
        delete_emp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultTableModel table_model= (DefaultTableModel)employees_list.getModel();

                if (employees_list.getSelectedRowCount()==1){
                    try {
                        admin.deleteEmployee(admin.employees.get(employees_list.getSelectedRow()).getEmail());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    table_model.removeRow(employees_list.getSelectedRow());
                }else{
                    JOptionPane.showMessageDialog(null,"No selected row!!");

                }

            }
        });
    }

    public  void listEmployee() throws FileNotFoundException {

        employees_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Admin admin = new Admin();
        Object[][] employees = new String[admin.employees.size()][7];
        for (int i = 0; i < employees.length; i++) {
            employees[i][0]=String.valueOf(admin.employees.get(i).getId());
            employees[i][1]=admin.employees.get(i).getName();
            employees[i][2]=admin.employees.get(i).getSurname();
            employees[i][3]=admin.employees.get(i).getEmail();
            employees[i][4]=admin.employees.get(i).getPassword();
            employees[i][5]=admin.employees.get(i).getPhone();
            employees[i][6]=admin.employees.get(i).getPosition();


        }
        employees_list.setModel(new DefaultTableModel(
                employees,new Object[]{"ID","Name","Surname","Email","Password","Phone","Position"}
        )
            {
                public boolean isCellEditable(int row , int column){
                    return false;
                }
            }

        );
    }

    private void createUIComponents() {
        this.part_icon= new JLabel(new ImageIcon("photos/admin_dashboard/employee.png"));

    }
}
