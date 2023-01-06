package tcp;

import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.util.Scanner;

public class TCPController {

    //rajouter une méthode clienécoute qui place le client en écoute ? => thread

    //appeler cette fonction pour lancer TCP dans le main
    //donner en argument user.portTCP
    public static Socket startSession(int portTCP) throws IOException, InterruptedException { //TODO adressip et réfléchir si cette méthode renvoie pas un socket et lequel

        //        TCPServer server = new TCPServer();
        //        server.start(portTCP);
        //        Thread.sleep(2000);
        //        server.stop() ;

            ServerSocket socketserver = new ServerSocket(portTCP);
            while (true) { // ce while doit permettre de vérifier qu'on est toujours connecté avec getState(Thread) ? Returns the state of this thread.
                System.out.println("Serveur est à l'écoute du port " + socketserver.getLocalPort());
                // créer objet socket Socket clientSocket
                System.out.println("Connecté");
                ThreadTCP.startThreadSender(clientSocket);
                ThreadTCP.startThreadReceiver(clientSocket);

                return clientSocket ; // est-ce qu'on retourne le socket du client ou du server ?

            }
            //revoir startSession, faire en sorte qu'elle renvoie le socket à utiliser dans sendMessage , enlever ou revoir while true
        //créer exception spéficique ex. contact unavailable à renvoyer

    }
    public static void sendMessage(String message, Socket socket) throws IOException {

        PrintWriter out = new PrintWriter(socket.getOutputStream());

        LocalTime time = LocalTime.now();
        out.println(message + " " + time);
        out.flush(); //vider les buffers
        }
    };


}
