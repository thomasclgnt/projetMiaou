import data.User;
import org.junit.Test;
import tcp.*;
import java.io.*;

import static org.junit.Assert.assertEquals;

public class TCPTest {

    @Test
    public void givenTCPClient_whenServerRespondsWhenStarted_thenCorrect() throws IOException, InterruptedException {

        User Thomas = new User("TDMKM", "192.168.3.100", 1234);

        TCPController.startSession(Thomas.portTCP) ;
        //finir le test TCP et TCP controller

        TCPClient client = new TCPClient();
        client.startConnection("127.0.0.1", Thomas.portTCP);
        String response = client.sendMessage("hello server");
        assertEquals("hello client", response);
    }
}
