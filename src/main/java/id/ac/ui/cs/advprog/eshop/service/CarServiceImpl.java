package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public Car create(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car object not detected");
        }
        validateCarAttribute(car);
        carRepository.create(car);
        return car;
    }

    @Override
    public List<Car> findAll() {
        Iterator<Car> carIterator = carRepository.findAll();
        List<Car> allCar = new ArrayList<>();
        carIterator.forEachRemaining(allCar::add);
        return allCar;
    }

    @Override
    public Car findById(String carId) {
        Car car = carRepository.findById(carId);
        return car;
    }

    @Override
    public void update(String carId, Car car) {
        if (car == null ||carId == null || carId.isEmpty()) {
            throw new IllegalArgumentException("Car ID not detected or Car object not detected");
        }
        if (carRepository.findById(carId) == null) {
            throw new IllegalArgumentException("Car with ID " + carId + " not exists");
        }
        validateCarAttribute(car);
        carRepository.update(carId, car);
    }

    @Override
    public void deleteCarById(String carId) {
        if (carId == null || carId.isEmpty()) {
            throw new IllegalArgumentException("Car ID not detected");
        }
        if (carRepository.findById(carId) == null) {
            throw new IllegalArgumentException("Car with ID " + carId + " not exists");
        }
        carRepository.delete(carId);
    }

    private void validateCarAttribute(Car car) {
        if(car.getCarName() == null || car.getCarName().trim().isEmpty() || car.getCarColor() == null || car.getCarColor().trim().isEmpty()){
            throw new IllegalArgumentException("Car name or color not detected");
        }
        if (car.getCarQuantity() <=0 ){
            throw new IllegalArgumentException("Car quantity should be positive integer");
        }
    }
}

