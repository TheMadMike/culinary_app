package service;

import data.DataContext;
import data.DataRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsService {
    private final DataRepository productsRepository = DataContext.getInstance().getProductsRepository();

    public void add(Product product) {
        productsRepository.create(product);
    }

    public void update(int index, Product product) {
        if(productsRepository.readAll().size() > index) {
            productsRepository.update(index, new Product(index, product.getName(), product.getQuantity()));
        }
    }

    public void delete(int index) {
        if(productsRepository.readAll().size() > index) {
            productsRepository.delete(index);
        }
    }

    public ObservableList<Product> getProductsObservableList() {
        List<Product> productList = productsRepository.readAll().stream()
                .map(serializable -> (Product) serializable)
                .collect(Collectors.toList());

        for(int i = 0; i < productList.size(); ++i) {
            productList.get(i).setId(i);
        }

        return FXCollections.observableArrayList(productList);
    }
}
