package data;

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
        //if user null raise usernotfound, rajouter l'adresse IP dans la classe en param
        return user ;
    } //TODO problème à gérer si l'user est pas trouvé

    public User findUser(InetAddress addressIP) throws UserNotFound {
        return findUser(addressIP.getHostAddress());
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

    public boolean checkUsernameAvailable(User user) {
        return activeUsers.contains(user) ;
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

}
