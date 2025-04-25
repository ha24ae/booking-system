package clinic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TreatmentSlot {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isBooked;
    private Treatment treatment;

    public TreatmentSlot(LocalDateTime startTime, LocalDateTime endTime, Treatment treatment) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.treatment = treatment;
        this.isBooked = false;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public Treatment getTreatment() {
        return treatment;
    }
    public String getFullTime() {
        return startTime.format(DateTimeFormatter.ofPattern("EEEE dd MMM yyyy HH:mm")) + " - " + endTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
    public String getTime() {
        return startTime + " - " + endTime;
    }
}
