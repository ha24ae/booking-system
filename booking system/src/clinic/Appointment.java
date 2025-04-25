package clinic;
import java.util.UUID;

class Appointment {
    private static int idCounter = 1;
    // private String bookingId;

    enum Status { BOOKED, CANCELLED, ATTENDED }

    private Patient patient;
    private final String id;
    private TreatmentSlot slot;
    private Status status = Status.BOOKED;

    public Appointment(Patient p, TreatmentSlot s) {
        this.id = "A" + idCounter++;
        this.patient = p;
        this.slot = s;
        //this.bookingId = UUID.randomUUID().toString().substring(0, 8);
    }

    public Patient getPatient() {
        return patient;
    }
    public String getId() {
        return id;
    }
    public void setSlot(TreatmentSlot slot) {
        this.slot = slot;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public TreatmentSlot getSlot() {
        return slot;
    }
}