package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setProductID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        sampleProduct.setProductName("Sampo Cap Bambang");
        sampleProduct.setProductQuantity(100);
    }

    @Test
    void testCreate() {
        when(productRepository.create(sampleProduct)).thenReturn(sampleProduct);
        Product result = productService.create(sampleProduct);
        assertEquals(sampleProduct, result);
        verify(productRepository, times(1)).create(sampleProduct);
    }

    @Test
    void testFindAll() {
        Iterator<Product> productIterator = Collections.singletonList(sampleProduct).iterator();
        when(productRepository.findAll()).thenReturn(productIterator);
        List<Product> result = productService.findAll();
        assertEquals(1, result.size());
        assertEquals(sampleProduct, result.getFirst());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindAllEmpty() {
        when(productRepository.findAll()).thenReturn(Collections.emptyIterator());
        List<Product> result = productService.findAll();
        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        when(productRepository.update(sampleProduct)).thenReturn(sampleProduct);
        Product result = productService.update(sampleProduct);
        assertEquals(sampleProduct, result);
        verify(productRepository, times(1)).update(sampleProduct);
    }

    @Test
    void testUpdateNonExistentProduct() {
        when(productRepository.update(sampleProduct)).thenReturn(null);
        Product result = productService.update(sampleProduct);
        assertNull(result);
        verify(productRepository, times(1)).update(sampleProduct);
    }

    @Test
    void testDelete() {
        doNothing().when(productRepository).delete(sampleProduct.getProductID());
        productService.delete(sampleProduct.getProductID());
        verify(productRepository, times(1)).delete(sampleProduct.getProductID());
    }
}
