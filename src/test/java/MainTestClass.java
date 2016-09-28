import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class MainTestClass {

    WebDriver driver;
    WebDriverWait wait;
    
    @BeforeClass
    public void setup(){
  	  System.setProperty("webdriver.chrome.driver", "chromedriver");
  	  this.driver = new ChromeDriver();
		this.driver.get("https://realtesters.slack.com");
    }

    
    @AfterClass
    public void closing(){
    	driver.quit();
    }
	
}
