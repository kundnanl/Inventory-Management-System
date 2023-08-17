package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Item;
import model.Manager;

/**
 * Controller class for the Inventory Management GUI.
 * Handles interactions between the user interface and the data model.
 * Principal Author: Shahid Khan
 */
public class InventoryController {
    @FXML
    private TableView<Item> tableView;
    @FXML
    private TableColumn<Item, String> nameColumn;
    @FXML
    private TableColumn<Item, Integer> quantityColumn;
    @FXML
    private TableColumn<Item, Double> priceColumn;
    @FXML
    private TextField nameField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField searchField; // Add this field for searching items

    private Manager manager; // Add this instance variable
    
    /**
     * Sets the data manager and initializes the user interface.
     * Loads items from file and populates the table.
     *
     * @param manager The data manager.
     */
    public void setManager(Manager manager) {
        this.manager = manager;
        manager.loadItemsFromFile(); // Load items from file when setting manager
        
        // Initialize table columns
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        
        // Convert ArrayList from model to ObservableList
        ObservableList<Item> itemList = FXCollections.observableArrayList(manager.getAllItems());

        // Populate table with data from in-memory storage
        tableView.setItems(itemList);

        // Initialize search field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterItems(newValue);
        });
    }

    /**
     * Handles the action of clicking the "Add Item" button.
     * Extracts input data, creates a new item, and adds it to the data manager.
     * Refreshes the table and clears input fields after adding.
     */
    @FXML
    private void addItemClicked() {
        String name = nameField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());

        Item newItem = new Item(manager.getNextId(), name, quantity, price); // Use getNextId to generate new ID
        manager.addItem(newItem);

        // Convert ArrayList from model to ObservableList
        ObservableList<Item> itemList = FXCollections.observableArrayList(manager.getAllItems());

        // Refresh table
        tableView.setItems(itemList);

        // Clear fields after adding
        nameField.clear();
        quantityField.clear();
        priceField.clear();
    }

    /**
     * Handles the action of clicking the "Update Item" button.
     * Updates the selected item's information with input data and saves changes.
     * Refreshes the table and clears input fields after updating.
     */
    @FXML
    private void updateItemClicked() {
        Item selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.setName(nameField.getText());
            selectedItem.setQuantity(Integer.parseInt(quantityField.getText()));
            selectedItem.setPrice(Double.parseDouble(priceField.getText()));

            // Update item in the in-memory storage
            manager.updateItem(selectedItem);

            // Refresh table
            tableView.refresh();

            // Clear fields after updating
            nameField.clear();
            quantityField.clear();
            priceField.clear();
        }
    }

    /**
     * Handles the action of clicking the "Search" button.
     * Filters the table based on the search query entered by the user.
     */
    @FXML
    private void searchClicked() {
        String searchQuery = searchField.getText();
        filterItems(searchQuery);
    }

    /**
     * Handles the action of clicking the "Delete Item" button.
     * Deletes the selected item from the data manager and updates the table.
     */
    @FXML
    private void deleteItemClicked() {
        Item selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            manager.deleteItem(selectedItem.getId());

            // Refresh table
            tableView.getItems().remove(selectedItem);
        }
    }

    /**
     * Filters and updates the table with items matching the search query.
     *
     * @param searchQuery The search query entered by the user.
     */
    private void filterItems(String searchQuery) {
        ObservableList<Item> filteredItems = FXCollections.observableArrayList();
        for (Item item : manager.getAllItems()) {
            if (item.getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                filteredItems.add(item);
            }
        }
        tableView.setItems(filteredItems);
    }
}
