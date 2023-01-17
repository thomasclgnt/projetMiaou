import data.*;
import org.junit.Test;
import tcp.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TCPTest {

    @Test
    public void givenTCPClient_whenServerRespondsWhenStarted_thenCorrect() throws IOException, InterruptedException {

        User Thomas = new User("TDMKM", "127.0.0.1", 1234);

        ListMessageIn receivedMessages = new ListMessageIn();
        //créer une seule instance de listUser dans le main de l'application
        ListUser users = new ListUser();
        users.addUser(Thomas.username, Thomas.addressIP, Thomas.portTCP);

        MessageReceivedCallback callback = new MessageReceivedCallback() {
            @Override
            public void received(InetAddress from, String message, String horodatage) {

                try {
                    User distant = users.findUser(from) ;//vérifier que socket.getInetAddress prend l'adresse distante et pas la notre
                    User us = users.findUser("127.0.0.1") ;

                    MessageIn msgData = new MessageIn(us.username, us.addressIP, distant.username, distant.addressIP, message, horodatage);

                    System.out.println("Message received from " + distant.addressIP + " : "+ message.toString());
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

        // assertEquals("hello server", receivedMessages.get(0).startsWith("hello_server"));

        System.out.println("done");

    }



}
