import java.io.IOException;

public class UDPTest {

    public static void main(String[] args) throws IOException {
        new UDPReceiver().start();
        new UDPSender().broadcast("Miaou");
    }

}
