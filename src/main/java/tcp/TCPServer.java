package tcp;
import java.net.*;
import java.io.*;

public class TCPServer {
    private ServerSocket serverSocket;

    public void startServer(int portTCP, MessageReceivedCallback callback) throws IOException {

        serverSocket = new ServerSocket(portTCP);

        Thread listen = new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    System.out.println("Serveur est à l'écoute du port " + serverSocket.getLocalPort());
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        System.out.println("[startServer] Connecté");
                        ThreadTCP.startThreadReceiver(clientSocket, callback);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("I am stopping to listen for new incoming connections");
                }
            }

        });

        listen.start();
    }
}