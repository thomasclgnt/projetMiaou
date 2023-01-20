import data.*;
import frontend.mainFXML;
import org.junit.*;
import service.Service;
import udp.*;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import static org.junit.Assert.*;

public class ServiceTest {


    @Test
    public void connectionClient() throws InterruptedException, IOException {
        Service serv = new Service();
        serv.lancerService();
        User Marie = new User("marielabest", "192.168.3.101", 1234);
        UDPController.sendConnexion(Marie);
        Thread.sleep(5000);
        UDPSender.broadcast("end"); //pas forcément utile
    }

    @Test
    public void connectionServer() throws InterruptedException, IOException {
        Service serv = new Service();
        serv.lancerService();
        Thread.sleep(10000);
        assertEquals("[marielabest, 192.168.3.101, 1234 ; \n" +
                "]", serv.getUsers().listToString());
    }

    @Test
    public void changeUsernameClient() throws InterruptedException, IOException {
        Service serv = new Service();
        serv.lancerService();
        User Marie = new User("marielabest", "192.168.3.101", 1234);
        UDPController.sendConnexion(Marie);
        Thread.sleep(5000);
        System.out.println("changement username");
        UDPController.sendNewUsername(Marie, "marie_d_ac");
    }

    @Test
    public void changeUsernameServer() throws InterruptedException, IOException {
        Service serv = new Service();
        serv.lancerService();
        Thread.sleep(5000);
        assertEquals("[marielabest, 192.168.3.101, 1234 ; \n" + "]", serv.getUsers().listToString());
        Thread.sleep(5000);
        assertEquals("[marie_d_ac, 192.168.3.101, 1234 ; \n" + "]" , serv.getUsers().listToString());
    }

    @Test
    public void deconnectionClient() throws InterruptedException, IOException {
        Service serv = new Service();
        serv.lancerService();
        User Marie = new User("marielabest", "192.168.3.101", 1234);
        System.out.println("connection");
        UDPController.sendConnexion(Marie);
        Thread.sleep(5000) ;
        System.out.println("deconnection");
        UDPController.sendDeconnexion(Marie);
    }

    @Test
    public void deconnectionServer() throws InterruptedException, IOException {
        Service serv = new Service();
        serv.lancerService();
        Thread.sleep(5000);
        assertEquals("[marielabest, 192.168.3.101, 1234 ; \n" + "]", serv.getUsers().listToString());
        Thread.sleep(5000);
        assertEquals("[]", serv.getUsers().listToString());
    }

    @Test
    public void chooseUniqueUsername_remoteUserAlreadyConnected() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();
        Service serv = new Service();
        serv.lancerService();
        Thread.sleep(1000);
        serv.processConnection("marielabest");
        System.out.println("Remote User connected");
        Thread.sleep(20000) ;
        //adresseIP attendue à changer selon les machines utilisées pour les tests
        assertEquals("[TDMKM, 10.1.5.12, 1234 ; \n" + "]", serv.getUsers().listToString());
        assertEquals("[TDMKM, 10.1.5.12, 1234 ; \n" + "]", DatabaseController.restoreListUsers().listToString());
        Thread.sleep(20000) ;
    }

    @Test
    public void chooseUniqueUsername_newUser() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();

        Service serv = new Service();
        serv.lancerService();

        Thread.sleep(1000);
        serv.processGetRemoteUsers();
        Thread.sleep(5000);
        serv.getListUsersFromDB();
        String usernameChosen = "TDMKM" ;
        boolean valid = serv.processCheckUsername(usernameChosen) ;

        assertEquals(true, valid);

        if (valid) {
            serv.processConnection(usernameChosen);
            Thread.sleep(20000) ;
            assertEquals("[marielabest, 10.1.5.13, 1234 ; \n" + "]", serv.getUsers().listToString());
            assertEquals("[marielabest, 10.1.5.13, 1234 ; \n" + "TDMKM, 10.1.5.12, 1234 ; \n" + "]", DatabaseController.restoreListUsers().listToString());
        }

        Thread.sleep(20000) ;
    }


}