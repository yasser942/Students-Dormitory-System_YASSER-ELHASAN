import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Chef  extends Employee {
    ArrayList<Meal> meals = new ArrayList<>();
    ArrayList<Student> studentsAtResturant = new ArrayList<>();

    public Chef() throws FileNotFoundException {
        readMeals();
        readRegisteredStudents();
    }

    public void readMeals() throws FileNotFoundException {
        File meals_data = new File("database/meals.txt");
        Scanner scanner = new Scanner(meals_data);
        if (meals_data.exists() && meals_data.length() != 0) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] attributes = line.split(",");
                if (attributes.length > 1) {
                    meals.add(new Meal(attributes[0], attributes[1]));
                }
            }
        }

    }


    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    public void writeMealData() throws IOException {
        FileWriter fw = new FileWriter("database/meals.txt");

        for (Meal m : meals) {
            fw.write(m.getName() + "," + m.getPath() + "\n");
        }
        fw.close();
    }

    public void deleteMeal(String name) {
        for (Meal m : meals) {
            if (m.getName().equals(name)) {
                meals.remove(m);
                break;
            }
        }
    }

    public boolean isMealExists(String name) {
        for (Meal m : meals) {
            if (m.getName().equalsIgnoreCase(name)) {
                return false;

            }
        }
        return true;
    }

    public void readRegisteredStudents() throws FileNotFoundException {

        File myMeals_data = new File("database/myMeals.txt");
        Student student = new Student();
        Scanner scanner = new Scanner(myMeals_data);
        if (myMeals_data.exists() && myMeals_data.length() != 0) {
            while (scanner.hasNextLine()) {
                ArrayList<String> k = new ArrayList<>();

                StringBuffer line = new StringBuffer(scanner.nextLine());
                if (line.length() > 1) {
                    line.deleteCharAt(line.length() - 1);
                    String b = String.valueOf(line);
                    String[] s = b.split(",");
                    studentsAtResturant.add(student.getByEmailStudent(s[0]));


                }
            }
        }
    }
}
