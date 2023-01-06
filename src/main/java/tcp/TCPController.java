package tcp;

import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.util.Scanner;

public class TCPController {

    //rajouter une méthode clienécoute qui place le client en écoute ? => thread

    //appeler cette fonction pour lancer TCP dans le main
    //donner en argument user.portTCP

    public static void initListening(int portTCP) throws IOException {
        TCPServer server = new TCPServer() ;
        server.startServer(portTCP);
    }

    public static Socket startSession(String addressIP, int portTCP) throws IOException, InterruptedException {

            ServerSocket socketserver = new ServerSocket(portTCP);
            System.out.println("Serveur est à l'écoute du port " + socketserver.getLocalPort());
            Socket clientSocket = new Socket(addressIP, portTCP) ;
            TCPClient client = new TCPClient();
            client.startClient(clientSocket);
            System.out.println("Connecté");
            //   ThreadTCP.startThreadReceiver(clientSocket);

            return clientSocket ; // est-ce qu'on retourne le socket du client ou du server ?

            //TODO créer exception spéficique ex. contact unavailable à renvoyer

    }
    public static void sendMessage(String message, Socket socket) throws IOException {

        PrintWriter out = new PrintWriter(socket.getOutputStream());

        LocalTime time = LocalTime.now();
        out.println(message + " " + time);
        out.flush(); //vider les buffers
        }
    }
