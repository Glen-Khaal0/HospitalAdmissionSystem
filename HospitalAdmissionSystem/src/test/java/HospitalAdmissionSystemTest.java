/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gkhaalo
 */
import com.mycompany.hospitaladmissionsystem.Bed;
import com.mycompany.hospitaladmissionsystem.BedManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class HospitalAdmissionSystemTest {

    private PatientManager patientManager;
    private BedManager bedManager;

    
    @BeforeEach
    public void setUp() {
        patientManager = new PatientManager();
        bedManager = new BedManager();
    }

    // PatientManager tests, Patient registration
    

    @Test
    public void testRegisterPatient_addsPatientSuccessfully() {
        Patient patient = new Patient("P001", "Alice", "Wonder", 29,
                "Female", "Fracture", PatientCategory.OUTPATIENT);

        boolean result = patientManager.registerPatient(patient);

        assertTrue(result, "Registering a new, unique patient should succeed");
        assertEquals(1, patientManager.size());
        assertNotNull(patientManager.findPatientById("P001"));
    }

    // Prevent duplicate Patient IDs

    @Test
    public void testRegisterPatient_rejectsDuplicateId() {
        Patient first = new Patient("A001", "Alice", "Wonder", 29,
                "Female", "Fracture", PatientCategory.OUTPATIENT);
        Patient duplicate = new Patient("A001", "Bob", "Builder", 40,
                "Male", "Flu", PatientCategory.EMERGENCY);

        patientManager.registerPatient(first);
        boolean secondResult = patientManager.registerPatient(duplicate);

        assertFalse(secondResult, "Registering a second patient with the same ID should fail");
        assertEquals(1, patientManager.size(), "Only the first patient should have been added");
        // The original patient's details should be untouched by the rejected duplicate
        assertEquals("Alice", patientManager.findPatientById("P001").getFirstName());
    }

    // Search for a patient

    @Test
    public void testFindPatientById_returnsCorrectPatient() {
        patientManager.registerPatient(new Patient("P001", "Alice", "Wonder", 29,
                "Female", "Fracture", PatientCategory.OUTPATIENT));

        Patient found = patientManager.findPatientById("P001");

        assertNotNull(found);
        assertEquals("Wonder", found.getLastName());
    }

    @Test
    public void testFindPatientById_isCaseInsensitive() {
        patientManager.registerPatient(new Patient("P001", "Alice", "Wonder", 29,
                "Female", "Fracture", PatientCategory.OUTPATIENT));

        assertNotNull(patientManager.findPatientById("p001"), "Search should be case-insensitive");
    }

    @Test
    public void testFindPatientById_returnsNullWhenNotFound() {
        assertNull(patientManager.findPatientById("P999"));
    }

    // Update patient details

    @Test
    public void testUpdatePatient_updatesFieldsSuccessfully() {
        patientManager.registerPatient(new Patient("P001", "Alice", "Wonder", 29,
                "Female", "Fracture", PatientCategory.OUTPATIENT));

        boolean result = patientManager.updatePatient("P001", "Alicia", "Wonderland", 30,
                "Female", "Recovering");

        assertTrue(result);
        Patient updated = patientManager.findPatientById("P001");
        assertEquals("Alicia", updated.getFirstName());
        assertEquals("Wonderland", updated.getLastName());
        assertEquals(30, updated.getAge());
        assertEquals("Recovering", updated.getMedicalCondition());
    }

    @Test
    public void testUpdatePatient_returnsFalseWhenPatientNotFound() {
        boolean result = patientManager.updatePatient("P999", "X", "Y", 20, "Male", "None");

        assertFalse(result, "Updating a non-existent patient should return false");
    }

    // Delete a patient

    @Test
    public void testDeletePatient_removesPatientSuccessfully() {
        patientManager.registerPatient(new Patient("P001", "Alice", "Wonder", 29,
                "Female", "Fracture", PatientCategory.OUTPATIENT));

        boolean result = patientManager.deletePatient("P001");

        assertTrue(result);
        assertEquals(0, patientManager.size());
        assertNull(patientManager.findPatientById("P001"));
    }

    @Test
    public void testDeletePatient_returnsFalseWhenPatientNotFound() {
        boolean result = patientManager.deletePatient("P999");

        assertFalse(result, "Deleting a non-existent patient should return false");
    }

    // Sort patients 

    @Test
    public void testSortBySurname_ordersPatientsAlphabetically() {
        patientManager.registerPatient(new Patient("A001", "Alice", "Zeta", 29,
                "Female", "Cond", PatientCategory.OUTPATIENT));
        patientManager.registerPatient(new Patient("A002", "Bob", "Alpha", 40,
                "Male", "Cond", PatientCategory.OUTPATIENT));
        patientManager.registerPatient(new Patient("A003", "Cara", "Mango", 22,
                "Female", "Cond", PatientCategory.EMERGENCY));

        patientManager.sortBySurname();
        List<Patient> sorted = patientManager.getAllPatients();

        assertEquals("Alpha", sorted.get(0).getLastName());
        assertEquals("Mango", sorted.get(1).getLastName());
        assertEquals("Zeta", sorted.get(2).getLastName());
    }

    @Test
    public void testSortById_ordersPatientsByPatientId() {
        // Registered out of ID order on purpose
        patientManager.registerPatient(new Patient("A003", "Cara", "Smith", 22,
                "Female", "Cond", PatientCategory.EMERGENCY));
        patientManager.registerPatient(new Patient("A001", "Alice", "Wonder", 29,
                "Female", "Cond", PatientCategory.OUTPATIENT));
        patientManager.registerPatient(new Patient("A002", "Bob", "Builder", 40,
                "Male", "Cond", PatientCategory.OUTPATIENT));

        patientManager.sortById();
        List<Patient> sorted = patientManager.getAllPatients();

        assertEquals("P001", sorted.get(0).getPatientId());
        assertEquals("P002", sorted.get(1).getPatientId());
        assertEquals("P003", sorted.get(2).getPatientId());
    }

    
    // BedManager tests
   

    // Allocate a bed 

    @Test
    public void testAllocateBed_assignsFirstAvailableBed() {
        Bed allocated = bedManager.allocateBed("A001");

        assertNotNull(allocated);
        assertEquals(1, allocated.getBedNumber(), "The first bed allocated should be bed 1");
        assertTrue(allocated.isOccupied());
        assertEquals("A001", allocated.getPatientId());
    }

    @Test
    public void testAllocateBed_bySpecificBedNumber() {
        Bed allocated = bedManager.allocateBed(5, "A001");

        assertNotNull(allocated);
        assertEquals(5, allocated.getBedNumber());
        assertEquals("A001", bedManager.findBedByNumber(5).getPatientId());
    }

    // Release a bed

    @Test
    public void testReleaseBed_freesTheBed() {
        bedManager.allocateBed("A001");

        boolean released = bedManager.releaseBedByPatientId("A001");

        assertTrue(released);
        assertNull(bedManager.findBedByPatientId("A001"));
        assertTrue(bedManager.hasAvailableBed());
        assertEquals(BedManager.TOTAL_BEDS, bedManager.getAvailableBedCount());
    }

    @Test
    public void testReleaseBed_returnsFalseWhenPatientHasNoBed() {
        boolean released = bedManager.releaseBedByPatientId("A999");

        assertFalse(released, "Releasing a bed for a patient with no allocated bed should fail");
    }

    // Prevent allocating an occupied bed

    @Test
    public void testAllocateBed_preventsDoubleBookingASpecificBed() {
        bedManager.allocateBed(1, "A001"); // Bed 1 is now occupied

        Bed secondAttempt = bedManager.allocateBed(1, "A002");

        assertNull(secondAttempt, "Allocating an already-occupied bed should fail");
        // Confirm the bed is still correctly assigned to the original patient
        assertEquals("A001", bedManager.findBedByNumber(1).getPatientId());
    }

    // Prevent bed allocation when all beds are occupied

    @Test
    public void testAllocateBed_returnsNullWhenWardIsFull() {
        // Fill every bed in the 20-bed ward
        for (int i = 1; i <= BedManager.TOTAL_BEDS; i++) {
            Bed bed = bedManager.allocateBed("A" + i);
            assertNotNull(bed, "Bed " + i + " should still be available while filling the ward");
        }

        assertFalse(bedManager.hasAvailableBed(), "No beds should remain available");

        Bed overflowAttempt = bedManager.allocateBed("A999");

        assertNull(overflowAttempt, "Allocating a bed once the ward is full should fail");
        assertEquals(BedManager.TOTAL_BEDS, bedManager.getOccupiedBedCount());
        assertEquals(0, bedManager.getAvailableBedCount());
    }
}
