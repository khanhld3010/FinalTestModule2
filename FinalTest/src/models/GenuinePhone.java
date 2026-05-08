package models;

public class GenuinePhone extends Phone{
    private int warrantyPeriod;
    private String warrantyScope;

    public GenuinePhone(int id, String phoneName, double price, int quantity, String brand, int warrantyPeriod, String warrantyScope) {
        super(id, phoneName, price, quantity, brand);
        this.warrantyPeriod = warrantyPeriod;
        this.warrantyScope = warrantyScope;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getWarrantyScope() {
        return warrantyScope;
    }

    public void setWarrantyScope(String warrantyScope) {
        this.warrantyScope = warrantyScope;
    }

    @Override
    public String toString() {
        return "GenuinePhone{" +
                "id=" + getId() +
                ", phoneName='" + getPhoneName() + '\'' +
                ", price=" + getPrice() +
                ", quantity=" + getQuantity() +
                ", brand='" + getBrand() + '\'' +
                ", warrantyPeriod=" + getWarrantyPeriod() +
                ", warrantyScope='" + getWarrantyScope() + '\'' +
                '}';
    }

    @Override
    public void displayInformation() {
        System.out.println(this);
    }
}
