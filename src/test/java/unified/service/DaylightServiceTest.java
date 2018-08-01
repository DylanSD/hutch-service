package unified.service;

import org.junit.Before;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by dylan on 3/4/18.
 */
public class DaylightServiceTest {

    private DaylightService daylightService;

    private DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
    private DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private ZoneId zoneId = ZoneId.of("America/Los_Angeles");

    @Before
    public void setUp() throws Exception {
        daylightService = new DaylightService();
    }

    @Test
    public void shouldLightBeOnNow() throws Exception {
        for (int i = 0 ; i < 24; i++) {
            System.out.println(i + ": " + daylightService.shouldLightBeOnNow(ZonedDateTime.now(zoneId).withHour(i).withMinute(0)));
        }
    }

    @Test
    public void getDusk() throws Exception {

    }

}