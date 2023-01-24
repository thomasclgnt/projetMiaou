import data.*;
import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test
    public void testDeleteUser() throws UserNotFound {
        ListUser listUTest = new ListUser() ;
        listUTest.addUser("Thomas","192.168.1.71", 1234);
        listUTest.addUser("Marie","192.168.1.79", 1234);

        listUTest.deleteUser("192.168.1.79");
        assertEquals(1, listUTest.convertToArrayList().size());
        listUTest.deleteUser("192.168.1.71");
        assertEquals(0, listUTest.convertToArrayList().size());

    }

    @Test
    public void testChangeUsername() throws UserNotFound {
        ListUser listUTest = new ListUser() ;
        listUTest.addUser("Thomas","192.168.1.71", 1234);
        listUTest.addUser("Marie","192.168.1.79", 1234);

        listUTest.changeUsername("THOMAS","192.168.1.71");


        assertEquals("THOMAS",listUTest.findUser("192.168.1.71").getUsername()) ;
    }


    @Test
    public void testCheckUsernameAvailable() {
        ListUser listUTest = new ListUser() ;
        listUTest.addUser("Thomas","192.168.1.71", 1234);
        listUTest.addUser("Marie","192.168.1.79", 1234);

        assertFalse(listUTest.checkUsernameAvailable("Thomas"));
        assertFalse(listUTest.checkUsernameAvailable("Marie"));
        assertTrue(listUTest.checkUsernameAvailable("Arthur_BM"));

    }



}
