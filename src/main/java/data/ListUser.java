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

    public User findUser(String addressIP) {
        User user = null ;
        for (User u : this.activeUsers){
            if (u.addressIP.equals(addressIP)) {
                user = u ;
            }
        }
        return user ;
    } //TODO problème à gérer si l'user est pas trouvé

    public void deleteUser(String addressIP) {
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
