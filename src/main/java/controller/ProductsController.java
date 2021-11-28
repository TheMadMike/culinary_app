package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.Product;
import service.ProductsService;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductsController implements Initializable {

    private @FXML VBox addMenu;
    private @FXML VBox updateMenu;
    private @FXML VBox removeMenu;

    private @FXML TableView<Product> tableView;
    private @FXML TableColumn<Product, Integer> idColumn;
    private @FXML TableColumn<Product, String> nameColumn;
    private @FXML TableColumn<Product, Integer> quantityColumn;

    ProductsService service = new ProductsService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));

        updateTable();
    }

    @FXML
    public void addProduct() {
        String name = ((TextField)addMenu.getChildren().get(1)).getText();
        int quantity = Integer.parseInt(((TextField)addMenu.getChildren().get(3)).getText());

        service.add(new Product(name, quantity));

        updateTable();
    }

    @FXML
    public void removeProduct() {
        int index = Integer.parseInt(((TextField)removeMenu.getChildren().get(1)).getText());

        service.delete(index);

        updateTable();
    }

    @FXML
    public void updateProduct() {
        int index = Integer.parseInt(((TextField)updateMenu.getChildren().get(1)).getText());
        String name = ((TextField)updateMenu.getChildren().get(3)).getText();
        int quantity = Integer.parseInt(((TextField)updateMenu.getChildren().get(5)).getText());

        service.update(index, new Product(name, quantity));

        updateTable();
    }

    private void updateTable() {
        tableView.setItems(service.getProductsObservableList());
    }

    @FXML
    public void refresh() {
        updateTable();
    }
}
