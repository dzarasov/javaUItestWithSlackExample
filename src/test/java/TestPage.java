import java.awt.AWTException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestPage extends MainTestClass {
	

    
    @Test(priority=1)
    @Parameters({"username", "password"})
    public void login(String username, String password) throws InterruptedException{
    	LoginPage loginPage = new LoginPage(driver);
    	
    	SlackMainPage mainPage = loginPage.doLogin(username, password);
    	mainPage.findMessageInput();
    	Assert.assertEquals(mainPage.addToFavorites(), SlackMainPage.dataInput);
    }
    
    @Test(priority=2)
    public void validateFavoriteStarItem() throws InterruptedException, AWTException{
    	SlackMainPage clackPage = new SlackMainPage(driver);
    	Assert.assertEquals(clackPage.favoritesWithStarItem(), clackPage.dataInput);    	
    }
    
    @Test(priority=3)
    public void fileUploadingTest() throws InterruptedException, AWTException{
    	SlackMainPage clackPage = new SlackMainPage(driver);    	
    	Assert.assertEquals(clackPage.fileName, clackPage.verifyFileWasUploaded());
    }
    
    
}
