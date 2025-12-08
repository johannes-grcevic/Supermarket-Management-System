package models;

import utils.MyLinkedList;

public class FloorArea {
    private String title;
    private int level;
    private MyLinkedList<Aisle> aisles;

    public FloorArea(String title, int level) {
        this.title = title;
        this.level = level;
        this.aisles = new MyLinkedList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Aisle addAisle(String name, StorageTemperature temperature) {
        if (name.isEmpty() || temperature == null) return null;

        for (int i = 0; i < this.aisles.size(); i++) {
            if (aisles.get(i).getName().equals(name) && aisles.get(i).getTemperature().equals(temperature)) {
                return null;
            }
        }

        Aisle newAisle = new Aisle(name, temperature);
        aisles.add(newAisle);

        return newAisle;
    }

    public MyLinkedList<Aisle> getAisles() {
        return aisles;
    }

    public Aisle getAisle(int index) {
        return aisles.get(index);
    }

    public Aisle getAisle(String name) {
        for (int i = 0; i < aisles.size(); i++) {
            if (aisles.get(i).getName().equals(name)) {
                return aisles.get(i);
            }
        }

        return null;
    }

    public void clear() {
        aisles.clear();
    }

    @Override
    public String toString() {
        return "Title: " + title +
                ", Floor Level: " + level;
    }
}


