import data.*;
import org.junit.*;
import udp.*;
import java.io.IOException;
import java.net.SocketException;

import static org.junit.Assert.*;

public class UDPTest {

    @Test
    public void whenCanSendAndReceiveAndUpdate_thenCorrect() throws InterruptedException, IOException {
        ListUser users = new ListUser();

        //équivalent plus tard d'une fonction lancer service
        UDPReceiver rec = new UDPReceiver();
        rec.addSubscriber(new Notify() {

            @Override
            public void notifyNewUser(String username, String addressIP, int portTCP) {
                System.out.println("new user");
                users.addUser(username, addressIP, portTCP);
            }

            @Override
            public void notifyDeleteUser(String addressIP) {
                System.out.println("del user");
                users.deleteUser(addressIP);
            }

            @Override
            public void notifyChangeUsername(String new_username, String addressIP) {
                System.out.println("change username");
                users.changeUsername(new_username, addressIP);
            }
        });
        rec.start();

        User Thomas = new User("TDMKM", "192.168.3.100", 1234);
        User Marie = new User("marielabest", "192.168.3.101", 1235);
        UDPController.sendConnexion(Thomas);
        UDPController.sendConnexion(Marie);
        //udp.UDPSender.broadcast("Miaou");
        //on test s'il y a bien UN utilisateur dans la liste

        //assert 1 == users.nbUsers(); ==> À REVOIR CE QU'IL FAIT
        Thread.sleep(2000);
        assertEquals("[TDMKM, 192.168.3.100, 1234 ; \n" +
                "marielabest, 192.168.3.101, 1235 ; \n" +
                "]", users.listToString());
        Thread.sleep(2000);
        UDPController.sendNewUsername(Marie, "marie_d_ac");
        Thread.sleep(2000);
        UDPController.sendDeconnexion(Thomas);
        Thread.sleep(2000);
        assertEquals("[marie_d_ac, 192.168.3.101, 1235 ; \n" + "]" , users.listToString());
        assertNotEquals("[TDMKM, 192.168.3.100, 1234 ; \n" +
                "marielabest, 192.168.3.101, 1235 ; \n" +
                "]", users.listToString());
        Thread.sleep(3000);
        UDPSender.broadcast("end");

        //  String echo = client.sendEcho("hello server");
        //        assertEquals("hello server", echo);
        //        echo = client.sendEcho("server is working");
        //        assertFalse(echo.equals("hello server"));
    }

}

