package tcp;

import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.util.Scanner;

public class TCPController {

    public static void initListening(int portTCP) throws IOException {
        TCPServer server = new TCPServer() ;
        server.startServer(portTCP);

    }

    public static Socket startSession(String addressIP, int portTCP, MessageReceivedCallback callback) throws IOException, InterruptedException {

            Socket clientSocket = new Socket(addressIP, portTCP) ;
            TCPClient client = new TCPClient();
            client.startClient(clientSocket);
            System.out.println("[startSession] Connecté");

            callback.received(socket.adress, message);

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
