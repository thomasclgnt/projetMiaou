package tcp;

import java.io.*;
import java.net.*;

public class TCPClient {

    private Socket clientSocket;
    // private PrintWriter out;
    // private BufferedReader in;

    //public void startConnection(String ip, int port) throws IOException {
    //        clientSocket = new Socket(ip, port);
    //        out = new PrintWriter(clientSocket.getOutputStream(), true);
    //        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    //    }
    //
    //    public String sendMessage(String msg) throws IOException {
    //        out.println(msg);
    //        String resp = in.readLine();
    //        return resp;
    //    }
    //
    //    public void stopConnection() throws IOException {
    //        in.close();
    //        out.close();
    //        clientSocket.close();
    //    }



    public void startClient(String addressIP, int portTCP) {

        try {
            clientSocket = new Socket(addressIP,portTCP);

            //ThreadTCP.startThreadSender(clientSocket); pas besoin hors du test
            ThreadTCP.startThreadReceiver(clientSocket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
