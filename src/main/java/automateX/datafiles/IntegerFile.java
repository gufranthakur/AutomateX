package automateX.datafiles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class IntegerFile extends DataFile<Integer> {
    private int[] values;

    public IntegerFile(String identifier, int size) {
        super(identifier, size);
        values = new int[size];
    }

    public int getValue(int index) {
        if (index < 0 || index >= size) return 0;
        return values[index];
    }

    public void setValue(int index, int value) {
        if (index < 0 || index >= size) return;
        values[index] = value;
    }

    @Override
    public JTable getAsTable() {
        String[] columnNames = {"Address", "Value"};
        Object[][] data = new Object[size][2];

        for (int i = 0; i < size; i++) {
            data[i][0] = identifier + ":" + i;
            data[i][1] = values[i];
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? String.class : Integer.class;
            }
        };

        return new JTable(model);
    }

    @Override
    public Object getValueAtAddress(String address) {
        String[] parts = address.split(":");
        if (parts.length != 2) return null;

        try {
            int index = Integer.parseInt(parts[1]);
            return getValue(index);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public boolean setValueAtAddress(String address, Object value) {
        String[] parts = address.split(":");
        if (parts.length != 2) return false;

        try {
            int index = Integer.parseInt(parts[1]);
            if (value instanceof Integer) {
                setValue(index, (Integer) value);
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
