package automateX.datafiles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FloatFile extends DataFile<Float> {
    private float[] values;

    public FloatFile(String identifier, int size) {
        super(identifier, size);
        values = new float[size];
    }

    public float getValue(int index) {
        if (index < 0 || index >= size) return 0;
        return values[index];
    }

    public void setValue(int index, float value) {
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
                return columnIndex == 0 ? String.class : Float.class;
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
            if (value instanceof Float) {
                setValue(index, (Float) value);
                return true;
            } else if (value instanceof Integer) {
                setValue(index, ((Integer) value).floatValue());
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
