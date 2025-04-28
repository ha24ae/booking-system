package clinic;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TreatmentSlotTest {
    @Test
    public void testSetBook(){
        AreaOfExpertise expertise = new AreaOfExpertise("Physiotherapy");
        Treatment treatment = new Treatment("Back Massage", expertise);
        LocalDateTime startTime = LocalDateTime.of(2025, 5, 1, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 5, 1, 11, 0);
        TreatmentSlot slot = new TreatmentSlot(startTime, endTime, treatment);

        assertFalse(slot.isBooked());
        slot.setBooked(true);
        assertTrue(slot.isBooked());

        slot.setBooked(false);
        assertFalse(slot.isBooked());
    }

}