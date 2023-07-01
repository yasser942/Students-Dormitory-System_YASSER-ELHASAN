import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileNotFoundException;

public class BedRooms extends JFrame {

    private JPanel bedRooms_panel;
    private JLabel bedrooms_icon;
    private JTable bedroomsList;

    public BedRooms() throws HeadlessException, FileNotFoundException {
        initialize();
    }

    private void initialize() throws FileNotFoundException {
        setSize(800, 600);
        setResizable(false);
        setLayout(new GridLayout(2, 4));
        setContentPane(bedRooms_panel);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Bedrooms");
        listBedrooms();
    }

    private void listBedrooms() throws FileNotFoundException {
        bedroomsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Room room= new Room();
        Object[][] bookedRooms = new String[room.rooms.size()][4];
        for (int i = 0; i < bookedRooms.length; i++) {
            bookedRooms[i][0]=String.valueOf(room.rooms.get(i).getRoomId());
            bookedRooms[i][1]=room.rooms.get(i).getStudentEmail();
            bookedRooms[i][2]=String.valueOf(room.rooms.get(i).isBooked());
            bookedRooms[i][3]=room.rooms.get(i).getDate();

        }
        bedroomsList.setModel(new DefaultTableModel(
                bookedRooms,new Object[]{"Room Id","Student Email","Is Booked","Booking Date"}
                                 )
                                 {
                                     public boolean isCellEditable(int row , int column){
                                         return false;
                                     }
                                 }

        );
    }

    private void createUIComponents() {
        this.bedrooms_icon= new JLabel(new ImageIcon("photos/admin_dashboard/bedrooms.png"));
    }
}
