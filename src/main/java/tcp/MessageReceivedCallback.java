package tcp;

import java.net.InetAddress;

public interface MessageReceivedCallback {

    void received(InetAddress from, String message);

}
