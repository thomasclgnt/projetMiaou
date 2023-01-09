package tcp;

import data.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Date;
import java.util.Scanner;

public class ThreadTCP {

    public static void startThreadReceiver(Socket socket, MessageReceivedCallback callback) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Thread receive = new Thread(new Runnable() {
            String message;

            @Override
            public void run() {

                try {
                    message = in.readLine();
                    while (message != null) {
                        //type Date
                        //SimpleDateFormat
                        //TODO ATTENTION PB
                        //demander au prof, comment on fait pour avoir IPsource et IPdest
                        // et est-ce que c'est pertinent d'avoir ces deux infos dans un type message ou non
                        // là j'ai mis deux fois socket.getInetAdress() parce que jsp comment récup notre adresse IP
                        // et pb pour récup l'user associé à l'adresse
                        Message msgData = new Message(message,
                                ListUser.findUser(socket.getInetAddress()),
                                ListUser.findUser(socket.getInetAddress()),
                                new Date().getTime())
                        callback.received(socket.getInetAddress(), msgData) ;
                        message = in.readLine();
                    }
                    System.out.println("Client déconnecté");
                    socket.close();

                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        });

        receive.setDaemon(true); // do not wait for the thread to finish to end the application
        receive.start();
    }

}