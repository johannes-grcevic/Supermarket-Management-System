package main;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import controllers.Supermarket;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.*;
import utils.Utils;

public class SupermarketController implements Initializable {
    @FXML
    private ListView<FloorArea> floorAreaList;
    @FXML
    private ListView<Aisle> aisleList;
    @FXML
    private ListView<Shelf> shelfList;
    @FXML
    private ListView<GoodItem> searchList;

    @FXML
    private TableView<GoodItem> productsTable;
    @FXML
    private TableColumn<GoodItem, String> descriptionColumn, weightColumn, priceColumn, quantityColumn, temperatureColumn, imageColumn;

    @FXML
    private TextArea stockTextArea;

    private Supermarket supermarket;

    public SupermarketController() {
        supermarket = new Supermarket();

        try {
            supermarket = supermarket.load();
        } catch (Exception e) {
            System.err.println("Error Loading File: " + e.getMessage());
        }
    }

    @FXML
    private void addFloorArea() {
        TextInputDialog floorAreaNameInput = new TextInputDialog();
        floorAreaNameInput.setTitle("Add Floor Area");
        floorAreaNameInput.setHeaderText("Add Floor Area");
        floorAreaNameInput.setContentText("Floor Area Name");

        String floorAreaName = floorAreaNameInput.showAndWait().orElse("");

        TextInputDialog floorAreaLevelInput = new TextInputDialog();
        floorAreaLevelInput.setTitle("Add Floor Area");
        floorAreaLevelInput.setHeaderText("Add Floor Area");
        floorAreaLevelInput.setContentText("Floor Area Level");

        int floorAreaLevel = Integer.parseInt(floorAreaLevelInput.showAndWait().orElse("-1"));

        FloorArea floorArea = supermarket.addFloorArea(floorAreaName, floorAreaLevel);
        if (floorArea != null) {
            floorAreaList.getItems().add(floorArea);
        }
    }

    @FXML
    private void addAisle() {
        if (Utils.showMissingSelectionAlert(floorAreaList, "Please select a Floor Area")) return;

        TextInputDialog aisleNameInput = new TextInputDialog();
        aisleNameInput.setTitle("Add Aisle");
        aisleNameInput.setHeaderText("Add Aisle");
        aisleNameInput.setContentText("Aisle Name");

        String aisleName = aisleNameInput.showAndWait().orElse("");

        TextInputDialog aisleTemperatureInput = new TextInputDialog();
        aisleTemperatureInput.setTitle("Add Aisle");
        aisleTemperatureInput.setHeaderText("1: Unrefrigerated, 2: Refrigerated, 3: Frozen");
        aisleTemperatureInput.setContentText("Aisle Temperature");

        Button cancel = (Button)aisleTemperatureInput.getDialogPane().lookupButton(ButtonType.CANCEL);
        AtomicBoolean cancelled = new AtomicBoolean(false);
        cancel.addEventFilter(ActionEvent.ACTION, event ->
                cancelled.set(true)
        );

        StorageTemperature aisleTemperature = null;
        while (aisleTemperature == null && !cancelled.get()) {
            switch (aisleTemperatureInput.showAndWait().orElse("")) {
                case "1":
                    aisleTemperature = StorageTemperature.UNREFRIGERATED;
                    break;
                case "2":
                    aisleTemperature = StorageTemperature.REFRIGERATED;
                    break;
                case "3":
                    aisleTemperature = StorageTemperature.FROZEN;
                    break;
                default:
                    aisleTemperatureInput.setContentText("Invalid Input!");
                    break;
            }
        }

        if (aisleName.isEmpty() || aisleTemperature == null) return;

        aisleList.getItems().add(floorAreaList.getSelectionModel().getSelectedItem().addAisle(aisleName, aisleTemperature));
    }

    @FXML
    private void addShelf() {
        if (Utils.showMissingSelectionAlert(aisleList, "Please select an Aisle")) return;

        shelfList.getItems().add(aisleList.getSelectionModel().getSelectedItem().addShelf(aisleList.getSelectionModel().getSelectedItem().getName()));
    }

    @FXML
    private void addItem() {
        if (Utils.showMissingSelectionAlert(shelfList, "Please select a Shelf")) return;

        TextInputDialog itemDescriptionInput = new TextInputDialog();
        itemDescriptionInput.setTitle("Add Item");
        itemDescriptionInput.setHeaderText("Add Item");
        itemDescriptionInput.setContentText("Item Description");
        String itemDescription = itemDescriptionInput.showAndWait().orElse("");

        TextInputDialog itemQuantityInput = new TextInputDialog();
        itemQuantityInput.setTitle("Add Item");
        itemQuantityInput.setHeaderText("Add Item");
        itemQuantityInput.setContentText("Item Quantity");
        int itemQuantity = Integer.parseInt(itemQuantityInput.showAndWait().orElse("1"));

        for (GoodItem goodItem : productsTable.getItems()) {
            if (goodItem.getDescription().equals(itemDescription)) {

                goodItem.setQuantity(goodItem.getQuantity() + itemQuantity);
                productsTable.refresh();

                return;
            }
        }

        TextInputDialog itemWeightInput = new TextInputDialog();
        itemWeightInput.setTitle("Add Item");
        itemWeightInput.setHeaderText("Add Item");
        itemWeightInput.setContentText("Item Weight (kg)");
        float itemWeight = Float.parseFloat(itemWeightInput.showAndWait().orElse("0.0"));

        TextInputDialog itemPriceInput = new TextInputDialog();
        itemPriceInput.setTitle("Add Item");
        itemPriceInput.setHeaderText("Add Item");
        itemPriceInput.setContentText("Item Price (€)");
        float itemPrice = Float.parseFloat(itemPriceInput.showAndWait().orElse("0.0"));

        TextInputDialog itemTemperatureInput = new TextInputDialog();
        itemTemperatureInput.setTitle("Add Item");
        itemTemperatureInput.setHeaderText("1: Unrefrigerated, 2: Refrigerated, 3: Frozen");
        itemTemperatureInput.setContentText("Item Temperature");

        Button cancel = (Button)itemTemperatureInput.getDialogPane().lookupButton(ButtonType.CANCEL);
        AtomicBoolean cancelled = new AtomicBoolean(false);
        cancel.addEventFilter(ActionEvent.ACTION, event ->
                cancelled.set(true)
        );

        StorageTemperature itemTemperature = null;
        while (itemTemperature == null && !cancelled.get()) {
            switch (itemTemperatureInput.showAndWait().orElse("")) {
                case "1":
                    itemTemperature = StorageTemperature.UNREFRIGERATED;
                    break;
                case "2":
                    itemTemperature = StorageTemperature.REFRIGERATED;
                    break;
                case "3":
                    itemTemperature = StorageTemperature.FROZEN;
                    break;
                default:
                    itemTemperatureInput.setContentText("Invalid Input!");
                    break;
            }
        }

        TextInputDialog itemImageURLInput = new TextInputDialog();
        itemImageURLInput.setTitle("Add Item");
        itemImageURLInput.setHeaderText("Add Item");
        itemImageURLInput.setContentText("Item Image URL");
        String itemImageURL = itemImageURLInput.showAndWait().orElse("");

        if (itemDescription.isEmpty()) return;

        productsTable.getItems().add(shelfList.getSelectionModel().getSelectedItem().addGoodItem(itemDescription, itemWeight, itemPrice, itemQuantity, itemTemperature, itemImageURL));
    }

    @FXML
    private void removeProduct() {
        if (Utils.showMissingSelectionAlert(shelfList, "Please select a Shelf")) return;
        if (Utils.showMissingSelectionAlert(productsTable, "Please select a Product")) return;

        TextInputDialog productRemoveQuantityInput = new TextInputDialog();
        productRemoveQuantityInput.setTitle("Remove Product");
        productRemoveQuantityInput.setHeaderText("Remove Product");
        productRemoveQuantityInput.setContentText("Enter Quantity");
        int removeQuantity = Integer.parseInt(productRemoveQuantityInput.showAndWait().orElse("-1"));

        GoodItem selectedItem = productsTable.getSelectionModel().getSelectedItem();

        if (selectedItem.getQuantity() < removeQuantity || removeQuantity < 1) return;

        if (selectedItem.getQuantity() - removeQuantity <= 0) {
            productsTable.getItems().remove(selectedItem);
            shelfList.getSelectionModel().getSelectedItem().getGoodItems().remove(selectedItem);
        }
        else {
            selectedItem.setQuantity(selectedItem.getQuantity() - removeQuantity);
        }

        productsTable.refresh();
    }

    @FXML
    private void smartAdd() {
        TextInputDialog itemDescriptionInput = new TextInputDialog();
        itemDescriptionInput.setTitle("Smart Add Item");
        itemDescriptionInput.setHeaderText("Smart Add Item");
        itemDescriptionInput.setContentText("Item Description");
        String itemDescription = itemDescriptionInput.showAndWait().orElse("");

        TextInputDialog itemQuantityInput = new TextInputDialog();
        itemQuantityInput.setTitle("Smart Add Item");
        itemQuantityInput.setHeaderText("Smart Add Item");
        itemQuantityInput.setContentText("Item Quantity");
        int itemQuantity = Integer.parseInt(itemQuantityInput.showAndWait().orElse("1"));

        for (GoodItem goodItem : productsTable.getItems()) {
            if (goodItem.getDescription().equals(itemDescription)) {

                goodItem.setQuantity(goodItem.getQuantity() + itemQuantity);
                productsTable.refresh();

                return;
            }
        }

        TextInputDialog itemWeightInput = new TextInputDialog();
        itemWeightInput.setTitle("Smart Add Item");
        itemWeightInput.setHeaderText("Smart Add Item");
        itemWeightInput.setContentText("Item Weight (kg)");
        float itemWeight = Float.parseFloat(itemWeightInput.showAndWait().orElse("0.0"));

        TextInputDialog itemPriceInput = new TextInputDialog();
        itemPriceInput.setTitle("Smart Add Item");
        itemPriceInput.setHeaderText("Smart Add Item");
        itemPriceInput.setContentText("Item Price (€)");
        float itemPrice = Float.parseFloat(itemPriceInput.showAndWait().orElse("0.0"));

        TextInputDialog itemTemperatureInput = new TextInputDialog();
        itemTemperatureInput.setTitle("Smart Add Item");
        itemTemperatureInput.setHeaderText("1: Unrefrigerated, 2: Refrigerated, 3: Frozen");
        itemTemperatureInput.setContentText("Item Temperature");

        Button cancel = (Button)itemTemperatureInput.getDialogPane().lookupButton(ButtonType.CANCEL);
        AtomicBoolean cancelled = new AtomicBoolean(false);
        cancel.addEventFilter(ActionEvent.ACTION, event ->
                cancelled.set(true)
        );

        StorageTemperature itemTemperature = null;
        while (itemTemperature == null && !cancelled.get()) {
            switch (itemTemperatureInput.showAndWait().orElse("")) {
                case "1":
                    itemTemperature = StorageTemperature.UNREFRIGERATED;
                    break;
                case "2":
                    itemTemperature = StorageTemperature.REFRIGERATED;
                    break;
                case "3":
                    itemTemperature = StorageTemperature.FROZEN;
                    break;
                default:
                    itemTemperatureInput.setContentText("Invalid Input!");
                    break;
            }
        }

        TextInputDialog itemImageURLInput = new TextInputDialog();
        itemImageURLInput.setTitle("Smart Add Item");
        itemImageURLInput.setHeaderText("Smart Add Item");
        itemImageURLInput.setContentText("Item Image URL");
        String itemImageURL = itemImageURLInput.showAndWait().orElse("");

        if (itemDescription.isEmpty()) return;

        Shelf suitableShelf = findSuitableShelf(itemDescription, itemTemperature);
        if (suitableShelf == null) return;

        productsTable.getItems().add(suitableShelf.addGoodItem(itemDescription, itemWeight, itemPrice, itemQuantity, itemTemperature, itemImageURL));
    }

    private Shelf findSuitableShelf(String itemDescription, StorageTemperature temperature) {
        for (FloorArea floorArea : supermarket.getFloorAreas()) {
            for (Aisle aisle : floorArea.getAisles()) {
                for (Shelf shelf : aisle.getShelves()) {
                    if (aisle.getTemperature().equals(temperature)) return shelf;

                    for (GoodItem goodItem : shelf.getGoodItems()) {
                        var matchTerms = itemDescription.toLowerCase().split(" ");

                        for (String term : matchTerms) {
                            if (aisle.getName().toLowerCase().contains(term.toLowerCase())) {
                                return shelf;
                            }

                            if (goodItem.getDescription().toLowerCase().contains(term.toLowerCase())) {
                                return shelf;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    @FXML
    private void viewAllStock() {
        if (!stockTextArea.getText().isEmpty()) {
            stockTextArea.clear();
        }

        for (FloorArea floorArea : supermarket.getFloorAreas()) {
            for (Aisle aisle : floorArea.getAisles()) {
                for (Shelf shelf : aisle.getShelves()) {
                    for (GoodItem goodItem : shelf.getGoodItems()) {
                        float totalValue = goodItem.getQuantity() * goodItem.getPrice();
                        float totalValueTruncated = (int)(totalValue * 100f) / 100f;

                        stockTextArea.appendText("Item Name: " + goodItem.getDescription()
                                + "\nFloor Area: " + floorArea.getTitle()
                                + "\nAisle: " + aisle.getName()
                                + "\nShelf: " + shelf.getShelfNumber()
                                + "\nTotal Value: " + goodItem.getQuantity() + " at €"
                                + goodItem.getPrice() + " = €"
                                + totalValueTruncated
                                + "\nWeight (kg): " + goodItem.getWeight()
                                + "\nStorage Temp: " + goodItem.getTemperature()
                                + "\nQuantity: " + goodItem.getQuantity() + "\n\n");
                    }
                }
            }
        }
    }

    @FXML
    private void searchProduct() {
        TextInputDialog searchQueryInput = new TextInputDialog();
        searchQueryInput.setTitle("Search Product");
        searchQueryInput.setHeaderText("Search Product");
        searchQueryInput.setContentText("Enter Product Name");
        String searchQuery = searchQueryInput.showAndWait().orElse("");

        if (searchQuery.isEmpty()) return;

        for (FloorArea floorArea : supermarket.getFloorAreas()) {
            for (Aisle aisle : floorArea.getAisles()) {
                for (Shelf shelf : aisle.getShelves()) {
                    for (GoodItem goodItem : shelf.getGoodItems()) {
                        if (goodItem.getDescription().toLowerCase().contains(searchQuery.toLowerCase())) {
                            if (!searchList.getItems().contains(goodItem)) {
                                searchList.getItems().add(goodItem);
                            }
                        }
                    }
                }
            }
        }

        if (searchList.getItems().isEmpty()) {
            Alert searchAlert = new Alert(Alert.AlertType.INFORMATION);
            searchAlert.setTitle("No Result");
            searchAlert.setHeaderText("No Products Found");
            searchAlert.setContentText("No Products Found for Search Query: " + searchQuery);
            searchAlert.showAndWait();
        }
    }

    @FXML
    private void clearSearch() {
        searchList.getItems().clear();
        searchList.refresh();
    }

    @FXML
    private void save() {
        try {
            supermarket.save();
        } catch (Exception e) {
            System.err.println("Error Saving File: " + e.getMessage());
        }
    }

    @FXML
    private void clearAllData() {
        supermarket.clear();

        for (FloorArea floorArea : supermarket.getFloorAreas()) {
            floorArea.clear();

            for (Aisle aisle : floorArea.getAisles()) {
                aisle.clear();

                for (Shelf shelf : aisle.getShelves()) {
                    shelf.clear();
                }
            }
        }

        floorAreaList.getItems().clear();
        aisleList.getItems().clear();
        shelfList.getItems().clear();
        productsTable.getItems().clear();

        refreshAll();
    }

    @FXML
    private void quit() {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        weightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        temperatureColumn.setCellValueFactory(new PropertyValueFactory<>("temperature"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("url"));

        populateListViews();
        populateTable();
    }

    private void populateListViews() {
        for (FloorArea floorArea : supermarket.getFloorAreas()) {
            floorAreaList.getItems().add(floorArea);

            for (Aisle aisle : floorArea.getAisles()) {
                aisleList.getItems().add(aisle);

                for (Shelf shelf : aisle.getShelves()) {
                    shelfList.getItems().add(shelf);
                }
            }
        }
    }

    private void populateTable() {
        for (FloorArea floorArea : supermarket.getFloorAreas()) {
            for (Aisle aisle : floorArea.getAisles()) {
                for (Shelf shelf : aisle.getShelves()) {
                    for (GoodItem goodItem : shelf.getGoodItems()) {
                        productsTable.getItems().add(goodItem);
                    }
                }
            }
        }
    }

    private void refreshAll() {
        floorAreaList.refresh();
        aisleList.refresh();
        shelfList.refresh();
        productsTable.refresh();
    }
}
