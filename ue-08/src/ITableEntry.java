public abstract class TableEntry {
    protected abstract String[] getHeaders();
    protected abstract Object[][] getLines();

    protected abstract boolean printDividerAfterLine();

    public static void printTable(TableEntry[] tableEntries) {
        if(tableEntries.length == 0) {
            Out.println("<table is empty>");
            return;
        }

        String[] headers = tableEntries[0].getHeaders();
        StringBuilder sb = new StringBuilder();
        int[] columnSizes = TableEntry.getColumnSizes(tableEntries);

        TableEntry.addLineToSb(sb, headers, columnSizes);
        TableEntry.addDividerToSb(sb, columnSizes);

        for(TableEntry tableEntry : tableEntries) {
            for(Object[] line : tableEntry.getLines()) {
                TableEntry.addLineToSb(sb, line, columnSizes);
            }

            if(tableEntry.printDividerAfterLine()) {
                TableEntry.addDividerToSb(sb, columnSizes);
            }
        }

        Out.println(sb.toString());
    }

    private static int[] getColumnSizes(TableEntry[] tableEntries) {
        int[] columnSizes = new int[tableEntries[0].getHeaders().length];

        for(int i = 0; i < columnSizes.length; i++) {
            columnSizes[i] = tableEntries[0].getHeaders()[i].length();
        }

        for(TableEntry tableEntry : tableEntries) {
            for(Object[] line : tableEntry.getLines()) {
                for(int i = 0; i < line.length; i++) {
                    columnSizes[i] = Math.max(columnSizes[i], line[i].toString().length());
                }
            }
        }

        return columnSizes;
    }

    private static void addDividerToSb(StringBuilder sb, int[] columnSizes) {
        for(int i = 0; i < columnSizes.length; i++) {
            sb.append("-".repeat(columnSizes[i]));

            if(i < columnSizes.length - 1) {
                sb.append("-+-");
            }
        }

        sb.append("\n");
    }

    private static void addLineToSb(StringBuilder sb, Object[] columns, int[] columnSizes) {
        for(int i = 0; i < columns.length; i++) {
            sb.append(pad(columns[i], columnSizes[i]));

            if(i < columns.length - 1) {
                sb.append(" | ");
            }
        }

        sb.append("\n");
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
