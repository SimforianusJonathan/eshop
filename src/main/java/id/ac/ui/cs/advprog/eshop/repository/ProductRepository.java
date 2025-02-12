package id.ac.ui.cs.advprog.eshop.repository;


import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product update(Product product) {
        for (int i = 0; i < productData.size(); i++) {
            if (productData.get(i).getProductID().equals(product.getProductID())) {
                productData.set(i, product);
                return product;
            }
        }
        return null; // product not found
    }

    public void delete(String productID) {
        Iterator<Product> iterator = productData.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getProductID().equals(productID)) {
                iterator.remove();
                break;
            }
        }
    }



}
