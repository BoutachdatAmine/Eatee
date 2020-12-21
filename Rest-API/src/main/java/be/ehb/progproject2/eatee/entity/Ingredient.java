package be.ehb.progproject2.eatee.entity;

public class Ingredient {
    private int ingredientId;
    private String name;
    private int quantityStock;
    private int categoryId;
    private boolean sandwichIngredient;

    public Ingredient(int ingredientId, String name, int quantityStock, int categoryId, boolean sandwichIngredient) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.quantityStock = quantityStock;
        this.categoryId = categoryId;
        this.sandwichIngredient = sandwichIngredient;
    }

    public String getName() {
        return name;
    }
    public int getIngredientId() {
        return ingredientId;
    }
    public int getQuantityStock() {
        return quantityStock;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public boolean isSandwichIngredient() {
        return sandwichIngredient;
    }
}
