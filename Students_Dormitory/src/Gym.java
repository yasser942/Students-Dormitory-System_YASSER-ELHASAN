import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Gym extends JFrame {
    private JPanel gym_panel;
    private JLabel gym_icon;
    private JTextField sport_name;
    private JTextField sport_path;
    private JButton updateSportButton;
    private JButton deleteSportButton;
    private JTable available_sports;
    private JTable currentStudents;
    private JButton choosePhotoButton;
    private JButton addSportButton;
    private JLabel sport_icon;

    public Gym() throws HeadlessException, FileNotFoundException {
        initialize();
    }

    private void initialize() throws FileNotFoundException {
        Trainer trainer = new Trainer();

        setSize(1000, 600);
        setResizable(false);
        setLayout(new GridLayout(2, 4));
        setContentPane(gym_panel);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Gym");
        listSports();
        listCurrentStudents();
        available_sports.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                DefaultTableModel table_model= (DefaultTableModel)available_sports.getModel();
                String path =trainer.sports.get(available_sports.getSelectedRow()).getPath();
                sport_icon.setIcon(chooseIcon(path));
                sport_name.setText(table_model.getValueAt(available_sports.getSelectedRow(),0).toString());
                sport_path.setText(trainer.sports.get(available_sports.getSelectedRow()).getPath());

            }
        });
        choosePhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sport_path.setText(getPath());
            }
        });
        addSportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)available_sports.getModel();

                if (sport_name.getText().length()<=0||sport_path.getText().length()<=0){
                    JOptionPane.showMessageDialog(null,"Please, fill all fields");

                }else {

                    trainer.addSport(new Sport(sport_name.getText(),sport_path.getText()));
                    try {
                        trainer.writeSportData();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    String [] rowData={
                            sport_name.getText().strip()
                    };
                    table_model.addRow(rowData);
                    JOptionPane.showMessageDialog(null,"Sport was added successfully !");

                }
            }
        });
        deleteSportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)available_sports.getModel();

                if (available_sports.getSelectedRowCount()==1){

                    trainer.deleteSport(trainer.sports.get(available_sports.getSelectedRow()).getName());
                    table_model.removeRow(available_sports.getSelectedRow());
                    try {
                        trainer.writeSportData();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null,"Sport was deleted successfully !");
                }else{
                    JOptionPane.showMessageDialog(null,"Please select a row !");

                }
            }
        });
        updateSportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)available_sports.getModel();

                if (available_sports.getSelectedRowCount()==1){

                    if (sport_name.getText().length()<=0||sport_path.getText().length()<=0){
                        JOptionPane.showMessageDialog(null,"Please, fill all fields");

                    }else {
                        if (trainer.isSportExists(sport_name.getText().strip())){
                            trainer.sports.get(available_sports.getSelectedRow()).setName(sport_name.getText().strip());
                            trainer.sports.get(available_sports.getSelectedRow()).setPath(sport_path.getText().strip());
                            table_model.setValueAt(trainer.sports.get(available_sports.getSelectedRow()).getName(),available_sports.getSelectedRow(),0);
                            JOptionPane.showMessageDialog(null,"Sport was updated successfully !");
                        }else {
                            JOptionPane.showMessageDialog(null,"Sport already exists !");

                        }


                    }
                }else {
                    JOptionPane.showMessageDialog(null,"Please select a row !");

                }
            }
        });
    }

    public  ImageIcon chooseIcon(String path){
        ImageIcon imageIcon= new ImageIcon(path);
        Image img = imageIcon.getImage();
        //Image newImage= img.getScaledInstance(meal_icon.getWidth(),meal_icon.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon image= new ImageIcon(img);
        return image;
    }

    public String getPath(){
        JFileChooser fileChooser= new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        FileNameExtensionFilter filter= new FileNameExtensionFilter("*.Images","png","jpg");
        int response=  fileChooser.showOpenDialog(null);
        fileChooser.addChoosableFileFilter(filter);
        if (response==JFileChooser.APPROVE_OPTION){
            File file= new File(fileChooser.getSelectedFile().getAbsolutePath());
            String path=file.getPath();
            return path;
        }
        return null;

    }

    public void  listSports() throws FileNotFoundException {
        available_sports.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Trainer trainer = new Trainer();
        Object[][] sports = new String[trainer.sports.size()][1];
        for (int i = 0; i < sports.length; i++) {
            sports[i][0]=trainer.sports.get(i).getName();




        }
        available_sports.setModel(new DefaultTableModel(
                sports,new Object[]{"Sport Name"}
                                 )
                                 {
                                     public boolean isCellEditable(int row , int column){
                                         return false;
                                     }
                                 }

        );

    }

    public void  listCurrentStudents() throws FileNotFoundException {
        currentStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Trainer trainer = new Trainer();
        Object[][] availableStudents = new String[trainer.studentsAtGym.size()][4];
        for (int i = 0; i < availableStudents.length; i++) {
            availableStudents[i][0]=trainer.studentsAtGym.get(i).getName();
            availableStudents[i][1]=trainer.studentsAtGym.get(i).getSurname();
            availableStudents[i][2]=trainer.studentsAtGym.get(i).getEmail();
            availableStudents[i][3]=trainer.studentsAtGym.get(i).getPhone();

        }
        currentStudents.setModel(new DefaultTableModel(
                                         availableStudents,new Object[]{"Student Name","Student Surname","Student Email","Student Phone"}
                                 )
                                 {
                                     public boolean isCellEditable(int row , int column){
                                         return false;
                                     }
                                 }

        );

    }

    private void createUIComponents() {
        this.gym_icon= new JLabel(new ImageIcon("photos/admin_dashboard/gym.png"));
    }
}
