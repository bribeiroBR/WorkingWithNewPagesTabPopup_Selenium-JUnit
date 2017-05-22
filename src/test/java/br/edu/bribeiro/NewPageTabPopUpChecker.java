package br.edu.bribeiro;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class NewPageTabPopUpChecker {

	private String url = "https://de.nametests.com/";

	private WebDriver driver;
	private Actions actions;
	private int timeOut = 45;
	private List<WebElement> links = new ArrayList<WebElement>();

	@Before
	public void setup() throws Exception {

		// chrome browser set up
		System.setProperty("webdriver.chrome.driver", "/Users/breno.ribeiro/Documents/vip_projects/chromedriver");
		this.driver = new ChromeDriver();

		// setting up a time out
		this.driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
		this.actions = new Actions(driver, timeOut);

		// open browser and getting all available links
		actions.openAndMaxPage(url);

	}

	@After
	public void closeBrowser() {
		driver.quit();
	}
	

	@Test
	public void shouldNOTFindNewPageTabPopup() {
		actions.openAndMaxPage(url);

		Assert.assertTrue(!actions.thisLinkOpenedNewPageTabPopup("Weiter"));
	}

	@Test
	public void shouldFindNewPageTabPopup() {
		actions.openAndMaxPage(url);

		Assert.assertTrue(actions.thisLinkOpenedNewPageTabPopup("AGB"));
	}

}