import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
           WebElement currenElementDescription = currentElement.findElement(By.xpath(".//a[@class='product-item-link']"));
           Assertions.assertFalse(currenElementDescription.getText().isEmpty());
           System.out.println(currenElementDescription.getText());
        }
    }

    @Test
    void checkForSearchBarExist() {
        List<WebElement> searchInputCollection = driver.findElements(By.xpath("//input[@class='amsearch-input']"));
        Assertions.assertEquals(1, searchInputCollection.size());
    }

    @Test
    void checkForSearchBarExistSpecificPage() {
        driver.get("https://petspot.rs/psi/hrana-za-pse.html");
        List<WebElement> searchInputCollection = driver.findElements(By.xpath("//input[@class='amsearch-input']"));
        Assertions.assertEquals(1, searchInputCollection.size());
    }

    @Test
    void checkForProductListNavigation() {
        driver.get("https://petspot.rs/psi/hrana-za-pse.html");
        WebElement pageItems = driver.findElement(By.xpath("//ul[@class='items pages-items']"));
        List<WebElement> pageItemsCollections = pageItems.findElements(By.xpath(".//li[contains(@class, 'item')]"));
        WebElement firstItem = pageItemsCollections.get(0);
        String firstItemClass = firstItem.getAttribute("class");
        Assertions.assertEquals("item current", firstItemClass);
        WebElement nextItem = pageItemsCollections.get(pageItemsCollections.size() - 1);
        nextItem.click();
        pageItems = driver.findElement(By.xpath("//ul[@class='items pages-items']"));
        pageItemsCollections = pageItems.findElements(By.xpath(".//li[contains(@class, 'item')]"));
        WebElement backItem = pageItemsCollections.get(0);
        String backItemClass = backItem.getAttribute("class");
        Assertions.assertEquals("item pages-item-previous", backItemClass);
        WebElement secondItem = pageItemsCollections.get(2);
        String secondItemClass = secondItem.getAttribute("class");
        Assertions.assertEquals("item current", secondItemClass);
        backItem.click();
        pageItems = driver.findElement(By.xpath("//ul[@class='items pages-items']"));
        pageItemsCollections = pageItems.findElements(By.xpath(".//li[contains(@class, 'item')]"));
        firstItem = pageItemsCollections.get(0);
        firstItemClass = firstItem.getAttribute("class");
        Assertions.assertEquals("item current", firstItemClass);
        Assertions.assertFalse(firstItemClass.contains("pages-item-previous"));

    }

    @Test
    void checkAddItemToCart() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        WebElement mackeCategory = driver.findElement(By.xpath("//a[@href='https://petspot.rs/macke.html']"));
        mackeCategory.click();
        driver.manage().window().fullscreen();
        List<WebElement> listOfCatsFood = driver.findElements(By.className("product-item-info"));
        WebElement firstCatFood = listOfCatsFood.get(0);
        WebElement addToCart = firstCatFood.findElement(By.xpath(".//button[@title='DODAJTE U KORPU']"));
        WebElement zeroBeforeClick = driver.findElement(By.className("empty"));
        Assertions.assertTrue(zeroBeforeClick.isDisplayed());
        addToCart.click();
        WebElement deleteItem = driver.findElement(By.xpath("//div[@data-block='minicart']"));
        deleteItem.click();
        Thread.sleep(5000);
        WebElement pressDeleteIcon = driver.findElement(By.cssSelector("a[title='Uklonite']"));
        pressDeleteIcon.click();
        WebElement removeItemFromCart = driver.findElement(By.className("action-accept"));
        removeItemFromCart.click();
        WebElement zeroAfterClick = driver.findElement(By.className("empty"));
        Assertions.assertTrue(zeroAfterClick.isDisplayed());
    }

    @Test
    void checkIfSearchBarIsWorking() {
        WebElement inputField = driver.findElement(By.cssSelector("input.amsearch-input"));
        inputField.sendKeys("aca");
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Clear Field']")));
        WebElement deleteButton = driver.findElement(By.xpath("//button[@title='Clear Field']"));
        Assertions.assertTrue(deleteButton.isDisplayed());
    }

    @Test
    void checkSearchResultCount() {
        WebElement inputField = driver.findElement(By.cssSelector("input.amsearch-input"));
        inputField.sendKeys("aca");
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("amsearch-product-list")));
        WebElement searchProductList = driver.findElement(By.className("amsearch-product-list"));
        List<WebElement> listOfSearchProductItems = searchProductList.findElements(By.className("amsearch-item"));
        Assertions.assertEquals(4, listOfSearchProductItems.size());
    }

    @Test
    void checkFirstItemFromSearchBar() {
        WebElement inputField = driver.findElement(By.cssSelector("input.amsearch-input"));
        inputField.sendKeys("aca");
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("amsearch-product-list")));
        WebElement searchProductList = driver.findElement(By.className("amsearch-product-list"));
        List<WebElement> listOfSearchProductItems = searchProductList.findElements(By.className("amsearch-item"));
        WebElement firstItemFromSearchItems = listOfSearchProductItems.get(0);
        String actualTitle = firstItemFromSearchItems.findElement(By.xpath(".//a[@title]")).getDomAttribute("title");
        Assertions.assertTrue(actualTitle.contains("Aca"));
    }

    @Test
    void checkSearchResaltsDetails() {
        WebElement inputField = driver.findElement(By.cssSelector("input.amsearch-input"));
        inputField.sendKeys("aca");
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("amsearch-product-list")));
        WebElement searchProductList = driver.findElement(By.className("amsearch-product-list"));
        List<WebElement> listOfSearchProductItems = searchProductList.findElements(By.className("amsearch-item"));
        for (int i = 0; i < listOfSearchProductItems.size(); i++) {
            WebElement priceValue = listOfSearchProductItems.get(i).findElement(By.className("price"));
           String priceText = priceValue.getText();
           Assertions.assertTrue(priceText.contains("RSD"));
        }
    }
}