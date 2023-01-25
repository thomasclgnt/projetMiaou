import data.*;
import org.junit.*;
import service.Service;
import service.UDPController;
import udp.*;
import java.io.IOException;
import static org.junit.Assert.*;

public class UDPTest {

    @Test
    public void whenCanSendAndReceiveAndUpdate_thenCorrect() throws InterruptedException, IOException {

        Service serv = new Service() ;
        serv.lancerService();

        User Thomas = new User("TDMKM", "192.168.3.100", 1234);
        User Marie = new User("marielabest", "192.168.3.101", 1235);
        UDPController.sendConnexion(Thomas);
        UDPController.sendConnexion(Marie);
        //udp.UDPSender.broadcast("Miaou");
        //on teste s'il y a bien UN utilisateur dans la liste

        //assert 1 == users.nbUsers(); ==> À REVOIR CE QU'IL FAIT
        Thread.sleep(2000);
        assertEquals("[TDMKM, 192.168.3.100, 1234 ; \n" +
                "marielabest, 192.168.3.101, 1235 ; \n" +
                "]", serv.getUsers().listToString());
        Thread.sleep(2000);
        UDPController.sendNewUsername(Marie, "marie_d_ac");
        Thread.sleep(2000);
        UDPController.sendDeconnexion(Thomas);
        Thread.sleep(2000);
        assertEquals("[marie_d_ac, 192.168.3.101, 1235 ; \n" + "]" , serv.getUsers().listToString());
        assertNotEquals("[TDMKM, 192.168.3.100, 1234 ; \n" +
                "marielabest, 192.168.3.101, 1235 ; \n" +
                "]", serv.getUsers().listToString());
        Thread.sleep(3000);
        UDPSender.broadcast("end");

        //  String echo = client.sendEcho("hello server");
        //        assertEquals("hello server", echo);
        //        echo = client.sendEcho("server is working");
        //        assertFalse(echo.equals("hello server"));
    }

}

