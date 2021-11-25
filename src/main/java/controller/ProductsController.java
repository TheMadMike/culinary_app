package controller;

import data.DataContext;
import data.DataRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Product;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ProductsController implements Initializable {

    private @FXML VBox addMenu;
    private @FXML VBox updateMenu;
    private @FXML VBox removeMenu;

    private @FXML TableView<Product> tableView;
    private @FXML TableColumn<Product, Integer> idColumn;
    private @FXML TableColumn<Product, String> nameColumn;
    private @FXML TableColumn<Product, Integer> quantityColumn;

    private final DataRepository productsRepository = DataContext.getInstance().getProductsRepository();

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

        productsRepository.create(new Product(name, quantity));
        updateTable();
    }

    private void updateTable() {
        List<Product> productList = productsRepository.readAll().stream()
                .map(serializable -> (Product) serializable)
                .collect(Collectors.toList());

        for(int i = 0; i < productList.size(); ++i) {
            productList.get(i).setId(i);
        }

        tableView.setItems(FXCollections.observableArrayList(productList));
    }

    @FXML
    public void removeProduct() {
        int index = Integer.parseInt(((TextField)removeMenu.getChildren().get(1)).getText());
        if(productsRepository.readAll().size() > index) {
            productsRepository.delete(index);
            updateTable();
        }
    }

    @FXML
    public void updateProduct() {
        int index = Integer.parseInt(((TextField)updateMenu.getChildren().get(1)).getText());
        String name = ((TextField)updateMenu.getChildren().get(3)).getText();
        int quantity = Integer.parseInt(((TextField)updateMenu.getChildren().get(5)).getText());

        if(productsRepository.readAll().size() > index) {
            productsRepository.update(index, new Product(index, name, quantity));
            updateTable();
        }
    }
}
