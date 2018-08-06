package unified.service;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

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

    @Mock
    private SunriseSunsetCalculator sunriseSunsetCalculator;

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
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "05-08-2018 10:20:56";
        Date date = sdf.parse(dateInString);

        Calendar retCalendar = Calendar.getInstance();
        retCalendar.setTime(date);

        SunriseSunsetCalculator  sunriseSunsetCalculatorMock = Mockito.mock(SunriseSunsetCalculator.class);
        Mockito.when(sunriseSunsetCalculatorMock.getOfficialSunriseCalendarForDate(any(Calendar.class))).thenReturn(retCalendar);
        Mockito.when(sunriseSunsetCalculatorMock.getOfficialSunsetCalendarForDate(any(Calendar.class))).thenReturn(retCalendar);
        daylightService.setCalculator(sunriseSunsetCalculatorMock);

        ZonedDateTime zdate = ZonedDateTime.parse("Sun, 5 Aug 2018 06:05:30 GMT",
                DateTimeFormatter.RFC_1123_DATE_TIME);
        System.out.println(date);

        System.out.println(daylightService.shouldLightBeOnNow(zdate));
    }

}