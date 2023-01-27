import data.*;
import org.junit.*;
import service.DatabaseController;
import service.Service;
import service.UDPController;
import udp.*;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

// À TESTER AVEC DEUX ORDIS (TOUJOURS LANCER LA VERSION SERVER/REMOTE USER EN PREMIER,
// ET LANCER RAPIDEMENT À LA SUITE L'AUTRE VERSION

public class ServiceTest {


    String ipaddress1 = "10.1.5.13" ; // à remplir selon les conditions de test, client
    String ipaddress2 = "10.1.5.12" ; // à remplir selon les conditions de test, receiver

    User Tester1 = new User("tester1", ipaddress1, 1234) ;
    User Tester2 = new User("tester2", ipaddress2, 1234) ;

    @Test
    public void connectionClient() throws InterruptedException, IOException {
        Service serv = new Service();
        serv.lancerService();

        UDPController.sendConnexion(Tester1);
        Thread.sleep(5000);
        UDPSender.broadcast("end");
    }

    @Test
    public void connectionServer() throws InterruptedException, IOException {
        Service serv = new Service();
        serv.lancerService();
        Thread.sleep(10000);
        assertEquals("[tester1, " + ipaddress1 + ", 1234 ; \n" +
                "]", serv.getUsers().listToString());
    }

    @Test
    public void changeUsernameClient() throws InterruptedException, IOException {
        Service serv = new Service();
        serv.lancerService();

        UDPController.sendConnexion(Tester1);
        Thread.sleep(5000);
        System.out.println("changement username");
        UDPController.sendNewUsername(Tester1, "Ultimate Tester");
    }

    @Test
    public void changeUsernameServer() throws InterruptedException, IOException {
        Service serv = new Service();
        serv.lancerService();
        Thread.sleep(5000);
        assertEquals("[tester1, " + ipaddress1 + ", 1234 ; \n" + "]", serv.getUsers().listToString());
        Thread.sleep(5000);
        assertEquals("[Ultimate Tester, " + ipaddress1 + ", 1234 ; \n" + "]", serv.getUsers().listToString());
    }

    @Test
    public void deconnectionClient() throws InterruptedException, IOException {
        Service serv = new Service();
        serv.lancerService();

        System.out.println("connection");
        UDPController.sendConnexion(Tester1);
        Thread.sleep(5000) ;
        System.out.println("deconnection");
        UDPController.sendDeconnexion(Tester1);
    }

    @Test
    public void deconnectionServer() throws InterruptedException, IOException {
        Service serv = new Service();
        serv.lancerService();
        Thread.sleep(5000);
        assertEquals("[tester1, " + ipaddress1 + ", 1234 ; \n" + "]", serv.getUsers().listToString());
        Thread.sleep(5000);
        assertEquals("[]", serv.getUsers().listToString());
    }

    @Test
    public void connexionService_usernameOk_Server() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();
        Service serv = new Service();
        serv.lancerService();
        Thread.sleep(1000);
        serv.processConnection(Tester2.username);
        System.out.println("Remote User connected");
        Thread.sleep(5000) ;
        assertEquals("[tester1, " + ipaddress1 + ", 1234 ; \n" + "]", serv.getUsers().listToString());
        assertEquals("[tester1, " + ipaddress1 + ", 1234 ; \n" + "]", DatabaseController.restoreListUsers().listToString());
    }

    @Test
    public void connexionService_usernameOk_Client() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();

        Service serv = new Service();
        serv.lancerService();

        Thread.sleep(1000);
        serv.processGetRemoteUsers();
        Thread.sleep(2000);
        serv.getListUsersFromDB();
        String usernameChosen = Tester1.username ;
        boolean valid = serv.processCheckUsername(usernameChosen) ;

        assertEquals(true, valid);

        if (valid) {
            serv.processConnection(usernameChosen);
            Thread.sleep(1000) ;
            assertEquals("[tester2, " + ipaddress2 + ", 1234 ; \n" + "]", serv.getUsers().listToString());
            assertEquals("[tester2, " + ipaddress2 + ", 1234 ; \n" + "]", DatabaseController.restoreListUsers().listToString());
        }
    }

    @Test
    public void connexionService_usernameNotOk_Server() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();
        Service serv = new Service();
        serv.lancerService();
        Thread.sleep(1000);
        serv.processConnection(Tester2.username);
        System.out.println("Remote User connected");
        Thread.sleep(5000) ;
    }

    @Test
    public void connexionService_usernameNotOk_Client() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();

        Service serv = new Service();
        serv.lancerService();

        Thread.sleep(1000);
        serv.processGetRemoteUsers();
        Thread.sleep(2000);
        serv.getListUsersFromDB();
        String usernameChosen = Tester2.username ;
        boolean valid = serv.processCheckUsername(usernameChosen) ;

        assertEquals(false, valid);

    }

    @Test
    public void changeUsernameService_Server() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();
        Service serv = new Service();
        serv.lancerService();
        Thread.sleep(1000);
        serv.processConnection(Tester2.username);
        System.out.println("Remote User connected");
        Thread.sleep(5000) ;

        System.out.println("changement username");
        serv.processChangeUsername("Ultimate Tester2");

        Thread.sleep(5000) ;
    }

    @Test
    public void changeUsernameService_Client() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();

        Service serv = new Service();
        serv.lancerService();

        Thread.sleep(1000);
        serv.processGetRemoteUsers();
        Thread.sleep(2000);
        serv.getListUsersFromDB();
        String usernameChosen = Tester1.username ;
        serv.processConnection(usernameChosen);
        Thread.sleep(5000);

        assertEquals("[Ultimate Tester2, " + ipaddress2 + ", 1234 ; \n" + "]", serv.getUsers().listToString());
        assertEquals("[Ultimate Tester2, " + ipaddress2 + ", 1234 ; \n" + "]", DatabaseController.restoreListUsers().listToString());

    }


    @Test
    public void deconnexionService_Server() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();
        Service serv = new Service();
        serv.lancerService();
        Thread.sleep(1000);
        serv.processConnection(Tester2.username);
        System.out.println("Remote User connected");
        Thread.sleep(5000) ;

        System.out.println("Test deconnexion");
        serv.processDeconnection();
    }

    @Test
    public void deconnexionService_Client() throws IOException, InterruptedException {
        DatabaseController.dropTableListUsers();
        DatabaseController.dropTableMyself();

        Service serv = new Service();
        serv.lancerService();

        Thread.sleep(1000);
        serv.processGetRemoteUsers();
        Thread.sleep(2000);
        serv.getListUsersFromDB();
        String usernameChosen = Tester1.username ;
        serv.processConnection(usernameChosen);
        Thread.sleep(5000) ;

        assertEquals("[]", serv.getUsers().listToString());
        assertEquals("[]", DatabaseController.restoreListUsers().listToString());

    }

    @Test
    public void testSendMessage_Client() throws IOException, InterruptedException {
        Service serv = new Service();
        serv.lancerService();

        serv.processConnection(Tester1.username);
        Thread.sleep(2000);
        System.out.println("udp connecté");


        System.out.println("tcp connecté au port");
        Thread.sleep(3000);

        serv.processSendMessage("bonjour", Tester2, serv.processStartConversation(Tester2));
        System.out.println("envoyé");

    }

    @Test
    public void testReceiveMessage_Server() throws IOException, InterruptedException {

        Service serv = new Service();
        serv.lancerService();

        serv.processConnection(Tester2.username);
        Thread.sleep(5000) ;

        System.out.println("users connectés : " + serv.getUsers().listToString());

        assertEquals("["+Tester1.username+", "+Tester1.addressIP+", "+Tester1.portTCP+" ; \n" +
                "]", serv.getUsers().listToString());

        //serv.processStartListening();
        Thread.sleep(7000);
        System.out.println(serv.getListMessage().listToString());
        assertEquals("bonjour ", serv.getListMessage().convertToArrayList().get(0).text);

    }


    @Test
    public void testSendAndReceive_Client() throws IOException, InterruptedException {

        Service serv = new Service();
        serv.lancerService();

        serv.processConnection(Tester1.username);
        serv.processGetRemoteUsers();
        Thread.sleep(2000);
        System.out.println("remote users récupérés");

        System.out.println("tcp connecté au port");
        Thread.sleep(3000);

        serv.processSendMessage("bonjour", Tester2, serv.processStartConversation(Tester2));
        Thread.sleep(2000);
        serv.processSendMessage("tu vas ?", Tester2, serv.processStartConversation(Tester2));
        Thread.sleep(2000);
        serv.processSendMessage("ouuui cool :)", Tester2, serv.processStartConversation(Tester2));

        assertEquals("oui et toi ?? ", serv.getListMessage().convertToArrayList().get(0).text);
    }

    @Test
    public void testSendAndReceive_Server() throws IOException, InterruptedException {
        Service serv = new Service();
        serv.lancerService();

        serv.processConnection(Tester2.username);
        Thread.sleep(9000) ;

        System.out.println("users connectés : " + serv.getUsers().listToString());

        assertEquals("["+Tester1.username+", "+Tester1.addressIP+", "+Tester1.portTCP+" ; \n" +
                "]", serv.getUsers().listToString());

        Thread.sleep(1000);
        serv.processSendMessage("oui et toi ??", serv.getUsers().convertToArrayList().get(0), serv.processStartConversation(serv.getUsers().convertToArrayList().get(0)));
        Thread.sleep(5000);
        System.out.println(serv.getListMessage().listToString());

        assertEquals("bonjour " + "tu vas ? " + "ouuui cool :) ",
                serv.getListMessage().convertToArrayList().get(0).text +
                serv.getListMessage().convertToArrayList().get(1).text +
                serv.getListMessage().convertToArrayList().get(2).text);

    }


}