package tcp;

import java.io.*;

public class StartSession {

    //appeler cette fonction pour lancer TCP dans le main
    //donner en argument user.portTCP
    public static void startSession(int portTCP) throws IOException, InterruptedException {
        TCPServer server = new TCPServer();
        server.start(portTCP);

        Thread.sleep(2000);
        server.stop() ;
    }

}
