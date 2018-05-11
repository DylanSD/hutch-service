package unified.service;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
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
    public void getLightEventsForDay() throws Exception {
        System.out.println(daylightService.getLightEventsForDay(LocalDate.parse("2018-03-10", formatterDate)));
        System.out.println(daylightService.getLightEventsForDay(LocalDate.parse("2018-03-11", formatterDate)));
        System.out.println(daylightService.getLightEventsForDay(LocalDate.parse("2018-11-03", formatterDate)));
        System.out.println(daylightService.getLightEventsForDay(LocalDate.parse("2018-11-04", formatterDate)));
    }

    @Test
    public void shouldLightBeOnNow() throws Exception {
        System.out.println(daylightService.shouldLightBeOnNow());
    }

    @Test
    public void getDawn() throws Exception {
        System.out.println(daylightService.getDawnTime(LocalDate.parse("2018-01-01", formatterDate)));
        System.out.println(daylightService.getDawnTime(LocalDate.parse("2018-02-01", formatterDate)));
        System.out.println(daylightService.getDawnTime(LocalDate.parse("2018-03-01", formatterDate)));
        System.out.println(daylightService.getDawnTime(LocalDate.parse("2018-04-01", formatterDate)));
        System.out.println(daylightService.getDawnTime(LocalDate.parse("2018-05-01", formatterDate)));
        System.out.println(daylightService.getDawnTime(LocalDate.parse("2018-06-01", formatterDate)));
        System.out.println(daylightService.getDawnTime(LocalDate.parse("2018-07-01", formatterDate)));
        System.out.println(daylightService.getDawnTime(LocalDate.parse("2018-08-01", formatterDate)));
        System.out.println(daylightService.getDawnTime(LocalDate.parse("2018-09-01", formatterDate)));
        System.out.println(daylightService.getDawnTime(LocalDate.parse("2018-10-01", formatterDate)));
        System.out.println(daylightService.getDawnTime(LocalDate.parse("2018-11-01", formatterDate)));
        System.out.println(daylightService.getDawnTime(LocalDate.parse("2018-12-01", formatterDate)));

    }

    @Test
    public void getDusk() throws Exception {

    }

}