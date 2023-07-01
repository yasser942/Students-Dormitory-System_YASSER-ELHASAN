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

public class MyMeals extends JFrame {
    private JPanel my_meals_panel;
    private JLabel meals_icon;
    private JTable myMeals;
    private JTable available_meals;
    private JButton add_meal;
    private JButton deleteMealButton;
    private JLabel meal_icon;

    public MyMeals() throws HeadlessException, FileNotFoundException {
        initialize();
    }

    private void initialize() throws FileNotFoundException {
        Chef chef = new Chef();
        Student student = new Student();

        setSize(1000, 500);
        setResizable(false);
        setLayout(new GridLayout(2, 4));
        setContentPane(my_meals_panel);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("My Meals");
        availableMeals();
        listMyMeals();



        available_meals.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DefaultTableModel table_model= (DefaultTableModel)available_meals.getModel();
                String path = chef.meals.get(available_meals.getSelectedRow()).getPath();
                meal_icon.setIcon(chooseIcon(path));

            }
        });
        add_meal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)myMeals.getModel();

                if (available_meals.getSelectedRowCount()==1){
                    try {
                        if (student.isMealExists(chef.meals.get(available_meals.getSelectedRow()).getName(),student.currentSession().getEmail())){
                            try {
                                student.haveMeal(student.currentSession().getEmail(),chef.meals.get(available_meals.getSelectedRow()).getName());
                                student.writeMyMeal();


                                String [] rowData={

                                        chef.meals.get(available_meals.getSelectedRow()).getName()

                                };
                                table_model.addRow(rowData);
                                listMyMeals();
                                JOptionPane.showMessageDialog(null,"Meal was added successfully");

                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Meal already exists !!");

                        }
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                }else {
                    JOptionPane.showMessageDialog(null,"Please select a meal!!");

                }
            }
        });
        deleteMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)myMeals.getModel();
                if (myMeals.getSelectedRowCount()==1){
                    try {
                        student.deleteMeal(student.returnMeals(student.currentSession().getEmail()).get(myMeals.getSelectedRow()));
                        table_model.removeRow(myMeals.getSelectedRow());
                        student.writeMyMeal();

                        JOptionPane.showMessageDialog(null,"Meal was deleted successfully");

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

    public void availableMeals() throws FileNotFoundException {
        available_meals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Chef chef = new Chef();
        Object[][] meals = new String[chef.meals.size()][1];
        for (int i = 0; i < meals.length; i++) {
            meals[i][0]=chef.meals.get(i).getName();


        }
        available_meals.setModel(new DefaultTableModel(
                                         meals,new Object[]{"available Meals"}
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
        meals_icon= new JLabel(new ImageIcon("photos/student_dashboard/my meals.png"));
    }

    public void listMyMeals() throws FileNotFoundException {
        myMeals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Student student= new Student();
        ArrayList<String> a= student.returnMeals(student.currentSession().getEmail());


       if (a!=null){
           Object[][] myCurrentMeals = new String[a.size()][1];
           for (int i = 0; i < myCurrentMeals.length; i++) {
               myCurrentMeals[i][0]=a.get(i);


           }
           myMeals.setModel(new DefaultTableModel(
                                    myCurrentMeals,new Object[]{"My Meals"}
                            )
                            {
                                public boolean isCellEditable(int row , int column){
                                    return false;
                                }
                            }

           );
       }



    }
}
