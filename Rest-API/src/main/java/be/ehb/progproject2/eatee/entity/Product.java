package be.ehb.progproject2.eatee.entity;

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

    public Product(int productId, double price, String name, int quantity, int categoryId, List<IngredientAmount> ingredients, List<Integer> allergies, boolean available, double promotionActive) {
        this.productId = productId;
        this.price = price;
        this.name = name;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.ingredients = ingredients;
        this.allergies = allergies;
        this.available = available;
        this.promotionActive = promotionActive;
    }

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
    public List<IngredientAmount> getIngredients() {
        return ingredients;
    }
    public List<Integer> getAllergies() {
        return allergies;
    }
    public double getPromotionActive() {
        return promotionActive;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
