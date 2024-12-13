public interface ITableEntry {
    /*
     * This is the equivalent to the "print" method of
     * Orders / Parts, however I want the classes to describe themselves
     * instead of having a print method, so I can abstract the whole
     * table print logic away into a Table class
     */
    Object[][] getLines();
}
