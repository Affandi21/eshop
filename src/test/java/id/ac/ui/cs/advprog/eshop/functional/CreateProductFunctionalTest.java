package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        // Kita langsung menuju ke halaman list produk
        baseUrl = String.format("http://localhost:%d/product/list", serverPort);
    }

    @Test
    void createProduct_isSuccessful(ChromeDriver driver) throws Exception {
        // 1. Buka halaman List Produk
        driver.get(baseUrl);

        // 2. Klik tombol "Create Product" (dari file ProductList.html)
        WebElement createButton = driver.findElement(By.linkText("Create Product"));
        createButton.click();

        // 3. Isi form Create Product (sesuai ID di CreateProduct.html)
        String name = "Sampo Cap Bambang";
        int quantity = 100;

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        nameInput.sendKeys(name);

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys(String.valueOf(quantity));

        // 4. Klik tombol Submit
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // 5. Verifikasi: Browser akan redirect kembali ke /product/list
        // Kita periksa apakah data yang kita masukkan muncul di tabel
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/list"));

        // Mengambil teks dari baris terakhir di tabel untuk verifikasi
        WebElement table = driver.findElement(By.className("table"));
        String tableContent = table.getText();

        assertTrue(tableContent.contains(name));
        assertTrue(tableContent.contains(String.valueOf(quantity)));
    }
}