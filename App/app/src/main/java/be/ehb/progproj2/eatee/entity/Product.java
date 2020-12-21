package be.ehb.progproj2.eatee.entity;

import java.util.List;

public class Product {
    private int productId;
    private double price;
    private String name;
    private int quantity;
    private int categoryId;
    private List<IngredientAmount> ingredients;
    private List<Integer> allergies;
    private boolean available;
    private double promotionActive;

    public boolean isAvailable() {
        return available;
    }
    public int getProductId() {
        return productId;
    }
    public double getPrice() {
        return price;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public String getName() {
        return name;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getPromotionActive() {
        return promotionActive;
    }
    public List<IngredientAmount> getIngredients() {
        return ingredients;
    }
    public List<Integer> getAllergies() {
        return allergies;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                '}';
    }
}
