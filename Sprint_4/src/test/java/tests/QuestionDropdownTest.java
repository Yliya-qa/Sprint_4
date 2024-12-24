package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class QuestionDropdownTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Parameterized.Parameter(0)
    public String questionTitle;

    @Parameterized.Parameter(1)
    public String expectedAnswer;

    @Parameterized.Parameter(2)
    public String questionId;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой.", "accordion__heading-0"},
                {"Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.", "accordion__heading-1"},
                {"Как рассчитывается время аренды?", "Допустим, Вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда Вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.", "accordion__heading-2"},
                {"Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее.", "accordion__heading-3"},
                {"Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.", "accordion__heading-4"},
                {"Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к Вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.", "accordion__heading-5"},
                {"Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.", "accordion__heading-6"},
                {"Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области.", "accordion__heading-7"}
        });
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void testDropdown() {
        By questionButton = By.id(questionId);
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(questionButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        System.out.println("Кликаем по вопросу: " + questionTitle);
        button.click();

        By answerPanel = By.xpath("//div[contains(@class, 'accordion__panel') and not(@hidden)]//p");
        String actualText = wait.until(ExpectedConditions.visibilityOfElementLocated(answerPanel)).getText().trim().toLowerCase();
        String expectedAnswerLower = expectedAnswer.toLowerCase();

        assertTrue("Текст не совпадает! Ожидалось: " + expectedAnswer + ", но получено: " + actualText,
                actualText.contains(expectedAnswerLower));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}


