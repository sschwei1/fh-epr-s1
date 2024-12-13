import java.util.HashMap;
import java.util.Map;

public class AutoPartWarehouse {
    private Part[] parts;
    private Order[] orders;

    public AutoPartWarehouse() {
        this.parts = new Part[0];
        this.orders = new Order[0];
    }

    public AutoPartWarehouse(String partsFile, String ordersFile) {
        this();
        this.readParts(partsFile);
        this.readOrders(ordersFile);
    }

    public AutoPartWarehouse(Part[] parts, Order[] orders) {
        this.parts = parts;
        this.orders = orders;
    }

    /*
     * Processes all orders
     *
     * This is copied from the exercise, however I very much dislike
     * the approach of setting processed order to null.
     *
     * The ideal solution would be to have a list of all orders
     * and add a boolean flag to the order, which indicates if it
     * has been processed or not. This way, we can keep track of
     * all orders and their process status and not lose
     * any information about previous orders.
     */
    public void processOrders() {
        for(int i = 0; i < orders.length; i++) {
            Order order = orders[i];
            if(order.isFulfillable()) {
                order.process();
                orders[i] = null;
            }
        }
    }

    /*
     * Handles reading parts from the input file
     *
     * This method can be used multiple times, in case
     * parts getting restocked or new parts are added.
     *
     * At the end, the newly read parts are merged with the
     * current parts, which means, the stock of existing parts
     * will be updated, while new parts will be added
     *
     * Furthermore, it would also be possible to contain parts
     * with the same articleNumber in multiple rows, their
     * quantity will be summed up
     */
    public void readParts(String filename) {
        In.open(filename);

        int partCount = In.readInt();
        Part[] newParts = new Part[partCount];

        for (int i = 0; i < partCount; i++) {
            newParts[i] = Part.readPart();
        }

        In.close();

        this.parts = Part.mergePartCollections(this.parts, newParts);
    }

    /*
     * Handles printing parts
     *
     * Still abstracted the table print logic away in a separate table
     * class, so the table printing logic is in a single place.
     *
     * The exercise states we should use an object-oriented way
     * and recommends using something like "part.print()",
     * however instead of adding a print method, I added an interface
     * which enforces the "part.getLines()" method. This works similar
     * to a print method, the logic of how a part should be printed
     * stays inside the part class.
     *
     * The table is updated to a generic class, where the given
     * class needs to implement the ITableEntry interface. This
     * way it is ensured, objects given to the table can call
     * the getLines method.
     */
    private void printParts() {
        Table<Part> partTable = Part.getAsTable(this.parts);
        Out.println("PARTS");
        Out.println(partTable.getTable());
    }

    /*
     * Handles reading orders from the input file
     *
     * This method can also be re-used multiple times, like the
     * readParts method.
     *
     * However, new orders will never be merged together, so
     * the merging logic is a bit different and optimized
     * for this specific use case
     */
    public void readOrders(String filename) {
        In.open(filename);

        Map<Integer, Part> partMap = this.getPartsAsHashMap();

        int orderCount = In.readInt();
        Order[] newOrders = new Order[orderCount];

        for(int i = 0; i < orderCount; i++) {
            newOrders[i] = Order.readOrder(partMap);
        }

        In.close();

        this.orders = Order.mergeOrderCollection(this.orders, newOrders);
    }

    /*
     * Handles printing orders
     */
    private void printOrders() {
        Table<Order> orderTable = Order.getAsTable(this.orders);
        Out.println("ORDERS");
        Out.println(orderTable.getTable());
    }

    /*
     * Handles printing the whole warehouse
     *
     * This method is just a combination of the printParts
     * and printOrders method
     */
    public void print() {
        this.printParts();
        this.printOrders();
    }

    /*
     * Ideally I want to use a HashMap to store parts, so this method would not
     * be used, however the exercise states, that parts and orders
     * need to be stored in an array
     *
     * This method is useful, since I don't want to assume that a parts articleNumber
     * is their index in the parts array.
     *
     * Since an Order can have multiple items, I would need to search for a part
     * everytime, which would be a O(n) runtime, however a HashMap would allow me
     * to do so in O(1) time
     *
     * Even tho, we are not using a HashMap to store parts, we can still use this
     * methods, because filling up the HashMap is close to O(1) and then searching
     * for a part is O(1) as well, this would result in 2 * O(1) runtime complexity
     * which is simplified to O(1) and is therefore better than O(n)
     *
     * When calculating runtime complexity 2 * O(1) is simplified O(1)
     * and therefore better than O(n)
     */
    public HashMap<Integer, Part> getPartsAsHashMap() {
        HashMap<Integer, Part> partMap = new HashMap<>();

        for(Part part : this.parts) {
            partMap.put(part.getArticleNr(), part);
        }

        return partMap;
    }
}