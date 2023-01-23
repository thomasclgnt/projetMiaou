package data;

import data.UserNotFound;

import java.io.* ;
import java.net.* ;



public interface Notify {

    void notifyNewUser(String username, String addressIP, int portTCP) ;

    void notifyDeleteUser(String addressIP) throws UserNotFound;

    void notifyChangeUsername(String new_username, String addressIP);

}
