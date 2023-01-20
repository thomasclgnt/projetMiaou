import data.*;
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
    public void chooseUniqueUsername_remoteUserAlreadyConnected() throws UnknownHostException, SocketException {
        Service serv = new Service();
        serv.lancerService();

        System.out.println("Remote User connected");
    }

    @Test
    public void chooseUniqueUsername_newUser() {

    }


}