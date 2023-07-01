import java.io.FileNotFoundException;


public class Book {
    private String name;
    private String author;
    private String genre;
    private String PagesNum;
    private String publisher;


    public Book(String name, String author, String genre, String pagesNum, String publisher) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        PagesNum = pagesNum;
        this.publisher = publisher;

    }

    public Book() throws FileNotFoundException {

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPagesNum() {
        return PagesNum;
    }

    public void setPagesNum(String pagesNum) {
        PagesNum = pagesNum;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
