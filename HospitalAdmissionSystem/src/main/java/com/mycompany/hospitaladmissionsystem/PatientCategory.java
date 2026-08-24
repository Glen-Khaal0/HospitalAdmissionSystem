/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitaladmissionsystem;

/**
 *
 * @author gkhaaLO
 */


 //Represents the category a patient is registered under.
 
public enum PatientCategory {

    INPATIENT("Inpatient"),
    OUTPATIENT("Outpatient"),
    EMERGENCY("Emergency");

    private final String displayName;

    PatientCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    //Returns the nicely capitalized name (e.g. "Inpatient") instead of the raw enum constant. 
    @Override
    public String toString() {
        return displayName;
    }
}
