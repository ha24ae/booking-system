package clinic;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;

public class ClinicBookingSystem {
    private final List<Physiotherapist> physiotherapists = new ArrayList<>();
    private final List<Patient> patients = new ArrayList<>();
    private final List<Appointment> appointments = new ArrayList<>();

    public static void main(String[] args) {
        ClinicBookingSystem system = new ClinicBookingSystem();
        system.initialiseTestData();

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n *** Boost Physio Clinic ***");
            System.out.println("1. Add New Patient");
//            System.out.println("2. Book Appointment by Searching Expertise,");
//            System.out.println("3. Book Appointment by Searching Physiotherapist Name");
//            System.out.println("4. Change Appointment");
//            System.out.println("5. Cancel Appointment");
//            System.out.println("6. Attend Appointment");
            System.out.println("2. Delete Patient");
//            System.out.println("8. Generate End-of-Term Report");
            System.out.println("3. Exit");
            System.out.print("Please select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> system.addPatientInteractive(scanner);
//                case 2 -> system.searchByExpertise(scanner);
//                case 3 -> system.searchByPhysiotherapistName(scanner);
//                case 4 -> system.changeAppointment(scanner);
//                case 5 -> system.cancelAppointment(scanner);
//                case 6 -> system.attendAppointment(scanner);
                case 2 -> system.deletePatientById(scanner);
//                case 8 -> system.generateReport();
                case 3 -> running = false;
                default -> System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }

    public void initialiseTestData() {
        //areas of expertise
        AreaOfExpertise physioArea = new AreaOfExpertise("Physiotherapy");
        AreaOfExpertise osteoArea = new AreaOfExpertise("Osteopathy");
        AreaOfExpertise rehabArea = new AreaOfExpertise("Rehabilitation");

        // treatments
        Treatment massage = new Treatment("Treatment Massage", physioArea);
        Treatment neuralMobilisation = new Treatment("Treatment Neural mobilisation", physioArea);
        Treatment mobilisationOfSpineAndJoints = new Treatment("Treatment Mobilisation of the spine and joint", osteoArea);
        Treatment acupuncture = new Treatment("Treatment Acupuncture", osteoArea);
        Treatment poolRehab = new Treatment("Treatment Pool Rehabilitation", rehabArea);

        //Physiotherapists and add treatments
        Physiotherapist physio1 = new Physiotherapist("PH1", "Dr. Alice Luke", "Wellness St", "0800000001");
        physio1.addAreaOfExpertise(physioArea);

        Physiotherapist physio2 = new Physiotherapist("PH2", "Dr. Mark Kite", "Health St", "0790000011");
        physio2.addAreaOfExpertise(osteoArea);

        Physiotherapist physio3 = new Physiotherapist("PH3", "Dr. Liv Bike", "Medical St", "0790000055");
        physio3.addAreaOfExpertise(rehabArea);

        Physiotherapist physio4 = new Physiotherapist("PH4", "Dr. Carter Fiz", "Wellness St", "0800000001");
        physio4.addAreaOfExpertise(physioArea);

        //physiotherapists
        physiotherapists.add(physio1);
        physiotherapists.add(physio2);
        physiotherapists.add(physio3);
        physiotherapists.add(physio4);

        //some patients
        patients.add(new Patient("P1", "Anna Blue", "12 Elm St", "0700000111"));
        patients.add(new Patient("P2", "Jake Green", "34 Oak St", "0700000222"));
        patients.add(new Patient("P3", "Hanna Purple", "2 Ark St", "0700000333"));
        patients.add(new Patient("P4", "Blake Yellow", "9 Bnd St", "0700000444"));
        patients.add(new Patient("P5", "Savanna Grey", "3 Cental St", "0700000555"));
        patients.add(new Patient("P6", "Drake White", "4 High St", "0700000666"));
        patients.add(new Patient("P7", "Han Pink", "99 Airport St", "0700000777"));
        patients.add(new Patient("P8", "liv Gray", "56 Mall St", "0700000888"));
        patients.add(new Patient("P9", "Bob Silver", "78 Dog St", "0700000001"));
        patients.add(new Patient("P10", "James Black", "6 Lane St", "0700000002"));

        createTreatmentSlotsForAllPhysiotherapists();
    }

    private void createTreatmentSlotsForAllPhysiotherapists() {
        String[] times = {"11:00-12:00", "13:00-14:00"};
        int totalAppointments = 8; // 2 per week for 4 weeks
        LocalDate start = LocalDate.of(2025, 5, 1);  // Starting from 1st May 2025
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

        // Step 1: Generate 8 dates (Mondays & Wednesdays only)
        List<LocalDate> treatmentDates = new ArrayList<>();
        LocalDate current = start;
        while (treatmentDates.size() < totalAppointments) {
            DayOfWeek day = current.getDayOfWeek();
            if (day == DayOfWeek.MONDAY || day == DayOfWeek.WEDNESDAY) {
                treatmentDates.add(current);
            }
            current = current.plusDays(1);
        }

        // Step 2: Assign one treatment per slot per physio
        for (Physiotherapist physio : physiotherapists) {
            // Gather all treatments across their areas
            List<Treatment> allTreatments = new ArrayList<>();
            for (AreaOfExpertise area : physio.getAreasOfExpertise()) {
                allTreatments.addAll(area.getTreatments());
            }

            int treatmentIndex = 0;
            int timeIndex = 0;

            for (LocalDate date : treatmentDates) {
                // Only one treatment per time per physio
                if (allTreatments.isEmpty()) continue;

                Treatment currentTreatment = allTreatments.get(treatmentIndex % allTreatments.size());

                // Build start and end times for the treatment slot using LocalDateTime
                String timeRange = times[timeIndex % times.length];
                String[] timeParts = timeRange.split("-");

                LocalDateTime startTime = LocalDateTime.of(date, LocalTime.parse(timeParts[0]));
                LocalDateTime endTime = LocalDateTime.of(date, LocalTime.parse(timeParts[1]));

                // Create TreatmentSlot with LocalDateTime values
                TreatmentSlot slot = new TreatmentSlot(startTime, endTime, currentTreatment);
                physio.addSlot(slot);

                treatmentIndex++;
                timeIndex++;
            }
        }
    }

    public void addPatientInteractive(Scanner scanner) {
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        String id = "P" + (patients.size() + 1);
        Patient newPatient = new Patient(id, name, address, phone);
        patients.add(newPatient);
        System.out.println("Patient added with ID: " + id);
    }

    public void deletePatientById(Scanner scanner) {
        System.out.print("Enter Patient ID to delete: ");
        String patientId = scanner.nextLine();

        Patient patient = findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }


        appointments.removeIf(appt -> {
            if (appt.getPatient().equals(patient)) {
                appt.getSlot().setBooked(false);
                System.out.println("Cancelled appointment: " + appt.getId());
                return true;
            }
            return false;
        });

        patients.remove(patient);
        System.out.println("Patient deleted successfully.");
    }

    public Patient findPatientById(String id) {
        return patients.stream().filter(p -> p.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
}