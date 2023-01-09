package tcp;

import java.io.*;
import java.net.*;

public class TCPClient {

    private Socket clientSocket;

    public void startClient(Socket clientSocket, MessageReceivedCallback callback) {

        try {

            ThreadTCP.startThreadReceiver(clientSocket, callback);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
