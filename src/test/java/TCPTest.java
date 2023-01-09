import data.User;
import org.junit.Test;
import tcp.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class TCPTest {

    @Test
    public void givenTCPClient_whenServerRespondsWhenStarted_thenCorrect() throws IOException, InterruptedException {

        User Thomas = new User("TDMKM", "127.0.0.1", 1234);

        TCPController.initListening(Thomas.portTCP);
        Thread.sleep(200);
        MessageReceivedCallback callback = new MessageReceivedCallback() {
            @Override
            public void received(InetAddress from, String message) {
                System.out.println("Message received from " + from + ": "+message);
                //ajout à la db
            }
        };
        Socket socket = TCPController.startSession(Thomas.addressIP, Thomas.portTCP, callback);
        TCPController.sendMessage("hello server", socket ); //récup socket avec start session

        Thread.sleep(500);
        System.out.println("done");



        //assertEquals("hello client", response);
    }



}
