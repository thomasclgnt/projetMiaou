import data.*;
import org.junit.*;
import service.Service;
import service.UDPController;
import udp.*;
import java.io.IOException;
import static org.junit.Assert.*;

public class UDPTest {

    String ipaddress1 = "192.168.1.71" ; // à remplir selon les conditions de test
    String ipaddress2 = "192.168.1.79" ; // à remplir selon les conditions de test


    User Tester1 = new User("tester1", ipaddress1, 1234) ;
    User Tester2 = new User("tester2", ipaddress2, 1234) ;

    @Test
    public void whenCanSendAndReceiveAndUpdate_thenCorrect() throws InterruptedException, IOException {

        Service serv = new Service() ;
        serv.lancerService();

        User Thomas = new User("TDMKM", "192.168.3.100", 1234);
        User Marie = new User("marielabest", "192.168.3.101", 1235);
        //UDPController.sendConnexion(Thomas);
        UDPController.sendConnexion(Marie);

        //UDPController.sendConnexion(Thomas);
        //UDPController.sendConnexion(Marie);

        //on teste s'il y a bien UN utilisateur dans la liste

        //assert 1 == users.nbUsers(); ==> À REVOIR CE QU'IL FAIT

        Thread.sleep(2000);
        System.out.println("Attention au premier test");
        System.out.println("liste connectés : " + serv.getUsers().listToString());

        assertEquals("[TDMKM, 192.168.3.100, 1234 ; \n" +
                "marielabest, 192.168.3.101, 1235 ; \n" +
                "]", serv.getUsers().listToString());
        System.out.println("premier test ok");
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
    }

}

