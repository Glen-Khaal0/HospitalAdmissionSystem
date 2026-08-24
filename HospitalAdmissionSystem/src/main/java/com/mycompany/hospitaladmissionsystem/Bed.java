package com.mycompany.hospitaladmissionsystem;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gkhaalo
 */

//Represents a single bed in the hospital ward. With the 4x5 layout

 
public class Bed {

    private int bedNumber;   // 1 - 20
    private int row;         // 0 - 3
    private int col;         // 0 - 4
    private boolean occupied;
    private String patientId; // ID of the inpatient currently occupying this bed (null if empty)

    public Bed(int bedNumber, int row, int col) {
        this.bedNumber = bedNumber;
        this.row = row;
        this.col = col;
        this.occupied = false;
        this.patientId = null;
    }

    public int getBedNumber() {
        return bedNumber;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public String getPatientId() {
        return patientId;
    }

    // Assigns this bed to a patient. 
    public void occupy(String patientId) {
        this.occupied = true;
        this.patientId = patientId;
    }

    // Frees this bed, ready to be allocated again. 
    public void release() {
        this.occupied = false;
        this.patientId = null;
    }
}
    

