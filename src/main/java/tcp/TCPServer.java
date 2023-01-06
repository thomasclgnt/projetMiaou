package tcp;
import java.net.*;
import java.io.*;

public class TCPServer {
    private ServerSocket serverSocket;

    //    private Socket clientSocket;
    //    private PrintWriter out;
    //    private BufferedReader in;
    //
    //    public void start(int port) throws IOException {
    //        serverSocket = new ServerSocket(port);
    //        clientSocket = serverSocket.accept();
    //        out = new PrintWriter(clientSocket.getOutputStream(), true);
    //        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    //        String message = in.readLine();
    //        if ("hello server".equals(message)) {
    //            out.println("hello client");
    //        }
    //        else {
    //            out.println("unrecognised message");
    //        }
    //    }
    //
    //    public void stop() throws IOException {
    //        in.close();
    //        out.close();
    //        clientSocket.close();
    //        serverSocket.close();
    //    }

    public void startServer(int portTCP) throws IOException {

        try {
            serverSocket = new ServerSocket(portTCP);

            while (true) { // ce while doit permettre de vérifier qu'on est toujours connecté avec getState(Thread) ? Returns the state of this thread.
                    System.out.println("Serveur est à l'écoute du port " + serverSocket.getLocalPort());
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Connecté");
                    //ThreadTCP.startThreadSender(clientSocket); //inutile
                    ThreadTCP.startThreadReceiver(clientSocket);
            }
        }catch(IOException e){
            e.printStackTrace();
        }


    }


}