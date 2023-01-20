package service;

import data.*;
import udp.UDPController;
import udp.UDPReceiver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Service {

    public ListUser users = new ListUser() ;

    public User userLocal = new User("", IPAddress.getLocalIP().getHostAddress(), 1234);

    public Service() throws UnknownHostException {
    }

    public void lancerService() throws SocketException {

        //on crée un serveur avec socket associé
        UDPReceiver rec = new UDPReceiver();

        //on implémente Notify
        rec.addSubscriber(new Notify() {

            @Override
            public void notifyNewUser(String username, String addressIP, int portTCP) {
                System.out.println("new user");
                users.addUser(username, addressIP, portTCP);
            }

            @Override
            public void notifyDeleteUser(String addressIP) throws UserNotFound {
                System.out.println("del user");
                users.deleteUser(addressIP);
            }

            @Override
            public void notifyChangeUsername(String new_username, String addressIP) {
                System.out.println("change username");
                users.changeUsername(new_username, addressIP);
            }
        });

        //on démarre l'écoute du serveur
        rec.start();

        //si jamais c'est la première fois qu'on se connecte au service, sinon ne fait rien
        DatabaseController.createDBmiaoudb();

        DatabaseController.createTableMyself();
        DatabaseController.createTableUsers();

        //si jamais c'est la première fois qu'on se connecte au service, sinon ne fait rien
        DatabaseController.createTableMsg();

    }

    public ListUser getUsers() {
        return users;
    }

    public String getMyUsername() {
        return userLocal.username ;
    }

    public boolean processCheckUsername (String username) {
        return users.checkUsernameAvailable(username) ;
    }

    public void getListUsersFromDB(){

        users = DatabaseController.restoreListUsers();

    }

    public void processGetRemoteUsers () throws IOException, InterruptedException {
        UDPController.sendGetRemoteUsers() ;
    }

    // le username a déjà été validé avant de lancer processConnection
    public void processConnection(String validUsername) throws IOException {

        userLocal.setUsername(validUsername);
        DatabaseController.addMyself(userLocal.username);
        UDPController.sendConnexion(userLocal);

    }
    public void processDeconnection() throws IOException {

        UDPController.sendDeconnexion(userLocal);
        DatabaseController.dropTableMyself();
        DatabaseController.dropTableListUsers();

    }

    public void processChangeUsername(String new_username) throws IOException {

        UDPController.sendNewUsername(userLocal, new_username);
        DatabaseController.updateMyself(new_username);

    }

//PARTIE GESTION TCP
    //connecter à une session

    // envoyer un message + ajout bdd
    //recevoir un message + ajout bdd


}
