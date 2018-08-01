package unified.service;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

import java.time.*;
import java.util.Calendar;

/**
 * Created by dylan on 3/4/18.
 */
public class DaylightService {

    private Location location = new Location("37.887509", "-122.54607");
    private SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, "America/New_York");

    public DaylightService() {
    }

    public boolean shouldLightBeOnNow(LocalTime now) {
        //37.887509, -122.546074

        Calendar officialSunrise = calculator.getOfficialSunriseCalendarForDate(Calendar.getInstance());
        Calendar officialSunset = calculator.getOfficialSunsetCalendarForDate(Calendar.getInstance());

        LocalTime sunrise = LocalDateTime.ofInstant(officialSunrise.toInstant(), ZoneId.systemDefault()).toLocalTime();
        LocalTime sunset = LocalDateTime.ofInstant(officialSunset.toInstant(), ZoneId.systemDefault()).toLocalTime();

        if (now.getHour() >= 22) {
            return false;
        }
        if (now.getHour() <= 4) {
            return false;
        }
        if (now.isAfter(sunrise.plusMinutes(30)) && now.isBefore(sunset.minusMinutes(30))) {
            return false;
        }
        return true;
    }

}
