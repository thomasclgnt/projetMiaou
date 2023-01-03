import org.junit.Test;
import tcp.*;
import java.io.*;

import static org.junit.Assert.assertEquals;

public class TCPTest {

    @Test
    public void givenTCPClient_whenServerRespondsWhenStarted_thenCorrect() throws IOException {
        TCPClient client = new TCPClient();
        client.startConnection("127.0.0.1", 6666);
        String response = client.sendMessage("hello server");
        assertEquals("hello client", response);
    }
}
