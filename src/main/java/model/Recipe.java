package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable {
    private int id;
    private String name;
    private List<Product> products;
    private String description;

    public Recipe() {
        this(0, "", new ArrayList<>(), "");
    }

    public Recipe(int id, String name, List<Product> products, String description) {
        this.id = id;
        this.name = name;
        this.products = products;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
