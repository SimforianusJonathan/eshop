package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void testCreateProduct(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");

        WebElement productNameInput = driver.findElement(By.id("nameInput"));
        WebElement productQuantityInput = driver.findElement(By.id("quantityInput"));
        productQuantityInput.clear();
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        productNameInput.sendKeys("Test Product");
        productQuantityInput.sendKeys("10");
        submitButton.click();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.endsWith("/product/list"));

        WebElement productList = driver.findElement(By.tagName("tbody"));
        List<WebElement> rows = productList.findElements(By.tagName("tr"));

        boolean productFound = false;
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            System.out.println(cells.get(0).getText());
            System.out.println(cells.get(1).getText());
            if (cells.get(0).getText().equals("Test Product") && cells.get(1).getText().equals("10")) {
                productFound = true;
                break;
            }
        }

        assertTrue(productFound, "Product 'Test Product' with quantity '10' should be in the product list.");
    }

    @Test
    void testCreateProductWithEmptyName(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");

        WebElement productNameInput = driver.findElement(By.id("nameInput"));
        WebElement productQuantityInput = driver.findElement(By.id("quantityInput"));
        productQuantityInput.clear();
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        productNameInput.sendKeys("");
        productQuantityInput.sendKeys("10");
        submitButton.click();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.endsWith("/product/create"));

        WebElement errorMessage = driver.findElement(By.cssSelector("small.text-danger"));
        assertEquals("Product name cannot be empty or whitespace", errorMessage.getText());
    }

    @Test
    void testCreateProductWithWhitespace(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");

        WebElement productNameInput = driver.findElement(By.id("nameInput"));
        WebElement productQuantityInput = driver.findElement(By.id("quantityInput"));
        productQuantityInput.clear();
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        productNameInput.sendKeys("                         ");
        productQuantityInput.sendKeys("10");
        submitButton.click();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.endsWith("/product/create"));

        WebElement errorMessage = driver.findElement(By.cssSelector("small.text-danger"));
        assertEquals("Product name cannot be empty or whitespace", errorMessage.getText());
    }

    @Test
    void testCreateProductWithNegativeQuantity(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");

        WebElement productNameInput = driver.findElement(By.id("nameInput"));
        WebElement productQuantityInput = driver.findElement(By.id("quantityInput"));
        productQuantityInput.clear();
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        productNameInput.sendKeys("Invalid Product");
        productQuantityInput.sendKeys("-5");
        submitButton.click();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.endsWith("/product/create"));

        WebElement errorMessage = driver.findElement(By.cssSelector("small.text-danger"));
        assertEquals("Product quantity must be a positive integer", errorMessage.getText());
    }

    @Test
    void testCreateProductWithZero(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");

        WebElement productNameInput = driver.findElement(By.id("nameInput"));
        WebElement productQuantityInput = driver.findElement(By.id("quantityInput"));
        productQuantityInput.clear();
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        productNameInput.sendKeys("Invalid Product");
        productQuantityInput.sendKeys("0");
        submitButton.click();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.endsWith("/product/create"));

        WebElement errorMessage = driver.findElement(By.cssSelector("small.text-danger"));
        assertEquals("Product quantity must be a positive integer", errorMessage.getText());
    }
}