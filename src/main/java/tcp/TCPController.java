package tcp;

import java.io.*;

public class TCPController {

    //rajouter une méthode clienécoute qui place le client en écoute ? => thread

    //appeler cette fonction pour lancer TCP dans le main
    //donner en argument user.portTCP
    public static void startSession(int portTCP) throws IOException, InterruptedException {
        TCPServer server = new TCPServer();
        server.start(portTCP);
        Thread.sleep(2000);
        server.stop() ;
    }

}
