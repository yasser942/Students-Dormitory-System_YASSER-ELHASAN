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

public class Restaurant  extends JFrame {
    private JPanel restaurant_panel;
    private JLabel restaurant_icon;
    private JTable currentStudents;
    private JTable available_meals;
    private JButton addMealButton;
    private JButton updateMealButton;
    private JButton deleteMealButton;
    private JLabel meal_icon;
    private JTextField meal_name;
    private JTextField meal_path;
    private JButton choosePhotoButton;

    public Restaurant() throws HeadlessException, FileNotFoundException {
        initialize();
    }

    private void initialize() throws FileNotFoundException {
        Chef chef = new Chef();
        setSize(1000, 600);
        setResizable(false);
        setLayout(new GridLayout(2, 4));
        setContentPane(restaurant_panel);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Restaurant");
        listMeals();
        listCurrentStudents();




        available_meals.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                DefaultTableModel table_model= (DefaultTableModel)available_meals.getModel();
                String path = chef.meals.get(available_meals.getSelectedRow()).getPath();
                meal_icon.setIcon(chooseIcon(path));
                meal_name.setText(table_model.getValueAt(available_meals.getSelectedRow(),0).toString());
                meal_path.setText(chef.meals.get(available_meals.getSelectedRow()).getPath());


            }
        });
        choosePhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                meal_path.setText(getPath());
            }
        });
        addMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)available_meals.getModel();

                if (meal_name.getText().length()<=0||meal_path.getText().length()<=0){
                    JOptionPane.showMessageDialog(null,"Please, fill all fields");

                }else {

                    chef.addMeal(new Meal(meal_name.getText(),meal_path.getText()));
                    try {
                        chef.writeMealData();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    String [] rowData={
                            meal_name.getText().strip()
                    };
                    table_model.addRow(rowData);
                    JOptionPane.showMessageDialog(null,"Meal was added successfully !");

                }
            }
        });
        deleteMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)available_meals.getModel();

                if (available_meals.getSelectedRowCount()==1){

                    chef.deleteMeal(chef.meals.get(available_meals.getSelectedRow()).getName());
                    table_model.removeRow(available_meals.getSelectedRow());
                    try {
                        chef.writeMealData();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null,"Meal was deleted successfully !");
                }else{
                    JOptionPane.showMessageDialog(null,"Please select a row !");

                }





            }
        });
        updateMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)available_meals.getModel();

                if (available_meals.getSelectedRowCount()==1){

                    if (meal_name.getText().length()<=0||meal_path.getText().length()<=0){
                        JOptionPane.showMessageDialog(null,"Please, fill all fields");

                    }else {
                        if (chef.isMealExists(meal_name.getText().strip())){
                            chef.meals.get(available_meals.getSelectedRow()).setName(meal_name.getText().strip());
                            chef.meals.get(available_meals.getSelectedRow()).setPath(meal_path.getText().strip());
                            table_model.setValueAt(chef.meals.get(available_meals.getSelectedRow()).getName(),available_meals.getSelectedRow(),0);
                            JOptionPane.showMessageDialog(null,"Meal was updated successfully !");
                        }else {
                            JOptionPane.showMessageDialog(null,"Meal already exists !");

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

    private void createUIComponents() {
        this.restaurant_icon= new JLabel(new ImageIcon("photos/admin_dashboard/restaurant.png"));
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

    public void  listMeals() throws FileNotFoundException {
        available_meals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Chef chef = new Chef();
        Object[][] meals = new String[chef.meals.size()][1];
        for (int i = 0; i < meals.length; i++) {
            meals[i][0]=chef.meals.get(i).getName();




        }
        available_meals.setModel(new DefaultTableModel(
                                       meals,new Object[]{"Meal Name"}
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
        Chef chef = new Chef();
        Object[][] availableStudents = new String[chef.studentsAtResturant.size()][4];
        for (int i = 0; i < availableStudents.length; i++) {
            availableStudents[i][0]=chef.studentsAtResturant.get(i).getName();
            availableStudents[i][1]=chef.studentsAtResturant.get(i).getSurname();
            availableStudents[i][2]=chef.studentsAtResturant.get(i).getEmail();
            availableStudents[i][3]=chef.studentsAtResturant.get(i).getPhone();

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
}


