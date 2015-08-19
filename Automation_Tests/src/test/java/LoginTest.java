
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class LoginTest {

    private WebDriver driver;

    /*
    This automation follows a test scenario of a use case ¹1.
    For more detailed information please refer to https://github.com/devprovers/QA-Teamwork-Provers/issues/1
    */

    @Before
    public void setUp(){
        driver  = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @Test
    public void validLogin(){
        // 1. Valid email AND Valid password - pass - "Activity Stream" is displayed
        driver.get("http://provers.bitrix24.com");

        driver.findElement(By.name("USER_LOGIN")).clear();
        driver.findElement(By.name("USER_LOGIN")).sendKeys("dev_provers@abv.bg");
        driver.findElement(By.name("USER_PASSWORD")).clear();
        driver.findElement(By.name("USER_PASSWORD")).sendKeys("provers2015");
        driver.findElement(By.id("AUTH_SUBMIT")).click();

        WebDriverWait pause = new WebDriverWait(driver, 10);
        pause.until(ExpectedConditions.visibilityOfElementLocated(By.id("workarea")));

        assertEquals("https://provers.bitrix24.com/?current_fieldset=SOCSERV", driver.getCurrentUrl());
        assertEquals("Activity Stream", driver.findElement(By.id("pagetitle")).getText());

        driver.findElement(By.className("user-name")).click();
        driver.findElement(By.xpath("/html/body/div[9]/table/tbody/tr[2]/td[2]/div/div/div/a[6]/span[3]")).click();
    }

    @Test
    public void incorrectLogin() throws InterruptedException {

        WebDriverWait pause = new WebDriverWait(driver, 10);

        // 2. Valid email AND Wrong password - pass - "Incorrect login or password" is displayed
        driver.get("http://provers.bitrix24.com");
        driver.findElement(By.name("USER_LOGIN")).clear();
        driver.findElement(By.name("USER_LOGIN")).sendKeys("dev_provers@abv.bg");
        driver.findElement(By.name("USER_PASSWORD")).clear();
        driver.findElement(By.name("USER_PASSWORD")).sendKeys("wrongPassword");
        driver.findElement(By.id("AUTH_SUBMIT")).click();

        pause.until(ExpectedConditions.visibilityOfElementLocated(By.className("error_message")));
        assertEquals("Incorrect login or password", driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div/div[4]/div/span[2]")).getText());

        // 3. Wrong email AND Wrong password - pass - "Incorrect login or password" is displayed
        Thread.sleep(2000);

        driver.findElement(By.name("USER_LOGIN")).clear();
        driver.findElement(By.name("USER_LOGIN")).sendKeys("wrongEMAIL@dir.bg");
        driver.findElement(By.name("USER_PASSWORD")).clear();
        driver.findElement(By.name("USER_PASSWORD")).sendKeys("wrongPassword");
        driver.findElement(By.id("AUTH_SUBMIT")).click();

        pause.until(ExpectedConditions.visibilityOfElementLocated(By.className("error_message")));
        assertEquals("Incorrect login or password", driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div/div[4]/div/span[2]")).getText());

        // 4. Wrong email AND Valid password - pass - "Incorrect login or password" is displayed
        Thread.sleep(2000);

        driver.findElement(By.name("USER_LOGIN")).clear();
        driver.findElement(By.name("USER_LOGIN")).sendKeys("wrongEMAIL@dir.bg");
        driver.findElement(By.name("USER_PASSWORD")).clear();
        driver.findElement(By.name("USER_PASSWORD")).sendKeys("provers2015");
        driver.findElement(By.id("AUTH_SUBMIT")).click();

        pause.until(ExpectedConditions.visibilityOfElementLocated(By.className("error_message")));
        assertEquals("Incorrect login or password", driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div/div[4]/div/span[2]")).getText());

        // 5. Only valid email - pass - "Incorrect login or password" is displayed
        Thread.sleep(2000);

        driver.findElement(By.name("USER_LOGIN")).clear();
        driver.findElement(By.name("USER_LOGIN")).sendKeys("dev_provers@abv.bg");
        driver.findElement(By.name("USER_PASSWORD")).clear();
        driver.findElement(By.id("AUTH_SUBMIT")).click();

        pause.until(ExpectedConditions.visibilityOfElementLocated(By.className("error_message")));
        assertEquals("Incorrect login or password", driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div/div[4]/div/span[2]")).getText());

        // 6. Only NOT valid type of an email - "Incorrect login or password" is displayed
        Thread.sleep(2000);

        driver.findElement(By.name("USER_LOGIN")).clear();
        driver.findElement(By.name("USER_LOGIN")).sendKeys("@.com");
        driver.findElement(By.name("USER_PASSWORD")).clear();
        driver.findElement(By.id("AUTH_SUBMIT")).click();

        pause.until(ExpectedConditions.visibilityOfElementLocated(By.className("error_message")));
        assertEquals("Incorrect login or password", driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div/div[4]/div/span[2]")).getText());

        //  7. Only wrong email - pass - "Incorrect login or password" is displayed
        Thread.sleep(2000);

        driver.findElement(By.name("USER_LOGIN")).clear();
        driver.findElement(By.name("USER_LOGIN")).sendKeys("some@mail.com");
        driver.findElement(By.name("USER_PASSWORD")).clear();
        driver.findElement(By.id("AUTH_SUBMIT")).click();

        pause.until(ExpectedConditions.visibilityOfElementLocated(By.className("error_message")));
        assertEquals("Incorrect login or password", driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div/div[4]/div/span[2]")).getText());

        // 8. Only valid password - pass - "Incorrect login or password" is displayed
        Thread.sleep(2000);

        driver.findElement(By.name("USER_LOGIN")).clear();
        driver.findElement(By.name("USER_PASSWORD")).clear();
        driver.findElement(By.name("USER_PASSWORD")).sendKeys("provers2015");
        driver.findElement(By.id("AUTH_SUBMIT")).click();

        pause.until(ExpectedConditions.visibilityOfElementLocated(By.className("error_message")));
        assertEquals("Incorrect login or password", driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div/div[4]/div/span[2]")).getText());

        // 9. Only wrong password - pass - "Incorrect login or password" is displayed
        Thread.sleep(2000);

        driver.findElement(By.name("USER_LOGIN")).clear();
        driver.findElement(By.name("USER_PASSWORD")).clear();
        driver.findElement(By.name("USER_PASSWORD")).sendKeys("2202fdd@$$#@rewfdsafsdgdfgergxv");
        driver.findElement(By.id("AUTH_SUBMIT")).click();

        pause.until(ExpectedConditions.visibilityOfElementLocated(By.className("error_message")));
        assertEquals("Incorrect login or password", driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div/div[4]/div/span[2]")).getText());
    }

    @Test
    public void forgottenPasswordValid() throws InterruptedException {

        WebDriverWait pause = new WebDriverWait(driver, 10);

        // 10. Forgotten password with valid email - pass - received email with instructions
        driver.get("http://provers.bitrix24.com");
        driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div/form/div[4]/div/div/a")).click();
        pause.until(ExpectedConditions.visibilityOfElementLocated(By.className("registration_block_home")));

        driver.findElement(By.id("USER_EMAIL")).clear();
        driver.findElement(By.id("USER_EMAIL")).sendKeys("dev_provers@abv.bg");
        driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div/div[5]/form/div[2]/input")).click();

        pause.until(ExpectedConditions.visibilityOfElementLocated(By.className("good_message")));
        assertEquals("A code to reset your password and your registration information has just been sent to your e-mail address. Please check your email. Note that the reset code is re-generated on each request.",
                driver.findElement(By.className("good_message")).getText());

        // 11. Email with instructions - pass - received and working
        Thread.sleep(15000);

        driver.get("http://abv.bg");
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys("dev_provers@abv.bg");
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("provers2015");
        driver.findElement(By.id("loginBut")).click();

        pause.until(ExpectedConditions.visibilityOfElementLocated(By.id("middlePagePanel")));
        assertEquals("Developer Developer", driver.findElement(By.className("userName")).getText());

        driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div[4]/div/div[4]/div/div[2]/div/div[2]/div[2]/div/div[2]/span")).click();
        pause.until(ExpectedConditions.visibilityOfElementLocated(By.id("main")));
        assertEquals("Bitrix24.Network", driver.findElement(By.xpath("/html/body/div[1]/div/div[4]/div/div[4]/div/div[4]/div/div[2]/div/div[2]/div/div[5]/div/div/table/tbody[1]/tr[1]/td[2]/div")).getText());

    }

    @Test
    public void forgottenPasswordWrong(){
        WebDriverWait pause = new WebDriverWait(driver, 10);


        // 12. Forgotten password with wrong email - pass - Login or E-mail not found is displayed
        driver.get("http://provers.bitrix24.com");
        driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div/form/div[4]/div/div/a")).click();
        pause.until(ExpectedConditions.visibilityOfElementLocated(By.className("registration_block_home")));

        driver.findElement(By.id("USER_EMAIL")).clear();
        driver.findElement(By.id("USER_EMAIL")).sendKeys("provers@abv.bg");
        driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div/div[5]/form/div[2]/input")).click();

        pause.until(ExpectedConditions.visibilityOfElementLocated(By.className("error_message")));
        assertEquals("Login or E-mail not found", driver.findElement(By.className("error_message")).getText());

    }


    @After
    public void tearDown() {


    }



}
