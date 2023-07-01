import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends Person{
    ArrayList<Admin> admins = new ArrayList<>();
    ArrayList<Employee> employees= new ArrayList<>();


    public Admin(String name, String surname,int id, String email, String password, String phone) {
        super(name, surname, id, email, password, phone);
    }

    public Admin() throws FileNotFoundException {

        readData();
    }

    public Admin(String name, String surname,  String email, String password, String phone) {
        super(name, surname, email, password, phone);
    }

    public boolean authenticate (String email,String password,String status){
        if (status.equals("admin")){
            for (Admin a :admins){
                if (a.getEmail().equals(email.strip())&&a.getPassword().equals(password.strip())){
                    return true;
                }
            }
        }else if (status.equals("employee")){
            for (Employee a :employees){
                if (a.getEmail().equals(email.strip())&&a.getPassword().equals(password.strip())){
                    return true;
                }
            }
        }

        return false;
    }

    public void readData() throws FileNotFoundException {

        File admins_data= new File("database/admins.txt");
        File employees_data= new File("database/employees.txt");
        Scanner scanner = new Scanner(admins_data);
        Scanner scanner1= new Scanner(employees_data);
        int index1=1;
        int index2=1;

        if (admins_data.exists()&&admins_data.length()!=0){
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] attributes = line.split(",");
                admins.add(new Admin(attributes[0],attributes[1],index1,attributes[2],attributes[3],attributes[4]));
                index1++;
            }
        }
        if (employees_data.exists()&&employees_data.length()!=0){
            while (scanner1.hasNextLine()){
                String line = scanner1.nextLine();
                String[] attributes = line.split(",");
                employees.add(new Employee(attributes[0],attributes[1],index2,attributes[2],attributes[3],attributes[4],attributes[5]));
                index2++;
            }
        }else{
            return;
        }



    }
    public  void writeData(Admin admin) throws IOException {
        admins.add(admin);
        FileWriter fw = new FileWriter("database/admins.txt");

        for (Admin a : admins){
            fw.write(a.getName()+","+a.getSurname()+","+
                    a.getEmail()+","+a.getPassword()+","+a.getPhone()+"\n");
        }
        fw.close();
    }


    public void writeData() throws IOException {
        FileWriter fw = new FileWriter("database/employees.txt");

        for (Employee e : employees){
            fw.write(e.getName()+","+e.getSurname()+","+
                    e.getEmail()+","+e.getPassword()+","+e.getPhone()+","+e.getPosition()+"\n");
        }
        fw.close();
    }
    public  void addEmployee(Employee employee) throws IOException {
        employees.add(employee);
        FileWriter fw = new FileWriter("database/employees.txt");

        for (Employee e : employees){
            fw.write(e.getName()+","+e.getSurname()+","+
                    e.getEmail()+","+e.getPassword()+","+e.getPhone()+","+e.getPosition()+"\n");
        }
        fw.close();

    }

    public String getEmStatus(String email){
        for (Employee a:employees){
            if (a.getEmail().equals(email)){
                return a.getPosition();
            }
        }
        return null;
    }


    public boolean checkExistence(String email,String state){
        if (state.equals("employee")){
            for (Employee a:employees){
                if (a.getEmail().equals(email)){
                    return true;
                }
            }

        }else {
            for (Admin a:admins){
                if (a.getEmail().equals(email)){
                    return true;
                }
            }

        }
        return false;
        }
        public void deleteEmployee(String email) throws IOException {
        for (Employee a : employees){
            if (a.getEmail().equals(email)){
                employees.remove(a);
                break;
            }
        }
        writeData();

        }





    public void add(Admin admin) {
        admins.add(admin);

    }
}
