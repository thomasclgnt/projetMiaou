package tcp;

import data.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
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
                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                        String horodatage = formatter.format(date);
                        callback.received(socket.getInetAddress(), message, horodatage) ;
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