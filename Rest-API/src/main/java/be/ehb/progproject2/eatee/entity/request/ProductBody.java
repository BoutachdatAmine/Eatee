package be.ehb.progproject2.eatee.entity.request;

import be.ehb.progproject2.eatee.entity.IngredientAmount;

import java.util.List;

public class ProductBody {
    private String name;
    private double price;
    private int categoryId;
    private List<Integer> allergies;
    private List<IngredientAmount> ingredients;

    public ProductBody(String name, double price, int categoryId) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
    }

    public ProductBody(String name, double price, int categoryId, List<Integer> allergies, List<IngredientAmount> ingredients) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.allergies = allergies;
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public List<Integer> getAllergies() {
        return allergies;
    }
    public List<IngredientAmount> getIngredients() {
        return ingredients;
    }

    /**
     * This method will check if all fields are filled in.
     * @throws Exception When one of the requirements is not met.
     */
    public void checkFields() throws Exception {
        // Missing required fields
        if(name == null || allergies == null || ingredients == null)
            throw new Exception("The following fields are required: name, price, categoryId, allergies, ingredients");

        // Wrong input
        if(price <= 0)
            throw new Exception("The field 'price' cannot be 0 or less!");

        if(categoryId <= 0)
            throw new Exception("The field 'categoryId' is invalid!");
    }

    @Override
    public String toString() {
        return "ProductBody{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", categoryId=" + categoryId +
                ", allergies=" + allergies +
                ", ingredients=" + ingredients +
                '}';
    }
}
