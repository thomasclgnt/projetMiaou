import java.io.IOException;

public class UDPTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        new UDPReceiver().start();
        new UDPSender().broadcast("Miaou");
        Thread.sleep(3000);
        new UDPSender().broadcast("end");
    }

}
