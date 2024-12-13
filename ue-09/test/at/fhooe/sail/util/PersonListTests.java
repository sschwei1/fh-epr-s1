package at.fhooe.sail.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class PersonListTests {
    public static PersonList getTestList() {
        PersonList list = new PersonList();
        list.add(new Person("John", "Smith", "john.smith@example.com", "+1-555-123-4567"));
        list.add(new Person("Emma", "Johnson", "emma.johnson@example.com", "+1-555-234-5678"));
        list.add(new Person("Michael", "Brown", "michael.brown@example.com", "+1-555-345-6789"));
        list.add(new Person("Sophia", "Davis", "sophia.davis@example.com", "+1-555-456-7890"));
        list.add(new Person("William", "Wilson", "william.wilson@example.com", "+1-555-567-8901"));

        return list;
    }

    @Test
    public void testAdd() {
        PersonList list = new PersonList();

        /*
         * Simple insertion
         */
        assertTrue(list.add(new Person("John", "Smith", "john.smith@example.com", "+1-555-123-4567")));
        assertTrue(list.add(new Person("Emma", "Johnson", "emma.johnson@example.com", "+1-555-234-5678")));
        assertTrue(list.add(new Person("Michael", "Brown", "michael.brown@example.com", "+1-555-345-6789")));

        /*
         * Insertion of existing elements
         */
        assertFalse(list.add(new Person("John", "Smith", "john.smith@example.com", "+1-555-123-4567")));
        assertFalse(list.add(new Person("Emma", "Johnson", "emma.johnson@example.com", "+1-555-234-5678")));

        /*
         * Inserting of the same instance
         */
        Person somePerson = new Person("Sophia", "Davis", "sopphia.davis@example.com", "+1-555-456-7890");
        assertTrue(list.add(somePerson));
        assertFalse(list.add(somePerson));

        /*
         * Since the element in the list and this object
         * are the same instance, they are updated
         * simultaneously and will fail at insertion
         */
        somePerson.setFirstname("William");
        somePerson.setLastname("Wilson");
        assertFalse(list.add(somePerson));

        /*
         * Insertion of null
         */
        assertFalse(list.add(null));
    }

    @Test
    public void testRemove() {
        PersonList list = PersonListTests.getTestList();

        /*
         * Removing the first element
         */
        Person removed = list.remove("John", "Smith");
        assertNotNull(removed);
        assertTrue(removed.equals("John", "Smith"));

        /*
         * Removing the last element
         */
        removed = list.remove("William", "Wilson");
        assertNotNull(removed);
        assertTrue(removed.equals("William", "Wilson"));

        /*
         * Removing a non-existing element
         */
        removed = list.remove("John", "Smith");
        assertNull(removed);

        list.clear();

        /*
         * Removing from an empty list
         */
        removed = list.remove("John", "Smith");
        assertNull(removed);

        /*
         * Removing with name null
         */
        removed = list.remove(null, "Smith");
        assertNull(removed);

        removed = list.remove("John", null);
        assertNull(removed);

        removed = list.remove(null, null);
        assertNull(removed);
    }

    @Test
    public void testClear() {
        PersonList list = PersonListTests.getTestList();

        /*
         * Check for initial size
         */
        assertEquals(5, list.getSize());

        /*
         * Clear list
         */
        list.clear();
        assertEquals(0, list.getSize());

        /*
         * Clear empty list
         */
        list.clear();
        assertEquals(0, list.getSize());
    }

    @Test
    public void testSearch() {
        PersonList list = PersonListTests.getTestList();

        /*
         * Search for existing elements
         */
        Person found = list.search("John", "Smith");
        assertNotNull(found);
        assertTrue(found.equals("John", "Smith"));

        found = list.search("Sophia", "Davis");
        assertNotNull(found);
        assertTrue(found.equals("Sophia", "Davis"));

        found = list.search("William", "Wilson");
        assertNotNull(found);
        assertTrue(found.equals("William", "Wilson"));

        /*
         * Search for non-existing elements
         */
        found = list.search("John", "Doe");
        assertNull(found);

        found = list.search("Jane", "Doe");
        assertNull(found);

        found = list.search("John", "Wilson");
        assertNull(found);

        list.clear();

        /*
         * Search in empty list
         */
        found = list.search("John", "Smith");
        assertNull(found);

        found = list.search("Sophia", "Davis");
        assertNull(found);

        /*
         * Search with name null
         */
        found = list.search(null, "Smith");
        assertNull(found);

        found = list.search("John", null);
        assertNull(found);

        found = list.search(null, null);
        assertNull(found);
    }

    @Test
    public void testGetNthElement() {
        PersonList list = PersonListTests.getTestList();

        /*
         * Get first element
         */
        Person first = list.getNthElement(0);
        assertNotNull(first);
        assertTrue(first.equals("John", "Smith"));

        /*
         * Get last element
         */
        Person last = list.getNthElement(4);
        assertNotNull(last);
        assertTrue(last.equals("William", "Wilson"));

        /*
         * Add new element
         */
        list.add(new Person("Jane", "Doe", "jane.doe@example.com", "+1-555-678-9012"));
        Person newElement = list.getNthElement(list.getSize() - 1);
        assertNotNull(newElement);
        assertTrue(newElement.equals("Jane", "Doe"));

        /*
         * Get non-existing element
         */
        Person nonExisting = list.getNthElement(list.getSize());
        assertNull(nonExisting);

        list.clear();

        /*
         * Get element from empty list
         */
        nonExisting = list.getNthElement(0);
        assertNull(nonExisting);

        /*
         * Get element with negative index
         */
        nonExisting = list.getNthElement(-1);
        assertNull(nonExisting);
    }

    @Test
    public void testToString() {
        PersonList list = PersonListTests.getTestList();

        /*
         * Check the string representation of the list
         */
        String expected = """
                HEAD
                 -> John Smith (john.smith@example.com, +1-555-123-4567)
                 -> Emma Johnson (emma.johnson@example.com, +1-555-234-5678)
                 -> Michael Brown (michael.brown@example.com, +1-555-345-6789)
                 -> Sophia Davis (sophia.davis@example.com, +1-555-456-7890)
                 -> William Wilson (william.wilson@example.com, +1-555-567-8901)
                 -> NULL
                """;
        assertEquals(expected.trim(), list.toString());

        /*
         * Remove elements and re-check the string representation
         */
        list.remove("Michael", "Brown");
        list.remove("Sophia", "Davis");
        expected = """
                HEAD
                 -> John Smith (john.smith@example.com, +1-555-123-4567)
                 -> Emma Johnson (emma.johnson@example.com, +1-555-234-5678)
                 -> William Wilson (william.wilson@example.com, +1-555-567-8901)
                 -> NULL
                """;
        assertEquals(expected.trim(), list.toString());

        /*
         * Check string representation of an empty list
         */
        list.clear();
        expected = """
                HEAD
                 -> NULL
                """;
        assertEquals(expected.trim(), list.toString());

        /*
         * Add new element and re-check the string representation
         */
        list.add(new Person("John", "Smith", "john.smith@example.com", "+1-555-123-4567"));
        expected = """
                HEAD
                 -> John Smith (john.smith@example.com, +1-555-123-4567)
                 -> NULL
                """;
        assertEquals(expected.trim(), list.toString());
    }

    @Test
    public void testToArray() {
        /*
         * Empty list
         */
        PersonList list = new PersonList();
        this.testArrayEquality(list);

        /*
         * List with elements
         */
        list = PersonListTests.getTestList();
        this.testArrayEquality(list);

        /*
         * Modify list
         */
        list.remove("John", "Smith");
        list.remove("William", "Wilson");
        this.testArrayEquality(list);

        /*
         * Clear list
         */
        list.clear();
        this.testArrayEquality(list);

        /*
         * Check from array to array
         */
        Person[] testArray = new Person[]{
                new Person("John", "Smith", "john.smith@example.com", "+1-555-123-4567"),
                new Person("Emma", "Johnson", "emma.johnson@example.com", "+1-555-234-5678"),
                new Person("Michael", "Brown", "michael.brown@example.com", "+1-555-345-6789"),
        };

        for(Person p : testArray) {
            list.add(p);
        }

        assertArrayEquals(testArray, list.toArray());
    }

    private void testArrayEquality(PersonList list) {
        Person[] array = list.toArray();
        assertNotNull(array);
        assertEquals(list.getSize(), array.length);

        /*
         * Check if the order of all elements is correct
         */
        for(int i = 0; i < array.length; i++) {
            Person fromArray = array[i];
            Person fromList = list.getNthElement(i);
            assertEquals(fromList, fromArray);
        }
    }

    @Test
    public void testGetSize() {
        PersonList list = PersonListTests.getTestList();
        assertEquals(5, list.getSize());

        /*
         * Removing elements
         * -> Size reduced by 1
         */
        list.remove("John", "Smith");
        assertEquals(4, list.getSize());

        /*
         * Remove 2 existing elements
         * Remove 1 non-existing element
         * -> Size reduced by 2
         */
        list.remove("William", "Wilson");
        list.remove("Sophia", "Davis");
        list.remove("John", "Smith");
        assertEquals(2, list.getSize());

        /*
         * Add new element
         * -> Size increased by 1
         */
        list.add(new Person("John", "Smith", "john.smith@example.com", "+1-555-123-4567"));
        assertEquals(3, list.getSize());

        /*
         * Clear list
         * -> Size reduced to 0
         */
        list.clear();
        assertEquals(0, list.getSize());

        /*
         * Add new element
         * -> Size increased by 1
         */
        list.add(new Person("John", "Smith", "john.smith@example.com", "+1-555-123-4567"));
        assertEquals(1, list.getSize());
    }
}
