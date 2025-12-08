package models;

import utils.MyLinkedList;

public class Aisle {
    private String name;
    private StorageTemperature temperature;
    private MyLinkedList<Shelf> shelves;

    public Aisle(String name, StorageTemperature temperature) {
        this.name = name;
        this.temperature = temperature;
        this.shelves = new MyLinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StorageTemperature getTemperature() {
        return temperature;
    }

    public void setTemperature(StorageTemperature temperature) {
        this.temperature = temperature;
    }

    public Shelf addShelf(String aisleName) {
        Shelf newShelf = new Shelf(aisleName);
        shelves.add(newShelf);

        return newShelf;
    }

    public MyLinkedList<Shelf> getShelves() {
        return shelves;
    }

    public Shelf getShelfByIndex(int index) {
        return shelves.get(index);
    }

    public Shelf getShelf(int shelfNumber) {
        for (int i = 0; i < shelves.size(); i++) {
            if (shelves.get(i).getShelfNumber() == shelfNumber) {
                return shelves.get(i);
            }
        }

        return null;
    }

    public void clear() {
        shelves.clear();
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", Temperature: " + temperature;
    }
}
