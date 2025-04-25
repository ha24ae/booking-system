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
            System.out.println("2. Book Appointment by Searching Expertise,");
            System.out.println("3. Book Appointment by Searching Physiotherapist Name");
            System.out.println("4. Change Appointment");
            System.out.println("5. Cancel Appointment");
            System.out.println("6. Attend Appointment");
            System.out.println("7. Delete Patient");
            System.out.println("8. Generate End-of-Term Report");
            System.out.println("9. Exit");
            System.out.print("Please select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> system.addPatientInteractive(scanner);
                case 2 -> system.searchByExpertise(scanner);
                case 3 -> system.searchByPhysiotherapistName(scanner);
                case 4 -> system.changeAppointment(scanner);
                case 5 -> system.cancelAppointment(scanner);
                case 6 -> system.attendAppointment(scanner);
                case 7 -> system.deletePatientById(scanner);
                case 8 -> system.generateReport();
                case 9 -> running = false;
                default -> System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }

    public void initialiseTestData() {
        //3 areas of expertise
        AreaOfExpertise physioArea = new AreaOfExpertise("Physiotherapy");
        AreaOfExpertise osteoArea = new AreaOfExpertise("Osteopathy");
        AreaOfExpertise rehabArea = new AreaOfExpertise("Rehabilitation");

        //5 treatments
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

        //4 physiotherapists
        physiotherapists.add(physio1);
        physiotherapists.add(physio2);
        physiotherapists.add(physio3);
        physiotherapists.add(physio4);

        //10 patients
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

        //8 dates (Mondays & Wednesdays)
        List<LocalDate> treatmentDates = new ArrayList<>();
        LocalDate current = start;
        while (treatmentDates.size() < totalAppointments) {
            DayOfWeek day = current.getDayOfWeek();
            if (day == DayOfWeek.MONDAY || day == DayOfWeek.WEDNESDAY) {
                treatmentDates.add(current);
            }
            current = current.plusDays(1);
        }

        //one treatment per slot per physio
        for (Physiotherapist physio : physiotherapists) {
            List<Treatment> allTreatments = new ArrayList<>();
            for (AreaOfExpertise area : physio.getAreasOfExpertise()) {
                allTreatments.addAll(area.getTreatments());
            }

            int treatmentIndex = 0;
            int timeIndex = 0;

            for (LocalDate date : treatmentDates) {
                //one treatment per time per physio
                if (allTreatments.isEmpty()) continue;

                Treatment currentTreatment = allTreatments.get(treatmentIndex % allTreatments.size());

                //start and end times for the treatment slot using LocalDateTime
                String timeRange = times[timeIndex % times.length];
                String[] timeParts = timeRange.split("-");

                LocalDateTime startTime = LocalDateTime.of(date, LocalTime.parse(timeParts[0]));
                LocalDateTime endTime = LocalDateTime.of(date, LocalTime.parse(timeParts[1]));

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

    public void searchByExpertise(Scanner scanner) {
        System.out.println("Enter area of expertise (Physiotherapy/Osteopathy/Rehabilitation):");
        String expertise = scanner.nextLine();

        List<TreatmentSlot> matchingSlots = new ArrayList<>();
        Map<Integer, TreatmentSlot> slotMap = new LinkedHashMap<>();
        int index = 1;

        for (Physiotherapist physio : physiotherapists) {
            for (TreatmentSlot slot : physio.getSlots()) {
                if (!slot.isBooked() &&
                        slot.getTreatment().getArea().getName().equalsIgnoreCase(expertise)) {

                    matchingSlots.add(slot);
                    slotMap.put(index, slot);

                    System.out.printf("%d.  %s - %s (%s)\n", //[%s]
                            index,
                            //physio.getId(),
                            physio.getName(),
                            slot.getFullTime(),
                            slot.getTreatment().getName());
                    index++;
                }
            }
        }

        if (matchingSlots.isEmpty()) {
            System.out.println("No available slots found for the given expertise.");
            return;
        }

        System.out.print("Select a slot number to book: ");
        int selectedIndex = Integer.parseInt(scanner.nextLine());

        TreatmentSlot selectedSlot = slotMap.get(selectedIndex);
        if (selectedSlot == null) {
            System.out.println("Invalid selection.");
            return;
        }

        System.out.print("Enter Patient ID to book appointment: ");
        String patientId = scanner.nextLine();
        Patient patient = findPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        LocalDateTime slotStartTime = selectedSlot.getStartTime();

        if (hasPatientConflict(patient, slotStartTime)) {
            System.out.println("Patient already has an appointment at this time.");
            return;
        }

        selectedSlot.setBooked(true);

        Physiotherapist physio = physiotherapists.stream()
                .filter(p -> p.getSlots().contains(selectedSlot))
                .findFirst()
                .orElse(null);

        if (physio == null) {
            System.out.println("Physiotherapist not found for selected slot.");
            return;
        }

        Appointment appointment = new Appointment(patient, selectedSlot);

        appointments.add(appointment);
        System.out.println("Appointment booked successfully.");
        System.out.println("Your Booking ID is: " + appointment.getId());

    }

    public void searchByPhysiotherapistName(Scanner scanner) {
        System.out.println("Enter physiotherapist name (Dr. Alice Luke/Dr. Mark Kite/Dr. Liv Bike/Dr. Carter Fiz):");
        String name = scanner.nextLine().trim();
        boolean found = false;

        for (Physiotherapist physio : physiotherapists) {
            if (physio.getName().equalsIgnoreCase(name)) {
                found = true;
                List<TreatmentSlot> availableSlots = physio.getSlots().stream()
                        .filter(slot -> !slot.isBooked())
                        .toList();

                if (availableSlots.isEmpty()) {
                    System.out.println("No available slots for this physiotherapist.");
                    return;
                }

                for (int i = 0; i < availableSlots.size(); i++) {
                    System.out.println((i + 1) + ". " + availableSlots.get(i));
                }

                System.out.print("Enter the number of the slot you'd like to book: ");
                int slotIndex = scanner.nextInt() - 1;
                scanner.nextLine();

                if (slotIndex < 0 || slotIndex >= availableSlots.size()) {
                    System.out.println("Invalid selection.");
                    return;
                }

                TreatmentSlot selectedSlot = availableSlots.get(slotIndex);
                System.out.print("Enter Patient ID to book appointment: ");
                String patientId = scanner.nextLine();
                Patient patient = findPatientById(patientId);

                if (patient == null) {
                    System.out.println("Patient not found.");
                    return;
                }
                LocalDateTime slotStartTime = selectedSlot.getStartTime();

                if (hasPatientConflict(patient, slotStartTime)) { // this will check for patient conflict based on the start time
                    System.out.println("Patient already has an appointment at this time.");
                    return;
                }

                selectedSlot.setBooked(true);
                Appointment appointment = new Appointment(patient, selectedSlot);
                appointments.add(appointment);
                System.out.println("Appointment booked successfully.");
                System.out.println("Your Booking ID is: " + appointment.getId());

            }
        }
        // System.out.println("Physiotherapist not found.");
        if (!found) {
            System.out.println("Physiotherapist not found.");
        }
    }

    public boolean hasPatientConflict(Patient patient, LocalDateTime slotStartTime) {

        for (Appointment appointment : appointments) {   // Check if the patient has an existing appointment at the same time
            if (appointment.getPatient().equals(patient)) {
                LocalDateTime existingAppointmentTime = appointment.getSlot().getStartTime();
                if (existingAppointmentTime.equals(slotStartTime)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void changeAppointment(Scanner scanner) {
        System.out.print("Enter Booking ID to change: ");
        String bookingId = scanner.nextLine();

        Appointment oldAppointment = appointments.stream()
                .filter(a -> a.getId().equalsIgnoreCase(bookingId))
                .findFirst()
                .orElse(null);

        if (oldAppointment == null || oldAppointment.getStatus() == Appointment.Status.CANCELLED) {
            System.out.println("Appointment not found or already cancelled.");
            return;
        }

        Patient patient = oldAppointment.getPatient();
        String expertise = oldAppointment.getSlot().getTreatment().getArea().getName();

        Map<Integer, TreatmentSlot> availableSlots = new LinkedHashMap<>(); //available slots for the same expertise
        int index = 1;

        for (Physiotherapist physio : physiotherapists) {
            for (TreatmentSlot slot : physio.getSlots()) {
                if (!slot.isBooked() &&
                        slot.getTreatment().getArea().getName().equalsIgnoreCase(expertise)) {

                    availableSlots.put(index, slot);
                    System.out.printf("%d. %s - %s (%s)\n", index, physio.getName(),
                            slot.getFullTime(), slot.getTreatment().getName());
                    index++;
                }
            }
        }

        if (availableSlots.isEmpty()) {
            System.out.println("No available slots for this expertise.");
            return;
        }

        System.out.print("Select a new slot number: ");
        int newSlotIndex = Integer.parseInt(scanner.nextLine());
        TreatmentSlot newSlot = availableSlots.get(newSlotIndex);

        if (newSlot == null || newSlot.isBooked()) {
            System.out.println("This slot is already booked. Please select another one.");
            return;
        }

        //checking if conflict with existing patient appointments
        if (hasPatientConflict(patient, newSlot.getStartTime(), oldAppointment)) {
            System.out.println("Conflict detected! You already have an appointment at that time.");
            return;
        }

        oldAppointment.getSlot().setBooked(false); //will release old slot and assign new one
        newSlot.setBooked(true);
        oldAppointment.setSlot(newSlot);
        oldAppointment.setStatus(Appointment.Status.BOOKED);

        System.out.println("Appointment successfully changed. Booking ID remains: " + oldAppointment.getId());
    }

    public boolean hasPatientConflict(Patient patient, LocalDateTime slotStartTime, Appointment toIgnore) {
        for (Appointment appointment : appointments) {
            if (appointment.equals(toIgnore)) continue; //will ignore the appointment being updated unlike other method
            if (appointment.getPatient().equals(patient)) {
                if (appointment.getSlot().getStartTime().equals(slotStartTime)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void cancelAppointment(Scanner scanner) {
        System.out.print("Enter Booking ID to cancel: ");
        String bookingId = scanner.nextLine();

        Appointment appointmentToCancel = appointments.stream()
                .filter(a -> a.getId().equalsIgnoreCase(bookingId))
                .findFirst()
                .orElse(null);

        if (appointmentToCancel == null || appointmentToCancel.getStatus() == Appointment.Status.CANCELLED) {
            System.out.println("Appointment not found or already cancelled.");
            return;
        }
//        appointments.remove(appointmentToCancel);
        appointmentToCancel.getSlot().setBooked(false);
        appointmentToCancel.setStatus(Appointment.Status.CANCELLED);

        System.out.println("Appointment cancelled successfully. Booking ID: " + appointmentToCancel.getId());
    }

    public void attendAppointment(Scanner scanner) {
        System.out.print("Enter Booking ID to mark as attended: ");
        String bookingId = scanner.nextLine();

        Appointment appointment = appointments.stream()
                .filter(a -> a.getId().equalsIgnoreCase(bookingId))
                .findFirst()
                .orElse(null);

        if (appointment == null) {
            System.out.println("Appointment not found.");
            return;
        }

        if (appointment.getStatus() == Appointment.Status.CANCELLED) {
            System.out.println("This appointment was cancelled and cannot be attended.");
            return;
        }

        if (appointment.getStatus() == Appointment.Status.ATTENDED) {
            System.out.println("This appointment has already been attended.");
            return;
        }

        appointment.setStatus(Appointment.Status.ATTENDED);
        System.out.println("Appointment marked as attended. Thank you!");
    }

    public void generateReport() {
        System.out.println("\n--- End of Term Report ---");

        // Map to count attended appointments per physiotherapist
        Map<Physiotherapist, Integer> attendedCounts = new HashMap<>();

        for (Physiotherapist physio : physiotherapists) {
            System.out.println("\nPhysiotherapist: " + physio.getName());
            boolean hasAppointments = false;

            for (Appointment appt : appointments) {
                if (physio.getSlots().contains(appt.getSlot())) {
                    hasAppointments = true;
                    String treatmentName = appt.getSlot().getTreatment().getName();
                    String patientName = appt.getPatient().getName();
                    String time = appt.getSlot().getFullTime();
                    String status = appt.getStatus().toString();

                    System.out.printf("  Treatment: %-35s | Patient: %-20s | Time: %-20s | Status: %s%n",
                            treatmentName, patientName, time, status);

                    if (appt.getStatus() == Appointment.Status.ATTENDED) {
                        attendedCounts.put(physio, attendedCounts.getOrDefault(physio, 0) + 1);
                    }
                }
            }

            if (!hasAppointments) {
                System.out.println("  No appointments.");
            }
        }

        System.out.println("\n--- Physiotherapists by Number of Attended Appointments ---");

        physiotherapists.stream()
                .sorted((a, b) -> {
                    int countA = attendedCounts.getOrDefault(a, 0);
                    int countB = attendedCounts.getOrDefault(b, 0);
                    return Integer.compare(countB, countA);
                })
                .forEach(p -> {
                    int attended = attendedCounts.getOrDefault(p, 0);
                    System.out.printf("  %s - %d attended appointment(s)%n", p.getName(), attended);
                });
    }
}