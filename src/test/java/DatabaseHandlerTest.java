import com.example.cab302_study_buddy.DatabaseHandler;
import org.junit.jupiter.api.*;
import java.sql.SQLException;
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
        String actual = DatabaseHandler.getPasswordForUsername("Luka");
        String expected = "1234";
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    @Test
    void testgetPasswordForUsernameFalse() {
        String actual = DatabaseHandler.getPasswordForUsername("noexist");
        assertNull(actual);
    }
}

