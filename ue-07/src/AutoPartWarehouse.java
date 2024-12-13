public class AutoPartWarehouse {
    public static void main(String[] args) {
        Part[] parts = readParts("parts.txt");
        printParts(parts);
        Out.println();

        Order[] orders = readOrders("orders.txt", parts);
        printOrders(orders);
    }

    /*
     * Handles reading all parts contained in a file
     */
    static Part[] readParts(String filename) {
        In.open(filename);

        int partCount = In.readInt();
        Part[] parts = new Part[partCount];

        for (int i = 0; i < partCount; i++) {
            Part part = new Part();
            part.articleNr = In.readInt();
            part.description = In.readString();
            part.stock = In.readInt();

            parts[i] = part;
        }

        In.close();

        return parts;
    }

    /*
     * Handles printing all parts in a table format
     */
    static void printParts(Part[] parts) {
        Table partTable = new Table(
                new String[]{"Part No.", "Description", "Stock"},
                parts.length
        );

        for (Part part : parts) {
            partTable.addRow(new Object[]{part.articleNr, part.description, part.stock});
        }

        Out.println(partTable.getTable());
    }

    /*
     * Handles reading all orders contained in a file
     */
    static Order[] readOrders(String filename, Part[] parts) {
        In.open(filename);

        int orderCount = In.readInt();
        Order[] orders = new Order[orderCount];

        for(int i = 0; i < orderCount; i++) {
            orders[i] = readOrder(parts);
        }

        return orders;
    }

    /*
     * Handles reading a single order from the input file
     */
    static Order readOrder(Part[] parts) {
        Order order = new Order();

        int itemCount = In.readInt();
        order.items = new Item[itemCount];

        for(int i = 0; i < itemCount; i++) {
            int articleNumber = In.readInt();
            In.read();

            Item item = new Item();
            /*
             * only possible since we can assume input file contains
             * no errors, is ordered and articleNumber = index
             */
            item.part = parts[articleNumber];
            item.quantity = In.readInt();
            order.items[i] = item;
        }

        return order;
    }

    /*
     * Handles printing all orders in a table format
     */
    static void printOrders(Order[] orders) {
        Table orderTable = new Table(new String[]{"Part No.", "Description", "Qty."});

        for (Order order : orders) {
            for (Item item : order.items) {
                orderTable.addRow(new Object[]{item.part.articleNr, item.part.description, item.quantity});
            }
            orderTable.addDivider();
        }

        Out.println(orderTable.getTable());
    }
}