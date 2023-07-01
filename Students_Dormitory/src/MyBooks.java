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
import java.util.HashMap;
import java.util.Map;

public class MyBooks extends JFrame {

    private JPanel my_book_panel;
    private JLabel myBooksIcon;
    private JTable borrowed_books;
    private JTable available_books;
    private JButton borrow_book;
    private JButton return_book;
    private JLabel book_name;
    private JLabel book_author;
    private JLabel book_genre;
    private JLabel book_pages;
    private JLabel book_publisher;

    public MyBooks() throws HeadlessException, FileNotFoundException {
        initialize();
    }

    private void initialize() throws FileNotFoundException {
        Librarian librarian= new Librarian();
        Student student = new Student();

        setSize(1000, 500);
        setResizable(false);
        setLayout(new GridLayout(2, 4));
        setContentPane(my_book_panel);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("My Books");
        listAvailableBooks();
        listBorrowedBooks();


        available_books.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                DefaultTableModel table_model= (DefaultTableModel)available_books.getModel();
                book_name.setText(table_model.getValueAt(available_books.getSelectedRow(),0).toString());
                book_author.setText(table_model.getValueAt(available_books.getSelectedRow(),1).toString());
                book_genre.setText(table_model.getValueAt(available_books.getSelectedRow(),2).toString());
                book_pages.setText(table_model.getValueAt(available_books.getSelectedRow(),3).toString());
                book_publisher.setText(table_model.getValueAt(available_books.getSelectedRow(),4).toString());


            }
        });
        borrow_book.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)borrowed_books.getModel();

                if (available_books.getSelectedRowCount() ==1){
                    try {
                        if (student.isBookExists(librarian.books.get(available_books.getSelectedRow()).getName(),student.currentSession().getEmail())){
                            try {
                                //student.readBorrowedBooks();
                                student.borrowBook(student.currentSession().getEmail(),librarian.books.get(available_books.getSelectedRow()).getName());
                                student.writeMyBooks();


                                String [] rowData={

                                        librarian.books.get(available_books.getSelectedRow()).getName()

                                };

                                table_model.addRow(rowData);
                                listBorrowedBooks();

                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Book already exists !!");

                        }
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                }else {
                    JOptionPane.showMessageDialog(null,"Please select a book !!");

                }
            }
        });
        return_book.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel table_model= (DefaultTableModel)borrowed_books.getModel();
                if (borrowed_books.getSelectedRowCount()==1){
                    try {
                        student.deleteBook(student.returnBooks(student.currentSession().getEmail()).get(borrowed_books.getSelectedRow()));
                        table_model.removeRow(borrowed_books.getSelectedRow());
                        student.writeMyBooks();
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

    public  void listAvailableBooks() throws FileNotFoundException {

        available_books.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Librarian librarian = new Librarian();
        Object[][] books = new String[librarian.books.size()][5];
        for (int i = 0; i < books.length; i++) {
            books[i][0]=librarian.books.get(i).getName();
            books[i][1]=librarian.books.get(i).getAuthor();
            books[i][2]=librarian.books.get(i).getGenre();
            books[i][3]=librarian.books.get(i).getPagesNum();
            books[i][4]=librarian.books.get(i).getPublisher();



        }
        available_books.setModel(new DefaultTableModel(
                                       books,new Object[]{"Name","Author","Genre","Page No.","Publisher"}
                               )
                               {
                                   public boolean isCellEditable(int row , int column){
                                       return false;
                                   }
                               }

        );

    }

    public void listBorrowedBooks() throws FileNotFoundException {
        borrowed_books.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        Student student= new Student();
        ArrayList<String> a= student.returnBooks(student.currentSession().getEmail());

       if (a!=null){
           Object[][] myBorrowedBooks = new String[a.size()][1];
           for (int i = 0; i < myBorrowedBooks.length; i++) {
               myBorrowedBooks[i][0]=a.get(i);




           }
           borrowed_books.setModel(new DefaultTableModel(
                                           myBorrowedBooks,new Object[]{"My Books"}
                                   )
                                   {
                                       public boolean isCellEditable(int row , int column){
                                           return false;
                                       }
                                   }

           );
       }



    }

    private void createUIComponents() {
        myBooksIcon= new JLabel(new ImageIcon("photos/student_dashboard/my books.png"));

    }
}
