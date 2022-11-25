import java.util.ArrayList;

public class ListUser {

    ArrayList<User> activeUsers = null;

    public ListUser(){
        this.activeUsers = new ArrayList<User>();
    }

    public void addUser(User user) {
        this.activeUsers.add(user) ;
    }

    public void deleteUser(User user) {
        this.activeUsers.remove(user);
    }

    public boolean checkUsernameAvailable(User user) {
        return activeUsers.contains(user) ;
    }

}
