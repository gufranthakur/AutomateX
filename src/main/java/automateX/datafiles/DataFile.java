package automateX.datafiles;

import javax.swing.*;

public abstract class DataFile<T> {
    protected String identifier;
    protected int size;

    public DataFile(String identifier, int size) {
        this.identifier = identifier;
        this.size = size;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getSize() {
        return size;
    }

    public abstract JTable getAsTable();
    public abstract Object getValueAtAddress(String address);
    public abstract boolean setValueAtAddress(String address, Object value);
}
