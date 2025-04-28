package clinic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AreaOfExpertiseTest {
    @Test
    public void testAddTreatment() {

        AreaOfExpertise expertise = new AreaOfExpertise("Orthopedics");
        Treatment treatment = new Treatment("Joint Therapy", expertise);

        assertEquals(1, expertise.getTreatments().size());
        assertEquals(treatment, expertise.getTreatments().get(0));
    }

}