import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Library extends JFrame{
    private JTable library_books;

    private JPanel library;
    private JLabel books_ic;
    private JTable currentStudents;
    private JButton delete_book;
    private JButton update_book;
    private JButton addBookButton;
    private JTextField book_name;
    private JTextField book_author;
    private JTextField book_genre;
    private JTextField book_pages;
    private JTextField book_publisher;


    public Library() throws HeadlessException, IOException {
        initialize();
    }

    private void initialize() throws IOException {
        Librarian librarian= new Librarian();
        setSize(1000, 600);
        setResizable(false);
        setLayout(new GridLayout(2, 4));
        setContentPane(library);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Library");
        creatLibraryBooks();
        listCurrentStudents();

        library_books.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)library_books.getModel();
                book_name.setText(table_model.getValueAt(library_books.getSelectedRow(),0).toString());
                book_author.setText(table_model.getValueAt(library_books.getSelectedRow(),1).toString());
                book_genre.setText(table_model.getValueAt(library_books.getSelectedRow(),2).toString());
                book_pages.setText(table_model.getValueAt(library_books.getSelectedRow(),3).toString());
                book_publisher.setText(table_model.getValueAt(library_books.getSelectedRow(),4).toString());





            }
        });
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)library_books.getModel();
                if (book_name.getText().length()!=0&&
                        book_author.getText().length()!=0&&
                        book_genre.getText().length()!=0&&
                        book_pages.getText().length()!=0&&
                        book_publisher.getText().length()!=0
                ){
                    try {
                        Librarian librarian= new Librarian();


                        if(librarian.checkExistence(book_name.getText().strip())){
                            librarian.addBook(new Book(
                                    book_name.getText().strip(),
                                    book_author.getText().strip(),
                                    book_genre.getText().strip(),
                                    book_pages.getText().strip(),
                                    book_publisher.getText().strip()
                            ));


                            String [] rowData={
                                    book_name.getText().strip(),
                                    book_author.getText().strip(),
                                    book_genre.getText().strip(),
                                    book_pages.getText().strip(),
                                    book_publisher.getText().strip()

                            };
                            table_model.addRow(rowData);
                            librarian.writeBookData();
                            JOptionPane.showMessageDialog(null,"Book was added successfully !!");


                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Book already exists !!");

                        }
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }else {
                    JOptionPane.showMessageDialog(null,"Please fill all fields !!");

                }



            }
        });
        update_book.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)library_books.getModel();

                if(library_books.getSelectedRowCount()==1){
                    librarian.books.get(library_books.getSelectedRow()).setName(book_name.getText().strip());
                    librarian.books.get(library_books.getSelectedRow()).setAuthor(book_name.getText().strip());
                    librarian.books.get(library_books.getSelectedRow()).setGenre(book_name.getText().strip());
                    librarian.books.get(library_books.getSelectedRow()).setPagesNum(book_name.getText().strip());
                    librarian.books.get(library_books.getSelectedRow()).setPublisher(book_name.getText().strip());

                    try {
                        librarian.writeBookData();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    table_model.setValueAt(librarian.books.get(library_books.getSelectedRow()).getName(), library_books.getSelectedRow(), 0);
                    table_model.setValueAt(librarian.books.get(library_books.getSelectedRow()).getAuthor(), library_books.getSelectedRow(), 1);
                    table_model.setValueAt(librarian.books.get(library_books.getSelectedRow()).getGenre(), library_books.getSelectedRow(), 2);
                    table_model.setValueAt(librarian.books.get(library_books.getSelectedRow()).getPagesNum(), library_books.getSelectedRow(), 3);
                    table_model.setValueAt(librarian.books.get(library_books.getSelectedRow()).getPublisher(), library_books.getSelectedRow(), 4);
                    JOptionPane.showMessageDialog(null,"Book was updates successfully !!");





                }else {
                    JOptionPane.showMessageDialog(null,"Please select a row !!");

                }

            }
        });
        delete_book.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)library_books.getModel();
                if(library_books.getSelectedRowCount()==1){
                    librarian.deleteBook(librarian.books.get(library_books.getSelectedRow()).getName());
                    table_model.removeRow(library_books.getSelectedRow());
                    try {
                        librarian.writeBookData();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null,"Book was deleted successfully !!");

                }else{
                    JOptionPane.showMessageDialog(null,"Please select a row !!");

                }

            }
        });
    }

    private void creatLibraryBooks() throws IOException {
        library_books.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Librarian librarian = new Librarian();
        Object[][] books = new String[librarian.books.size()][5];
        for (int i = 0; i < books.length; i++) {
            books[i][0]=librarian.books.get(i).getName();
            books[i][1]=librarian.books.get(i).getAuthor();
            books[i][2]=librarian.books.get(i).getGenre();
            books[i][3]=librarian.books.get(i).getPagesNum();
            books[i][4]=librarian.books.get(i).getPublisher();



        }
        library_books.setModel(new DefaultTableModel(
                                       books,new Object[]{"Name","Author","Genre","Page No.","Publisher"}
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
        Librarian librarian = new Librarian();
        Object[][] availableStudents = new String[librarian.studentsAtLibrary.size()][4];
        for (int i = 0; i < availableStudents.length; i++) {
            availableStudents[i][0]=librarian.studentsAtLibrary.get(i).getName();
            availableStudents[i][1]=librarian.studentsAtLibrary.get(i).getSurname();
            availableStudents[i][2]=librarian.studentsAtLibrary.get(i).getEmail();
            availableStudents[i][3]=librarian.studentsAtLibrary.get(i).getPhone();

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
        this.books_ic= new JLabel(new ImageIcon("photos/admin_dashboard/library.png"));

    }
}
