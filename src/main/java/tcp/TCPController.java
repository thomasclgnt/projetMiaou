package tcp;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Scanner;

public class TCPController {

    public static void initListening(int portTCP, MessageReceivedCallback callback) throws IOException {
        TCPServer server = new TCPServer() ;
        server.startServer(portTCP, callback);

    }

    public static Socket startConversation(String addressIP, int portTCP, MessageReceivedCallback callback) throws IOException, InterruptedException {

            Socket clientSocket = new Socket(addressIP, portTCP) ;
            TCPClient client = new TCPClient();
            client.startClient(clientSocket, callback);
            System.out.println("[startSession] Connecté");

            // à faire quand on reçoit un message => callback.received(socket.adress, message); TODO

            return clientSocket ;

            //TODO créer exception spéficique ex. contact unavailable à renvoyer

    }

    public static void sendMessage(String message, Socket socket) throws IOException {

        PrintWriter out = new PrintWriter(socket.getOutputStream());

        out.println(message + " ");
        out.flush(); //vider les buffers
        }

    //dater les messages
    public static String horodatage() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String horodatage = formatter.format(date);
        return horodatage ;
    }

    }
