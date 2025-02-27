package id.ac.ui.cs.advprog.eshop.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Car {
    private String carId;

    @NotBlank(message = "Car name cannot be empty or whitespace")
    private String carName;

    @NotBlank(message = "Car color cannot be empty or whitespace")
    private String carColor;

    @NotNull(message = "Car quantity cannot be null")
    @Positive(message = "Car quantity must be a positive integer")
    private int carQuantity;

    public void update(Car newCarData) {
        if (newCarData != null) {
            this.carName = newCarData.getCarName();
            this.carColor = newCarData.getCarColor();
            this.carQuantity = newCarData.getCarQuantity();
        }
    }
}
