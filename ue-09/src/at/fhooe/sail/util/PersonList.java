package at.fhooe.sail.util;

public class PersonList {
    private PersonNode head;

    public PersonList() {
        this.head = null;
    }

    /**
     * Adds a new person to the end of the list
     *
     * @param person - the person to add
     * @return true if the person was added, false otherwise
     */
    public boolean add(Person person) {
        if(person == null) {
            return false;
        }

        /*
         * Special case: empty list
         */
        if(this.head == null) {
            this.head = new PersonNode(person);
            return true;
        }

        /*
         * Since the loop starts with head and always checks
         * for the next element, we need to check head separately
         */
        if(head.getPerson().equals(person)) {
            return false;
        }

        PersonNode current = this.head;

        while(current.getNext() != null) {
            current = current.getNext();

            if(current.getPerson().equals(person)) {
                return false;
            }
        }

        /*
         * The new node is the last element, so the next
         * node of the newly inserted node never needs to be
         * specifically set since null is the default
         */
        current.setNext(new PersonNode(person));
        return true;
    }

    /**
     * Removes a person from the list
     *
     * @param firstname - the first name of the person
     * @param lastname  - the last name of the person
     * @return the removed person if found, null otherwise
     */
    public Person remove(String firstname, String lastname) {
        if(firstname == null || lastname == null) {
            return null;
        }

        /*
         * Special case: empty list
         */
        if(this.head == null) {
            return null;
        }

        /*
         * Special case: remove from head
         */
        if(this.head.getPerson().equals(firstname, lastname)) {
            Person removedPerson = this.head.getPerson();
            this.head = this.head.getNext();
            return removedPerson;
        }

        PersonNode current = this.head;

        while(current.getNext() != null) {
            if(current.getNext().getPerson().equals(firstname, lastname)) {
                Person removedPerson = current.getNext().getPerson();
                current.setNext(current.getNext().getNext());
                return removedPerson;
            }

            current = current.getNext();
        }

        /*
         * Person not found
         */
        return null;
    }

    /**
     * Removes all elements from the list
     */
    public void clear() {
        this.head = null;
    }

    /**
     * Searches for a person in the list
     *
     * @param firstname - the first name of the person
     * @param lastname  - the last name of the person
     * @return the person if found, null otherwise
     */
    public Person search(String firstname, String lastname) {
        if(firstname == null || lastname == null) {
            return null;
        }

        PersonNode current = this.head;
        while(current != null) {
            if(current.getPerson().equals(firstname, lastname)) {
                return current.getPerson();
            }
            current = current.getNext();
        }

        return null;
    }

    /**
     * Gets the nth element from the list
     * Even tho this is not part of the exercise, this
     * method is very useful for testing purposes
     *
     * @param position - the index of the element
     * @return the person if found, null otherwise
     */
    public Person getNthElement(int position) {
        int size = this.getSize();
        if(position < 0 || position >= size) {
            return null;
        }

        PersonNode current = this.head;
        for(int i = 0; i < position; i++) {
            current = current.getNext();
        }

        return current.getPerson();
    }

    /**
     * Converts the list to a string
     *
     * @return the string representation of the list
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("HEAD");

        PersonNode current = this.head;
        while(current != null) {
            sb.append("\n -> ");
            sb.append(current.getPerson().toString());
            current = current.getNext();
        }

        sb.append("\n -> NULL");

        return sb.toString();
    }

    /**
     * Converts the list to an array
     *
     * @return the array of persons
     */
    public Person[] toArray() {
        int size = this.getSize();

        Person[] persons = new Person[size];

        /*
         * Don't need to work with null here, since
         * we just checked for the size of the list
         */
        PersonNode current = this.head;
        for(int i = 0; i < size; i++) {
            persons[i] = current.getPerson();
            current = current.getNext();
        }

        return persons;
    }

    /**
     * Calculate the size of the list
     *
     * @return the size of the list
     */
    public int getSize() {
        int size = 0;

        PersonNode current = this.head;

        while(current != null) {
            size++;
            current = current.getNext();
        }

        return size;
    }
}
