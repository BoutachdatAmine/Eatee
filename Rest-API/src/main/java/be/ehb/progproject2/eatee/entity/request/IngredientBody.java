package be.ehb.progproject2.eatee.entity.request;

public class IngredientBody {
    private String name;
    private Integer quantity;
    private int categoryId;
    private Boolean sandwichIngredient;

    public IngredientBody(String name, Integer quantity, int categoryId, Boolean sandwichIngredient) {
        this.name = name;
        this.quantity = quantity;
        this.categoryId = categoryId;
        this.sandwichIngredient = sandwichIngredient;
    }

    public String getName() {
        return name;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public int getCategoryId() {
        return categoryId;
    }
    public Boolean isSandwichIngredient() {
        return sandwichIngredient;
    }

    public void checkFields() throws Exception {
        // Required fields
        if(name == null || quantity < 0 || categoryId <= 0)
            throw new Exception("The following fields are required: name, quantity, categoryId");
    }
}
