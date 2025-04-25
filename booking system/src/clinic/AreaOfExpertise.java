package clinic;
import java.util.*;

class AreaOfExpertise {
    private String name;
    private List<Treatment> treatments;

    public AreaOfExpertise(String name) {
        this.name = name;
        this.treatments = new ArrayList<>();
    }

    public void addTreatment(Treatment t) {
        treatments.add(t);
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public String getName() {
        return name;
    }
}
