package udp;

import data.*;
import java.io.*;
import java.util.ArrayList;

public class UDPController {

    public static void sendConnexion(User user) throws IOException {
        UDPSender.broadcast("Connexion : " + user.username + " : " + user.addressIP + " : " + Integer.toString(user.portTCP));
        System.out.println("New connection : " + user.username);
    }

    public static void sendDeconnexion(User user) throws IOException{
        UDPSender.broadcast("Deconnexion : " + user.addressIP);
        System.out.println("Deconnexion : " + user.username);
    }

    public static void sendNewUsername(User user, String new_username) throws IOException{
        UDPSender.broadcast("Username changed : " + new_username + " : " + user.addressIP);
        System.out.println("Username changed, " + user.addressIP + "is now named " + new_username);
    }

    public static void messageReceived(String message, ArrayList<Notify> sub){

        String[] segments = message.split(" : ");
        String msgSyst = segments[0];


        if (msgSyst.equals("Connexion")) {
            String msgRest1 = segments[1];
            String msgRest2 = segments[2];
            String msgRest3 = segments[3];
            receiveConnexion(msgRest1, msgRest2, msgRest3, sub);
        } else if (msgSyst.equals("Deconnexion")){
            String msgRest1 = segments[1];
            receiveDeconnexion(msgRest1, sub);
        } else if (msgSyst.equals("Username changed")){
            String msgRest1 = segments[1];
            String msgRest2 = segments[2];
            receiveNewUsername(msgRest1, msgRest2, sub);
        }

    }

    public static void receiveConnexion(String username, String addressIP, String portTCP, ArrayList<Notify> subscribers){
        for (Notify sub : subscribers) {
            sub.notifyNewUser(username, addressIP, Integer.parseInt(portTCP));
        }
    }

    public static  void receiveDeconnexion(String addressIP, ArrayList<Notify> subscribers){
        for (Notify sub : subscribers) {
            try {
                sub.notifyDeleteUser(addressIP);
            } catch (UserNotFound userNotFound) {
                userNotFound.printStackTrace();
            }
        }
    }

    public static void receiveNewUsername(String new_username, String addressIP, ArrayList<Notify> subscribers){
        for (Notify sub : subscribers) {
            sub.notifyChangeUsername(new_username, addressIP);
        }
    }


}

/*
public class data.Notify {

    public static void notifyChangeUsername(String username) throws IOException{
        udp.UDPSender.broadcast(username) ;
        System.out.println("Pseudo changed");
    }

    public static ListUsers notifyGetUsers(){
        //TODO
        //on veut faire un broadcast où on au moins un utilisateur nous
        //envoie en réponse sa ListUsers
        //bonne idée ou plutot on build la liste nous même à chaque réponde user par user ?
        //comment faire dans les deux cas ?
    }


}
*/