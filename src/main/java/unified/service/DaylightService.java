package unified.service;


import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dylan on 3/4/18.
 */
public class DaylightService {

    private static ZoneId zoneId = ZoneId.of("America/Los_Angeles");
    private List<LocalTime> dawnTimes = new ArrayList<>();
    private List<LocalTime> duskTimes = new ArrayList<>();

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    private LocalTime marchDawnPostDST = LocalTime.parse("05:56", formatter);
    private LocalTime marchDuskPostDST = LocalTime.parse("20:42", formatter);

    private LocalTime novDawnPostDST = LocalTime.parse("05:10", formatter);
    private LocalTime novDuskPostDST = LocalTime.parse("18:36", formatter);

    public DaylightService() {
        //Jan
        addMonthlyTimes("05:51", dawnTimes);
        addMonthlyTimes("18:48", duskTimes);

        //Feb
        addMonthlyTimes("05:28", dawnTimes);
        addMonthlyTimes("19:18", duskTimes);

        //Mar
        addMonthlyTimes("05:02", dawnTimes);
        addMonthlyTimes("19:26", duskTimes);

        //Apr
        addMonthlyTimes("04:51", dawnTimes);
        addMonthlyTimes("21:26", duskTimes);

        //May
        addMonthlyTimes("04:10", dawnTimes);
        addMonthlyTimes("22:02", duskTimes);

        //June
        addMonthlyTimes("03:52", dawnTimes);
        addMonthlyTimes("22:29", duskTimes);

        //July
        addMonthlyTimes("04:18", dawnTimes);
        addMonthlyTimes("22:12", duskTimes);

        //Aug
        addMonthlyTimes("04:54", dawnTimes);
        addMonthlyTimes("21:30", duskTimes);

        //Sept
        addMonthlyTimes("04:54", dawnTimes);
        addMonthlyTimes("20:37", duskTimes);

        //Oct
        addMonthlyTimes("05:53", dawnTimes);
        addMonthlyTimes("19:55", duskTimes);

        //Nov
        addMonthlyTimes("06:06", dawnTimes);
        addMonthlyTimes("19:39", duskTimes);

        //Dec
        addMonthlyTimes("05:49", dawnTimes);
        addMonthlyTimes("18:31", duskTimes);
    }

    public List<LightOnWindow> getLightEventsForDay(LocalDate localDate) {
        List<LightOnWindow> events = new ArrayList<>();
        LocalTime dawn = retrieveMonthlyDawnOrDusk(dawnTimes, localDate);
        LocalTime dusk = retrieveMonthlyDawnOrDusk(duskTimes, localDate);
        LocalTime kateStartTime = LocalTime.parse("06:00", formatter);
        LocalTime lightsShouldBeOff = LocalTime.parse("20:00", formatter);
        if (kateStartTime.isBefore(dawn.plusMinutes(30))) {
            events.add(new LightOnWindow(kateStartTime, dawn.plusMinutes(30)));
        }
        events.add(new LightOnWindow(dusk.minusMinutes(60), lightsShouldBeOff));
        return events;
    }

    public boolean shouldLightBeOnNow() {
        LocalTime now = LocalTime.now();
        List<LightOnWindow> events = getLightEventsForDay(LocalDate.now(zoneId));
        for (LightOnWindow event : events) {
            if (now.isAfter(event.getStart()) && now.isBefore(event.getEnd())) {
                return true;
            }
        }
        return false;
    }

    public LocalTime getDawnTime(LocalDate localDate) {
        return retrieveMonthlyDawnOrDusk(dawnTimes, localDate);
    }

    public LocalTime getDuskTime(LocalDate localDate) {
        return retrieveMonthlyDawnOrDusk(duskTimes, localDate);
    }

    private void addMonthlyTimes(String time, List<LocalTime> times) {
        times.add(LocalTime.parse(time, formatter));
    }

    private LocalTime retrieveMonthlyDawnOrDusk(List<LocalTime> times, LocalDate date) {
        if (Month.MARCH.equals(date.getMonth()) && date.getDayOfMonth() >= 11) {
            if (times.equals(dawnTimes)) {
                return marchDawnPostDST;
            }
            return marchDuskPostDST;
        } else if (Month.NOVEMBER.equals(date.getMonth()) && date.getDayOfMonth() >= 4) {
            if (times.equals(dawnTimes)) {
                return novDawnPostDST;
            }
            return novDuskPostDST;
        }
        return times.get(date.getMonthValue() - 1);
    }
}
