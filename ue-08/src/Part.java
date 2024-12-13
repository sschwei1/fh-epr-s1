import java.util.HashMap;

public class Part implements ITableEntry {
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
    private int articleNr;
    private String description;
    private int stock;

    public Part(int articleNr, String description, int stock) {
        this.articleNr = articleNr;
        this.description = description;
        this.stock = stock;
    }

    public int getArticleNr() {
        return this.articleNr;
    }

    public void setArticleNr(int articleNr) {
        this.articleNr = articleNr;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return this.stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    /*
     * Used to merge two parts together
     *
     * Even tho we could simply update the stock, there might
     * be additional logic of merging parts together.
     */
    public void merge(Part part) {
        this.stock += part.getStock();
    }

    /*
     * Used to check out a part
     *
     * Again, the stock could simply be updated here, it is simpler
     * to abstract the checkout logic away in case it gets
     * more complex at some point
     */
    public void checkout(int quantity) {
        this.stock -= quantity;
    }

    /*
     * Reads a part from the input
     *
     * This method could also be part of a factory pattern
     */
    public static Part readPart() {
        int articleNr = In.readInt();
        String description = In.readString();
        int stock = In.readInt();

        return new Part(articleNr, description, stock);
    }

    /*
     * Handles adding together multiple part collections
     *
     * This happens by creating a hashMap with the articleNr
     * as key and the part as value.
     *
     * We use the ".merge" method of the hashMap to add new Parts
     * to the map, this offers a convenient way to pass a
     * callback method, on how to handle the case when an item
     * with the same key already exists.
     *
     * At the end the map is converted back to an array
     */
    public static Part[] mergePartCollections(Part[]... partCollections) {
        HashMap<Integer, Part> partMap = new HashMap<>();

        for(Part[] parts : partCollections) {
            for(Part part : parts) {
                if(part == null) {
                    continue;
                }

                partMap.merge(part.getArticleNr(), part, (existingPart, newPart) -> {
                    existingPart.merge(newPart);
                    return existingPart;
                });
            }
        }

        return partMap.values().toArray(new Part[0]);
    }

    /*
     * Handles table representation of the part
     *
     * This method is used to represent how a part should be printed.
     * A part is printed as a single line.
     */
    @Override
    public Object[][] getLines() {
        return new Object[][]{
                { this.getArticleNr(), this.getDescription(), this.getStock() }
        };
    }

    /*
     * Method to create partTable
     *
     * Sadly static methods can't be part of interfaces in java
     * but adding this method is still the best solution in my
     * opinion, so the headers are set inside the part class
     * and don't need to be managed outside.
     */
    public static Table<Part> getAsTable(Part[] parts) {
        return new Table<>(
                new String[]{"Article Nr", "Description", "Stock"},
                parts
        );
    }
}
