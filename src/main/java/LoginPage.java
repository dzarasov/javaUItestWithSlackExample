import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class LoginPage {
	
	

	WebDriver driver;
	
	@FindBy(id="signin_btn")
	WebElement signInButton;
	
	@FindBy(id="email")
	WebElement userName;
	
	@FindBy(id="password")
	WebElement password;
		
	public LoginPage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
	
	public String loginButtonText(){
    	return signInButton.getText();
	}
	
	public SlackMainPage doLogin(String usernameData, String passwordData){
		
		userName.sendKeys(usernameData);
    	password.sendKeys(passwordData);
    	signInButton.click();
       	return new SlackMainPage(driver);
	}
	
	
}
