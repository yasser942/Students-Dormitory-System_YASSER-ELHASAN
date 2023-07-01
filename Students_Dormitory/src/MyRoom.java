import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyRoom extends JFrame {

    private JPanel room_panel;
    private JLabel room_logo;
    private JButton bookARoomButton;
    private JLabel studnet_email;
    private JLabel room_num;
    private JLabel booking_date;
    private JLabel SE;
    private JLabel RN;
    private JLabel BD;

    public MyRoom() throws HeadlessException, FileNotFoundException {
        initialize();

    }

    private void initialize() throws FileNotFoundException {
        Student student= new Student();
        setSize(550,300);
        setContentPane(room_panel);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("My Room");
        setResizable(false);
        getInfo();
        if (student.hasRoom(student.currentSession().getEmail())){

            bookARoomButton.setEnabled(false);
            bookARoomButton.setBackground(Color.gray);

        }
        bookARoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (student.bookRoom(student.currentSession().getEmail())==0){
                        JOptionPane.showMessageDialog(null,"There is no available room now!!");

                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                JButton source = (JButton) e.getSource();
                source.setEnabled(false);
                source.setBackground(Color.gray);
                try {
                    getInfo();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }


            }
        });
    }


    private void createUIComponents() {
        room_logo= new JLabel( new ImageIcon("photos/student_dashboard/my room.png"));

    }

    private void getInfo() throws FileNotFoundException {
        Student student= new Student();
        if (student.getRoomByEmail(student.currentSession().getEmail())!=null){
            try {
                SE.setText(student.currentSession().getEmail());
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            try {
                RN.setText(String.valueOf(student.getRoomByEmail(student.currentSession().getEmail()).getRoomId()));
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            try {
                BD.setText(student.getRoomByEmail(student.currentSession().getEmail()).getDate());
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }

    }
}
