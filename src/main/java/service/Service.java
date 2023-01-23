package service;

import data.*;
import observer.Notify;
import observer.Observable;
import observer.Observer;
import tcp.MessageReceivedCallback;
import tcp.TCPController;
import udp.UDPController;
import udp.UDPReceiver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Service implements Observable {

    public ListUser users = new ListUser();

    public User userLocal = new User("", IPAddress.getLocalIP().getHostAddress(), 1234);

    ListMessageIn receivedMessages = new ListMessageIn();

    MessageReceivedCallback callback = new MessageReceivedCallback() {
        @Override
        public void received(InetAddress from, String message, String horodatage) {
           try {
               getListUsersFromDB() ; // on met à jour la liste users en faisant un appel à la bdd
               User distant = getUsers().findUser(from.getHostAddress());//vérifier que socket.getInetAddress prend l'adresse distante et pas la notre //renvoie l'user correspondant à l'adresse ip
               User us = new User(DatabaseController.getMyName(), IPAddress.getLocalIP().getHostAddress(), 1234) ;

               MessageIn msgData = new MessageIn(distant.username, distant.addressIP, us.username, us.addressIP, message, horodatage);

               System.out.println("Message received from " + msgData.source + " at address : " + msgData.IPsource + " : " + msgData.text);
               receivedMessages.addMessage(msgData.source, msgData.IPsource, msgData.dest, msgData.IPdest, msgData.text, msgData.horodatage);

            } catch (UserNotFound userNotFound) {
                throw new AssertionError("[callback] no such user");
            }

        }
    };

    public Service() throws UnknownHostException {
    }

    public void lancerService() throws SocketException {

        //sécurité pour les tests
        DatabaseController.dropTableMyself();
        DatabaseController.dropTableListUsers();

        //on crée un serveur avec socket associé
        UDPReceiver rec = new UDPReceiver();

        //on implémente Notify
        rec.addSubscriber(new Notify() {

            @Override
            public void notifyNewUser(String username, String addressIP, int portTCP) {
                System.out.println("new user");
                users.addUser(username, addressIP, portTCP);
                this.notifyChangeListUsers();
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

    public ListMessageIn getListMessage() {
        return receivedMessages;
    }

    public String getMyUsername() {
        return userLocal.username;
    }

    public boolean processCheckUsername(String username) {
        return users.checkUsernameAvailable(username);
    }

    public void getListUsersFromDB() {

        users = DatabaseController.restoreListUsers();

    }

    public void processGetRemoteUsers() throws IOException, InterruptedException {
        UDPController.sendGetRemoteUsers();
    }

    // le username a déjà été validé avant de lancer processConnection
    public void processConnection(String validUsername) throws IOException {

        userLocal.setUsername(validUsername);
        DatabaseController.addMyself(userLocal.username);
        this.notifyChangeListUsers();
        UDPController.sendConnexion(userLocal);
        processStartListening() ;

    }

    public void processDeconnection() throws IOException {

        UDPController.sendDeconnexion(userLocal);
        DatabaseController.dropTableMyself();
        DatabaseController.dropTableListUsers();
        this.notifyChangeListUsers();

    }

    public void processChangeUsername(String new_username) throws IOException {

        UDPController.sendNewUsername(userLocal, new_username);
        DatabaseController.updateMyself(new_username);
        //TODO est ce qu'il faut pas relancer un process start listening ? pour que le callback ait le bon nom user_local ?

    }

//---------------------------------------------PARTIE GESTION TCP-------------------------------------------------------


    //connecter à une session
    public void processStartListening() throws IOException {
        TCPController.initListening(userLocal.portTCP, callback);
    }

    public Socket processStartConversation(User user_dest) throws IOException, InterruptedException {

        return TCPController.startConversation(user_dest.addressIP, userLocal.portTCP, callback);
    }

    // envoyer un message + ajout bdd
    public void processSendMessage(String message, User user_dest) throws IOException, InterruptedException {
        Socket socket = processStartConversation(user_dest);
        TCPController.sendMessage(message, socket);

        Message msg = new Message(userLocal.username, userLocal.addressIP, user_dest.username, user_dest.addressIP, message, TCPController.horodatage());
        DatabaseController.addMessage(msg);

    }


    @Override
    public void suscribe(Observer observer) {
        System.out.println("New suscriber : " + observer);
        this.observers.add(observer);
    }

    @Override
    public void notifyChangeListUsers() {
        System.out.println("The list of users has changed.");
        this.observers.forEach(Observer::updateListUsers);
        System.out.println("ListUsers updated.");
    }

    @Override
    public void notifyChangeConversation() {
        //TODO
    }
}
