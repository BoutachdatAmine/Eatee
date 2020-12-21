package be.ehb.progproj2.eatee.entity;

public class Ingredient {
    private int ingredientId;
    private String name;
    private int quantityStock;
    private int categoryId;
    private boolean sandwichIngredient;

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

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                '}';
    }
}
