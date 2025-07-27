import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class PetSpotTests {
    public static String BASE_URL = "https://petspot.rs/";
    WebDriver driver;

    @BeforeEach
    void openHomePage() {
        driver = new ChromeDriver();
        driver.get(BASE_URL);
        driver.manage().window().fullscreen();

    }

    @Test
    void openMackePageTest() {
        WebElement mackeLink = driver.findElement(By.xpath("//a[@href='https://petspot.rs/macke.html']"));
        mackeLink.click();
        //<span class="base" data-ui-id="page-title-wrapper">Mačke</span>
        WebElement mackeTitle = driver.findElement(By.xpath("//span[@data-ui-id='page-title-wrapper']"));
        String mackeTitleActualText = mackeTitle.getText();
        Assertions.assertEquals("Mačke", mackeTitleActualText);


    }

    @AfterEach
    void closeBrowser() {
        driver.close();
    }


    @Test
    void kupovinaHraneZaMacke() {
        driver.get("https://petspot.rs/macke/hrana-za-macke.html");
        WebElement hranaZaMackeTitle = driver.findElement(By.xpath("//span[@data-ui-id='page-title-wrapper']"));
        String hranaZaMackeActualText = hranaZaMackeTitle.getText();
        Assertions.assertEquals("Hrana za mačke", hranaZaMackeActualText);
        //data-name="Acana Regionals Cat Grasslands 1,8kg"
        WebElement dryFoodForCat = driver.findElement(By.xpath("//a[[@data-name='Acana Regionals Cat Grasslands 1,8kg']"));
        //class="product-item-info"
        WebElement productInfo = dryFoodForCat.findElement(By.xpath("./parent::div[@class='product-item-info']"));
        //./parent::div[@class='product-info']
        WebElement priceForCatFood = productInfo.findElement(By.xpath(".//span[@class='price']"));
        String priceForCatFoodActualText = priceForCatFood.getText();
        Assertions.assertEquals("4.520,00 RSD", priceForCatFoodActualText);

    }

    @Test
    void checkFirstFoodForCat() throws InterruptedException {
        Thread.sleep(5000);
        WebElement mackeLink = driver.findElement(By.xpath("//a[@href='https://petspot.rs/macke.html']"));
        mackeLink.click();
        //<span class="base" data-ui-id="page-title-wrapper">Mačke</span>
        WebElement mackeTitle = driver.findElement(By.xpath("//span[@data-ui-id='page-title-wrapper']"));
        String mackeTitleActualText = mackeTitle.getText();
        WebElement hranaZaMackeButton = driver.findElement(By.cssSelector("a[title ='Hrana za mačke']"));
        hranaZaMackeButton.click();
        WebElement hranaZaMackeTitle = driver.findElement(By.xpath("//span[@data-ui-id='page-title-wrapper']"));
        String hranaZaMackeActualText = hranaZaMackeTitle.getText();
        Assertions.assertEquals("Hrana za mačke", hranaZaMackeActualText);
        //get all products
        //get first
        //get + button
        List<WebElement> listOfCatsFood = driver.findElements(By.className("product-item-info"));
        WebElement firstCatFood = listOfCatsFood.get(0);
        firstCatFood.findElement(By.xpath(".//button[@data-event='addToCart']")).click();
        System.out.println("Congratulation almost survived");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement cart = driver.findElement(By.xpath("//div[@data-block='minicart']"));
        WebElement numberOfItem = cart.findElement(By.cssSelector("span.counter-number"));
        //wait.until(ExpectedConditions.textToBe(By.xpath("//div[@data-block='minicart']"),"1"));
        //Assertions.assertEquals("1", numberOfItem.getText());
        WebElement finalPriceCatFood = firstCatFood.findElement(By.xpath("//span[@data-price-type='finalPrice']/span[@class='price']"));
        String finalPriceCatFoodText = finalPriceCatFood.getText();
        Assertions.assertEquals("1.921,00 RSD", finalPriceCatFoodText);
        WebElement wishlistButton = firstCatFood.findElement(By.xpath("//div[@data-role='add-to-links']"));
        //step0 By.xpath for example
        //step1 start with double "",
        //step2 double //
        //step3 add a tag div, span, a
        //step4 optional [], than @, atribute, =, '' expected value
        wishlistButton.click();
        Assertions.assertEquals("https://petspot.rs/customer/account/login/", driver.getCurrentUrl());

    }

    @Test
    void checkAllTheProductsHasDescription() {
        driver.get("https://petspot.rs/macke/hrana-za-macke.html");
        List<WebElement> listOfCatsFood = driver.findElements(By.className("product-item-info"));
        for (int i = 0; i < listOfCatsFood.size(); i++) {
           WebElement currentElement = listOfCatsFood.get(i);
           WebElement currenElementDescription = currentElement.findElement(By.xpath("//a[@class='product-item-link']"));
           Assertions.assertFalse(currenElementDescription.getText().isEmpty());
           System.out.println(currenElementDescription.getText());
        }
    }


}

