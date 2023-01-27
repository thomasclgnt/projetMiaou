package data;

import java.net.*;
import java.util.Enumeration;

public class IPAddress {

    public static InetAddress localIP;

    public static InetAddress getLocalIP()   {
        try {
            Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaceEnumeration.hasMoreElements()) {
                for (InterfaceAddress interfaceAddress : networkInterfaceEnumeration.nextElement().getInterfaceAddresses())
                    if (interfaceAddress.getAddress().isSiteLocalAddress()) {
                        localIP = InetAddress.getByName(interfaceAddress.getAddress().getHostAddress());
                    }
            }
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        return localIP;
    }

}

