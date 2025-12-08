package models;

import utils.MyLinkedList;

public class Shelf {
    private String aisleName;
    private int shelfNumber;
    private static int nextShelfNumber;
    private MyLinkedList<GoodItem> goodItems;

    public Shelf(String aisleName) {
        this.aisleName = aisleName;
        this.shelfNumber = nextShelfNumber;
        this.goodItems = new MyLinkedList<>();

        nextShelfNumber++;
    }

    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        this.shelfNumber = shelfNumber;
    }

    public String getAisleName() {
        return aisleName;
    }

    public void setAisleName(String aisleName) {
        this.aisleName = aisleName;
    }

    public MyLinkedList<GoodItem> getGoodItems() {
        return goodItems;
    }

    public GoodItem addGoodItem(String description, float weight, float price, int quantity, StorageTemperature temperature, String url) {
        GoodItem newGoodItem = new GoodItem(description, weight, price, quantity, temperature, url);

        goodItems.add(newGoodItem);

        return newGoodItem;
    }

    public void clear() {
        goodItems.clear();
    }

    @Override
    public String toString() {
        return "Aisle Name: " + aisleName +
                ", Shelf Number: " + shelfNumber;
    }
}
