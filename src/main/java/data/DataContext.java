package data;

import java.nio.file.Path;

public class DataContext {
    private static DataContext instance;
    private DataRepository products, recipes;
    private static final String PRODUCTS_FILE_NAME = "products.bin";
    private static final String RECIPES_FILE_NAME = "recipes.bin";

    private DataContext() {
        products = new BinaryRepository(Path.of(PRODUCTS_FILE_NAME));
        recipes = new BinaryRepository(Path.of(RECIPES_FILE_NAME));
    }

    public static DataContext getInstance() {
        if(instance == null) {
            instance = new DataContext();
        }
        return instance;
    }

    public DataRepository getProductsRepository() {
        return products;
    }
    public DataRepository getRecipesRepository() {
        return recipes;
    }
}
