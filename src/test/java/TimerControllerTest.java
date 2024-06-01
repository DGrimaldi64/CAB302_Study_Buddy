
import com.example.cab302_study_buddy.TimerController;
import org.junit.jupiter.api.*;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;

class TimerControllerTest {


    private String FormatTime(int timeInt) {
        String time = Integer.toString(timeInt);
        if (time.length() == 2) {
            return time;
        }
        else if (time.length() == 1) {
            return "0" + time;
        }
        else {
            return "00";
        }
    }
    @Test
    void testFormatTime() throws SQLException {
        String actual = FormatTime(59);
        String expected = "59";
        assertEquals(expected, actual);
    }

    @Test
    void testFormatTimeSingle() throws SQLException {
        String actual = FormatTime(9);
        String expected = "09";
        assertEquals(expected, actual);
    }

    @Test
    void testFormatTimeNone() throws SQLException {
        String actual = FormatTime(0);
        String expected = "00";
        assertEquals(expected, actual);
    }


}

