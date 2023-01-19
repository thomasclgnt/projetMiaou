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

            /*
        User Thomas = new User("TDMKM", "192.168.3.100", 1234);
        //User Marie = new User("marielabest", "192.168.3.101", 1235);
        UDPController.sendConnexion(Thomas);
        //UDPController.sendConnexion(Marie);
        */

        Thread.sleep(10000);

        //assertEquals("[TDMKM, 192.168.3.100, 1234 ; \n" +
        //        "marielabest, 192.168.3.101, 1235 ; \n" +
        //        "]", serv.getUsers().listToString());

        assertEquals("[marielabest, 192.168.3.101, 1234 ; \n" +
                "]", serv.getUsers().listToString());

        /*
        Thread.sleep(8000);

        //UDPController.sendNewUsername(Marie, "marie_d_ac");
        Thread.sleep(2000);
        UDPController.sendDeconnexion(Thomas);
        Thread.sleep(2000);
        assertEquals("[marie_d_ac, 192.168.3.101, 1234 ; \n" + "]" , serv.getUsers().listToString());

        //        assertNotEquals("[TDMKM, 192.168.3.100, 1234 ; \n" +
        //                "marielabest, 192.168.3.101, 1235 ; \n" +
        //                "]", serv.getUsers().listToString());

        assertNotEquals("[marielabest, 192.168.3.101, 1235 ; \n" +
                "]", serv.getUsers().listToString());

        Thread.sleep(3000);
        UDPSender.broadcast("end");
         */

    }

}