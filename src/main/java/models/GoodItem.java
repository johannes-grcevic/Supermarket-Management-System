package models;

public class GoodItem {
    String description;
    float weight;
    float price;
    int quantity;
    StorageTemperature temperature;
    String url;

    public GoodItem(String description, float weight, float price, int quantity, StorageTemperature temperature, String url) {
        this.description = description;
        this.weight = weight;
        this.price = price;
        this.quantity = quantity;
        this.temperature = temperature;
        this.url = url;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public StorageTemperature getTemperature() {
        return temperature;
    }

    public void setTemperature(StorageTemperature temperature) {
        this.temperature = temperature;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Description: " + description +
                ", Weight: " + weight +
                ", Price: " + price +
                ", Quantity: " + quantity +
                ", Temperature: " + temperature +
                ", URL: " + url;
    }
}
