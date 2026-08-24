/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitaladmissionsystem;

/**
 *
 * @author gkhaaLO
 */
   
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class PatientManager {

    private List<Patient> patients;

    public PatientManager() {
        this.patients = new ArrayList<>();
    }

    
    public boolean registerPatient(Patient patient) {
        if (findPatientById(patient.getPatientId()) != null) {
            return false; // Duplicate ID - reject
        }
        patients.add(patient);
        return true;
    }

    //Finds a patient by ID (case-insensitive). Returns null if not found. 
    public Patient findPatientById(String id) {
        for (Patient p : patients) {
            if (p.getPatientId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    public boolean updatePatient(String id, String firstName, String lastName,
                                  int age, String gender, String medicalCondition) {
        Patient patient = findPatientById(id);
        if (patient == null) {
            return false;
        }
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setMedicalCondition(medicalCondition);
        return true;
    }

    //Removes a patient by ID. Returns true if a patient was found and removed.
    public boolean deletePatient(String id) {
        Patient patient = findPatientById(id);
        if (patient == null) {
            return false;
        }
        patients.remove(patient);
        return true;
    }

    //Returns the full list of registered patients (in current internal order).
    public List<Patient> getAllPatients() {
        return patients;
    }

    //Returns the number of registered patients. 
    public int size() {
        return patients.size();
    }

    //Sorts the patient list alphabetically by surname (last name), A-Z. 
    public void sortBySurname() {
        patients.sort(Comparator.comparing(Patient::getLastName, String.CASE_INSENSITIVE_ORDER));
    }

    //Sorts the patient list by Patient ID, ascending (e.g. A001, A002, A003...).
    public void sortById() {
        patients.sort(Comparator.comparing(Patient::getPatientId, String.CASE_INSENSITIVE_ORDER));
    }
}
