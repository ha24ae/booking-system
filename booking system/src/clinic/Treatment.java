package clinic;

public class Treatment {
    private String name;
    private AreaOfExpertise area;

    public Treatment(String name, AreaOfExpertise area) {
        this.name = name;
        this.area = area;
        area.addTreatment(this);
    }

    public String getName() {
        return name;
    }

    public AreaOfExpertise getArea() {
        return area;
    }
}
