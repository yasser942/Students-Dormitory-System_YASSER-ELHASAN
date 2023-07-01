import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Librarian extends Employee{

    ArrayList<Book> books= new ArrayList<>();
    ArrayList<Student> studentsAtLibrary = new ArrayList<>();

    public Librarian() throws FileNotFoundException {

        readBooks();
        readRegisteredStudents();
    }

    public void readBooks() throws FileNotFoundException {
        File booksData= new File("database/books.txt");
        Scanner scanner= new Scanner(booksData);
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] attributes = line.split(",");
            books.add(new Book(attributes[0],attributes[1],attributes[2],attributes[3],attributes[4]));
        }
    }
    public void writeBookData() throws IOException {
        FileWriter fw = new FileWriter("database/books.txt");

        for (Book b : books){
            fw.write(b.getName()+","+b.getAuthor()+","+
                    b.getGenre()+","+b.getPagesNum()+","+b.getPublisher()+"\n");
        }
        fw.close();
    }


    public boolean checkExistence(String name){
        for (Book b : books){
            if (b.getName().equals(name)){
                return  false;
            }
        }
        return true;
    }

    public void addBook(Book book)
    {
        books.add(book);
    }
    public  void deleteBook(String name){
        for (Book b: books){
            if (b.getName().equals(name)){
                books.remove(b);
                break;
            }
        }

    }
    public void readRegisteredStudents() throws FileNotFoundException {

        File myBooks_data = new File("database/myBooks.txt");
        Student student = new Student();
        Scanner scanner = new Scanner(myBooks_data);
        if (myBooks_data.exists() && myBooks_data.length() != 0) {
            while (scanner.hasNextLine()) {
                ArrayList<String> k = new ArrayList<>();

                StringBuffer line = new StringBuffer(scanner.nextLine());
                if (line.length() > 1) {
                    line.deleteCharAt(line.length() - 1);
                    String b = String.valueOf(line);
                    String[] s = b.split(",");
                    studentsAtLibrary.add(student.getByEmailStudent(s[0]));


                }
            }
        }
    }





}
