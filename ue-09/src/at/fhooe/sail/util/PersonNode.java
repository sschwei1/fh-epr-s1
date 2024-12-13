public class PersonNode {
    private Person data;
    private PersonNode next;

    public PersonNode(Person data) {
        this.data = data;
        this.next = null;
    }

    public Person getData() {
        return data;
    }

    public void setData(Person data) {
        this.data = data;
    }

    public PersonNode getNext() {
        return next;
    }

    public void setNext(PersonNode next) {
        this.next = next;
    }
}
