package id.ac.ui.cs.advprog.eshop.controller;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;
    private static final String REDIRECT_PRODUCT_LIST = "redirect:/product/list";

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@Valid @ModelAttribute Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // If there are validation errors, return to the form with error messages
            return "createProduct";
        }
        service.create(product);
        return "redirect:list";
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }

    @GetMapping("/edit/{id}")
    public String editProductPage(@PathVariable("id") String id, Model model) {
        for (Product product : service.findAll()) {
            if (product.getProductID().equals(id)) {
                model.addAttribute("product", product);
                return "editProduct";
            }
        }
        return REDIRECT_PRODUCT_LIST; // product not found
    }

    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable("id") String id, @Valid @ModelAttribute("product") Product updatedProduct, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // If there are validation errors, return to the edit form with error messages
            return "editProduct";
        }
        for (Product product : service.findAll()) {
            if (product.getProductID().equals(id)) {
                product.setProductName(updatedProduct.getProductName());
                product.setProductQuantity(updatedProduct.getProductQuantity());

                service.update(product);
                return REDIRECT_PRODUCT_LIST;
            }
        }
        return REDIRECT_PRODUCT_LIST; // Product not found
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") String id) {
        service.delete(id);
        return REDIRECT_PRODUCT_LIST;
    }

}

@Controller
@RequestMapping("/car")
class CarController extends ProductController {

    @Autowired
    private CarServiceImpl carservice;

    @GetMapping("/createCar")
    public String createCarPage(Model model) {
        Car car = new Car();
        model.addAttribute("car", car);
        return "createCar";
    }

    @PostMapping("/createCar")
    public String createCarPost(@ModelAttribute Car car, Model model) {
        carservice.create(car);
        return "redirect:listCar";
    }

    @GetMapping("/listCar")
    public String carListPage(Model model) {
        List<Car> allCars = carservice.findAll();
        model.addAttribute("cars", allCars);
        return "carList";
    }

    @GetMapping("/editCar/{carId}")
    public String editCarPage(@PathVariable String carId, Model model) {
        Car car = carservice.findById(carId);
        model.addAttribute("car", car);
        return "editCar";
    }

    @PostMapping("/editCar")
    public String editCarPost(@ModelAttribute Car car, Model model) {
        System.out.println(car.getCarId());
        carservice.update(car.getCarId(), car);

        return "redirect:listCar";
    }

    @PostMapping("/deleteCar")
    public String deleteCar(@RequestParam("carId") String carId) {
        carservice.deleteCarById(carId);
        return "redirect:listCar";
    }

}
