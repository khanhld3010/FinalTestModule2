package models;

public abstract class Phone {
    private int id;
    private String phoneName;
    private double price;
    private int quantity;
    private String brand;

    public Phone(int id, String phoneName, double price, int quantity, String brand) {
        this.id = id;
        this.phoneName = phoneName;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
    }

    abstract public void displayInformation();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


}
