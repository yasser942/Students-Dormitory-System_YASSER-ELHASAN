import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Register extends JFrame {
    private JRadioButton admin_rad;
    private JRadioButton student_rad;
    private JTextField name_field;
    private JTextField surname_field;
    private JTextField email_field;
    private JTextField password_field;
    private JTextField phone_field;
    private JButton register_button;
    private JPanel register_panel;
    private JLabel user_name;
    private JLabel user_surname;
    private JLabel user_email;
    private JLabel user_password;
    private JLabel user_phone;
    ButtonGroup group;

    public Register() throws HeadlessException, FileNotFoundException {
        initialize();
    }

    private void initialize() throws FileNotFoundException {
        Admin admin = new Admin();
        Student student = new Student();

        setSize(600, 300);
        setResizable(false);
        setLayout(new GridLayout(2, 4));
        setContentPane(register_panel);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Register");
        group= new ButtonGroup();
        group.add(admin_rad);
        group.add(student_rad);
        group.setSelected(admin_rad.getModel(),true);
        admin_rad.setActionCommand("admin");
        student_rad.setActionCommand("student");

        register_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( name_field.getText().length()<=0||
                        surname_field.getText().length()<=0||
                        email_field.getText().length()<=0||
                        password_field.getText().length()<=0||
                        phone_field.getText().length()<=0){


                    JOptionPane.showMessageDialog(null,"Please, fill all fields");


                }else if(!email_field.getText().contains("@")||email_field.getText().strip().contains(" ")){
                    JOptionPane.showMessageDialog(null,"Email not valid");
                }
                else {

                    if (group.getSelection().getActionCommand()=="admin") {
                        if(admin.checkExistence(email_field.getText().strip(),"admin")){
                            JOptionPane.showMessageDialog(null,"Please,use another email");
                        }else{
                            try {
                                admin.writeData(new Admin(
                                        name_field.getText().strip(),
                                        surname_field.getText().strip(),
                                        email_field.getText().strip().toLowerCase(),
                                        password_field.getText().strip(),
                                        phone_field.getText().strip()
                                ));
                                admin.readData();
                                JOptionPane.showMessageDialog(null,"You have Registered Successfully !!");

                                dispose();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }

                    }else if (group.getSelection().getActionCommand()=="student"){
                        if(student.checkExistence(email_field.getText().strip())){
                            JOptionPane.showMessageDialog(null,"Please,use another email");
                        }else{
                            try {

                                student.WriteStudentData(new Student(
                                        name_field.getText().strip(),
                                        surname_field.getText().strip(),
                                        email_field.getText().strip().toLowerCase(),
                                        password_field.getText().strip(),
                                        phone_field.getText().strip()

                                ));
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                            try {
                                student.readData();
                                JOptionPane.showMessageDialog(null,"You have Registered Successfully !!");
                                dispose();


                            } catch (FileNotFoundException fileNotFoundException) {
                                fileNotFoundException.printStackTrace();
                            }
                        }

                    }
                    else {
                        JOptionPane.showMessageDialog(null,"Please select one of the above buttons");

                    }
                }

            }
        });
    }
}
