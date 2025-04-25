package clinic;

import java.util.ArrayList;
import java.util.List;

public class Physiotherapist extends Person{
    private List<AreaOfExpertise> areasOfExpertise = new ArrayList<>();
    private List<TreatmentSlot> slots = new ArrayList<>();
    public Physiotherapist(String id, String name, String address, String phone) {
        super(id, name, address, phone);
    }
    public void addAreaOfExpertise(AreaOfExpertise area) {
        areasOfExpertise.add(area);
    }
    public List<TreatmentSlot> getSlots() {
        return slots;
    }
    public void addSlot(TreatmentSlot slot) {
        slots.add(slot);
    }

    public List<AreaOfExpertise> getAreasOfExpertise() {
        return areasOfExpertise;
    }
}
