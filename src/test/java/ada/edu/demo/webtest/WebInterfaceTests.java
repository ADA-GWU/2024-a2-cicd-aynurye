package ada.edu.demo.webtest;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WebInterfaceTests {

	@Autowired
	private WebDriver webDriver;

	@LocalServerPort
	private int port;
	@Test
	@Order(1)
	@DisplayName("Create a user")
	public void CreateUser() {
		// Second user
		webDriver.get("http://localhost:" + port + "/student/new");
		WebElement studentIdInput = webDriver.findElement(By.id("studentId"));
		WebElement firstNameInput = webDriver.findElement(By.id("firstName"));
		WebElement lastNameInput = webDriver.findElement(By.id("lastName"));
		WebElement emailInput = webDriver.findElement(By.id("email"));
		assertNotNull(firstNameInput);
		try {
			studentIdInput.sendKeys("12002"); // Adjusted Student ID to "12002"
			Thread.sleep(2000);
			firstNameInput.sendKeys("Aynur"); // Adjusted First Name to "Aynur"
			Thread.sleep(2000);
			lastNameInput.sendKeys("Yeganzada"); // Adjusted Last Name to "Yeganzada"
			Thread.sleep(2000);
			emailInput.sendKeys("ayeganzada@gmail.com"); // Adjusted Email to "ayeganzada@gmail.com"
			Thread.sleep(2000);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		WebElement submitButton = webDriver.findElement(By.id("submit"));
		submitButton.click();
	}
	
	@Test
	@Order(2)
	@DisplayName("Check the created user")
	public void CheckUser() {
		// Check if the student is added
		webDriver.get("http://localhost:" + port + "/student/list");
		List<WebElement> bodyElementFName = webDriver.findElements(By.xpath("//*[contains(text(), 'Aynur')]"));
		List<WebElement> bodyElementLName = webDriver.findElements(By.xpath("//*[contains(text(), 'Yeganzada')]"));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		
		assert (bodyElementFName.size() == 1);
		assert (bodyElementLName.size() == 1);
	}
	@Test
	@Order(3)
	@DisplayName("Change a user first name")
	public void Change_Fname(){
		webDriver.get("http://localhost:" + port + "/student/update?id=12002");
		WebElement f_name=webDriver.findElement(By.id("firstName"));
		assertNotNull(f_name);
		try {
			f_name.clear();
			f_name.sendKeys("Aliya");
			Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println(e);

		}
		WebElement submitButton=webDriver.findElement(By.id("submit"));
		submitButton.click();

	}
	@Test
	@Order(4)
	@DisplayName("Check the changed user first name")
	public void Check_fname() {
		// Check if the student is added
		webDriver.get("http://localhost:" + port + "/student/list");
		List<WebElement> bodyElementFName = webDriver.findElements(By.xpath("//*[contains(text(), 'Aliya')]"));
		List<WebElement> bodyElementLName = webDriver.findElements(By.xpath("//*[contains(text(), 'Yeganzada')]"));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		
		assert (bodyElementFName.size() == 1);
		assert (bodyElementLName.size() == 1);
	}

}
    