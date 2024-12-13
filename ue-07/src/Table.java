public class Table {
    private final String[] columnNames;
    private final int[] columnSizes;

    private final String separator;
    private final String dividerSeparator;

    private Object[][] data;
    private int rowCount;

    private String cachedTable;

    public Table(String[] columnsNames) {
        this(columnsNames, 1);
    }

    public Table(String[] columnNames, int tableSize) {
        this(columnNames, tableSize, " | ", "-+-");
    }

    /**
     * Creates a new table with the given column names,
     * table size, separator and divider separator
     */
    public Table(String[] columnNames, int tableSize, String separator, String dividerSeparator) {
        this.columnNames = columnNames;
        this.data = new Object[tableSize][columnNames.length];
        this.rowCount = 0;

        this.separator = separator;
        this.dividerSeparator = dividerSeparator;
        this.cachedTable = null;

        this.columnSizes = new int[columnNames.length];

        for(int i = 0; i < columnNames.length; i++) {
            this.columnSizes[i] = columnNames[i].length();
        }
    }

    /*
     * Adds a new row to the table and checks if the
     * column count matches the column names count
     *
     * Furthermore, the cache is invalidated
     */
    public void addRow(Object[] row) {
        if(row.length != this.columnNames.length) {
            return;
        }

        this.validateTableSize();

        this.cachedTable = null;
        this.data[rowCount] = row;
        this.rowCount++;

        for(int i = 0; i < row.length; i++) {
            this.columnSizes[i] = Math.max(this.columnSizes[i], row[i].toString().length());
        }
    }

    /*
     * Adds a divider to the table and invalidates the cache
     */
    public void addDivider() {
        this.validateTableSize();

        this.cachedTable = null;
        this.data[this.rowCount] = null;
        this.rowCount++;
    }

    /*
     * Validates the table size and doubles the size
     * if the row count exceeds the data length
     *
     * This functionality is similar to how ArrayList works
     */
    private void validateTableSize() {
        if(this.rowCount >= this.data.length) {
            Object[][] newData = new Object[this.data.length * 2][this.columnNames.length];
            System.arraycopy(this.data, 0, newData, 0, this.data.length);
            this.data = newData;
        }
    }

    public String getTable() {
        if(this.cachedTable != null) {
            return this.cachedTable;
        }

        StringBuilder sb = new StringBuilder();

        this.addLineToSb(sb, this.columnNames);
        this.addDividerToSb(sb);

        for(int i = 0; i < this.rowCount; i++) {
            if(this.data[i] == null) {
                this.addDividerToSb(sb);
            } else {
                this.addLineToSb(sb, this.data[i]);
            }
        }

        this.cachedTable = sb.toString();
        return this.cachedTable;
    }

    private void addLineToSb(StringBuilder sb, Object[] columns) {
        sb.append("\n");

        for(int i = 0; i < columns.length; i++) {
            sb.append(pad(columns[i], this.columnSizes[i]));

            if(i < columns.length - 1) {
                sb.append(this.separator);
            }
        }
    }

    private void addDividerToSb(StringBuilder sb) {
        sb.append("\n");

        for(int i = 0; i < this.columnSizes.length; i++) {
            sb.append("-".repeat(this.columnSizes[i]));

            if(i < this.columnNames.length - 1) {
                sb.append(this.dividerSeparator);
            }
        }
    }

    private static String pad(Object s, int n) {
        return s.getClass() == Integer.class ?
                padLeft(s, n) :
                padRight(s, n);
    }

    private static String padRight(Object s, int n) {
        return String.format("%-" + n + "s", s);
    }

    private static String padLeft(Object s, int n) {
        return String.format("%" + n + "s", s);
    }
}
