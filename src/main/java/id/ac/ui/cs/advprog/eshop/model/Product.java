package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Product {
    private String productID;
    private String productName;
    private int productQuantity;

    public Product() {
        this.productID = UUID.randomUUID().toString(); // Generate ID otomatis
    }
}

