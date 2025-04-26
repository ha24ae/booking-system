package clinic;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
class AppointmentTest {

    @Test
    void testAppointmentStatusChange() {

        AreaOfExpertise physio = new AreaOfExpertise("Physiotherapy");
        Treatment massage = new Treatment("Massage", physio);

        LocalDateTime start = LocalDateTime.of(2025, 5, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 5, 1, 11, 0);

        TreatmentSlot slot = new TreatmentSlot(start, end, massage); //creating a treatment slot

        Patient patient = new Patient("P1", "Anna Blue", "12 Elm St", "0700000111");

        Appointment appointment = new Appointment(patient, slot);//creating an Appointment

        assertEquals(Appointment.Status.BOOKED, appointment.getStatus());

        appointment.setStatus(Appointment.Status.CANCELLED); //changing status

        assertEquals(Appointment.Status.CANCELLED, appointment.getStatus());
    }
  
}