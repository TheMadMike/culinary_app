package service;

import data.DataContext;
import data.DataRepository;
import model.Product;
import model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeCreatorService {

    private List<Product> ingredients = new ArrayList<>();
    private final DataRepository recipesRepository = DataContext.getInstance().getRecipesRepository();
    private final DataRepository productsRepository = DataContext.getInstance().getProductsRepository();

    public void addIngredient(int id, int quantity) {
        if(productsRepository.readAll().size() > id) {
            Product product = (Product) productsRepository.read(id);
            product.setQuantity(quantity);
            ingredients.add(product);
        }
    }

    public void saveRecipe(String title, String description) {
        Recipe recipe = new Recipe(0, title, ingredients, description);
        recipesRepository.create(recipe);
    }

    public List<Product> getIngredients() {
        return ingredients;
    }
}
