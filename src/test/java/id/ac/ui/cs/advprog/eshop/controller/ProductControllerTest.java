package id.ac.ui.cs.advprog.eshop.controller;

import java.util.ArrayList;
import java.util.List;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    // simulate sending http request "get" to '/' (ensure http response code 200)
    @Test
    void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testCreateProduct() throws Exception {
        Product sample = new Product();
        sample.setProductName("Valid Product");
        sample.setProductQuantity(10);

        mockMvc.perform(post("/product/create")
                        .flashAttr("product", sample))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:list"))
                .andExpect(redirectedUrl("list"));;
    }

    @Test
    void testCreateProductFailed() throws Exception {
        Product wrongSample = new Product();
        wrongSample.setProductName("");
        wrongSample.setProductQuantity(-1);

        mockMvc.perform(post("/product/create")
                        .flashAttr("product", wrongSample))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeHasFieldErrors("product", "productName"))
                .andExpect(model().attributeHasFieldErrors("product", "productQuantity"));
    }

    private static List<Product> getListExampleProducts() {
        Product sample = new Product();
        sample.setProductID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        sample.setProductName("warung bekicot");
        sample.setProductQuantity(35);

        Product sample2 = new Product();
        sample.setProductID("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        sample.setProductName("warung bekicotz");
        sample.setProductQuantity(35);

        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(sample);
        mockProducts.add(sample2);
        return mockProducts;
    }

    @Test
    void testProductListPage() throws Exception {
        List<Product> mockProducts = getListExampleProducts();

        // configuration for mock returning list of created products above
        Mockito.when(productService.findAll()).thenReturn(mockProducts);

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("products"));
    }

    @Test
    void testEditExistProduct() throws Exception {
        List<Product> mockProducts = getListExampleProducts();

        // configuration for mock returning list of created products above
        Mockito.when(productService.findAll()).thenReturn(mockProducts);

        mockMvc.perform(get("/product/edit/a0f9de46-90b1-437d-a0bf-d0821dde9096"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testEditEmptyProductList() throws Exception {
        Mockito.when(productService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/product/edit/a0f9de46-90b1-437d-a0bf-d0821dde9096"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/list"))
                .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    void testEditNonExistenceProduct() throws Exception {
        List<Product> mockProducts = getListExampleProducts();

        // configuration for mock returning list of created products above
        Mockito.when(productService.findAll()).thenReturn(mockProducts);

        mockMvc.perform(get("/product/edit/a0f9de46-90b1-437d-a0bf-d0821dde90968"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/list"))
                .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    void testUpdateProductSuccess() throws Exception {
        List<Product> mockProducts = getListExampleProducts();

        // configuration for mock returning list of created products above
        Mockito.when(productService.findAll()).thenReturn(mockProducts);

        mockMvc.perform(post("/product/edit/a0f9de46-90b1-437d-a0bf-d0821dde9096")
                        .param("productName", "Ubah Aja Cis")
                        .param("productQuantity", "2000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/list"))
                .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    void testUpdateProductFailed() throws Exception {
        List<Product> mockProducts = getListExampleProducts();

        // configuration for mock returning list of created products above
        Mockito.when(productService.findAll()).thenReturn(mockProducts);

        mockMvc.perform(post("/product/edit/a0f9de46-90b1-437d-a0bf-d0821dde9096")
                        .param("productName", "         ")
                        .param("productQuantity", "-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attributeHasFieldErrors("product", "productQuantity"))
                .andExpect(model().attributeHasFieldErrors("product", "productName"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(post("/product/delete/123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        Mockito.verify(productService, Mockito.times(1)).delete("123");
    }
}
