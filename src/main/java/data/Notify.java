package data;

import java.io.* ;
import java.net.* ;



public interface Notify {

    //PROCHAINE FOIS : CODER CHANGE USERNAME ET TESTER LE TOUT

    public void notifyNewUser(String username, String addressIP, int portTCP) ;

    public void notifyDeleteUser(String addressIP);

    public void notifyChangeUsername(String new_username, String addressIP);

}
