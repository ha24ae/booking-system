package clinic;

public class Person {
    protected String id, fullName, address, phoneNumber;

    public Person(String id, String name, String address, String phone) {
        this.id = id;
        this.fullName = name;
        this.address = address;
        this.phoneNumber = phone;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return fullName;
    }

    public String toString() {
        return fullName + " (" + id + ")";
    }
}
