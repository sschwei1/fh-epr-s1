public class Table<T extends ITableEntry> {
    private final String[] columnNames;
    private final int[] columnSizes;

    private final String separator;
    private final String dividerSeparator;

    private final T[] data;
    private boolean printDividerAfterLine;
    private boolean addNewLineAfterTable;


    private String cachedTable;

    public Table(String[] columnNames, T[] data) {
        this(columnNames, data, " | ", "-+-");
    }

    /**
     * Creates a new table with the given column names,
     * table size, separator and divider separator
     */
    public Table(String[] columnNames, T[] data, String separator, String dividerSeparator) {
        this.columnNames = columnNames;
        this.data = data;

        this.separator = separator;
        this.dividerSeparator = dividerSeparator;
        this.cachedTable = null;

        this.printDividerAfterLine = false;
        this.addNewLineAfterTable = true;

        this.columnSizes = this.calculateColumnSizes();
    }

    private int[] calculateColumnSizes() {
        int[] columnSizes = new int[this.columnNames.length];

        for (int i = 0; i < this.columnNames.length; i++) {
            columnSizes[i] = this.columnNames[i].length();
        }

        for (T entry : this.data) {
            if(entry == null) {
                continue;
            }

            Object[][] lines = entry.getLines();

            for (Object[] line : lines) {
                for (int i = 0; i < line.length; i++) {
                    columnSizes[i] = Math.max(columnSizes[i], line[i].toString().length());
                }
            }
        }

        return columnSizes;
    }

    public String getTable() {
        if(this.cachedTable != null) {
            return this.cachedTable;
        }

        StringBuilder sb = new StringBuilder();

        this.addLineToSb(sb, this.columnNames, false);
        this.addDividerToSb(sb);

        for (T entry : this.data) {
            /*
             * Skip null entries in the table
             * needed for orders, since they are set to null when
             * processed successfully
             */
            if(entry == null) {
                continue;
            }

            Object[][] lines = entry.getLines();

            for (Object[] line : lines) {
                this.addLineToSb(sb, line);
            }

            if(this.printDividerAfterLine) {
                this.addDividerToSb(sb);
            }
        }

        if(this.addNewLineAfterTable) {
            sb.append("\n");
        }

        this.cachedTable = sb.toString();
        return this.cachedTable;
    }

    private void addLineToSb(StringBuilder sb, Object[] columns) {
        this.addLineToSb(sb, columns, true);
    }

    private void addLineToSb(StringBuilder sb, Object[] columns, boolean addNewLine) {
        if(addNewLine) {
            sb.append("\n");
        }

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

    /*
     * Pads a string with spaces to the left or right, depending
     * on the type of the object
     */
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

    /*
     * Getter and setter for the table flags
     *
     * If a table flag is updated, the cache of the table
     * is invalidated
     */
    public boolean getPrintDividerAfterLine() {
        return this.printDividerAfterLine;
    }

    public void setPrintDividerAfterLine(boolean printDividerAfterLine) {
        this.cachedTable = null;
        this.printDividerAfterLine = printDividerAfterLine;
    }

    public boolean getAddNewLineAfterTable() {
        return this.addNewLineAfterTable;
    }

    public void setAddNewLineAfterTable(boolean addNewLineAfterTable) {
        this.cachedTable = null;
        this.addNewLineAfterTable = addNewLineAfterTable;
    }
}
