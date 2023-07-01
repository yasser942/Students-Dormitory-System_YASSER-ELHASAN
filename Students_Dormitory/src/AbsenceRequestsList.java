import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AbsenceRequestsList extends JFrame {
    private JPanel absenceRequestsList;
    private JLabel absence_icon;
    private JTable requests_table;
    private JButton accepetRequestButton;
    private JButton rejectRequestButton;

    public AbsenceRequestsList() throws HeadlessException, IOException {
        initialize();
    }
    public void initialize() throws IOException {
        AbsenceRequest absenceRequest = new AbsenceRequest();

        setSize(1000, 600);
        setResizable(false);
        setLayout(new GridLayout(2, 4));
        setContentPane(absenceRequestsList);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Absence Requests");
        listAbsenceRequests();
        accepetRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (requests_table.getSelectedRowCount()==1){
                    absenceRequest.requests.get(requests_table.getSelectedRow()).setRequestState("Approved");
                    requests_table.setValueAt( absenceRequest.requests.get(requests_table.getSelectedRow()).getRequestState(), requests_table.getSelectedRow(), 2);
                    JOptionPane.showMessageDialog(null,"Request was approved successfully !!");
                    try {
                        absenceRequest.writeData();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else {
                    JOptionPane.showMessageDialog(null,"Please select a row  !!");

                }
            }
        });
        rejectRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (requests_table.getSelectedRowCount()==1){
                    absenceRequest.requests.get(requests_table.getSelectedRow()).setRequestState("Rejected");
                    requests_table.setValueAt( absenceRequest.requests.get(requests_table.getSelectedRow()).getRequestState(), requests_table.getSelectedRow(), 2);
                    JOptionPane.showMessageDialog(null,"Request was Rejected successfully !!");
                    try {
                        absenceRequest.writeData();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else {
                    JOptionPane.showMessageDialog(null,"Please select a row  !!");

                }
            }
        });
    }

    private void listAbsenceRequests() throws IOException {
        requests_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        AbsenceRequest absenceRequest = new AbsenceRequest();
        Student student = new Student();
        Object[][] requests = new String[absenceRequest.requests.size()][3];


        if (absenceRequest.requests.size()>0){
            for (int i = 0; i < requests.length; i++) {
                requests[i][0] = absenceRequest.requests.get(i).getEmail();
                requests[i][1] = absenceRequest.requests.get(i).getDate();
                requests[i][2] = absenceRequest.requests.get(i).getRequestState();

            }
            requests_table.setModel(new DefaultTableModel(
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
        absence_icon= new JLabel(new ImageIcon("photos/admin_dashboard/absence list.png"));
    }
}
