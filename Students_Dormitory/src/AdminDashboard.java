import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AdminDashboard extends JFrame {

    private JPanel Admin_dashboard;

    private JButton library;
    private JButton restaurant;
    private JButton absence_request;
    private JButton employees_management;
    private JButton gym;
    private JButton students_management;
    private JButton exit;
    private JButton bedrooms;
    private ImageIcon imageIcon;


    public AdminDashboard() throws HeadlessException {
        initialize();


    }

    private void initialize() {
        setSize(1000,600);
        setResizable(false);
        setLayout(new GridLayout(2,4));
        setContentPane(Admin_dashboard);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Admin Dashboard");

        library.setIcon(new ImageIcon("photos/admin_dashboard/library.png"));
        restaurant.setIcon(new ImageIcon("photos/admin_dashboard/restaurant.png"));
        gym.setIcon(new ImageIcon("photos/admin_dashboard/gym.png"));
        bedrooms.setIcon(new ImageIcon("photos/admin_dashboard/bedrooms.png"));
        employees_management.setIcon(new ImageIcon("photos/admin_dashboard/employee.png"));
        absence_request.setIcon(new ImageIcon("photos/admin_dashboard/absence list.png"));
        students_management.setIcon(new ImageIcon("photos/admin_dashboard/home page.png"));
        exit.setIcon(new ImageIcon("photos/admin_dashboard/Exit.png"));



        bedrooms.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BedRooms bedRooms= new BedRooms();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }

            }
        });
        employees_management.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EmployeeManagement employeeManagement= new EmployeeManagement();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog( null,"Please confirm if you want to exit","Students Dormitory",
                        JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                    System.exit(0);
            }
        });
        students_management.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Students students= null;
                try {
                    students = new Students();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                students.setVisible(true);

            }
        });
        restaurant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Restaurant restaurant = null;
                try {
                    restaurant = new Restaurant();
                } catch (FileNotFoundException fileNotFoundException) {

                }
                restaurant.setVisible(true);
            }
        });
        library.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Library library = null;
                try {
                    library = new Library();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                library.setVisible(true);
            }
        });
        gym.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gym gym= null;
                try {
                    gym = new Gym();
                } catch (FileNotFoundException fileNotFoundException) {

                }
                gym.setVisible(true);
            }
        });
        absence_request.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbsenceRequestsList absenceRequestsList= null;
                try {
                    absenceRequestsList = new AbsenceRequestsList();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                absenceRequestsList.setVisible(true);
            }
        });
    }
}
