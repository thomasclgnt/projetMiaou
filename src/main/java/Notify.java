import java.io.* ;
import java.net.* ;

public class Notify {

    public static void notifyChangeUsername(String username) throws IOException{
        UDPSender.broadcast(username) ;
        System.out.println("Pseudo changed");
    }

    public static void notifyGetUsers(){
        //TODO
        //pas un void mais renvoie un ListUsers (classe à créer)
        //on veut faire un broadcast où on au moins un utilisateur nous
        //envoie en réponse sa ListUsers
        //bonne idée ou plutot on build la liste nous même à chaque réponde user par user ?
        //comment faire dans les deux cas ?
    }

    public static void notifyNewUser(User user) throws IOException {
        //TODO
        //???? à clarifier
        UDPSender.broadcast(user.addressIP);
        System.out.println("New connection : " + user.username);
    }

    public static void notifyDeleteUser(User user){
        //TODO
    }

}
