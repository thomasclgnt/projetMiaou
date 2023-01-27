import bdd.*;
import data.*;
import org.junit.Test;
import service.Service;
import service.TCPController;

import static org.junit.Assert.assertEquals;

public class DatabaseTest {

    @Test
    public void testInsert () {
        CreateTable.dropTableListUsers();
        CreateTable.createTableListUsers();
        Insert.add_user("User1", "192.168.1.1");

        assertEquals(1, Select.restoreUsers().convertToArrayList().size());
    }

    @Test
    public void testInsertMultiple() {
        CreateTable.dropTableListUsers();
        CreateTable.createTableListUsers();
        Insert.add_user("User1", "192.168.1.1");
        Insert.add_user("User2", "192.168.1.2");
        Insert.add_user("User3", "192.168.1.3");
        assertEquals(3, Select.restoreUsers().convertToArrayList().size());

    }

    @Test
    public void testDelete() {
        CreateTable.dropTableListUsers();
        CreateTable.createTableListUsers();
        Insert.add_user("User1", "192.168.1.1");
        Insert.add_user("User2", "192.168.1.2");
        Insert.add_user("User3", "192.168.1.3");
        Delete.deleteUser("192.168.1.1");
        assertEquals(2, Select.restoreUsers().convertToArrayList().size());
    }

    @Test
    public void testUpdate() throws UserNotFound {
        CreateTable.dropTableListUsers();
        CreateTable.createTableListUsers();
        Insert.add_user("User1", "192.168.1.1");
        Update.runUpdateListUsers("Ultimate User","192.168.1.1");
        assertEquals("Ultimate User", Select.restoreUsers().findUser("192.168.1.1").username);
    }


    @Test
    public void testUpdateOneWithManyIn() throws UserNotFound {
        CreateTable.dropTableListUsers();
        CreateTable.createTableListUsers();
        Insert.add_user("User1", "192.168.1.1");
        Insert.add_user("User2", "192.168.1.2");
        Insert.add_user("User3", "192.168.1.3");
        Insert.add_user("User4", "192.168.1.4");

        Update.runUpdateListUsers("Ultimate User","192.168.1.2");
        assertEquals("Ultimate User", Select.restoreUsers().findUser("192.168.1.2").username);
    }

}
