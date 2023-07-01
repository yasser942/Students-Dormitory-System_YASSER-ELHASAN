public  class  Person {
    private  String name ;
    private  String surname;
    private int id;
    private String status;
    private  String email;
    private String phone;
    private String password;


    public Person() {
    }

    public Person(String name, String surname, int id, String email, String password, String phone) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Person(String name, String surname, String email, String password, String phone) {
        this.name = name;
        this.surname = surname;
        this.status = status;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public void add() {}




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
