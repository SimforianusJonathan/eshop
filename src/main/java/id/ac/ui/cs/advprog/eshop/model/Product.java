package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class Product {
    private String productID;

    @NotBlank(message = "Product name cannot be empty or whitespace")
    private String productName;

    @NotNull(message = "Product quantity cannot be null")
    @Positive(message = "Product quantity must be a positive integer")
    private int productQuantity;

    public Product() {
        this.productID = UUID.randomUUID().toString(); // Generate ID otomatis
    }
}

