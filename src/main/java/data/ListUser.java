package data;

import udp.UDPReceiver;

import java.net.InetAddress;
import java.util.ArrayList;

public class ListUser {

    ArrayList<User> activeUsers = null;

    public ListUser(){
        this.activeUsers = new ArrayList<User>();
    }

    public void addUser(String username, String addressIP, int portTCP) {
        this.activeUsers.add(new User(username, addressIP, portTCP)) ;
    }

    public User findUser(String addressIP) throws UserNotFound {
        User user = null ;
        for (User u : this.activeUsers){
            if (u.addressIP.equals(addressIP)) {
                user = u ;
            }
        }
        if (user == null) {
            throw new UserNotFound();
            // rajouter en paramètre (addressIP);
        }
        return user ;
    }

    public User findUser(InetAddress addressIP) throws UserNotFound {
        return findUser(addressIP.getHostAddress());
    }

    public User findUserWithUsername(String username) {
        User user = null ;
        for (User u : this.activeUsers){
            if (u.username.equals(username)) {
                user = u ;
            }
        }
        return user ;

    }

    public void deleteUser(String addressIP) throws UserNotFound {
        this.activeUsers.remove(findUser(addressIP));
    }

    public void changeUsername(String new_username, String addressIP) {
        for (User user : this.activeUsers) {
            if (user.addressIP.equals(addressIP)) {
                user.setUsername(new_username);
            }
        }
    }

    public boolean checkUsernameAvailable(String username) {
        boolean available = true ;
        for (User user : this.activeUsers) {
            if (user.username.equals(username)) {
                available = false;
            }
        }
        return available ;
    }

    public int nbUsers () {
        return activeUsers.size() ;
    }

    public String listToString(){
        String listDeb = "[" ;
        String list = "" ;
        for (User user : this.activeUsers) {
            list = list + user.username + ", " + user.addressIP + ", " + user.portTCP + " ; \n";
        }
        String listFin = "]" ;
        return listDeb + list + listFin ;
    }

    public void printList(){
        System.out.println(listToString());
    }

    public void addAll(ArrayList<User> list) {
        list.addAll(this.activeUsers);
    }

    public ArrayList<User> convertToArrayList() {
        ArrayList<User> list = new ArrayList<User>();
        list.addAll(this.activeUsers);
        return list ;
    }

    public ArrayList<String> toUsernameList(){
        ArrayList<String> usernameList = new ArrayList<>();
        for (User user : this.activeUsers){
            usernameList.add(user.username);
        }
        return usernameList ;
    }

}
