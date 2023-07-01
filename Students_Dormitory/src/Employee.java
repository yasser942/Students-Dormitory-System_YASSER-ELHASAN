public class Employee extends Person{
    String position;

    public Employee() {

    }

    public Employee(String name, String surname, int id, String email, String password, String phone, String position) {
        super(name, surname, id, email, password, phone);
        this.position = position;
    }

    public Employee(String name, String surname, String email, String password, String phone, String position) {
        super(name, surname, email, password, phone);
        this.position = position;
    }


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
