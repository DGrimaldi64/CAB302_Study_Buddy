import com.example.cab302_study_buddy.DatabaseHandler;
import com.example.cab302_study_buddy.TaskController;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseHandlerTest {


    @Test
    void testisUsernameExistsTrue() throws SQLException {
        boolean actual = DatabaseHandler.isUsernameExists("test");
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    void testisUsernameExistsFalse() throws SQLException {
        boolean actual = DatabaseHandler.isUsernameExists("Doesn't");
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    void testgetPasswordForUsernameTrue() {
        String actual = DatabaseHandler.getPasswordForUsername("test");
        String expected = "test";
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    @Test
    void testgetPasswordForUsernameFalse() {
        String actual = DatabaseHandler.getPasswordForUsername("noexist");
        assertNull(actual);
    }

    @Test
    void testgetIdForUsernameTrue() {
        int actual = DatabaseHandler.getIdForUsername("test");
        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    void testgetIdForUsernameFalse() {
        int actual = DatabaseHandler.getIdForUsername("test1");
        int expected = 100;
        assertNotEquals(expected, actual);
    }



}

