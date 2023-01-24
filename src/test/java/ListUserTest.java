import data.*;
import org.junit.Test;
import tcp.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ListUserTest {


    @Test
    public void testAddUser() throws UserNotFound {
        ListUser listUTest = new ListUser() ;
        listUTest.addUser("Thomas","192.168.1.71", 1234);
        listUTest.addUser("Marie","192.168.1.79", 1234);
        assertEquals(2, listUTest.convertToArrayList().size());

    }
    @Test
    public void testfindUsername() throws UserNotFound {
        ListUser listUTest = new ListUser() ;
        listUTest.addUser("Thomas","192.168.1.71", 1234);
        listUTest.addUser("Marie","192.168.1.79", 1234);

        assertEquals("Thomas",listUTest.findUser("192.168.1.71").getUsername()) ;
        assertEquals("Marie",listUTest.findUser("192.168.1.79").getUsername()) ;
    }

    @Test
    public void testfindUserWithUsername() throws UserNotFound {
        ListUser listUTest = new ListUser() ;
        listUTest.addUser("Thomas","192.168.1.71", 1234);
        listUTest.addUser("Marie","192.168.1.79", 1234);

        assertEquals("Thomas",listUTest.findUserWithUsername("Thomas").getUsername()) ;
        assertEquals("Marie",listUTest.findUserWithUsername("Marie").getUsername()) ;
    }





}
