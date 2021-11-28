package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.RecipeCreatorService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RecipeCreatorController implements Initializable {

    @FXML
    private TextField ingredientId;

    @FXML
    private TextField quantity;

    @FXML
    private ListView<String> ingredientList;

    @FXML
    private TextField title;

    @FXML
    private TextArea description;

    private final RecipeCreatorService service = new RecipeCreatorService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateIngredientList();
    }

    private void updateIngredientList() {
        List<String> stringList = service.getIngredients().stream()
                .map(ingredient -> ingredient.getName() + " " + ingredient.getQuantity())
                .collect(Collectors.toList());

        ingredientList.setItems(FXCollections.observableArrayList(stringList));
    }

    @FXML
    public void addIngredient() {
        int id = Integer.parseInt(ingredientId.getText());
        int q = Integer.parseInt(quantity.getText());
        service.addIngredient(id, q);
        updateIngredientList();
    }

    public void saveRecipe() {
        service.saveRecipe(title.getText(), description.getText());
        Stage stage = (Stage) title.getScene().getWindow();
        stage.close();
        updateIngredientList();
    }
}
