package udp;

import data.*;
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

            //à utiliser si on souhaite répondre au broadcast
            InetAddress senderAddress = packet.getAddress();
            int senderPort = packet.getPort();

            String msgReceived = new String (packet.getData(), 0, packet.getLength());
            String[] segments = msgReceived.split(" : ");

            String msgSyst = segments[0];


            if (msgSyst.equals("Connexion")) {
                String msgRest1 = segments[1];
                String msgRest2 = segments[2];
                String msgRest3 = segments[3];

                for (Notify sub : this.subscribers) {
                    sub.notifyNewUser(msgRest1, msgRest2, Integer.parseInt(msgRest3));
                }

            } else if (msgSyst.equals("Deconnexion")){
                String msgRest1 = segments[1];

                for (Notify sub : this.subscribers) {
                    sub.notifyDeleteUser(msgRest1);
                }
            } else if (msgSyst.equals("Username changed")){
                String msgRest1 = segments[1];
                String msgRest2 = segments[2];

                for (Notify sub : this.subscribers) {
                    sub.notifyChangeUsername(msgRest1, msgRest2);
                }
            }

            System.out.println(msgReceived);

            if (msgReceived.equals("end")) {
                running = false;
                System.out.println("Socket closed");
                // (message de warning : unnessecary) continue;
            }

        }

        socket.close();
    }

}
