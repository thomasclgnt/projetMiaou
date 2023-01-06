import data.User;
import org.junit.Test;
import tcp.*;
import java.io.*;

import static org.junit.Assert.assertEquals;

public class TCPTest {

    @Test
    public void givenTCPClient_whenServerRespondsWhenStarted_thenCorrect() throws IOException, InterruptedException {

        User Thomas = new User("TDMKM", "192.168.3.100", 1234);

        Thread thread = new Thread(() -> {
            try {
                TCPController.startSession(Thomas.portTCP);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        );
        thread.start();
        //finir le test TCP et TCP controller

        TCPClient client = new TCPClient();
        client.startClient("127.0.0.1", Thomas.portTCP);
        String response = TCPController.sendMessage("hello server", socket ); //récup socket avec start session
        assertEquals("hello client", response);
    }
}
