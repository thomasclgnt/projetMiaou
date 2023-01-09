import data.ListMessage;
import data.ListUser;
import data.Message;
import data.User;
import org.junit.Test;
import tcp.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TCPTest {

    @Test
    public void givenTCPClient_whenServerRespondsWhenStarted_thenCorrect() throws IOException, InterruptedException {

        User Thomas = new User("TDMKM", "127.0.0.1", 1234);

        ListMessage receivedMessages = new ListMessage();

        MessageReceivedCallback callback = new MessageReceivedCallback() {
            @Override
            public void received(InetAddress from, Message message) {
                System.out.println("Message received from " + from + " : "+ message.toString());
                receivedMessages.addMessage(message.text, message.source, message.dest, message.horodatage);
                //ajout à ListMessage, penser à faire le lien ajout à la db
            }
        };

        TCPController.initListening(Thomas.portTCP, callback);
        Thread.sleep(200);
        Socket socket = TCPController.startSession(Thomas.addressIP, Thomas.portTCP, callback);
        TCPController.sendMessage("hello server", socket );

        Thread.sleep(500);

        // assertEquals("hello server", receivedMessages.get(0).startsWith("hello_server"));

        System.out.println("done");

    }



}
