import java.util.Map;

public class Order implements ITableEntry {
    /*
     * Defining fields as private and make the accessible
     * through getters and setters is a good practice, so
     * other classes are unable to directly change the fields
     *
     * Does it make a lot of sense if no additional logic
     * is required in any getter or setter?
     *
     * Not really, but it is still a good practice to follow
     */
    private Item[] items;

    /*
     * Could theoretically also be private or protected and the
     * readOrder method could serve as some kind of factory pattern
     */
    public Order(Item[] items) {
        this.items = items;
    }

    public Item[] getItems() {
        return this.items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    /*
     * Checks if the order can be fulfilled
     *
     * It is iterated over all items of the order and check if
     * the stock of the part is less than the quantity of the item
     * if that is the case, the order can't be fulfilled
     */
    public boolean isFulfillable() {
        for(Item item : this.items) {
            if(item.getPart().getStock() < item.getQuantity()) {
                return false;
            }
        }

        return true;
    }

    /*
     * Processes the order
     *
     * First it is checked, if the order is fulfill-able or not
     *
     * Even tho in the given "processOrders" method it is checked
     * right before calling the process method, I don't want to
     * assume it is the standard to do so.
     *
     * Especially in bigger project I personally would assume this
     * happens in here and so might others. If this method is used
     * elsewhere, it might not be re-checked and boom, stupid stuff
     * happened.
     */
    public void process() {
        if(!this.isFulfillable()) {
            return;
        }

        for(Item item : this.items) {
            item.getPart().checkout(item.getQuantity());
        }
    }

    /*
     * Handles reading a single order from the input file
     *
     * This method could also be part of a factory pattern
     */
    public static Order readOrder(Map<Integer, Part> partTable) {
        int itemCount = In.readInt();
        Item[] items = new Item[itemCount];

        for(int i = 0; i < itemCount; i++) {
            int articleNumber = In.readInt();
            In.read();

            Part part = partTable.get(articleNumber);
            int quantity = In.readInt();

            items[i] = new Item(part, quantity);;
        }

        return new Order(items);
    }

    /*
     * Handles adding together multiple order collections
     *
     * This method is used to merge multiple order collections
     * into a single order collection.
     *
     * This happens by creating a new array with the total length
     * of all orders and then copying all orders into the new array
     */
    public static Order[] mergeOrderCollection(Order[]... orderCollection) {
        int totalLength = 0;
        for(Order[] orders : orderCollection) {
            totalLength += orders.length;
        }

        Order[] mergedOrders = new Order[totalLength];

        int currentIndex = 0;
        for(Order[] orders : orderCollection) {
            System.arraycopy(orders, 0, mergedOrders, currentIndex, orders.length);
            currentIndex += orders.length;
        }

        return mergedOrders;
    }

    /*
     * Handles creating a table representation of the order
     *
     * This method is used to represent how an order should be printed.
     * An order is printed, by printing every single item of the order
     * in a separate line.
     */
    @Override
    public Object[][] getLines() {
        Object[][] lines = new Object[this.items.length][];

        for(int i = 0; i < items.length; i++) {
            lines[i] = new Object[]{
                items[i].getPart().getArticleNr(),
                items[i].getPart().getDescription(),
                items[i].getQuantity()
            };
        }

        return lines;
    }

    /*
     * Method to create orderTable
     *
     * Sadly static methods can't be part of interfaces in java
     * but adding this method is still the best solution in my
     * opinion, so the headers are set inside the order class
     * and don't need to be managed outside.
     */
    public static Table<Order> getAsTable(Order[] orders) {
        Table<Order> orderTable = new Table<>(
                new String[]{"Article Nr", "Description", "Quantity"},
                orders
        );

        orderTable.setPrintDividerAfterLine(true);

        return orderTable;
    }
}
