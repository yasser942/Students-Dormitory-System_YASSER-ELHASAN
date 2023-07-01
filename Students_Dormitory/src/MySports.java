import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MySports extends JFrame {
    private JPanel my_sports_panel;
    private JLabel sports_icon;
    private JTable available_sports;
    private JTable mySports;
    private JButton addSportButton;
    private JButton deleteSportButton;
    private JLabel sport_icon;

    public MySports() throws HeadlessException, FileNotFoundException {
        initialize();
    }

    private void initialize() throws FileNotFoundException {

        Trainer trainer = new Trainer();
        Student student = new Student();
        setSize(1000, 500);
        setResizable(false);
        setLayout(new GridLayout(2, 4));
        setContentPane(my_sports_panel);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("My Sports");
        availableSports();
        listMySports();
        available_sports.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DefaultTableModel table_model= (DefaultTableModel)available_sports.getModel();
                String path = trainer.sports.get(available_sports.getSelectedRow()).getPath();
                sport_icon.setIcon(chooseIcon(path));
            }
        });
        addSportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)mySports.getModel();

                if (available_sports.getSelectedRowCount()==1){
                    try {
                        if (student.isSportExists(trainer.sports.get(available_sports.getSelectedRow()).getName(),student.currentSession().getEmail())){
                            try {
                                student.practiceSport(student.currentSession().getEmail(),trainer.sports.get(available_sports.getSelectedRow()).getName());
                                student.writeMySport();


                                String [] rowData={

                                        trainer.sports.get(available_sports.getSelectedRow()).getName()

                                };
                                table_model.addRow(rowData);
                                listMySports();
                                JOptionPane.showMessageDialog(null,"Sport was added successfully");

                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Sport already exists !!");

                        }
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Please select a sport!!");

                }
            }
        });
        deleteSportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)mySports.getModel();
                if (mySports.getSelectedRowCount()==1){
                    try {
                        student.deleteSport(student.returnSports(student.currentSession().getEmail()).get(mySports.getSelectedRow()));
                        table_model.removeRow(mySports.getSelectedRow());
                        student.writeMySport();

                        JOptionPane.showMessageDialog(null,"Sport was deleted successfully");

                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"No selected row!!");

                }
            }
        });
    }


    public void listMySports() throws FileNotFoundException {
        mySports.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Student student = new Student();
        ArrayList<String> a = student.returnSports(student.currentSession().getEmail());


        if (a != null) {
            Object[][] myCurrentSports = new String[a.size()][1];
            for (int i = 0; i < myCurrentSports.length; i++) {
                myCurrentSports[i][0] = a.get(i);


            }
            mySports.setModel(new DefaultTableModel(
                                      myCurrentSports, new Object[]{"My Sports"}
                              ) {
                                  public boolean isCellEditable(int row, int column) {
                                      return false;
                                  }
                              }

            );
        }
    }


    public void availableSports() throws FileNotFoundException {
        available_sports.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Trainer trainer = new Trainer();
        Object[][] sports = new String[trainer.sports.size()][1];
        for (int i = 0; i < sports.length; i++) {
            sports[i][0]=trainer.sports.get(i).getName();


        }
        available_sports.setModel(new DefaultTableModel(
                sports,new Object[]{"available Sports"}
                                 )
                                 {
                                     public boolean isCellEditable(int row , int column){
                                         return false;
                                     }
                                 }

        );
    }

    public  ImageIcon chooseIcon(String path){
        ImageIcon imageIcon= new ImageIcon(path);
        Image img = imageIcon.getImage();
        //Image newImage= img.getScaledInstance(meal_icon.getWidth(),meal_icon.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon image= new ImageIcon(img);
        return image;
    }

    private void createUIComponents() {
        sports_icon= new JLabel(new ImageIcon("photos/student_dashboard/gym.png"));
    }
}
