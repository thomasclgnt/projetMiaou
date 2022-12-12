package data;

import java.util.ArrayList;

public class ListUser {

    ArrayList<User> activeUsers = null;

    public ListUser(){
        this.activeUsers = new ArrayList<User>();
    }

    public void addUser(String username, String addressIP, int portTCP) {
        this.activeUsers.add(new User(username, addressIP, portTCP)) ;
    }

    public void deleteUser(String addressIP) {
        for (User user : this.activeUsers){
            if (user.addressIP.equals(addressIP)) {
                this.activeUsers.remove(user);
            }
        }
    }

    public void changeUsername(String new_username, String addressIP) {
        for (User user : this.activeUsers) {
            if (user.addressIP.equals(addressIP)) {
                user.setUsername(new_username);
            }
        }
    }

    public boolean checkUsernameAvailable(User user) {
        return activeUsers.contains(user) ;
    }

    public int nbUsers () {
        return activeUsers.size() ;
    }

    public void printList(){
        //System.out.println(Arrays.toString(activeUsers.toArray()));
        System.out.print("[");
        for (User user : this.activeUsers) {
            System.out.print(user.username + ", " + user.addressIP + ", " + user.portTCP);
            System.out.println(" ; ");
        }
        System.out.println("]");
    }

}
