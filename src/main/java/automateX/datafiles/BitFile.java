package automateX.datafiles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BitFile extends DataFile<Boolean> {
    private boolean[][] bits;
    private int wordSize;

    public BitFile(String identifier, int numWords, int bitsPerWord) {
        super(identifier, numWords);
        this.wordSize = bitsPerWord;
        bits = new boolean[numWords][bitsPerWord];
    }

    public boolean getBit(int word, int bit) {
        if (word < 0 || word >= size || bit < 0 || bit >= wordSize) return false;
        return bits[word][bit];
    }

    public void setBit(int word, int bit, boolean value) {
        if (word < 0 || word >= size || bit < 0 || bit >= wordSize) return;
        bits[word][bit] = value;
    }

    @Override
    public JTable getAsTable() {
        String[] columnNames = new String[wordSize + 1];
        columnNames[0] = "Word/Bit";
        for (int i = 0; i < wordSize; i++) {
            columnNames[i + 1] = Integer.toString(i);
        }

        Object[][] data = new Object[size][wordSize + 1];
        for (int i = 0; i < size; i++) {
            data[i][0] = identifier + ":" + i;
            for (int j = 0; j < wordSize; j++) {
                data[i][j + 1] = bits[i][j] ? 1 : 0;
            }
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0; // First column is read-only
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? String.class : Integer.class;
            }
        };

        JTable table = new JTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        return table;
    }

    @Override
    public Object getValueAtAddress(String address) {
        String[] parts = address.split("[:/]");
        if (parts.length != 3) return null;

        try {
            int word = Integer.parseInt(parts[1]);
            int bit = Integer.parseInt(parts[2]);
            return getBit(word, bit);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public boolean setValueAtAddress(String address, Object value) {
        String[] parts = address.split("[:/]");
        if (parts.length != 3) return false;

        try {
            int word = Integer.parseInt(parts[1]);
            int bit = Integer.parseInt(parts[2]);

            if (value instanceof Boolean) {
                setBit(word, bit, (Boolean) value);
                return true;
            } else if (value instanceof Integer) {
                setBit(word, bit, ((Integer) value) != 0);
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
