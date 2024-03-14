<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

# POM Assignment

**<h3> Tests are done through the files <i>(the ones I modified)</i>:</h3>**

**Nemes:** <i>FunctionalityTests</i> and <i>WebInterfaceTests</i>

**<i>FunctionalityTests.java</i>**

```bash
  @Test
    @DisplayName("Search by email")
    public void testStudentSearchByEmail()
    {
        Student s2 = new Student(2,"Aliya","Mammadova","aliya@b.com",new Date(),null,null);

        when(studentRepository.findByEmail("aliya@b.com")).thenReturn(Optional.of(s2));
        Student result= studentService.getStudentByEmail("aliya@b.com");
        assertNotNull(result);
        assertEquals("Aliya", result.getFirstName());
        assertEquals("Mammadova", result.getLastName());
        assertEquals("aliya@b.com", result.getEmail());

    }
```
**What <i>FunctionalityTests</i> does?:**

- **Description:** This code snippet is essentially a behind-the-scenes look at ensuring a feature works as expected in a software application. Imagine you've got a digital list of students, each with their own unique email address. Now, there's a part of the software whose job is to find a student's details when you only give it their email address. To make sure this part is doing its job right, the code sets up a little test First, it pretends to add a student named Aliya Mammadova to the list with her email. Then, it tells the software, "Hey, go find the details for someone with the email aliya@b.com." The software should ideally come back with Aliya's details. The test checks a few things after getting a response: Did the software actually find someone? Is it really Aliya by checking her first and last name against what was expected? And, most crucially, did the software get the right email address? By running this test, developers can sleep a little easier knowing that this feature of their software is working correctly. It's like a rehearsal for the software, ensuring it performs its part flawlessly before going live on stage.

<br>

**<i>WebInterfaceTests.java</i>**

```bash
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
    
```
**What <i>WebInterfaceTests</i> does?:**

- **Description:** That one is like a digital rehearsal for a website's functionality, ensuring everything runs smoothly before the curtain rises. It's part of a series of checks, a dress rehearsal for a web application that manages student information. This particular series of tests uses a tool that mimics a user's actions on the website to see if things work as expected. The story unfolds in a series of acts, each designed to test a specific part of the application's performance. First, it's about adding a new user—think of it as casting a new actor into our play. The script instructs the browser to navigate to the part of the website where new students sign up. It carefully fills out a form with a student's details, like assigning a role, costume, and lines to our new actor, ensuring every detail like the student's ID, first name, last name, and email is correctly entered. After the curtain call, it even checks to ensure the submit button works, sending the new student's details backstage to be added to the cast. Then, like a director peeking through the curtains to ensure the new actor is ready on stage, the script checks the list of students to ensure our new addition is indeed present and accounted for. It specifically looks for the names it just added, ensuring the spotlight shines brightly on them, confirming their presence. The plot thickens as our director decides a name change is in order for more dramatic flair. The script navigates to an update page, where it changes the student's first name in the script. It's a quick scene change, ensuring the spotlight reflects the new name Finally, like a meticulous director making one last check before the final act, the script verifies the name change took effect. It scans through the cast list again, this time looking for the updated name, ensuring that when the curtain rises, everything is as it should be. Each act of this rehearsal ensures the web application behaves exactly as expected, from adding new characters to the play, ensuring they're ready for their debut, to last-minute name changes before the show goes live. It's a thorough preparation to ensure a flawless performance when the audience arrives.
