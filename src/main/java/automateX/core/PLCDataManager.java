package automateX.core;

import automateX.datafiles.BitFile;
import automateX.datafiles.DataFile;
import automateX.datafiles.FloatFile;
import automateX.datafiles.IntegerFile;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Map;

/**
 * PLCDataManager - Manages PLC data files and memory locations
 */
public class PLCDataManager {
    // Different types of data files
    private BitFile bitFile;               // B3 - Bit storage
    private IntegerFile integerFile;       // N7 - Integer values
    private FloatFile floatFile;           // F8 - Floating point values
    //private TimerFile timerFile;           // T4 - Timer values
    //private CounterFile counterFile;       // C5 - Counter values

    // Map to track all data files by identifier
    private Map<String, DataFile<?>> dataFiles;

    public PLCDataManager() {
        dataFiles = new HashMap<>();

        // Initialize standard data files
        bitFile = new BitFile("B3", 32, 16);          // 32 words, 16 bits each
        integerFile = new IntegerFile("N7", 100);     // 100 integers
        floatFile = new FloatFile("F8", 50);          // 50 floats
        //timerFile = new TimerFile("T4", 20);          // 20 timers
        //counterFile = new CounterFile("C5", 20);      // 20 counters

        // Register files in the map
        dataFiles.put(bitFile.getIdentifier(), bitFile);
        dataFiles.put(integerFile.getIdentifier(), integerFile);
        dataFiles.put(floatFile.getIdentifier(), floatFile);
        //dataFiles.put(timerFile.getIdentifier(), timerFile);
       // dataFiles.put(counterFile.getIdentifier(), counterFile);
    }

    // Get a JTable for a specific data file
    public JTable getDataFileTable(String fileIdentifier) {
        if (dataFiles.containsKey(fileIdentifier)) {
            return dataFiles.get(fileIdentifier).getAsTable();
        }
        return null;
    }

    // Access methods for specific data files
    public BitFile getBitFile() {
        return bitFile;
    }

    public IntegerFile getIntegerFile() {
        return integerFile;
    }

    public FloatFile getFloatFile() {
        return floatFile;
    }

//    public TimerFile getTimerFile() {
//        return timerFile;
//    }
//
//    public CounterFile getCounterFile() {
//        return counterFile;
//    }

    // Get value from a specific address (e.g., "B3:1/4" for bit 4 of word 1 in bit file)
    public Object getValue(String address) {
        String[] parts = address.split("[:/]");
        if (parts.length < 2) return null;

        String fileId = parts[0];
        if (!dataFiles.containsKey(fileId)) return null;

        return dataFiles.get(fileId).getValueAtAddress(address);
    }

    // Set value at a specific address
    public boolean setValue(String address, Object value) {
        String[] parts = address.split("[:/]");
        if (parts.length < 2) return false;

        String fileId = parts[0];
        if (!dataFiles.containsKey(fileId)) return false;

        return dataFiles.get(fileId).setValueAtAddress(address, value);
    }
}