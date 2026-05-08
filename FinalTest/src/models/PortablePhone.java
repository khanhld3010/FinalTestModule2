package models;

public class PortablePhone extends Phone{
    private String portableCountry;
    private String status;

    public PortablePhone(int id, String phoneName, double price, int quantity, String brand, String portableCountry, String status) {
        super(id, phoneName, price, quantity, brand);
        this.portableCountry = portableCountry;
        this.status = status;
    }

    public String getPortableCountry() {
        return portableCountry;
    }

    public void setPortableCountry(String portableCountry) {
        this.portableCountry = portableCountry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PortablePhone{" +
                "id=" + getId() +
                ", phoneName='" + getPhoneName() + '\'' +
                ", price=" + getPrice() +
                ", quantity=" + getQuantity() +
                ", brand='" + getBrand() + '\'' +
                ", portableCountry='" + getPortableCountry() + '\'' +
                ", status='" + getStatus() + '\'' +
                '}';
    }

    @Override
    public void displayInformation() {
        System.out.println(this);
    }
}
