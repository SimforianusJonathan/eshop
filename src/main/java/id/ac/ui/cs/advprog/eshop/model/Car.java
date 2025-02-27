package id.ac.ui.cs.advprog.eshop.model;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Car {
    private String carId;
    private String carName;
    private String carColor;
    private int carQuantity;

    public void update(Car newCarData) {
        if (newCarData != null) {
            this.carName = newCarData.getCarName();
            this.carColor = newCarData.getCarColor();
            this.carQuantity = newCarData.getCarQuantity();
        }
    }
}
