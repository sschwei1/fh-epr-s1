public class Main {
    public static void main(String[] args) {
        AutoPartWarehouse warehouse = new AutoPartWarehouse("parts.txt", "orders.txt");
        warehouse.print();
        warehouse.processOrders();
        Out.println("*** Warehouse after processing orders ***");
        Out.println();
        warehouse.print();
    }
}
