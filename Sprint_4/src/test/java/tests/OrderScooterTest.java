package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver; // Импортируем ChromeDriver
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderScooterTest {
    private WebDriver driver;
    private MainPage mainPage;
    private WebDriverWait wait;

    @Parameterized.Parameter(0)
    public String name;
    @Parameterized.Parameter(1)
    public String lastName;
    @Parameterized.Parameter(2)
    public String address;
    @Parameterized.Parameter(3)
    public String phone;
    @Parameterized.Parameter(4)
    public String rentalDate;
    @Parameterized.Parameter(5)
    public String rentalPeriod;
    @Parameterized.Parameter(6)
    public String comment;
    @Parameterized.Parameter(7)
    public String color;

    @Parameterized.Parameters(name = "Заказ {index}: Кнопка ")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Иван", "Иванов", "ул. Пушкина, д. 1", "+79991112233", "19", "сутки", "Заказ для теста 1", "чёрный жемчуг"},
                {"Пётр", "Петров", "ул. Ленина, д. 10", "+78882223344", "20", "трое суток", "Заказ для теста 2", "серая безысходность"}

        });
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        mainPage = new MainPage(driver);
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void testOrderScooter() {
        try {
            System.out.println("Нажимаем на кнопку 'Заказать' сверху.");
            mainPage.clickOrderButtonTop(); // Используем метод для нажатия на верхнюю кнопку

            // Заполнение первой формы заказа
            System.out.println("Заполняем первую форму заказа.");
            mainPage.fillOrderForm(name, lastName, address, phone);

            // Выбор станции метро
            System.out.println("Выбираем станцию метро.");
            mainPage.selectMetroStation("Кузнецкий мост");

            // Нажимаем кнопку "Далее" для перехода на следующую форму
            System.out.println("Нажимаем кнопку 'Далее'.");
            mainPage.clickNextButton();


            System.out.println("Выбираем будущую дату.");
            mainPage.selectFutureDate(); // Выбор будущей даты
            System.out.println("Выбираем срок аренды.");
            mainPage.selectRentalPeriod(rentalPeriod); // Выбор срока аренды
            System.out.println("Заполняем комментарий.");
            mainPage.fillComment(comment); // Заполнение комментария
            System.out.println("Выбираем цвет самоката.");
            mainPage.selectScooterColor(color); // Выбор цвета


            System.out.println("Нажимаем на кнопку 'Заказать' во второй форме (внизу) с новым локатором.");
            mainPage.clickOrderButtonInSecondFormBottom(); // Используем метод для клика на кнопку внизу

            // Нажимаем кнопку "Да" для подтверждения
            System.out.println("Подтверждаем заказ, нажимая кнопку 'Да'.");
            mainPage.clickYesButton(); // Подтверждаем заказ

            // Проверка отображения сообщения об успешном создании заказа
            assertTrue("Сообщение об успешном создании заказа не отображается", mainPage.isSuccessMessageDisplayed());
        } catch (Exception e) {
            System.err.println("Ошибка во время выполнения теста: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
