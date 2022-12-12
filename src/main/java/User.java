import java.net.InetAddress;

public class User {

    public String username;
    public String addressIP;
    public int portTCP;

    User(String username, String addressIP, int portTCP){
        this.username = username;
        this.addressIP = addressIP;
        this.portTCP = portTCP;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
