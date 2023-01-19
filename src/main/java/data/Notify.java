package data;

import java.io.* ;
import java.net.* ;



public interface Notify {

    //PROCHAINE FOIS : CODER CHANGE USERNAME ET TESTER LE TOUT

    void notifyNewUser(String username, String addressIP, int portTCP) ;

    void notifyDeleteUser(String addressIP) throws UserNotFound;

    void notifyChangeUsername(String new_username, String addressIP);

}
