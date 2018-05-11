package unified.service;

import java.time.LocalTime;

/**
 * Created by dylan on 3/4/18.
 */
public class LightOnWindow {

    private final LocalTime startTime;
    private final LocalTime endTime;
    private boolean start;
    private LocalTime end;

    public LightOnWindow(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStart() {
        return startTime;
    }

    public LocalTime getEnd() {
        return endTime;
    }

    @Override
    public String toString() {
        return startTime.toString() + ", " + endTime.toString();
    }
}
