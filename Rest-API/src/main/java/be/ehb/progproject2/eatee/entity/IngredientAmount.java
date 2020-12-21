package be.ehb.progproject2.eatee.entity;

public class IngredientAmount {
    private int id;
    private int amount;

    public IngredientAmount(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }
    public int getAmount() {
        return amount;
    }
}
