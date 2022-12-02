import java.util.ArrayList;

public class ListUser {

    ArrayList<User> activeUsers = null;

    public ListUser(){
        this.activeUsers = new ArrayList<User>();
    }

    public void addUser(String username, String addressIP, int portTCP) {
        this.activeUsers.add(new User(username, addressIP, portTCP)) ;
    }

    public void deleteUser(String IP) {
        for (User user : this.activeUsers){
            if (user.addressIP.equals(IP)) {
                this.activeUsers.remove(user);
            }
        }
    }

    public boolean checkUsernameAvailable(User user) {
        return activeUsers.contains(user) ;
    }

}
