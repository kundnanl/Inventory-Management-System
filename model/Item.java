package model;

import javafx.beans.property.*;

/**
 * This class represents an inventory item.
 * Principal Author: Shahid Khan
 */
public class Item {
    private final IntegerProperty id;
    private final StringProperty name;
    private final IntegerProperty quantity;
    private final DoubleProperty price;

    /**
     * Constructs an item with the given attributes.
     * @param id The unique ID of the item.
     * @param name The name of the item.
     * @param quantity The quantity of the item.
     * @param price The price of the item.
     */
    public Item(int id, String name, int quantity, double price) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
    }

    // Getter for item ID
    public int getId() {
        return id.get();
    }

    // Property getter for item ID
    public IntegerProperty idProperty() {
        return id;
    }

    // Setter for item ID
    public void setId(int id) {
        this.id.set(id);
    }

    // Getter for item name
    public String getName() {
        return name.get();
    }

    // Property getter for item name
    public StringProperty nameProperty() {
        return name;
    }

    // Setter for item name
    public void setName(String name) {
        this.name.set(name);
    }

    // Getter for item quantity
    public int getQuantity() {
        return quantity.get();
    }

    // Property getter for item quantity
    public IntegerProperty quantityProperty() {
        return quantity;
    }

    // Setter for item quantity
    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    // Getter for item price
    public double getPrice() {
        return price.get();
    }

    // Property getter for item price
    public DoubleProperty priceProperty() {
        return price;
    }

    // Setter for item price
    public void setPrice(double price) {
        this.price.set(price);
    }
}
