package UDP;

import UDP.UDPSender;

import java.io.*;

public class UDPController {
    
    /*
    public void sendNewUsername(User user, String newusername) throws IOException {
        UDP.UDPSender.broadcast("Username changed for "  ": "username) ;
        System.out.println("Pseudo changed");
    } */

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

}

/*
public class Notify {

    public static void notifyChangeUsername(String username) throws IOException{
        UDP.UDPSender.broadcast(username) ;
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