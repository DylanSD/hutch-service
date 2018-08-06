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

    public boolean shouldLightBeOnNow(ZonedDateTime now) {
        //37.887509, -122.546074

        Calendar officialSunrise = calculator.getOfficialSunriseCalendarForDate(Calendar.getInstance());
        Calendar officialSunset = calculator.getOfficialSunsetCalendarForDate(Calendar.getInstance());

        ZonedDateTime sunrise = ZonedDateTime.ofInstant(officialSunrise.toInstant(), zoneId);
        ZonedDateTime sunset = ZonedDateTime.ofInstant(officialSunset.toInstant(), zoneId);

        logger.info("Sunrise: " + sunrise);
        logger.info("Sunset: " + sunset);

        if (now.isAfter(sunrise) && now.isBefore(sunset)) {
            logger.info("Hour is in the day, light should be off");
            return false;
        }
        if (now.getHour() < 5) {
            logger.info("Before sunrise by too much");
            return false;
        }
        if (now.getHour() > 19) {
            logger.info("After sunset by too much");
            return false;
        }
        logger.info("Reporting that light is on for time: " + now);
        return true;
    }

    public void setCalculator(SunriseSunsetCalculator calculator) {
        this.calculator = calculator;
    }

}
