/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hospitaladmissionsystem;

/**
 *
 * @author gkhaalo
 */


//Manages the hospital ward
public class BedManager {

    public static final int ROWS = 4;
    public static final int COLS = 5;
    public static final int TOTAL_BEDS = ROWS * COLS; // 20

    private Bed[][] wardLayout;

    public BedManager() {
        wardLayout = new Bed[ROWS][COLS];
        int bedNumber = 1;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                wardLayout[r][c] = new Bed(bedNumber, r, c);
                bedNumber++;
            }
        }
    }

    
 //Allocates the first available bed to the given patient ID.
     
    public Bed allocateBed(String patientId) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Bed bed = wardLayout[r][c];
                if (!bed.isOccupied()) {
                    bed.occupy(patientId);
                    return bed;
                }
            }
        }
        return null; // No beds available
    }

    //Releases the bed occupied by the given patient ID.
     
    public boolean releaseBedByPatientId(String patientId) {
        Bed bed = findBedByPatientId(patientId);
        if (bed != null) {
            bed.release();
            return true;
        }
        return false;
    }

    //Finds the bed (if any) currently occupied by the given patient ID.
    public Bed findBedByPatientId(String patientId) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Bed bed = wardLayout[r][c];
                if (bed.isOccupied() && bed.getPatientId().equalsIgnoreCase(patientId)) {
                    return bed;
                }
            }
        }
        return null;
    }

    //Returns true if at least one bed is free.
    public boolean hasAvailableBed() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (!wardLayout[r][c].isOccupied()) {
                    return true;
                }
            }
        }
        return false;
    }

    //Returns the total number of beds currently occupied.
    public int getOccupiedBedCount() {
        int count = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (wardLayout[r][c].isOccupied()) {
                    count++;
                }
            }
        }
        return count;
    }

    // Returns the total number of beds currently available.
    public int getAvailableBedCount() {
        return TOTAL_BEDS - getOccupiedBedCount();
    }

    //Returns the percentage of beds currently occupied (0.0 - 100.0).
    public double getOccupancyPercentage() {
        return (getOccupiedBedCount() * 100.0) / TOTAL_BEDS;
    }

    // Prints the full 4x5 ward layout, marking each bed occupied (X) or free (O).
    public void displayWardLayout() {
        System.out.println("\n--- Ward Layout (4 rows x 5 columns) ---");
        System.out.println("[O = Available   X = Occupied]\n");

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Bed bed = wardLayout[r][c];
                String marker = bed.isOccupied() ? "X" : "O";
                System.out.printf("[%02d:%s] ", bed.getBedNumber(), marker);
            }
            System.out.println();
        }
        System.out.println();
    }

    //Prints a list of all currently available (unoccupied) beds.
    public void displayAvailableBeds() {
        System.out.println("\n--- Available Beds ---");
        boolean any = false;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Bed bed = wardLayout[r][c];
                if (!bed.isOccupied()) {
                    System.out.println("Bed " + bed.getBedNumber());
                    any = true;
                }
            }
        }
        if (!any) {
            System.out.println("No beds available.");
        }
        System.out.println();
    }

    //Prints a list of all currently occupied beds, along with the patient ID in each. 
    public void displayOccupiedBeds() {
        System.out.println("\n--- Occupied Beds ---");
        boolean any = false;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                Bed bed = wardLayout[r][c];
                if (bed.isOccupied()) {
                    System.out.println("Bed " + bed.getBedNumber() + " - Patient ID: " + bed.getPatientId());
                    any = true;
                }
            }
        }
        if (!any) {
            System.out.println("No beds currently occupied.");
        }
        System.out.println();
    }

    public Object findBedByNumber(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Bed allocateBed(int i, String a002) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object findBedByNumber(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object findBedByNumber(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}