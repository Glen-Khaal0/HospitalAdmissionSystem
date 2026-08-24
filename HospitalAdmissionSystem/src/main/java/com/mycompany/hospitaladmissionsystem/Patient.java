package com.mycompany.hospitaladmissionsystem;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gkhaalo
 */


public class Patient {

    // Fields 
    private String patientId;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String medicalCondition;
    private PatientCategory category; // INPATIENT, OUTPATIENT, or EMERGENCY

    // Constructor
    public Patient(String patientId, String firstName, String lastName, int age,
                   String gender, String medicalCondition, PatientCategory category) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.category = category;
    }

    //Getters 
    public String getPatientId() {
        return patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getMedicalCondition() {
        return medicalCondition;
    }

    public PatientCategory getCategory() {
        return category;
    }

    // ----- Setters (used when updating a patient's details) -----
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMedicalCondition(String medicalCondition) {
        this.medicalCondition = medicalCondition;
    }

    
    public void printRow() {
        System.out.printf("%-10s %-12s %-12s %-5d %-8s %-20s %-12s%n",
                patientId, firstName, lastName, age, gender, medicalCondition, category);
    }

    
    public void displayDetails() {
        System.out.println("------------------------------------");
        System.out.println("Patient ID       : " + patientId);
        System.out.println("First Name       : " + firstName);
        System.out.println("Last Name        : " + lastName);
        System.out.println("Age              : " + age);
        System.out.println("Gender           : " + gender);
        System.out.println("Medical Condition: " + medicalCondition);
        System.out.println("Category         : " + category);
        System.out.println("------------------------------------");
    }
}