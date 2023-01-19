package service;

import data.ListUser;
import data.Notify;
import data.User;
import data.UserNotFound;
import udp.UDPController;
import udp.UDPReceiver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Service {

    public ListUser users = new ListUser() ;

    public User userLocal = new User("", InetAddress.getLocalHost().getHostAddress(), 1234);

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

    public void processGetRemoteUsers () throws IOException {
        UDPController.sendGetRemoteUsers() ;
        //appeler une méthode de UDP Controller
    }


    public void processNewConnection(String username) throws IOException {

        userLocal.setUsername(username);

        //remplir la liste des users connectés avec le retour de TCP

        UDPController.sendConnexion(userLocal);

    }

    public void processChangeUsername(String new_username) throws IOException {

        UDPController.sendNewUsername(userLocal, new_username);

    }



}
