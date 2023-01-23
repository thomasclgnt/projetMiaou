package udp;

import observer.Notify;
import observer.Observer;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class UDPReceiver extends Thread {

    private static DatagramSocket socket = null;
    private boolean running;
    private ArrayList<Notify> subscribers = new ArrayList<>();

    public UDPReceiver() throws SocketException {
        socket = new DatagramSocket(UDPSender.port);
        System.out.println("Socket created");
    }

    public void addSubscriber(Notify sub) {
        this.subscribers.add(sub);
    }

    public void run() {
        running = true;
        byte[] buffer = new byte[256];

        while (running) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // récupérer notre addresse actuelle : socket.getLocalAddress() ;
            //à utiliser si on souhaite répondre au broadcast => À FAIRE POUR RÉCUP LES REMOTE USERS
            InetAddress senderAddress = packet.getAddress();
            //int senderPort = packet.getPort();

            String msgReceived = new String (packet.getData(), 0, packet.getLength());
            System.out.println("UDP Receiver : " + msgReceived);
            UDPController.messageReceived(msgReceived, this.subscribers, senderAddress);

            if (msgReceived.equals("end")) {
                running = false;
                System.out.println("Socket closed");
                // (message de warning : unnessecary) continue;
            }

        }

        socket.close();
    }

}
