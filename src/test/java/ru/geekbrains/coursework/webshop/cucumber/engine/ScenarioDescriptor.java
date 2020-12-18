package ru.geekbrains.coursework.webshop.cucumber.engine;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ru.*;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Optional;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:/cucumber/scenarios",
        glue = "ru/geekbrains/coursework/webshop/cucumber/engine")
public class ScenarioDescriptor {

    public static class StepDefinition {
        private WebDriver webDriver;
        private long waitTimiOut = 60;

        @Before
        public void openBrowser() {
            //TODO сделать поддержку других браузеров или проверку на ОС
            WebDriverManager.edgedriver().arch64().setup();
            this.webDriver = new EdgeDriver();
        }

        @After
        public void closeBrowser() {
            Optional.ofNullable(this.webDriver).ifPresent(WebDriver::quit);
        }

        @Пусть("откроется страница {string}")
        public void openPage(String pageUrl) {
            webDriver.get(pageUrl);
        }

        @Если("доступен элемент {string}")
        public void haveElement(String htmlElement) {
            WebElement chatButton = this.waitElement(getElementBy(htmlElement));
        }

        @То("все нормально")
        public void returnTrue() {
            Assertions.assertTrue(true);
        }

        @Затем("нажмем кнопку {string}")
        public void clickButton(String buttonName) {
//            not work!!!
//            WebElement element = this.waitElement(By.linkText(buttonName));
            WebElement element = this.waitElement(this.getElementBy(buttonName));

            System.out.println("buttonName = " + buttonName);
            System.out.println("*".repeat(40));
            System.out.println(element);
            System.out.println("*".repeat(40));
            System.out.println("tagName = " + element.getTagName());
            System.out.println(".isDisplayed = " + element.isDisplayed());

            System.out.println("*".repeat(40));

            element.click();
        }

        @Тогда("перейдем на {string}")
        public void isCurrentPage(String actual) {
            String expected = this.webDriver.getCurrentUrl();
            Assertions.assertEquals(expected, actual);
        }

        private WebElement waitElement(By waitingElement) {
            return new WebDriverWait(webDriver, Duration.ofSeconds(waitTimiOut).toSeconds())
                    .until(driver -> driver.findElement(waitingElement));
        }

        private By getElementBy(String name) {
            if (name.length() > 1) {
                if (name.startsWith("#") && !name.contains(">")) {
                    return By.id(name.substring(1));
                } else if (name.startsWith(".")) {
                    return By.className(name.substring(1));
                } else if (name.startsWith("/")) {
                    return By.xpath(name);
                } else {
                    return By.cssSelector(name);
                }
            }
            return null;
        }
        /*
        selector = "#chat" | "body > footer > div.bot.bg-dark"
        XPath="//*[@id="chat"]" |
        fullXPath = "/html/body/footer/div[3]/div"
         */
    }
}
