package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.*;

public class Product implements Externalizable {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty quantity;

    public Product() {
        this(0, "", 0);
    }

    public Product(String name, int quantity) {
        this(0, name, quantity);
    }
    public Product(int id, String name, int quantity) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name.get());
        out.writeInt(quantity.get());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.quantity = new SimpleIntegerProperty();

        this.name.set(in.readUTF());
        this.quantity.set(in.readInt());
        this.id.set(0);
    }
}
