public class Item {
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
    private Part part;
    private int quantity;

    public Item(Part part, int quantity) {
        this.part = part;
        this.quantity = quantity;
    }

    public Part getPart() {
        return this.part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
