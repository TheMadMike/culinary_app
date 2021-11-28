package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Recipe;
import service.RecipesService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RecipesController implements Initializable {
    private static final String RECIPE_CREATOR_FXML_FILE_NAME = "RecipeCreator.fxml";

    @FXML
    private ListView<String> recipeList;

    @FXML
    private Label title;

    @FXML
    private Label description;

    @FXML
    private ListView<String> ingredients;

    private final RecipesService service = new RecipesService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateRecipeList();
    }

    @FXML
    public void openRecipeCreator() {
        try {
            URL resourceUrl = getClass().getClassLoader().getResource(RECIPE_CREATOR_FXML_FILE_NAME);
            Parent root = FXMLLoader.load(resourceUrl);
            Stage creatorStage = new Stage();
            creatorStage.setScene(new Scene(root));
            creatorStage.setTitle("Recipe creator");
            creatorStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
                updateRecipeList();
            });
            creatorStage.show();
        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    public void updateRecipeList() {
        recipeList.setItems(service.getRecipesAsObservableStringList());
    }

    @FXML
    public void deleteRecipe() {
        service.deleteRecipe(recipeList.getSelectionModel().getSelectedIndex());
        updateRecipeList();
    }

    @FXML
    public void showRecipe() {
        Recipe recipe = getSelectedRecipe();

        title.setText(recipe.getName());
        description.setText(recipe.getDescription());

        ingredients.setItems(service.getIngredientsAsObservableStringList(getSelectedIndex()));
    }

    @FXML
    public void prepareRecipe() {
        Recipe recipe = getSelectedRecipe();

        service.prepare(recipe);
        updateRecipeList();
        showRecipe();
    }

    private Recipe getSelectedRecipe() {
        return service.getRecipe(getSelectedIndex());
    }

    private int getSelectedIndex() {
        return recipeList.getSelectionModel().getSelectedIndex();
    }
}
