
import org.junit.jupiter.api.*;
import java.sql.SQLException;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;




class SignupControllerTest {

    final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    final Pattern VALID_PHONE_NUMBER_REGEX = Pattern.compile("^\\d{10}$");

    private boolean isValidPhoneOrEmail(String phone, String email) {
        return (phone.isEmpty() || VALID_PHONE_NUMBER_REGEX.matcher(phone).matches())
                && (email.isEmpty() || VALID_EMAIL_ADDRESS_REGEX.matcher(email).matches());
    }
    @Test
    void testisValidPhoneOrEmailFail() throws SQLException {
        boolean actual = isValidPhoneOrEmail("10", "test");
        boolean expected = false;
        assertEquals(expected, actual);
    }

    @Test
    void testisValidPhoneOrEmailTrue() throws SQLException {
        boolean actual = isValidPhoneOrEmail("1234567890", "something@gmail.com");
        boolean expected = true;
        assertEquals(expected, actual);
    }

}

