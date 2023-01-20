package udp;

import data.*;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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
        System.out.println("Username changed, " + user.addressIP + " is now named " + new_username);
    }

    public static void sendGetRemoteUsers() throws IOException {
        UDPSender.broadcast("GetRemoteUser");
        System.out.println("Demande de remote user");
        //se placer en attente de réception ?
    }

    public static void sendGetRemoteUsersAck(String username, InetAddress senderAddress) throws IOException {
        String msg = "Remote User : " + username ;
        UDPSender.send_udp(msg, senderAddress);
    }

    public static void messageReceived(String message, ArrayList<Notify> sub, InetAddress senderAddress){

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
        } else if (msgSyst.equals("GetRemoteUser")){
            try {
                receiveGetRemoteUsers(senderAddress);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (msgSyst.equals("Remote User")){
            String msgRest1 = segments[1];
            receiveGetRemoteUsersAck(msgRest1, senderAddress);

    }

    }


    public static void receiveGetRemoteUsers(InetAddress senderAddress) throws IOException {
        //il faut récupère son propre nom, à part si on est nous même la personne faisant la demande
        if (IPAddress.getLocalIP() != senderAddress) {
            String myUsername = DatabaseController.getMyName();
            System.out.println("My username : " + myUsername);
            //InetAddress.getHostAddress() pour transformer en string
            sendGetRemoteUsersAck(myUsername, senderAddress) ;
        }

    }

    public static void receiveGetRemoteUsersAck(String remoteUsername, InetAddress connectedUserAddress) {

        // ajouter à la base de donnée le nouvel utilisateur
        DatabaseController.addUser(remoteUsername, connectedUserAddress.getHostAddress());

    }

    public static void receiveConnexion(String username, String addressIP, String portTCP, ArrayList<Notify> subscribers){
        for (Notify sub : subscribers) {
            sub.notifyNewUser(username, addressIP, Integer.parseInt(portTCP));
            DatabaseController.addUser(username, addressIP);
        }
    }

    public static  void receiveDeconnexion(String addressIP, ArrayList<Notify> subscribers){
        for (Notify sub : subscribers) {
            try {
                sub.notifyDeleteUser(addressIP);
                //DatabaseController.deleteUser(addressIP);
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