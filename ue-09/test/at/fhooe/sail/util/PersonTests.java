package at.fhooe.sail.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PersonTests {
    @Test
    public void testEquals() {
        Person person1 = new Person("John", "Smith", "john.smith@example.com", "+1-555-123-4567");
        Person person2 = new Person("Emma", "Johnson", "emma.johnson@example.com", "+1-555-234-5678");
        Person person3 = new Person("Michael", "Brown", "michael.brown@example.com", "+1-555-345-6789");

        /*
         * Check for all combination of test persons
         */
        assertFalse(person1.equals(person2));
        assertFalse(person1.equals(person3));
        assertFalse(person2.equals(person3));

        /*
         * Check for person with same name
         */
        assertTrue(person1.equals(new Person("John", "Smith", "", "")));
        assertTrue(person2.equals(new Person("Emma", "Johnson", "", "")));
        assertTrue(person3.equals(new Person("Michael", "Brown", "", "")));

        /*
         * Check for different name, but same email and phone number
         */
        assertFalse(person1.equals(new Person("John", "Doe", "john.smith@example.com", "+1-555-123-4567")));
        assertFalse(person2.equals(new Person("Jane", "Johnson", "emma.johnson@example.com", "+1-555-234-5678")));
        assertFalse(person3.equals(new Person("Michael", "Green", "michael.brown@example.com", "+1-555-345-6789")));

        /*
         * Check for name directly
         */
        assertTrue(person1.equals("John", "Smith"));
        assertTrue(person2.equals("Emma", "Johnson"));
        assertTrue(person3.equals("Michael", "Brown"));

        assertFalse(person1.equals("John", "Doe"));
        assertFalse(person2.equals("Jane", "Johnson"));
        assertFalse(person3.equals("Michael", "Green"));

        /*
         * Check for name null name
         */
        assertFalse(person1.equals(null, null));
        assertFalse(person1.equals("John", null));
        assertFalse(person1.equals(null, "Smith"));

        /*
         * Check for null person
         */
        assertFalse(person1.equals(null));
        assertFalse(person2.equals(null));
        assertFalse(person3.equals(null));
    }
}
