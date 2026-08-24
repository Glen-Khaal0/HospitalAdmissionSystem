/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitaladmissionsystem;

/**
 *
 * @author gkhaalo
 */

public class Inpatient extends Patient {

    private int wardNumber;
    private int bedNumber; // -1 means no bed has been allocated yet

    public Inpatient(String patientId, String firstName, String lastName, int age,
                      String gender, String medicalCondition, int wardNumber) {
        // Category is always INPATIENT for this class, so it is fixed here
        // rather than being passed in separately.
        super(patientId, firstName, lastName, age, gender, medicalCondition, PatientCategory.INPATIENT);
        this.wardNumber = wardNumber;
        this.bedNumber = -1;
    }

    public int getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(int wardNumber) {
        this.wardNumber = wardNumber;
    }

    public int getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    
     //Overrides displayDetails() to also show ward and bed information.
     
    @Override
    public void displayDetails() {
        super.displayDetails();
        String bedText = (bedNumber == -1) ? "Not yet allocated" : String.valueOf(bedNumber);
        System.out.println("Ward Number      : " + wardNumber);
        System.out.println("Bed Number       : " + bedText);
        System.out.println("------------------------------------");
    }
}