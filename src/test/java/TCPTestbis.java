import data.*;
import org.junit.Test;
import service.TCPController;
import tcp.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class TCPTestbis {

    @Test
    public void givenTCPClient_whenServerRespondsWhenStarted_thenCorrect() throws IOException, InterruptedException {

        User Thomas = new User("thomas", "127.0.0.1", 1234);

        ListMessageIn receivedMessages = new ListMessageIn();
        //créer une seule instance de listUser dans le main de l'application
        ListUser users = new ListUser();
        users.addUser(Thomas.username, Thomas.addressIP, Thomas.portTCP);

        MessageReceivedCallback callback = new MessageReceivedCallback() {
            @Override
            public void received(InetAddress from, String message, String horodatage) {

                try {
                    User distant = users.findUser(from) ;//vérifier que socket.getInetAddress prend l'adresse distante et pas la notre
                    User us = users.findUser(IPAddress.getLocalIP().getHostAddress()) ;

                    MessageIn msgData = new MessageIn(distant.username, distant.addressIP, us.username, us.addressIP, message, horodatage);

                    System.out.println("Message received from " + distant.addressIP + " : "+ message);
                    receivedMessages.addMessage(msgData.source, msgData.IPsource, msgData.dest, msgData.IPdest, msgData.text, msgData.horodatage);

                } catch (UserNotFound userNotFound) {
                    throw new AssertionError("no such user") ;
                }

            }

        };

        TCPController.initListening(Thomas.portTCP, callback);
        Thread.sleep(200);
        Socket socket = TCPController.startConversation(Thomas.addressIP, Thomas.portTCP, callback);
        TCPController.sendMessage("hello server", socket );

        Thread.sleep(1000); // il nous faut du temps sinon ça coupe les actions avant de mettre en bdd

        assertEquals("hello server ", receivedMessages.get(0).text);

        System.out.println("done");

    }

    @Test
    public void TCPSender() throws IOException, InterruptedException {

        User Thomas2 = new User("thomas2 droite", "192.168.1.79", 1234);

        ListMessageIn receivedMessages = new ListMessageIn();
        //créer une seule instance de listUser dans le main de l'application
        ListUser users = new ListUser();
        users.addUser(Thomas2.username, Thomas2.addressIP, Thomas2.portTCP);

        MessageReceivedCallback callback = new MessageReceivedCallback() {
            @Override
            public void received(InetAddress from, String message, String horodatage) {

                try {
                    User distant = users.findUser(from) ;//vérifier que socket.getInetAddress prend l'adresse distante et pas la notre
                    //User us = users.findUser("127.0.0.1") ;
                    User us = new User ("thomas","192.168.1.71",1234) ;

                    MessageIn msgData = new MessageIn(distant.username, distant.addressIP, us.username, us.addressIP, message, horodatage);

                    System.out.println("Message received from " + distant.addressIP + " : "+ message);
                    receivedMessages.addMessage(msgData.source, msgData.IPsource, msgData.dest, msgData.IPdest, msgData.text, msgData.horodatage);

                } catch (UserNotFound userNotFound) {
                    throw new AssertionError("no such user") ;
                }

            }

        };

        TCPController.initListening(Thomas2.portTCP, callback);
        Thread.sleep(5000);
        Socket socket = TCPController.startConversation(Thomas2.addressIP, Thomas2.portTCP, callback);
        TCPController.sendMessage("hello server comment tu vas ? ", socket );

        Thread.sleep(1000); // il nous faut du temps sinon ça coupe les actions avant de mettre en bdd

        //assertEquals("hello server ", receivedMessages.get(0).text);

        System.out.println("send");

    }

    @Test
    public void receiveTCP() throws IOException, InterruptedException {
        User distant = new User("ordi gauche", "192.168.1.71", 1234);

        ListMessageIn receivedMessages = new ListMessageIn();
        //créer une seule instance de listUser dans le main de l'application
        ListUser users = new ListUser();
        users.addUser(distant.username, distant.addressIP, distant.portTCP);

        MessageReceivedCallback callback = new MessageReceivedCallback() {
            @Override
            public void received(InetAddress from, String message, String horodatage) {

                try {
                    User distant = users.findUser(from) ;//vérifier que socket.getInetAddress prend l'adresse distante et pas la notre
                    //User us = users.findUser("127.0.0.1") ;
                    User us = new User ("thomas","192.168.1.79",1234) ;

                    MessageIn msgData = new MessageIn(distant.username, distant.addressIP, us.username, us.addressIP, message, horodatage);

                    System.out.println("Message received from " + distant.addressIP + " : "+ message);
                    receivedMessages.addMessage(msgData.source, msgData.IPsource, msgData.dest, msgData.IPdest, msgData.text, msgData.horodatage);

                } catch (UserNotFound userNotFound) {
                    throw new AssertionError("no such user") ;
                }

            }

        };
        System.out.println("debut");
        TCPController.initListening(distant.portTCP, callback);
        Thread.sleep(8000);
        Thread.sleep(4000);
        System.out.println(receivedMessages.listToString());



    }

}
