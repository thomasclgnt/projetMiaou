package tcp;

import data.Message;

import java.net.InetAddress;

public interface MessageReceivedCallback {

    void received(InetAddress from, String message, String horodatage);

}
