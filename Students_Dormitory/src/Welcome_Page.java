import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Welcome_Page extends JFrame {
    private JPanel welcome;
    private JLabel welcome_txt;
    private JTextField email;
    private JPasswordField password;
    private JButton login;
    private JButton newRegisterButton;
    private JLabel welcome_image;
    private JRadioButton admin_button;
    private JRadioButton student_button;
    private JRadioButton employee_button;




    ButtonGroup group;
    public  Welcome_Page() throws FileNotFoundException {
        initialize();
    }

    private void initialize() throws FileNotFoundException {
        Admin admin= new Admin();
        Student student = new Student();

        setSize(700,500);
        setContentPane(welcome);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Welcome Page");
        setResizable(false);
        group= new ButtonGroup();
        group.add(admin_button);
        group.add(student_button);
        group.add(employee_button);
        group.setSelected(admin_button.getModel(),true);
        admin_button.setActionCommand("admin");
        student_button.setActionCommand("student");
        employee_button.setActionCommand("employee");





        //setIconImage(Toolkit.getDefaultToolkit().createImage("photos/img_1.png"));
        newRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Register register = new Register();

                    register.setVisible(true);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }


            }
        });

        login.addActionListener(new ActionListener() {



            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    admin.readData();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }

                if (email.getText().length()<=0|| password.getText().length()<=0){
                    JOptionPane.showMessageDialog(null,"Please, fill all fields");

                }else if(!email.getText().contains("@")||email.getText().strip().contains(" ")){
                    JOptionPane.showMessageDialog(null,"Email not valid");
                }

                else {

                    if (group.getSelection().getActionCommand()=="admin") {
                        if (admin.authenticate(email.getText().trim(), password.getText().trim(),group.getSelection().getActionCommand())) {
                            AdminDashboard adminDashboard = new AdminDashboard();
                            adminDashboard.setVisible(true);

                            dispose();
                        }else {
                            JOptionPane.showMessageDialog(null,"User not exists!!");

                        }
                    }else  if (group.getSelection().getActionCommand()=="employee") {
                        if (admin.authenticate(email.getText().trim(), password.getText().trim(),group.getSelection().getActionCommand())) {
                            if(admin.getEmStatus(email.getText().trim()).equals("librarian")){
                                try {
                                    Library library = new Library();
                                    library.setVisible(true);
                                    dispose();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }

                            }else if(admin.getEmStatus(email.getText().trim()).equals("chef")){
                                try {
                                    Restaurant restaurant= new Restaurant();
                                    restaurant.setVisible(true);
                                    dispose();
                                } catch (FileNotFoundException fileNotFoundException) {
                                    fileNotFoundException.printStackTrace();
                                }

                            }else if (admin.getEmStatus(email.getText().trim()).equals("trainer")){
                                Gym gym= null;
                                try {
                                    gym = new Gym();
                                } catch (FileNotFoundException fileNotFoundException) {
                                    fileNotFoundException.printStackTrace();
                                }
                                gym.setVisible(true);
                                dispose();
                            }


                        }else {
                            JOptionPane.showMessageDialog(null,"User not exists!!");

                        }
                    }else{
                        try {
                            student.readData();
                        } catch (FileNotFoundException fileNotFoundException) {
                            fileNotFoundException.printStackTrace();
                        }
                        if (student.authenticate(email.getText().trim(), password.getText().trim())) {

                            Student s= null;
                            try {
                                s = student.currentLogin(email.getText());
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }


                            StudentDashboard studentDashboard = null;
                            try {
                                studentDashboard = new StudentDashboard();

                            } catch (FileNotFoundException fileNotFoundException) {
                                fileNotFoundException.printStackTrace();
                            }

                            studentDashboard.setVisible(true);

                            dispose();
                        }else {
                            JOptionPane.showMessageDialog(null,"User not exists!!");

                        }
                    }
                }


                /*
                Admin admin= new Admin();
                admin.readFile(admin);
                Student student = new Student();
                student.readFile(student);
                Employee employee = new Employee();
                employee.readFile(employee);
                if (group.getSelection().getActionCommand()=="admin"){
                    if (admin.search(email.getText().trim(),password.getText().trim())){
                        AdminDashboard adminDashboard = new AdminDashboard();
                        adminDashboard.setVisible(true);
                        dispose();
                    }else {
                        JOptionPane.showMessageDialog(null,"Invalid");
                    }
                }else if (group.getSelection().getActionCommand()=="student"){
                    if (student.search(email.getText().trim(),password.getText().trim())){
                        StudentDashboard studentDashboard = new StudentDashboard();
                        studentDashboard.setVisible(true);
                        dispose();
                    }else {
                        JOptionPane.showMessageDialog(null,"Invalid");
                    }
                }else {
                    if (employee.search(email.getText().trim().toLowerCase(),password.getText().trim())){
                        StudentDashboard studentDashboard = new StudentDashboard();
                        studentDashboard.setVisible(true);
                        dispose();
                    }else {
                        JOptionPane.showMessageDialog(null,"Invalid");
                    }
                }
               // System.out.println(group.getSelection().getActionCommand());

                 */








            }
        });

    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        welcome_image= new JLabel( new ImageIcon("photos/WELCOM ICON-01.png"));


    }





}
