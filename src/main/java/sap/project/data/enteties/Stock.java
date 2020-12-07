package sap.project.data.enteties;

import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;

@Entity
@Configurable(preConstruction = true)
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String product;

    private String color;

    private String size;

    private double price;

    private int quantity;

    public Stock() {

    }

    public Stock(Long id, String product, String color, String size, double price, int quantity) {
        this.id = id;
        this.product = product;
        this.color = color;
        this.size = size;
        this.price = price;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
