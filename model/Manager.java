package model;

import java.io.*;
import java.util.ArrayList;

/**
 * This class manages the inventory items and handles file operations.
 * Principal Author: Shahid Khan
 */
public class Manager {
    private int nextId = 1; // Initialize next ID to 1
    private ArrayList<Item> items = new ArrayList<>();
    private static final String FILE_PATH = "items.dat"; // Change the file name as needed

    /**
     * Retrieves a list of all inventory items.
     * @return The list of inventory items.
     */
    public ArrayList<Item> getAllItems() {
        return items;
    }

    /**
     * Updates an item in the inventory.
     * @param updatedItem The updated item to be saved.
     */
    public void updateItem(Item updatedItem) {
        for (Item item : items) {
            if (item.getId() == updatedItem.getId()) {
                item.setName(updatedItem.getName());
                item.setQuantity(updatedItem.getQuantity());
                item.setPrice(updatedItem.getPrice());
                break;
            }
        }
        saveItemsToFile();
    }

    /**
     * Deletes an item from the inventory.
     * @param itemId The ID of the item to be deleted.
     */
    public void deleteItem(int itemId) {
        items.removeIf(item -> item.getId() == itemId);
        saveItemsToFile();
    }

    /**
     * Adds an item to the inventory.
     * @param item The item to be added.
     */
    public void addItem(Item item) {
        items.add(item);
        saveItemsToFile();
    }

    /**
     * Saves the inventory items to a file.
     */
    public void saveItemsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Item item : items) {
                writer.write(item.getId() + ", " + item.getName() + ", " + item.getQuantity() + ", " + item.getPrice());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads inventory items from a file.
     */
    public void loadItemsFromFile() {
        items.clear();
        int nextId = 1;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);
                    items.add(new Item(id, name, quantity, price));
                    if (id >= nextId) {
                        nextId = id + 1;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.nextId = nextId;
    }

    /**
     * Retrieves the next available ID for adding an item.
     * @return The next available ID.
     */
    public int getNextId() {
        return nextId;
    }
}
