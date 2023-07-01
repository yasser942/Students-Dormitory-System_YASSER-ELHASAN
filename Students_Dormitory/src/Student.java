import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Student extends Person{
    private int roomId;
    Room room;
    ArrayList<String> studentMeals= new ArrayList<>();
    HashMap<String,ArrayList<String>> myMeals = new HashMap<>();

    ArrayList<String> studentSports= new ArrayList<>();
    HashMap<String,ArrayList<String>> mySports = new HashMap<>();

    ArrayList<Student> students= new ArrayList<>();

    HashMap<String,ArrayList<String>> myBooks = new HashMap<>();
    ArrayList<String> studentBooks= new ArrayList<>();



    public Student(String name, String surname, String email, String password, String phone,int roomId) {
        super(name, surname, email, password, phone);
        this.roomId=roomId;
    }

    public Student() throws FileNotFoundException {
        readData();
        readBorrowedBooks();
        readMyMeals();
        readMySports();
        room=new Room();
    }

    public Student(String name, String surname, int id, String email, String password, String phone) {
        super(name, surname, id, email, password, phone);
    }

    public Student(String name, String surname, String email, String password, String phone) {
        super(name, surname, email, password, phone);
    }


    public void WriteStudentData(Student student) throws IOException {
        students.add(student);
        FileWriter fw = new FileWriter("database/students.txt");

        for (Student a : students){
            fw.write(a.getName()+","+a.getSurname()+","+
                    a.getEmail()+","+a.getPassword()+","+a.getPhone()+","+a.getRoomId()+"\n");
        }
        fw.close();

    }

    public void WriteStudentData() throws IOException {

        FileWriter fw = new FileWriter("database/students.txt");

        for (Student a : students){
            fw.write(a.getName()+","+a.getSurname()+","+
                    a.getEmail()+","+a.getPassword()+","+a.getPhone()+"\n");
        }
        fw.close();

    }
    public  void readData() throws FileNotFoundException {
        int index=1;
        File students_data= new File("database/students.txt");
        Scanner scanner = new Scanner(students_data);
        if (students_data.exists()&&students_data.length()!=0){
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] attributes = line.split(",");
                if (attributes.length>1){
                    students.add(new Student(attributes[0],attributes[1],index,attributes[2],attributes[3],attributes[4]));
                    index++;
                }

            }
        }

    }

    public boolean checkExistence(String email){

            for (Student a:students){
                if (a.getEmail().equals(email)){
                    return true;
                }
            }


        return false;
    }

    public boolean authenticate (String email,String password){

            for (Student a :students){
                if (a.getEmail().equals(email.strip())&&a.getPassword().equals(password.strip())){

                    return true;
                }}


        return false;
    }
    public Student currentLogin(String email) throws IOException {
        FileWriter fw = new FileWriter("database/currentLogin.txt");

        for (Student a :students){
            if (a.getEmail().equals(email)){

                fw.write(a.getEmail()+"\n");
                fw.close();
                return a;
            }
        }



        return null;

    }
    public Student currentSession() throws FileNotFoundException {
        File file= new File("database/currentLogin.txt");
        Scanner scanner= new Scanner(file);
        String currentEmail= scanner.nextLine();

        for (Student a :students){
            if (a.getEmail().equals(currentEmail)){
                //a.setLogin(true);
                return a;
            }
        }
        return null;
    }


    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    public int bookRoom(String email) throws IOException {
        for (Room r: room.rooms){
            if (!r.isBooked()&&!r.getStudentEmail().equals(email)){
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                String formatted = date.format(new Date());
                r.setBooked(true);
                r.setStudentEmail(email);
                r.setDate(formatted);
                room.writeData();
                return r.getRoomId();


            }
        }
        return 0;
    }

    public  void emptyRoom(String email) throws IOException {
        for (Room r: room.rooms){
            if (r.getStudentEmail().equals(email)){

                r.setBooked(false);
                r.setStudentEmail("empty");
                r.setDate("undefined");
                room.writeData();

            }
        }
    }
    public  void updateRoom(String email,String newEmail) throws IOException {
        for (Room r: room.rooms){
            if (r.getStudentEmail().equals(email)){
                r.setStudentEmail(newEmail);
                room.writeData();
            }
        }
    }

    public boolean hasRoom(String email){
        for (Room r:room.rooms){
            if (r.getStudentEmail().equals(email)){
                return true;
            }
        }
        return false;
    }
    public Room getRoomByEmail(String email){
        for (Room r:room.rooms){
            if (r.getStudentEmail().equals(email)){
                return r;
            }
        }
        return null;
    }
    public  void borrowBook(String email,String bookName) throws IOException {

        studentBooks.add(bookName);
        ArrayList<String> s= myBooks.get(email);
        if (s==null){
            ArrayList<String> c= new ArrayList<>();
            myBooks.put(email,c);
        }else {
            s.add(bookName);
            myBooks.put(email,s);
        }



    }
    public void writeMyBooks() throws IOException {
        FileWriter fw = new FileWriter("database/myBooks.txt");
        for(Map.Entry<String, ArrayList<String>> m: myBooks.entrySet()) {
            fw.write(m.getKey()+",");
            for (String a :m.getValue()){
                fw.write(a+",");
            }
            fw.write("\n");
        }


        fw.close();
    }
    public void readBorrowedBooks() throws FileNotFoundException {


        File myBooks_data= new File("database/myBooks.txt");
        Scanner scanner = new Scanner(myBooks_data);
        if (myBooks_data.exists()&&myBooks_data.length()!=0)
        {
            while (scanner.hasNextLine()){
                ArrayList<String> k= new ArrayList<>();

                StringBuffer line= new StringBuffer(scanner.nextLine());
               if (line.length()>1){
                   line.deleteCharAt(line.length()-1);
                   String b= String.valueOf(line);
                   String[] s= b.split(",");
                   for (int i = 1; i <s.length ; i++) {
                       k.add(s[i]);
                   }
                   myBooks.put(s[0],k);

               }
            }
        }





    }
    public ArrayList<String> returnBooks(String email){


        for(Map.Entry<String, ArrayList<String>> m: myBooks.entrySet()) {

            if(m.getKey().equals(email)) {
                return myBooks.get(m.getKey());
            }

        }
        return null;
    }
    public void deleteBook(String name){

        for(Map.Entry<String, ArrayList<String>> m: myBooks.entrySet()) {
            for (int j = 0; j < m.getValue().size(); j++) {
                if (m.getValue().get(j).equals(name)){
                    m.getValue().remove(name);
                    break;
                }
            }



        }
    }
    public boolean isBookExists(String name,String email){
        for(Map.Entry<String, ArrayList<String>> m: myBooks.entrySet()) {
            if (m.getKey().equals(email)) {

                for (int j = 0; j < m.getValue().size(); j++) {
                    if (m.getValue().get(j).equals(name)){
                        return false;
                    }
                }
            }

        }
        return true;
    }

    /////////////
    public  void haveMeal(String email,String mealName) throws IOException {

        studentMeals.add(mealName);
        ArrayList<String> s= myMeals.get(email);
        if (s==null){
            ArrayList<String> c= new ArrayList<>();
            c.add(mealName);
            myMeals.put(email,c);
        }else {
            s.add(mealName);
            myMeals.put(email,s);
        }


    }
    public void writeMyMeal() throws IOException {
        FileWriter fw = new FileWriter("database/myMeals.txt");
        for(Map.Entry<String, ArrayList<String>> m: myMeals.entrySet()) {
            fw.write(m.getKey()+",");
            for (String a :m.getValue()){
                fw.write(a+",");
            }
            fw.write("\n");
        }
        fw.close();
    }

    public void readMyMeals() throws FileNotFoundException {


        File myMeals_data= new File("database/myMeals.txt");
        Scanner scanner = new Scanner(myMeals_data);
        if (myMeals_data.exists()&&myMeals_data.length()!=0)
        {
            while (scanner.hasNextLine()){
                ArrayList<String> k= new ArrayList<>();

                StringBuffer line= new StringBuffer(scanner.nextLine());
                if (line.length()>1){
                    line.deleteCharAt(line.length()-1);
                    String b= String.valueOf(line);
                    String[] s= b.split(",");
                    for (int i = 1; i <s.length ; i++) {
                        k.add(s[i]);
                    }
                    myMeals.put(s[0],k);

                }
            }
        }
}
    public ArrayList<String> returnMeals(String email){


        for(Map.Entry<String, ArrayList<String>> m: myMeals.entrySet()) {

            if(m.getKey().equals(email)) {
                return myMeals.get(m.getKey());
            }

        }
        return null;
    }
    public boolean isMealExists(String name,String email){
        for(Map.Entry<String, ArrayList<String>> m: myMeals.entrySet()) {
            if (m.getKey().equals(email)){

                for (int j = 0; j < m.getValue().size(); j++) {
                    if (m.getValue().get(j).equals(name)){
                        return false;
                    }
                }
            }


        }
        return true;
    }

    public void deleteMeal(String name){

        for(Map.Entry<String, ArrayList<String>> m: myMeals.entrySet()) {
            for (int j = 0; j < m.getValue().size(); j++) {
                if (m.getValue().get(j).equals(name)){
                    m.getValue().remove(name);
                    break;
                }
            }



        }
    }

    public  void addStudent(Student student) throws IOException {
        students.add(student);
        FileWriter fw = new FileWriter("database/students.txt");

        for (Student e : students){
            fw.write(e.getName()+","+e.getSurname()+","+
                    e.getEmail()+","+e.getPassword()+","+e.getPhone()+"\n");
        }
        fw.close();

    }
    public void deleteStudent(String email) throws IOException {
        for (Student a : students){
            if (a.getEmail().equals(email)){
                students.remove(a);
                break;
            }
        }
        WriteStudentData();

    }
    //////
    public Student getByEmailStudent(String email){
        for (Student a : students){
            if (a.getEmail().equals(email)){
              return a;

            }
        }
        return null;
    }
    //////////////////////
    public ArrayList<String> returnSports(String email){


        for(Map.Entry<String, ArrayList<String>> m: mySports.entrySet()) {

            if(m.getKey().equals(email)) {
                return mySports.get(m.getKey());
            }

        }
        return null;
    }
    public boolean isSportExists(String name,String email){
        for(Map.Entry<String, ArrayList<String>> m: mySports.entrySet()) {
            if (m.getKey().equals(email)){

                for (int j = 0; j < m.getValue().size(); j++) {
                    if (m.getValue().get(j).equals(name)){
                        return false;
                    }
                }
            }


        }
        return true;
    }

    public  void practiceSport(String email,String sportName) throws IOException {

        studentSports.add(sportName);
        ArrayList<String> s= mySports.get(email);
        if (s==null){
            ArrayList<String> c= new ArrayList<>();
            c.add(sportName);
            mySports.put(email,c);
        }else {
            s.add(sportName);
            mySports.put(email,s);
        }


    }
    public void readMySports() throws FileNotFoundException {


        File mySports_data= new File("database/mySports.txt");
        Scanner scanner = new Scanner(mySports_data);
        if (mySports_data.exists()&&mySports_data.length()!=0)
        {
            while (scanner.hasNextLine()){
                ArrayList<String> k= new ArrayList<>();

                StringBuffer line= new StringBuffer(scanner.nextLine());
                if (line.length()>1){
                    line.deleteCharAt(line.length()-1);
                    String b= String.valueOf(line);
                    String[] s= b.split(",");
                    for (int i = 1; i <s.length ; i++) {
                        k.add(s[i]);
                    }
                    mySports.put(s[0],k);

                }
            }
        }
    }
    public void writeMySport() throws IOException {
        FileWriter fw = new FileWriter("database/mySports.txt");
        for(Map.Entry<String, ArrayList<String>> m: mySports.entrySet()) {
            fw.write(m.getKey()+",");
            for (String a :m.getValue()){
                fw.write(a+",");
            }
            fw.write("\n");
        }
        fw.close();
    }
    public void deleteSport(String name){

        for(Map.Entry<String, ArrayList<String>> m: mySports.entrySet()) {
            for (int j = 0; j < m.getValue().size(); j++) {
                if (m.getValue().get(j).equals(name)){
                    m.getValue().remove(name);
                    break;
                }
            }



        }
    }
    //////////////
    public void deleteStudentFromGym(String email) throws IOException {
        for(Map.Entry<String, ArrayList<String>> m: mySports.entrySet()) {

            if (m.getKey().equals(email)) {

                mySports.remove(m.getKey());
            }
        }
        writeMySport();
    }
    public void updateStudentInGym(String email,String newEmail) throws IOException {
        for(Map.Entry<String, ArrayList<String>> m: mySports.entrySet()) {

            if (m.getKey().equals(email)) {
                ArrayList<String> temp= m.getValue();
                mySports.remove(m.getKey());

                mySports.put(newEmail,temp);
            }
        }
        writeMySport();
    }
    public void deleteStudentFromRestaurant(String email) throws IOException {
        for(Map.Entry<String, ArrayList<String>> m: myMeals.entrySet()) {

            if (m.getKey().equals(email)) {

                myMeals.remove(m.getKey());
            }
        }
        writeMyMeal();
    }
    public void updateStudentInRestaurant(String email,String newEmail) throws IOException {
        for(Map.Entry<String, ArrayList<String>> m: myMeals.entrySet()) {

            if (m.getKey().equals(email)) {
                ArrayList<String> temp= m.getValue();
                myMeals.remove(m.getKey());

                myMeals.put(newEmail,temp);
            }
        }
        writeMyMeal();
    }
    public void deleteStudentFromLibrary(String email) throws IOException {
        for(Map.Entry<String, ArrayList<String>> m: myBooks.entrySet()) {

            if (m.getKey().equals(email)) {

                myBooks.remove(m.getKey());
            }
        }
        writeMyBooks();
    }
    public void updateStudentInLibrary(String email,String newEmail) throws IOException {
        for(Map.Entry<String, ArrayList<String>> m: myBooks.entrySet()) {

            if (m.getKey().equals(email)) {
                ArrayList<String> temp= m.getValue();
                myBooks.remove(m.getKey());

                myBooks.put(newEmail,temp);
            }
        }
        writeMyBooks();
    }


}
