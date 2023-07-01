import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class AbsenceRequest {
    String email;
    String date;
    String requestState;
    ArrayList<AbsenceRequest> requests= new ArrayList<>();

    public AbsenceRequest(String email, String date, String requestState) {
        this.email = email;
        this.date = date;
        this.requestState = requestState;
    }

    public AbsenceRequest() throws FileNotFoundException {
        readData();
    }

    public void addAbsence(String email) throws IOException {

        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String formatted = date.format(new Date());
        requests.add(new AbsenceRequest(email,formatted,"Not Approved"));
        writeData();
    }

    public void writeData() throws IOException {
        FileWriter fw = new FileWriter("database/absence requests.txt");

        for (AbsenceRequest a : requests){
            fw.write(a.getEmail()+","+a.getDate()+","+ a.getRequestState()+"\n");
        }
        fw.close();
    }

    public  void readData() throws FileNotFoundException {

        File requests_data= new File("database/absence requests.txt");
        Scanner scanner = new Scanner(requests_data);
        if (requests_data.exists()&&requests_data.length()!=0){
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] attributes = line.split(",");
                if (attributes.length>1){
                    requests.add(new AbsenceRequest(attributes[0],attributes[1],attributes[2]));

                }

            }


        }



    }

    public AbsenceRequest getByEmail(String email){
        for (AbsenceRequest r :requests){
            if (r.getEmail().equals(email)){
                return  r;

            }
        }
        return null;
    }
    public  boolean isRequestExists(String email){
        for (AbsenceRequest r :requests){
            if (r.getEmail().equals(email)){
                return  false;

            }
        }
        return true;
    }
    public  void deleteRequest(String email) throws IOException {
        for (AbsenceRequest r :requests){
            if (r.getEmail().equals(email)){
                requests.remove(r);
                break;

            }
        }
        writeData();
    }






    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRequestState() {
        return requestState;
    }

    public void setRequestState(String requestState) {
        this.requestState = requestState;
    }
}
