import data.*;
import org.junit.*;
import udp.*;
import java.io.IOException;
import static org.junit.Assert.*;

public class ServiceTest {

    @Test
    public void connectionFromRemoteUser() throws InterruptedException, IOException {

        Service serv = new Service() ;
        serv.lancerService();

        Thread.sleep(10000);

        assertEquals("[marielabest, 192.168.3.101, 1234 ; \n" +
                "]", serv.getUsers().listToString());
    }

    @Test
    public void connectionToRemoteUser() throws InterruptedException, IOException {

        Service serv = new Service();
        serv.lancerService();

        User Marie = new User("marielabest", "192.168.3.101", 1234);
        UDPController.sendConnexion(Marie);

        Thread.sleep(20000);

        UDPSender.broadcast("end"); //pas forcément utile

    }

}