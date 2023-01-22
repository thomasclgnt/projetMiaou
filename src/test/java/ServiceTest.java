import data.*;
import frontend.mainFXML;
import org.junit.*;
import service.Service;
import udp.*;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import static org.junit.Assert.*;

// À TESTER AVEC DEUX ORDIS (TOUJOURS LANCER LA VERSION SERVER/REMOTE USER EN PREMIER,
// ET LANCER RAPIDEMENT À LA SUITE L'AUTRE VERSION

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
    public void connexionService_usernameOk_remoteUserAlreadyConnected() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();
        Service serv = new Service();
        serv.lancerService();
        Thread.sleep(1000);
        serv.processConnection("marielabest");
        System.out.println("Remote User connected");
        Thread.sleep(5000) ;
        //adresseIP attendue à changer selon les machines utilisées pour les tests
        assertEquals("[TDMKM, 10.1.5.12, 1234 ; \n" + "]", serv.getUsers().listToString());
        assertEquals("[TDMKM, 10.1.5.12, 1234 ; \n" + "]", DatabaseController.restoreListUsers().listToString());
    }

    @Test
    public void connexionService_usernameOk_newUser() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();

        Service serv = new Service();
        serv.lancerService();

        Thread.sleep(1000);
        serv.processGetRemoteUsers();
        Thread.sleep(2000);
        serv.getListUsersFromDB();
        String usernameChosen = "TDMKM" ;
        boolean valid = serv.processCheckUsername(usernameChosen) ;

        assertEquals(true, valid);

        if (valid) {
            serv.processConnection(usernameChosen);
            Thread.sleep(1000) ;
            assertEquals("[marielabest, 10.1.5.13, 1234 ; \n" + "]", serv.getUsers().listToString());
            assertEquals("[marielabest, 10.1.5.13, 1234 ; \n" + "]", DatabaseController.restoreListUsers().listToString());
        }
    }

    @Test
    public void connexionService_usernameNotOk_remoteUserAlreadyConnected() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();
        Service serv = new Service();
        serv.lancerService();
        Thread.sleep(1000);
        serv.processConnection("marielabest");
        System.out.println("Remote User connected");
        Thread.sleep(5000) ;
    }

    @Test
    public void connexionService_usernameNotOk_newUser() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();

        Service serv = new Service();
        serv.lancerService();

        Thread.sleep(1000);
        serv.processGetRemoteUsers();
        Thread.sleep(2000);
        serv.getListUsersFromDB();
        String usernameChosen = "marielabest" ;
        boolean valid = serv.processCheckUsername(usernameChosen) ;

        assertEquals(false, valid);

    }

    @Test
    public void changeUsernameService_remoteUserAlreadyConnected() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();
        Service serv = new Service();
        serv.lancerService();
        Thread.sleep(1000);
        serv.processConnection("marielabest");
        System.out.println("Remote User connected");
        Thread.sleep(5000) ;

        System.out.println("changement username");
        serv.processChangeUsername("marie_d_ac");

        Thread.sleep(5000) ;
    }

    @Test
    public void changeUsernameService_newUser() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();

        Service serv = new Service();
        serv.lancerService();

        Thread.sleep(1000);
        serv.processGetRemoteUsers();
        Thread.sleep(2000);
        serv.getListUsersFromDB();
        String usernameChosen = "TDMKM" ;
        serv.processConnection(usernameChosen);
        Thread.sleep(5000);

        assertEquals("[marie_d_ac, 10.1.5.13, 1234 ; \n" + "]", serv.getUsers().listToString());
        assertEquals("[marie_d_ac, 10.1.5.13, 1234 ; \n" + "]", DatabaseController.restoreListUsers().listToString());

    }


    @Test
    public void deconnexionService_remoteUserAlreadyConnected() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();
        Service serv = new Service();
        serv.lancerService();
        Thread.sleep(1000);
        serv.processConnection("marielabest");
        System.out.println("Remote User connected");
        Thread.sleep(5000) ;

        System.out.println("Test deconnexion");
        serv.processDeconnection();
    }

    @Test
    public void deconnexionService_newUser() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();

        Service serv = new Service();
        serv.lancerService();

        Thread.sleep(1000);
        serv.processGetRemoteUsers();
        Thread.sleep(2000);
        serv.getListUsersFromDB();
        String usernameChosen = "TDMKM" ;
        serv.processConnection(usernameChosen);
        Thread.sleep(5000) ;

        assertEquals("[]", serv.getUsers().listToString());
        assertEquals("[]", DatabaseController.restoreListUsers().listToString());

    }

    @Test
    public void testAddArrayList_Server() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();
        Service serv = new Service();
        serv.lancerService();
        Thread.sleep(1000);
        serv.processConnection("marielabest");
        System.out.println("Remote User connected");
        Thread.sleep(5000) ;

        System.out.println("Server : " + serv.getUsers().listToString());
        ArrayList<User> liste = serv.getUsers().convertToArrayList();
        System.out.println("Server : " +liste.toString());

    }

    @Test
    public void testAddArrayList_Client() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();

        Service serv = new Service();
        serv.lancerService();

        Thread.sleep(1000);
        serv.processGetRemoteUsers();
        Thread.sleep(2000);
        serv.getListUsersFromDB();
        String usernameChosen = "TDMKM" ;
        serv.processConnection(usernameChosen);
        Thread.sleep(5000);

        System.out.println("Client : " + serv.getUsers().listToString());
        ArrayList<User> liste = serv.getUsers().convertToArrayList();
        System.out.println("Client : " +liste.toString());

    }

    @Test
    public void testSendMessage_Client() throws IOException, InterruptedException {
        Service serv = new Service();
        serv.lancerService();

        User dest = new User("pc_droite", "192.168.1.79", 1234) ;
        User Gauche = new User("ordi_gauche", "192.168.1.71", 1234);

        UDPController.sendConnexion(Gauche);
        Thread.sleep(5000);
        System.out.println("udp connecté");
        serv.processStartListening();

        System.out.println("tcp connecté au port");
        Thread.sleep(10000);

        serv.processSendMessage("bonjour droite", dest);
        System.out.println("envoyé");

    }

    @Test
    public void testReceiveMessage_Server() throws IOException, InterruptedException {

        Service serv = new Service();
        serv.lancerService();
        System.out.println("service udp lancé");
        Thread.sleep(10000);
        assertEquals("[ordi_gauche, 192.168.1.71, 1234 ; \n" +
                "]", serv.getUsers().listToString());

        //ListUser users = new ListUser();
        //User distant = new User("thomas gauche", "192.168.1.71", 1234) ;
        //User local = new User("local thomas droite","192.168.1.79", 1234) ;
        //users.addUser(distant.username, distant.addressIP, distant.portTCP);
        //users.addUser(local.username, local.addressIP, local.portTCP) ;

        Thread.sleep(4000) ;
        serv.processStartListening();
        Thread.sleep(10000);
        System.out.println(serv.getListMessage().listToString());
        //assertEquals("[marie_d_ac, 10.1.5.13, 1234 ; \n" + "]", serv.getListMessage().listToString());
    }


}