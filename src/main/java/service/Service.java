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

public class Service {

    public ListUser users = new ListUser() ;

    public Service() {
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

    public void processNewConnection(String username, InetAddress addressIP) throws IOException {
        User Thomas = new User(username, "192.168.3.100", 1234);

        //remplir la liste des users connectés avec le retour de TCP

        UDPController.sendConnexion(Thomas);

    }



}
