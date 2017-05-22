package br.edu.bribeiro;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class Actions {

	private WebDriver driver;
	private int timeOut;

	public Actions(WebDriver driver, int timeOut) {
		this.driver = driver;
		this.timeOut = timeOut;
	}

	// open and maximize a page
	public void openAndMaxPage(String page) {
		driver.get(page);
		driver.manage().window().maximize();
	}

	// this is to change the focus to the new tab opened
	public void changeToNewTab() {
		ArrayList tabs = new ArrayList(driver.getWindowHandles());
		try {
			driver.switchTo().window((String) tabs.get(1));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// this is for close any new tab
	public void closeNewTab() {
		ArrayList tabs = new ArrayList(driver.getWindowHandles());
		try {
			driver.switchTo().window((String) tabs.get(1));
			driver.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// back to the master window
	public void backToMasterTab() {
		ArrayList tabs = new ArrayList(driver.getWindowHandles());
		try {
			driver.switchTo().window((String) tabs.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// waiting for new Tab
	public boolean waitForNewTabOrWindow() {
		boolean result = false;

		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				ArrayList tabs = new ArrayList(driver.getWindowHandles());
				if (tabs.size() > 1) {
					return true;
				} else {
					return false;
				}
			}
		};

		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(2000, TimeUnit.MILLISECONDS)
					.pollingEvery(20, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class)
					.ignoring(StaleElementReferenceException.class);
			result = wait.until(pageLoadCondition);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	// this method is an easy way to make an regression test in every clickable
	// link or menu
	public boolean thisLinkOpenedNewPageTabPopup(String linkText) {
		WebElement wantedPageObject;
		Boolean result = false;

		wantedPageObject = driver.findElement(By.linkText(linkText));
		wantedPageObject.click();

		try {
			if (waitForNewTabOrWindow()) {
				result = true;
				changeToNewTab();
				System.out.println("This link opened a New Page / Tab or Popup");
				closeNewTab();
				backToMasterTab();
			} else {
				System.out.println("There is no New Page / Tab or Popup");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;

	}

}
