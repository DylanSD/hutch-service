package unified.service;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;

/**
 * Created by dylan on 3/4/18.
 */
public class DaylightServiceTest {

    private DaylightService daylightService;

    private DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
    private DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private ZoneId zoneId = ZoneId.of("America/Los_Angeles");

    private Location location = new Location("37.887509", "-122.54607");
    private SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, "America/Los_Angeles");

    private SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

    @Mock
    private SunriseSunsetCalculator sunriseSunsetCalculatorMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        daylightService = new DaylightService();
        daylightService.setCalculator(sunriseSunsetCalculatorMock);
    }

    @Test
    public void shouldLightBeOnNow() throws Exception {
        for (int i = 0 ; i < 24; i++) {
            System.out.println(i + ": " + daylightService.shouldLightBeOnNow(ZonedDateTime.now(zoneId).withHour(i).withMinute(0)));
        }
    }

    @Test
    public void getDusk() throws Exception {
        mockSunrise("05-08-2018 06:20:56");
        mockSunset("05-08-2018 18:30:56");
        Assert.assertTrue(daylightService.shouldLightBeOnNow(getTimeOfDay("05-08-2018 05:30:56")));
        Assert.assertTrue(daylightService.shouldLightBeOnNow(getTimeOfDay("05-08-2018 06:19:56")));
        Assert.assertFalse(daylightService.shouldLightBeOnNow(getTimeOfDay("05-08-2018 06:30:56")));

        Assert.assertFalse(daylightService.shouldLightBeOnNow(getTimeOfDay("05-08-2018 17:30:56")));
        Assert.assertTrue(daylightService.shouldLightBeOnNow(getTimeOfDay("05-08-2018 18:31:56")));
        Assert.assertFalse(daylightService.shouldLightBeOnNow(getTimeOfDay("05-08-2018 20:00:01")));

        Assert.assertFalse(daylightService.shouldLightBeOnNow(getTimeOfDay("05-08-2018 04:59:59")));
    }

    private ZonedDateTime getTimeOfDay(String datetime) throws ParseException {
        return ZonedDateTime.ofInstant(sdf.parse(datetime).toInstant(), zoneId);
    }

    private void mockSunset(String date) throws ParseException {
        Calendar retCalendar = Calendar.getInstance();
        retCalendar.setTime(sdf.parse(date));
        Mockito.when(sunriseSunsetCalculatorMock.getOfficialSunsetCalendarForDate(any(Calendar.class))).thenReturn(retCalendar);
    }

    private void mockSunrise(String date) throws ParseException {
        Calendar retCalendar = Calendar.getInstance();
        retCalendar.setTime(sdf.parse(date));
        Mockito.when(sunriseSunsetCalculatorMock.getOfficialSunriseCalendarForDate(any(Calendar.class))).thenReturn(retCalendar);
    }

}