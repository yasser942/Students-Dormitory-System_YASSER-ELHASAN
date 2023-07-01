import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Room {
    private int roomId;
    private String StudentEmail;
    private boolean isBooked;
    private String date;
    ArrayList<Room> rooms= new ArrayList<>();


    public Room() throws FileNotFoundException {
        readRoomData();
    }

    public Room(int roomId, String studentEmail, boolean isBooked,String date) {
        this.roomId = roomId;
        StudentEmail = studentEmail;
        this.isBooked = isBooked;
        this.date=date;
    }

    public void readRoomData() throws FileNotFoundException {
        File rooms_data= new File("database/rooms.txt");
        Scanner scanner = new Scanner(rooms_data);
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] attributes = line.split(",");
            rooms.add(new Room(Integer.valueOf(attributes[0]),attributes[1],Boolean.valueOf(attributes[2]),attributes[3]));

        }
    }
    public void writeData() throws IOException {
        FileWriter fw = new FileWriter("database/rooms.txt");
        for (Room r :rooms){
            fw.write(r.getRoomId()+","+r.getStudentEmail()+","+r.isBooked()+","+r.getDate()+"\n");
        }
        fw.close();

    }




    public int getRoomId() {
        return roomId;
    }
    public String getStudentEmail() {
        return StudentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        StudentEmail = studentEmail;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
