package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    @Getter
    private String productID;
    private String productName;
    private int productQuantity;
}
