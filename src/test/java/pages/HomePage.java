package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static constants.CommonConstants.BASE_URL;

public class HomePage {
    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.driver.get(BASE_URL);
        this.driver.manage().window().fullscreen();
    }

    public void openSubCategory(String categoryName, String subcategoryName) throws InterruptedException {
        Thread.sleep(5000);
        List<WebElement> categories = driver.findElements(By.xpath("//a[(@role='menuitem') and (contains(@class, 'level-top'))]"));
        //https://www.geeksforgeeks.org/java/difference-between-for-loop-and-enhanced-for-loop-in-java/
        for (WebElement category : categories) {
            List<WebElement> searchedCategory = category.findElements(By.xpath(".//span[string-length(text()) >0]"));
            if (!searchedCategory.isEmpty()) {
                String categoryText = searchedCategory.get(0).getText();
                System.out.println("categoryText: " + categoryText);
                if (categoryText.equalsIgnoreCase(categoryName)) {
                    new Actions(driver)
                            .moveToElement(searchedCategory.get(0))
                            .perform();
                    Thread.sleep(2000);
                    List<WebElement> subCategories = driver.findElements(By.xpath("//a[(@role='menuitem') and not(contains(@class, 'level-top'))]"));
                    for (WebElement subCategory : subCategories) {
                        List<WebElement> searchedSubCategory = subCategory.findElements(By.xpath(".//span[text() = " + subcategoryName + "]"));
                        if (!searchedCategory.isEmpty()) {
                            System.out.println("searchedSubCategory");
                            searchedSubCategory.get(0).click();
                        }
                    }

                }
            }
        }
    }

    public void quit() {
        driver.quit();
    }
}
