package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;

public class ThreadTCP {

    public static void startThreadReceiver(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Thread receive = new Thread(new Runnable() {
            String Message ;
            @Override
            public void run() {

                try {
                    Message = in.readLine();
                    while (Message != null) {
                        System.out.println("message Client : " + Message);
                        Message = in.readLine();
                    }
                    System.out.println("Client déconnecté");
                    socket.close();

                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        });
        receive.start();
    }

    public static void startThreadSender(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream());

        Scanner scanner = new Scanner(System.in);

        Thread send = new Thread(new Runnable() {
            String Message;

            public void run() {
                while (true) { //TODO penser à tester la connexion
                    Message = scanner.nextLine();
                    LocalTime time = LocalTime.now();
                    //ajouter à l'historique/BDD à ce niveau
                    out.println(Message + " " + time);
                    out.flush(); //vider les buffers
                }
            }
        });

        send.start();

    }


}