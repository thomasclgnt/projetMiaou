import java.io.IOException;

public class UDPTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        ListUser users = new ListUser();
        UDPReceiver rec = new UDPReceiver();
        rec.addSubscriber(new Notify() {

            @Override
            public void notifyNewUser(String username, String addressIP, int portTCP) {
                System.out.println("new user");
                users.addUser(username, addressIP, portTCP);
            }

            @Override
            public void notifyDeleteUser(String addressIP) {
                System.out.println("del user");
                users.deleteUser(addressIP);
            }
        });
        rec.start();
        new UDPSender().broadcast("Miaou");
        Thread.sleep(3000);
        new UDPSender().broadcast("end");
    }

}
