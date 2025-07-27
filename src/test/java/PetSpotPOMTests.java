import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.HomePage;

public class PetSpotPOMTests {
    WebDriver driver;
    HomePage homePage;

    @BeforeEach
    void openHomePage() {
        this.driver = new ChromeDriver();
        homePage = new HomePage(driver);
    }

    @AfterEach
    void clear() {
        this.driver.quit();
    }

    @Test
    void checkAllTheProductsHasDescriptionUsingPOM() throws InterruptedException {
        homePage.openSubCategory("Mačke", "Hrana za mačke");

    }
}

