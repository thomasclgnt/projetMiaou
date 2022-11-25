import java.io.*;
import java.net.*;

public class UDPReceiver extends Thread {

    private static DatagramSocket socket = null;
    private boolean running;

    public UDPReceiver() throws SocketException {
        socket = new DatagramSocket(UDPSender.port);
        System.out.println("Socket created");
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

            System.out.println(msgReceived);

            if (msgReceived.equals("end")) {
                running = false;
                System.out.println("Socket closed");
                continue;
            }

        }

        socket.close();
    }

}
