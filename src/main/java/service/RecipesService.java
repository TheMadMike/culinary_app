package service;

import data.DataContext;
import data.DataRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Product;
import model.Recipe;

import java.util.List;
import java.util.stream.Collectors;

public class RecipesService {

    private DataRepository recipesRepository = DataContext.getInstance().getRecipesRepository();
    private DataRepository productsRepository = DataContext.getInstance().getProductsRepository();

    public ObservableList<String> getRecipesAsObservableStringList() {
        List<String> stringList = recipesRepository.readAll().stream()
                .map(recipe -> ((Recipe) recipe).getName())
                .collect(Collectors.toList());

        return FXCollections.observableArrayList(
            stringList
        );
    }

    public void deleteRecipe(int id) {
        if(id < recipesRepository.readAll().size()) {
            recipesRepository.delete(id);
        }
    }

    public Recipe getRecipe(int index) {
        return (Recipe) recipesRepository.read(index);
    }

    public ObservableList<String> getIngredientsAsObservableStringList(int index) {
        return FXCollections.observableArrayList(
                ((Recipe) recipesRepository.read(index))
                        .getProducts().stream()
                    .map(product -> {
                        int available = getAvailableProducts(product.getName());
                        return product.getName()
                                + " " + available + "/"
                                + product.getQuantity();
                    })
                .collect(Collectors.toList())
        );
    }

    private int getAvailableProducts(String name) {
        return productsRepository.readAll().stream()
                .map(product -> (Product) product)
                .filter(product -> product.getName().equals(name))
                .findFirst().orElse(new Product()).getQuantity();
    }

    private boolean canBePrepared(Recipe recipe) {
        for(var ingredient : recipe.getProducts()) {
            int available = getAvailableProducts(ingredient.getName());
            if(available < ingredient.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    private void changeProductQuantity(String name, int quantity) {
        Product prod = productsRepository.readAll().stream()
                .map(product -> (Product) product)
                .filter(product -> product.getName().equals(name))
                .findFirst().orElse(new Product());
        int index = productsRepository.readAll().indexOf(prod);
        prod.setQuantity(quantity);
        productsRepository.update(index, prod);
    }

    public void prepare(Recipe recipe) {
        if(canBePrepared(recipe)) {
            for(var ingredient : recipe.getProducts()) {
                int available = getAvailableProducts(ingredient.getName());
                changeProductQuantity(ingredient.getName(), available - ingredient.getQuantity());
            }
        }
    }
}
