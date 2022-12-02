import java.io.* ;
import java.net.* ;

public class UDPSender {
    private static DatagramSocket socket = null ;
    private InetAddress address ;
    public static int port = 1324;

    public static void send_udp(String msg, InetAddress address) throws IOException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);
        byte[] buffer = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
        socket.close();
    }

    public static void broadcast(String broadcastMsg) throws IOException {
        send_udp(broadcastMsg, InetAddress.getByName("255.255.255.255"));
    }




}
