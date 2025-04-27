package clinic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClinicBookingSystemTest {


    private ClinicBookingSystem system;

    @BeforeEach
    void setUp() {
        system = new ClinicBookingSystem();
        system.initialiseTestData();  // Initialize the system with test data
    }

    @Test
    void testAddPatient() {

        int initialCount = system.getPatients().size();

        // Act: Add a new patient
        system.addPatientInteractive(new java.util.Scanner("John Doe\n123 Elm St\n0800123456"));

        // Assert: Ensure the patient count has increased by 1
        assertEquals(initialCount + 1, system.getPatients().size(), "A new patient should have been added.");
    }

}