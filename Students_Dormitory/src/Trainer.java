import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Trainer extends Employee{
    ArrayList<Sport> sports= new ArrayList<>();
    ArrayList<Student> studentsAtGym = new ArrayList<>();


    public Trainer() throws FileNotFoundException {
        readSports();
        readRegisteredStudents();

    }

    public void readSports() throws FileNotFoundException {
        File sports_data= new File("database/sports.txt");
        Scanner scanner= new Scanner(sports_data);
        if (sports_data.exists()&&sports_data.length()!=0){
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] attributes = line.split(",");
                if (attributes.length>1){
                    sports.add(new Sport(attributes[0],attributes[1]));
                }
            }
        }

    }
    public void addSport(Sport sport){
        sports.add(sport);
    }
    public void writeSportData() throws IOException {
        FileWriter fw = new FileWriter("database/sports.txt");

        for (Sport m : sports){
            fw.write(m.getName()+","+m.getPath()+"\n");
        }
        fw.close();
    }
    public void deleteSport(String name){
        for (Sport m: sports){
            if (m.getName().equals(name)){
                sports.remove(m);
                break;
            }
        }
    }
    public boolean isSportExists(String name){
        for (Sport m :sports){
            if (m.getName().equalsIgnoreCase(name)){
                return false;

            }
        }
        return true;
    }

    public void readRegisteredStudents() throws FileNotFoundException {

        File mySports_data = new File("database/mySports.txt");
        Student student = new Student();
        Scanner scanner = new Scanner(mySports_data);
        if (mySports_data.exists() && mySports_data.length() != 0) {
            while (scanner.hasNextLine()) {
                ArrayList<String> k = new ArrayList<>();

                StringBuffer line = new StringBuffer(scanner.nextLine());
                if (line.length() > 1) {
                    line.deleteCharAt(line.length() - 1);
                    String b = String.valueOf(line);
                    String[] s = b.split(",");
                    studentsAtGym.add(student.getByEmailStudent(s[0]));


                }
            }
        }
    }


}
