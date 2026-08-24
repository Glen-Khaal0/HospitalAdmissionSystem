/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.hospitaladmissionsystem;

/**
 *
 * @author gkhaalo
 */
import java.util.Scanner;


public class HospitalAdmissionSystem {

    // Manages the list of registered patients
    private static PatientManager patientManager = new PatientManager();

    // Manages the 20-bed (4x5) ward layout
    private static BedManager bedManager = new BedManager();

    // Used to auto-generate simple, sequential patient IDs (e.g. A001, A002...)
    private static int nextIdNumber = 1;

    // Single shared Scanner for reading user input
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerPatient();
                    break;
                case "2":
                    searchPatient();
                    break;
                case "3":
                    updatePatient();
                    break;
                case "4":
                    deletePatient();
                    break;
                case "5":
                    displayAllPatients();
                    break;
                case "6":
                    sortPatientsMenu();
                    break;
                case "7":
                    allocateBed();
                    break;
                case "8":
                    releaseBed();
                    break;
                case "9":
                    bedManager.displayWardLayout();
                    break;
                case "10":
                    bedManager.displayAvailableBeds();
                    break;
                case "11":
                    bedManager.displayOccupiedBeds();
                    break;
                case "12":
                    reportsMenu();
                    break;
                case "13":
                    running = false;
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select an option between 1 and 13.\n");
            }
        }

        scanner.close();
    }

    
    // Menu display
    
    private static void printMenu() {
        System.out.println("===== HOSPITAL ADMISSION SYSTEM =====");
        System.out.println("1. Register a new patient");
        System.out.println("2. Search for a patient (by ID)");
        System.out.println("3. Update an existing patient's details");
        System.out.println("4. Delete a patient");
        System.out.println("5. Display all registered patients");
        System.out.println("6. Sort patients (by surname or Patient ID)");
        System.out.println("---- Bed Management ----");
        System.out.println("7. Allocate a bed to an inpatient");
        System.out.println("8. Release a bed (discharge patient)");
        System.out.println("9. Display ward layout");
        System.out.println("10. Display available beds");
        System.out.println("11. Display occupied beds");
        System.out.println("---- Reports ----");
        System.out.println("12. Reports menu");
        System.out.println("13. Exit");
        System.out.print("Enter your choice: ");
    }

    
    // Reports menu
    
    private static void reportsMenu() {
        boolean inReportsMenu = true;

        while (inReportsMenu) {
            System.out.println("\n===== REPORTS =====");
            System.out.println("1. Display all registered patients");
            System.out.println("2. Display all available beds");
            System.out.println("3. Display all occupied beds");
            System.out.println("4. Display total number of registered patients");
            System.out.println("5. Display total number of occupied beds");
            System.out.println("6. Display ward occupancy percentage");
            System.out.println("0. Back to main menu");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayAllPatients();
                    break;
                case "2":
                    bedManager.displayAvailableBeds();
                    break;
                case "3":
                    bedManager.displayOccupiedBeds();
                    break;
                case "4":
                    System.out.println("\nTotal registered patients: " + patientManager.size() + "\n");
                    break;
                case "5":
                    System.out.println("\nTotal occupied beds: " + bedManager.getOccupiedBedCount()
                            + " out of " + BedManager.TOTAL_BEDS + "\n");
                    break;
                case "6":
                    System.out.printf("%nWard occupancy: %.1f%%%n%n", bedManager.getOccupancyPercentage());
                    break;
                case "0":
                    inReportsMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select an option between 0 and 6.\n");
            }
        }
    }

    
    // 1. Register a new patient
    
    private static void registerPatient() {
        System.out.println("\n--- Register New Patient ---");

        String firstName = readNonEmptyString("Enter first name: ");
        String lastName = readNonEmptyString("Enter last name: ");
        int age = readValidAge("Enter age: ");
        String gender = readNonEmptyString("Enter gender: ");
        String medicalCondition = readNonEmptyString("Enter medical condition: ");
        PatientCategory category = readValidCategory();

        // Auto-generate a simple patient ID, e.g. A001, A002, ...
        String patientId = String.format("A%03d", nextIdNumber);
        nextIdNumber++;

        Patient newPatient;

        if (category == PatientCategory.INPATIENT) {
            // Inpatients need a ward number; bed number is set later via bed allocation.
            int wardNumber = readPositiveInt("Enter ward number: ");
            newPatient = new Inpatient(patientId, firstName, lastName, age,
                    gender, medicalCondition, wardNumber);
        } else {
            newPatient = new Patient(patientId, firstName, lastName, age,
                    gender, medicalCondition, category);
        }

        boolean registered = patientManager.registerPatient(newPatient);

        if (registered) {
            System.out.println("\nPatient registered successfully! Assigned Patient ID: " + patientId + "\n");
        } else {
            // Should not normally happen since IDs are auto-generated, but PatientManager
            // guards against duplicate IDs regardless of how a Patient object was built.
            System.out.println("\nA patient with ID " + patientId + " already exists. Registration cancelled.\n");
        }
    }

    
    // 2. Search for a patient by ID
    
    private static void searchPatient() {
        System.out.println("\n--- Search Patient ---");

        if (patientManager.size() == 0) {
            System.out.println("No patients registered yet.\n");
            return;
        }

        System.out.print("Enter Patient ID to search: ");
        String id = scanner.nextLine().trim();

        Patient found = patientManager.findPatientById(id);

        if (found != null) {
            System.out.println("\nPatient found:");
            found.displayDetails();
        } else {
            System.out.println("No patient found with ID: " + id);
        }
        System.out.println();
    }

    
    // 3. Update an existing patient's details
    
    private static void updatePatient() {
        System.out.println("\n--- Update Patient Details ---");

        if (patientManager.size() == 0) {
            System.out.println("No patients registered yet.\n");
            return;
        }

        System.out.print("Enter Patient ID to update: ");
        String id = scanner.nextLine().trim();

        Patient patient = patientManager.findPatientById(id);

        if (patient == null) {
            System.out.println("No patient found with ID: " + id + "\n");
            return;
        }

        System.out.println("\nCurrent details:");
        patient.displayDetails();

        System.out.println("\nLeave a field blank and press Enter to keep its current value.");
        System.out.println("Note: category can't be changed here - register a new patient to reclassify them.\n");

        System.out.print("New first name [" + patient.getFirstName() + "]: ");
        String firstNameInput = scanner.nextLine();
        String firstName = firstNameInput.isEmpty() ? patient.getFirstName() : firstNameInput;

        System.out.print("New last name [" + patient.getLastName() + "]: ");
        String lastNameInput = scanner.nextLine();
        String lastName = lastNameInput.isEmpty() ? patient.getLastName() : lastNameInput;

        System.out.print("New age [" + patient.getAge() + "]: ");
        String ageInput = scanner.nextLine();
        int age = patient.getAge();
        if (!ageInput.isEmpty()) {
            try {
                int parsedAge = Integer.parseInt(ageInput);
                if (parsedAge > 0 && parsedAge < 150) {
                    age = parsedAge;
                } else {
                    System.out.println("Invalid age entered. Age was not updated.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid age entered. Age was not updated.");
            }
        }

        System.out.print("New gender [" + patient.getGender() + "]: ");
        String genderInput = scanner.nextLine();
        String gender = genderInput.isEmpty() ? patient.getGender() : genderInput;

        System.out.print("New medical condition [" + patient.getMedicalCondition() + "]: ");
        String conditionInput = scanner.nextLine();
        String medicalCondition = conditionInput.isEmpty() ? patient.getMedicalCondition() : conditionInput;

        // Applies all resolved values through the same method the unit tests use.
        patientManager.updatePatient(id, firstName, lastName, age, gender, medicalCondition);

        // Inpatients also have a ward number that can be updated.
        // (Bed number is managed separately, through bed allocation/release.)
        if (patient instanceof Inpatient) {
            Inpatient inpatient = (Inpatient) patient;
            System.out.print("New ward number [" + inpatient.getWardNumber() + "]: ");
            String wardInput = scanner.nextLine();
            if (!wardInput.isEmpty()) {
                try {
                    int ward = Integer.parseInt(wardInput);
                    if (ward > 0) {
                        inpatient.setWardNumber(ward);
                    } else {
                        System.out.println("Invalid ward number entered. Ward number was not updated.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ward number entered. Ward number was not updated.");
                }
            }
        }

        System.out.println("\nPatient details updated successfully!");
        patient.displayDetails();
        System.out.println();
    }

    
    // 4. Delete a patient
    
    private static void deletePatient() {
        System.out.println("\n--- Delete Patient ---");

        if (patientManager.size() == 0) {
            System.out.println("No patients registered yet.\n");
            return;
        }

        System.out.print("Enter Patient ID to delete: ");
        String id = scanner.nextLine();

        Patient patient = patientManager.findPatientById(id);

        if (patient == null) {
            System.out.println("No patient found with ID: " + id + "\n");
            return;
        }

        // If this patient currently occupies a bed, release it first so the
        // bed doesn't stay marked occupied by a patient who no longer exists.
        if (bedManager.findBedByPatientId(id) != null) {
            int freedBed = bedManager.findBedByPatientId(id).getBedNumber();
            bedManager.releaseBedByPatientId(id);
            System.out.println("Bed " + freedBed + " was released as part of this deletion.");
        }

        patientManager.deletePatient(id);
        System.out.println("Patient " + id + " (" + patient.getFirstName() + " " + patient.getLastName()
                + ") has been deleted.\n");
    }

    
    // 5. Display all registered patients
    
    private static void displayAllPatients() {
        System.out.println("\n--- All Registered Patients ---");

        if (patientManager.size() == 0) {
            System.out.println("No patients registered yet.\n");
            return;
        }

        System.out.printf("%-10s %-12s %-12s %-5s %-8s %-20s %-12s%n",
                "ID", "First Name", "Last Name", "Age", "Gender", "Condition", "Category");
        System.out.println("--------------------------------------------------------------------------------");

        for (Patient p : patientManager.getAllPatients()) {
            p.printRow();
        }
        System.out.println();
    }

    
    // 6. Sort patients
    
    private static void sortPatientsMenu() {
        System.out.println("\n--- Sort Patients ---");

        if (patientManager.size() == 0) {
            System.out.println("No patients registered yet.\n");
            return;
        }

        System.out.println("1. Sort by surname (A-Z)");
        System.out.println("2. Sort by Patient ID");
        System.out.println("0. Cancel");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                patientManager.sortBySurname();
                System.out.println("\nPatients sorted by surname.");
                displayAllPatients();
                break;
            case "2":
                patientManager.sortById();
                System.out.println("\nPatients sorted by Patient ID.");
                displayAllPatients();
                break;
            case "0":
                break;
            default:
                System.out.println("Invalid choice.\n");
        }
    }

    
    // 7. Allocate a bed to an inpatient
    
    private static void allocateBed() {
        System.out.println("\n--- Allocate Bed ---");

        if (patientManager.size() == 0) {
            System.out.println("No patients registered yet.\n");
            return;
        }

        if (!bedManager.hasAvailableBed()) {
            System.out.println("No beds available. Cannot allocate a bed at this time.\n");
            return;
        }

        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine().trim();

        Patient patient = patientManager.findPatientById(id);

        if (patient == null) {
            System.out.println("No patient found with ID: " + id + "\n");
            return;
        }

        if (patient.getCategory() != PatientCategory.INPATIENT) {
            System.out.println("Only Inpatients may be allocated a bed. This patient is registered as: "
                    + patient.getCategory() + "\n");
            return;
        }

        if (bedManager.findBedByPatientId(id) != null) {
            System.out.println("This patient already has an allocated bed: Bed "
                    + bedManager.findBedByPatientId(id).getBedNumber() + "\n");
            return;
        }

        Bed allocated = bedManager.allocateBed(patient.getPatientId());

        if (allocated != null) {
            // Keep the Inpatient's own bed-number field in sync with the ward system
            if (patient instanceof Inpatient) {
                ((Inpatient) patient).setBedNumber(allocated.getBedNumber());
            }
            System.out.println("Bed " + allocated.getBedNumber() + " allocated to patient "
                    + patient.getFirstName() + " " + patient.getLastName() + " (ID: " + id + ").\n");
        } else {
            // Safety net - should not happen since we checked hasAvailableBed() above
            System.out.println("No beds available. Cannot allocate a bed at this time.\n");
        }
    }

    
    // 8. Release a bed when a patient is discharged
    
    private static void releaseBed() {
        System.out.println("\n--- Release Bed (Discharge Patient) ---");

        System.out.print("Enter Patient ID: ");
        String id = scanner.nextLine();

        Bed bed = bedManager.findBedByPatientId(id);

        if (bed == null) {
            System.out.println("No bed is currently allocated to patient ID: " + id + "\n");
            return;
        }

        int bedNumber = bed.getBedNumber();
        bedManager.releaseBedByPatientId(id);

        // Keep the Inpatient's own bed-number field in sync with the ward system
        Patient patient = patientManager.findPatientById(id);
        if (patient instanceof Inpatient) {
            ((Inpatient) patient).setBedNumber(-1);
        }

        System.out.println("Bed " + bedNumber + " has been released and is now available.\n");
    }

    
    // Helper methods
    

    //Keeps prompting until the user enters a non-empty string.
    private static String readNonEmptyString(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("This field cannot be empty. Please try again.");
        }
    }

    // Keeps prompting until the user enters a valid, realistic age.
    private static int readValidAge(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                int age = Integer.parseInt(input);
                if (age > 0 && age < 150) {
                    return age;
                }
                System.out.println("Please enter a realistic age (1-149).");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number for age.");
            }
        }
    }

    // Keeps prompting until the user enters a valid patient category.
    private static PatientCategory readValidCategory() {
        while (true) {
            System.out.print("Enter patient category (Inpatient/Outpatient/Emergency): ");
            String input = scanner.nextLine();
            PatientCategory category = parseCategory(input);
            if (category != null) {
                return category;
            }
            System.out.println("Invalid category. Please enter Inpatient, Outpatient, or Emergency.");
        }
    }

    //Matches a typed string against the PatientCategory enum values (case-insensitive).
    private static PatientCategory parseCategory(String input) {
        for (PatientCategory c : PatientCategory.values()) {
            if (c.name().equalsIgnoreCase(input) || c.getDisplayName().equalsIgnoreCase(input)) {
                return c;
            }
        }
        return null;
    }

    //Keeps prompting until the user enters a positive whole number.
    private static int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("Please enter a positive whole number.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }
}