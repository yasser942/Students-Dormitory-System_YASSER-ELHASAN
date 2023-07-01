import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class StudentDashboard extends  JFrame {
    private JButton student_library;
    private JButton student_meals;
    private JButton request_gym;
    private JButton student_room;
    private JButton request_absence;
    private JButton exit;
    private JPanel student_dashboard;
    private JLabel student_icon;
    private JLabel Sname;
    private JLabel student_id;
    private JLabel student_email;

    public StudentDashboard() throws HeadlessException, FileNotFoundException {
        initialize();
    }

    private void initialize() throws FileNotFoundException {
        Student student= new Student();

        setSize(1000,700);
        setResizable(false);
        setLayout(new GridLayout(2,4));
        setContentPane(student_dashboard);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Student Dashboard");
        student_library.setIcon(new ImageIcon("photos/student_dashboard/my books.png"));

        request_gym.setIcon(new ImageIcon("photos/student_dashboard/gym.png"));
        exit.setIcon(new ImageIcon("photos/student_dashboard/exit.png"));
        student_room.setIcon(new ImageIcon("photos/student_dashboard/my room.png"));
        student_meals.setIcon(new ImageIcon("photos/student_dashboard/my meals.png"));
        request_absence.setIcon(new ImageIcon("photos/student_dashboard/requset.png"));
        Sname.setText(student.currentSession().getName()+" "+student.currentSession().getSurname());
        student_id.setText(String.valueOf(student.currentSession().getId()));
        student_email.setText(student.currentSession().getEmail());

        student_library.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {


                    MyBooks myBooks= new MyBooks();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        student_room.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyRoom myRoom= null;
                try {
                    myRoom = new MyRoom();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                myRoom.setVisible(true);
            }
        });
        student_meals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyMeals myMeals= null;
                try {
                    myMeals = new MyMeals();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                myMeals.setVisible(true);
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
        request_absence.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyAbsences myAbsences= null;
                try {
                    myAbsences = new MyAbsences();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                myAbsences.setVisible(true);
            }
        });
        request_gym.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MySports mySports= null;
                try {
                    mySports = new MySports();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
                mySports.setVisible(true);
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.student_icon=new JLabel( new ImageIcon("photos/student_dashboard/photo.png"));
    }
}
