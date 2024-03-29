import data.*;
import org.junit.Test;
import service.TCPController;
import tcp.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class TCPTest {

    String ipaddress1 = "10.1.5.13" ; // à remplir selon les conditions de test, sender
    String ipaddress2 = "10.1.5.12" ; // à remplir selon les conditions de test, receiver

    User Tester1 = new User("tester1", ipaddress1, 1234) ;
    User Tester2 = new User("tester2", ipaddress2, 1234) ;

    @Test
    public void testTCP_sendLoopback() throws IOException, InterruptedException {
    //test à un seul ordinateur

        User Tester = new User("tester", "127.0.0.1", 1234);

        ListMessageIn receivedMessages = new ListMessageIn();
        //créer une seule instance de listUser dans le main de l'application
        ListUser users = new ListUser();
        users.addUser(Tester.username, Tester.addressIP, Tester.portTCP);

        MessageReceivedCallback callback = new MessageReceivedCallback() {
            @Override
            public void received(InetAddress from, String message, String horodatage) {

                try {
                    User distant = users.findUser(from) ;//vérifier que socket.getInetAddress prend l'adresse distante et pas la notre
                    User us = users.findUser("127.0.0.1") ;

                    MessageIn msgData = new MessageIn(distant.username, distant.addressIP, us.username, us.addressIP, message, horodatage);

                    System.out.println("Message received from " + distant.addressIP + " : "+ message);
                    receivedMessages.addMessage(msgData.source, msgData.IPsource, msgData.dest, msgData.IPdest, msgData.text, msgData.horodatage);

                } catch (UserNotFound userNotFound) {
                    throw new AssertionError("no such user") ;
                }

            }

        };

        TCPController.initListening(Tester.portTCP, callback);
        Thread.sleep(200);
        Socket socket = TCPController.startConversation(Tester.addressIP, Tester.portTCP, callback);
        TCPController.sendMessage("hello server", socket );

        Thread.sleep(1000); // il nous faut du temps sinon ça coupe les actions avant de mettre en bdd

        assertEquals("hello server ", receivedMessages.get(0).text);

        System.out.println("done");

    }


    @Test
    public void testSendTCP() throws IOException, InterruptedException {
    //We are tester1

        ListMessageIn receivedMessages = new ListMessageIn();
        //créer une seule instance de listUser dans le main de l'application
        ListUser users = new ListUser();
        users.addUser(Tester2.username, Tester2.addressIP, Tester2.portTCP);

        MessageReceivedCallback callback = new MessageReceivedCallback() {
            @Override
            public void received(InetAddress from, String message, String horodatage) {

                try {
                    User distant = users.findUser(from) ;//vérifier que socket.getInetAddress prend l'adresse distante et pas la notre
                    //User us = users.findUser("127.0.0.1") ;
                    User Tester1 = new User("tester1", ipaddress1, 1234) ;

                    MessageIn msgData = new MessageIn(distant.username, distant.addressIP, Tester1.username, Tester1.addressIP, message, horodatage);

                    System.out.println("Message received from " + distant.addressIP + " : "+ message);
                    receivedMessages.addMessage(msgData.source, msgData.IPsource, msgData.dest, msgData.IPdest, msgData.text, msgData.horodatage);

                } catch (UserNotFound userNotFound) {
                    throw new AssertionError("no such user") ;
                }

            }

        };

        TCPController.initListening(Tester2.portTCP, callback);
        Thread.sleep(5000);
        Socket socket = TCPController.startConversation(Tester2.addressIP, Tester2.portTCP, callback);
        TCPController.sendMessage("hello server", socket );

        Thread.sleep(1000); // il nous faut du temps sinon ça coupe les actions avant de mettre en bdd


        System.out.println("send");

    }

    @Test
    public void testReceiveTCP() throws IOException, InterruptedException {
    //we are Tester2

        ListMessageIn receivedMessages = new ListMessageIn();
        //créer une seule instance de listUser dans le main de l'application
        ListUser users = new ListUser();
        users.addUser(Tester1.username, Tester1.addressIP, Tester1.portTCP);

        MessageReceivedCallback callback = new MessageReceivedCallback() {
            @Override
            public void received(InetAddress from, String message, String horodatage) {

                try {
                    User distant = users.findUser(from) ;//vérifier que socket.getInetAddress prend l'adresse distante et pas la notre
                    //User us = users.findUser("127.0.0.1") ;
                    User Tester2 = new User("tester2", ipaddress2, 1234) ;

                    MessageIn msgData = new MessageIn(distant.username, distant.addressIP, Tester2.username, Tester2.addressIP, message, horodatage);

                    System.out.println("Message received from " + distant.addressIP + " : "+ message);
                    receivedMessages.addMessage(msgData.source, msgData.IPsource, msgData.dest, msgData.IPdest, msgData.text, msgData.horodatage);

                } catch (UserNotFound userNotFound) {
                    throw new AssertionError("no such user") ;
                }
            }

        };
        System.out.println("debut");
        TCPController.initListening(Tester1.portTCP, callback);
        Thread.sleep(8000);
        Thread.sleep(4000);
        System.out.println(receivedMessages.listToString());
        assertEquals("hello server ", receivedMessages.get(0).text);

    }

}
