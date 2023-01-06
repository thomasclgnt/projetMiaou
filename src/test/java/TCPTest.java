import data.User;
import org.junit.Test;
import tcp.*;
import java.io.*;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class TCPTest {

    @Test
    public void givenTCPClient_whenServerRespondsWhenStarted_thenCorrect() throws IOException, InterruptedException {

        User Thomas = new User("TDMKM", "127.0.0.1", 1234);

        Socket socket = TCPController.startSession(Thomas.addressIP, Thomas.portTCP);

        TCPController.sendMessage("hello server", socket ); //récup socket avec start session
        //assertEquals("hello client", response);
    }
}
