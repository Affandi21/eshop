package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("http://localhost:%d", serverPort);
    }

    @Test
    void createProduct_isSuccessful(ChromeDriver driver) {

        // Explicit wait untuk menghindari flaky test
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // 1. Buka halaman Product List
        driver.get(baseUrl + "/product/list");

        // 2. Tunggu dan klik tombol Create Product
        WebElement createButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.linkText("Create Product"))
        );
        createButton.click();

        // 3. Tunggu form Create Product muncul
        WebElement nameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("nameInput"))
        );

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));

        // Gunakan data unik supaya tidak bentrok antar test
        String name = "Sampo " + System.currentTimeMillis();
        int quantity = 100;

        nameInput.sendKeys(name);
        quantityInput.sendKeys(String.valueOf(quantity));

        // 4. Submit form
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // 5. Tunggu redirect kembali ke product list
        wait.until(ExpectedConditions.urlContains("/product/list"));

        // 6. Tunggu tabel muncul
        WebElement table = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("table"))
        );

        String tableContent = table.getText();

        // 7. Verifikasi isi tabel
        assertTrue(tableContent.contains(name));
        assertTrue(tableContent.contains(String.valueOf(quantity)));
    }
}
