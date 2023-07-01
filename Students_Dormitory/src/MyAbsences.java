import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyAbsences extends JFrame {
    private JPanel myAbsence_panel;
    private JLabel absence_icon;
    private JButton askForRequestButton;
    private JTable myRequestsTable;
    private JButton deleteRequestButton;

    public MyAbsences() throws HeadlessException, IOException {
        initialize();
    }

    private void initialize() throws IOException {

        AbsenceRequest absenceRequest= new AbsenceRequest();
        Student student= new Student();
        setSize(700,400);
        setContentPane(myAbsence_panel);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("My Absence");
        setResizable(false);
        listMyAbsence();

        askForRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)myRequestsTable.getModel();

                try {
                    if (absenceRequest.isRequestExists(student.currentSession().getEmail())){
                        try {
                            absenceRequest.addAbsence(student.currentSession().getEmail());
                            String [] rowData={

                                    absenceRequest.getByEmail(student.currentSession().getEmail()).getEmail(),
                                    absenceRequest.getByEmail(student.currentSession().getEmail()).getDate(),
                                    absenceRequest.getByEmail(student.currentSession().getEmail()).getRequestState(),



                            };
                            table_model.addRow(rowData);
                            listMyAbsence();
                            JOptionPane.showMessageDialog(null,"Request was added successfully !!");

                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }else{

                        JOptionPane.showMessageDialog(null,"You can not have more than 1 request!!");

                    }
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });
        deleteRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)myRequestsTable.getModel();

                if (myRequestsTable.getSelectedRowCount()==1){
                    try {
                        absenceRequest.deleteRequest(student.currentSession().getEmail());
                        table_model.removeRow(myRequestsTable.getSelectedRow());
                        JOptionPane.showMessageDialog(null,"Request was removed successfully !!");


                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else
                {
                    JOptionPane.showMessageDialog(null,"PLease select a row !!");

                }
            }
        });
    }

    private void listMyAbsence() throws IOException {
        myRequestsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        AbsenceRequest absenceRequest = new AbsenceRequest();
        Student student = new Student();
        Object[][] requests = new String[1][3];
        if (absenceRequest.requests.size()>0&&absenceRequest.getByEmail(student.currentSession().getEmail())!=null){
            requests[0][0] = absenceRequest.getByEmail(student.currentSession().getEmail()).getEmail();
            requests[0][1] = absenceRequest.getByEmail(student.currentSession().getEmail()).getDate();
            requests[0][2] = absenceRequest.getByEmail(student.currentSession().getEmail()).getRequestState();
            myRequestsTable.setModel(new DefaultTableModel(
                                             requests, new Object[]{"Email", "Date", "Request State"}
                                     ) {
                                         public boolean isCellEditable(int row, int column) {
                                             return false;
                                         }
                                     }

            );
        }



    }

    private void createUIComponents() {
        absence_icon= new JLabel( new ImageIcon("photos/student_dashboard/requset.png"));
    }
}
