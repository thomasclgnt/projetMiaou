package tcp;

import java.io.*;
import java.net.*;

public class TCPClient {

    private Socket clientSocket;

    public void startClient(Socket clientSocket) {

        try {

            ThreadTCP.startThreadReceiver(clientSocket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
