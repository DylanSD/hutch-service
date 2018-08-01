package unified.service;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by dylan on 3/4/18.
 */
public class DaylightServiceTest {

    private DaylightService daylightService;

    private DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
    private DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Before
    public void setUp() throws Exception {
        daylightService = new DaylightService();
    }

    @Test
    public void shouldLightBeOnNow() throws Exception {
        LocalTime now = LocalTime.now();
        LocalTime.of(0,0);
        for (int i = 0 ; i < 24; i++) {
            System.out.println(i + ": " + daylightService.shouldLightBeOnNow(LocalTime.of(i,0)));
        }
    }

    @Test
    public void getDusk() throws Exception {

    }

}