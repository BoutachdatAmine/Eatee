package be.ehb.progproject2.eatee.entity;

public class Allergy {
    private int id;
    private String name;

    public Allergy(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
