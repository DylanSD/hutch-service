package unified.service;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.util.Calendar;

/**
 * Created by dylan on 3/4/18.
 */
public class DaylightService {

    private ZoneId zoneId = ZoneId.of("America/Los_Angeles");
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Location location = new Location("37.887509", "-122.54607");
    private SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, "America/Los_Angeles");

    public DaylightService() {
    }

    public boolean shouldLightBeOnNow(LocalTime now) {
        //37.887509, -122.546074

        Calendar officialSunrise = calculator.getOfficialSunriseCalendarForDate(Calendar.getInstance());
        Calendar officialSunset = calculator.getOfficialSunsetCalendarForDate(Calendar.getInstance());

        LocalTime sunrise = LocalDateTime.ofInstant(officialSunrise.toInstant(), zoneId).toLocalTime();
        LocalTime sunset = LocalDateTime.ofInstant(officialSunset.toInstant(), zoneId).toLocalTime();

        logger.info("Sunrise: " + sunrise);
        logger.info("Sunset: " + sunset);

        if (now.getHour() >= 22) {
            logger.info("Hour is greater or equal to 22, light should be off");
            return false;
        }
        if (now.getHour() <= 4) {
            logger.info("Hour is less or equal to 4, light should be off");
            return false;
        }
        if (now.isAfter(sunrise.plusMinutes(30)) && now.isBefore(sunset.minusMinutes(30))) {
            logger.info("Hour is in the middle of the day, light should be off");
            return false;
        }
        logger.info("Reporting that light is on for time: " + now);
        return true;
    }

}
