import java.io.* ;
import java.net.* ;

public class Notify {

    public static void notifyChangeUsername(String username){
        try {
            UDPSender.broadcast(username) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Pseudo changed");
    }

    public static void notifyGetUsers(){
        //TODO
        //pas un void mais renvoie un ListUsers (classe à créer)
    }

    public static void notifyNewUsers(User user){
        //TODO
    }

    public static void notifyDeleteUser(User user){
        //TODO
    }

}
