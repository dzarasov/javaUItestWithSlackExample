import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SlackMainPage {

	WebDriver driver;
	public static final String dataInput = "cooltexthere5test10";
	public static final String fileName = "pom.xml";

	
	@FindBy(xpath="//*[@id=\"message-input\"]")
	WebElement messageInput;
	
	@FindBy(id="search_terms")
	WebElement searchField;	

	@FindBy(id="stars_toggle")
	WebElement favoriteStarOnTop;	
	
	@FindBy(id="primary_file_button")
	WebElement uploadIcon;	
	
	@FindBy(xpath="//ul[@id=\"menu_items\"]/*[1]")
	WebElement uploadButtonFromDropDown;	
	
	@FindBy(xpath="//*[@id=\"upload_dialog\"]/div[3]/div/a[2]")
	WebElement uploadButton;	
	

    @FindBys(@FindBy(xpath="//div[@class=\"message_content \"]/*[4]/*[1]/*[1]"))
    private List<WebElement> listIFFileNames;
    
    public List<WebElement> getAlllistIFFileNames() {
           return listIFFileNames;
        }
    
    
    @FindBys(@FindBy(xpath="//div[@class='star_item']/*[1]/*[3]/*[3]"))
    private List<WebElement> favoriteElementsText;
    
    public List<WebElement> getFavoriteElementsText() {
           return favoriteElementsText;
        }    
    
    
    @FindBys(@FindBy(xpath="//*[@class=\"search_result_with_extract\"]/*[2]/*[2]/*[4]"))
    private List<WebElement> listOFMessagesFromSearch;
    
    public List<WebElement> getListOFMessagesFromSearch() {
           return listOFMessagesFromSearch;
        }      

    
    @FindBys(@FindBy(xpath="//*[@id=\"msgs_div\"]/*"))
    private List<WebElement> listOfMessages;
    
    public List<WebElement> getListOfMessages() {
           return listOfMessages;
        }      

    
	public SlackMainPage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}
	

	public void findMessageInput() throws InterruptedException{
		
		Thread.sleep(3000);
		//driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		messageInput.click();
		messageInput.sendKeys(dataInput);
		messageInput.submit();
		messageInput.sendKeys(dataInput);
		messageInput.submit();
	}
	
	public String addToFavorites() throws InterruptedException{
		
		WebDriverWait wait = new WebDriverWait(driver, 10000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"msgs_div\"]/*")));
		
		int lastDayElement = getListOfMessages().size();
		List<WebElement> listOfMessagesWithLastDays = driver.findElements(By.xpath("//*[@id=\"msgs_div\"]/div["+lastDayElement+"]/div[2]/*"));
		int valOfContent = listOfMessagesWithLastDays.size();

		Actions action = new Actions(driver);
		System.out.println("one " + lastDayElement);
		System.out.println("two " + valOfContent);
		WebElement starElement = driver.findElement(By.xpath("//*[@id=\"msgs_div\"]/div["+lastDayElement+"]/div[2]/*["+valOfContent+ "]"));
		action.moveToElement(starElement).moveToElement(driver.findElement(By.xpath("//*[@id=\"msgs_div\"]/div["+lastDayElement+"]/div[2]/*["+valOfContent+ "]/*[2]/*[3]/*[1]"))).click().build().perform();

		// wait 30 seconds and let server upload the data to clinet
		Thread.sleep(30000);		
		searchField.click();
		searchField.sendKeys("has:star");
		searchField.submit();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class=\"search_result_with_extract\"]/*[2]/*[2]/*[4]")));
		//List<WebElement> listOFMessagesFromSearch = driver.findElements(By.xpath("//*[@class=\"search_result_with_extract\"]/*[2]/*[2]/*[4]"));
		return getListOFMessagesFromSearch().get(0).getText();		
	}
	
	public String favoritesWithStarItem() throws InterruptedException{
		favoriteStarOnTop.click();
		
		WebDriverWait wait = new WebDriverWait(driver, 10000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("star_item")));
		
		//List<WebElement>favoriteElementsText = driver.findElements(By.xpath("//div[@class='star_item']/*[1]/*[3]/*[3]"));
		return getFavoriteElementsText().get(0).getText();
	}
	
	
	public String verifyFileWasUploaded() throws InterruptedException, AWTException{
		
		WebDriverWait wait = new WebDriverWait(driver, 10000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("primary_file_button")));
		
		uploadIcon.click();
		//Thread.sleep(3000);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id=\"menu_items\"]/*[1]")));
		
		Actions action = new Actions(driver);
		action.moveToElement(uploadButtonFromDropDown).click().build().perform();
		Thread.sleep(5000);
		//driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
		
		//File Need to be imported
		File file = new File("/Users/mikhaildzarasov/Desktop/"+ fileName);
		StringSelection stringSelection= new StringSelection(file.getAbsolutePath());
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		Robot robot = new Robot();

		// Cmd + Tab is needed since it launches a Java app and the browser looses focus
		robot.keyPress(KeyEvent.VK_META);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_META);
		robot.keyRelease(KeyEvent.VK_TAB);
		robot.delay(500);
		//Open Goto window
		robot.keyPress(KeyEvent.VK_META);
		robot.keyPress(KeyEvent.VK_SHIFT);
		robot.keyPress(KeyEvent.VK_G);
		robot.keyRelease(KeyEvent.VK_META);
		robot.keyRelease(KeyEvent.VK_SHIFT);
		robot.keyRelease(KeyEvent.VK_G);	
		//Paste the clipboard value
		robot.keyPress(KeyEvent.VK_META);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_META);
		robot.keyRelease(KeyEvent.VK_V);
		//Press Enter key to close the Goto window and Upload window
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.delay(500);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"upload_dialog\"]/div[3]/div/a[2]")));		
		uploadButton.click();		
		Thread.sleep(5000);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"file_progress\"]/div[1]/span")));
		//List<WebElement>listIFFileNames = driver.findElements(By.xpath("//div[@class=\"message_content \"]/*[4]/*[1]/*[1]"));
		int value = getAlllistIFFileNames().size()-1;
		
		return getAlllistIFFileNames().get(value).getText();	
	}
}
