package be.ehb.progproject2.eatee.entity.request;

import java.util.List;

public class SandwichBody {
    private List<Integer> ingredients;

    public SandwichBody(List<Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Integer> getIngredients() {
        return ingredients;
    }

    public void checkFields() throws Exception {
        if(ingredients == null)
            throw new Exception("The following field is required: ingredients");

        if (ingredients.size() <= 0)
            throw new Exception("The field ingredients must contain at least 1 ingredient ID");
    }
}
