package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы для кнопок и заголовков
    private static final By orderButtonTop = By.className("Button_Button__ra12g");
    private static final By orderButtonBottom = By.xpath("//div[contains(@class, 'Order_Buttons__1xGrp')]//button[contains(@class, 'Button_Button__ra12g') and normalize-space()='Заказать']"); // Новый локатор для кнопки внизу
    private static final By cookieConsentButton = By.id("rcc-confirm-button");

    // Локаторы для первой формы заказа
    private static final By firstNameInput = By.xpath("//input[@placeholder='* Имя']");
    private static final By lastNameInput = By.xpath("//input[@placeholder='* Фамилия']");
    private static final By addressInput = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private static final By phoneInput = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private static final By metroStationInput = By.className("select-search__input");
    private static final By nextButton = By.xpath("//button[contains(text(), 'Далее')]");

    // Локаторы для второй формы заказа
    private static final By rentalDateInput = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private static final By rentalPeriodDropdown = By.className("Dropdown-control");
    private static final By commentInput = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private static final By colorBlackCheckbox = By.xpath("//label[contains(text(), 'чёрный жемчуг')]/input");
    private static final By colorGreyCheckbox = By.xpath("//label[contains(text(), 'серая безысходность')]/input");
    private static final By orderButtonSecondForm = By.xpath("//button[contains(text(), 'Заказать') and contains(@class, 'Button_Button__ra12g')][1]"); // Кнопка вверху

    // Локатор для кнопки "Да"
    private static final By yesButton = By.xpath("//button[contains(@class, 'Button_Button__ra12g') and contains(text(), 'Да')]");

    // Локаторы для сообщения об успешном заказе
    private static final By orderConfirmationModal = By.className("Order_Modal__YZ-d3");
    private static final By successMessage = By.className("Order_Text__2broi");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // Метод для закрытия уведомления о куки
    private void closeCookieConsentIfVisible() {
        try {
            WebElement cookieConsentButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(cookieConsentButton));
            if (cookieConsentButtonElement.isDisplayed()) {
                cookieConsentButtonElement.click(); // Закрываем окно согласия
            }
        } catch (Exception e) {
            // Элемент согласия не найден или не отображается, игнорируем
        }
    }

    public void clickOrderButtonTop() {
        closeCookieConsentIfVisible(); // Закрываем элемент согласия, если он видим
        WebElement orderButton = wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop));
        orderButton.click();
    }

    public void clickOrderButtonBottom() {
        closeCookieConsentIfVisible(); // Закрываем элемент согласия, если он видим
        WebElement orderButton = wait.until(ExpectedConditions.elementToBeClickable(orderButtonBottom));
        orderButton.click();
    }

    public void clickOrderButtonInSecondForm() {
        WebElement orderButtonElement = wait.until(ExpectedConditions.elementToBeClickable(orderButtonSecondForm));
        orderButtonElement.click(); // Кликаем на кнопку "Заказать" вверху
    }

    // Новый метод для клика по кнопке "Заказать" внизу
    public void clickOrderButtonInSecondFormBottom() {
        WebElement orderButtonElement = wait.until(ExpectedConditions.elementToBeClickable(orderButtonBottom));
        orderButtonElement.click(); // Кликаем на кнопку "Заказать" внизу
    }

    // Новый метод для клика по кнопке "Заказать" с использованием JavaScript
    public void clickOrderButtonInSecondFormUsingJS() {
        try {
            WebElement orderButtonElement = wait.until(ExpectedConditions.elementToBeClickable(orderButtonSecondForm));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", orderButtonElement); // Используем JS для клика
            System.out.println("Кнопка 'Заказать' во второй форме успешно нажата с использованием JavaScript.");
        } catch (Exception e) {
            System.out.println("Ошибка при нажатии кнопки 'Заказать' во второй форме: " + e.getMessage());
        }
    }

    // Метод для клика по кнопке "Да"
    public void clickYesButton() {
        WebElement yesButtonElement = wait.until(ExpectedConditions.elementToBeClickable(yesButton));
        yesButtonElement.click(); // Кликаем на кнопку "Да"
    }

    public void fillOrderForm(String name, String lastName, String address, String phone) {
        fillInputField(firstNameInput, name);
        fillInputField(lastNameInput, lastName);
        fillInputField(addressInput, address);
        fillInputField(phoneInput, phone);
    }

    private void fillInputField(By locator, String value) {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        inputField.clear(); // Очищаем поле перед вводом
        inputField.sendKeys(value);
    }

    public void selectMetroStation(String stationName) {
        WebElement metroStationInputElement = wait.until(ExpectedConditions.elementToBeClickable(metroStationInput));
        metroStationInputElement.click();

        // Вводим название станции
        metroStationInputElement.sendKeys(stationName);

        // Ждем, пока появится список станций
        WebElement dropdownList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("select-search__options")));
        List<WebElement> stationOptions = dropdownList.findElements(By.tagName("li"));

        // Ищем нужную станцию среди опций
        for (WebElement option : stationOptions) {
            if (option.getText().equalsIgnoreCase(stationName)) {
                option.click(); // Выбираем нужную станцию
                break;
            }
        }
    }

    public void clickNextButton() {
        WebElement nextButtonElement = wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        nextButtonElement.click();
    }

    public void selectFutureDate() {
        // Открываем календарь
        WebElement calendarInput = wait.until(ExpectedConditions.elementToBeClickable(rentalDateInput));
        calendarInput.click();

        // Ждем, пока календарь станет видимым
        WebElement calendar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("react-datepicker__month")));

        // Находим нужную дату в календаре
        List<WebElement> days = calendar.findElements(By.className("react-datepicker__day"));
        for (WebElement day : days) {
            if (day.getText().equals("18")) { // Замените на нужный день
                day.click(); // Кликаем на нужный день
                break;
            }
        }
    }

    public void selectRentalPeriod(String period) {
        WebElement dropdownControl = wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodDropdown));
        dropdownControl.click(); // Открываем выпадающий список

        // Ждем и выбираем элемент из выпадающего списка
        List<WebElement> dropdownItems = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class, 'Dropdown-option')]")));
        for (WebElement item : dropdownItems) {
            if (item.getText().equals(period)) {
                item.click(); // Кликаем на нужный срок аренды
                break;
            }
        }
    }

    public void fillComment(String comment) {
        fillInputField(commentInput, comment);
    }

    public void selectScooterColor(String color) {
        WebElement colorCheckbox;
        if (color.equals("чёрный жемчуг")) {
            colorCheckbox = wait.until(ExpectedConditions.elementToBeClickable(colorBlackCheckbox));
        } else if (color.equals("серая безысходность")) {
            colorCheckbox = wait.until(ExpectedConditions.elementToBeClickable(colorGreyCheckbox));
        } else {
            throw new IllegalArgumentException("Неизвестный цвет: " + color);
        }

        if (!colorCheckbox.isSelected()) {
            colorCheckbox.click(); // Кликаем на чекбокс
        }
    }

    public void confirmOrder() {
        try {
            // Увеличиваем время ожидания до 30 секунд
            WebElement modal = wait.withTimeout(Duration.ofSeconds(30))
                    .until(ExpectedConditions.visibilityOfElementLocated(orderConfirmationModal));
            if (modal.isDisplayed()) {
                System.out.println("Модальное окно подтверждения отображается.");
            }
        } catch (Exception e) {
            System.out.println("Модальное окно подтверждения не отображается: " + e.getMessage());
        }
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            String messageText = messageElement.getText();
            return messageElement.isDisplayed() && messageText.contains("Номер заказа:");
        } catch (Exception e) {
            return false;
        }
    }

    // Методы доступа для кнопок
    public static By getOrderButtonTop() {
        return orderButtonTop;
    }

    public static By getOrderButtonBottom() {
        return orderButtonBottom;
    }
}