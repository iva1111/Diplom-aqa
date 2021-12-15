package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataGeneration;
import ru.netology.data.DbUtils;
import ru.netology.page.BuyPage;
import ru.netology.data.Card;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TravelTest {
    StartPage buyPage = new StartPage();
    BuyPage startPage = new BuyPage();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        DbUtils.clearTables();
        open("http://localhost:8080");
        buyPage.buy();
    }

    // успешная покупка одобренной картой
    @Test
    public void shouldBuySuccessApprovedCard() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getValidName(), DataGeneration.getValidCvc());
        startPage.inputData(card);
        startPage.checkSuccessNotification();
        assertEquals("APPROVED", DbUtils.getPaymentStatus());
    }

    // не успешная покупка отклонненной картой
    @Test
    public void shouldNonBuySuccessDeclinedCard() {
        Card card = new Card(DataGeneration.getDeclinedNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getValidName(), DataGeneration.getValidCvc());
        startPage.inputData(card);
        startPage.checkDeclineNotification();
        assertEquals("DECLINED", DbUtils.getPaymentStatus());
    }

    // не успешная покупка одобренной картой
    @Test
    public void shouldNonBuySuccessApprovedCard() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getValidName(), DataGeneration.getValidCvc());
        startPage.inputData(card);
        startPage.checkSuccessNotification();
        assertEquals("APPROVED", DbUtils.getPaymentStatus());
    }


    // Ввести номер несуществующей карты (короткий 4444)
    @Test
    void shouldMessageErrorShortCardNumber() {
        Card card = new Card(DataGeneration.getShortCardNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getValidName(), DataGeneration.getValidCvc());
        startPage.inputData(card);
        startPage.checkInvalidFormat();
    }

    // Ввести номер несуществующей карты (все 0)
    @Test
    void shouldMessageErrorNonCardNumber() {
        Card card = new Card(DataGeneration.getErrorCardNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getValidName(), DataGeneration.getValidCvc());
        startPage.inputData(card);
        startPage.checkDeclineNotification();
    }


    // Ввести несуществующий номер месяца (13)
    @Test
    void shouldMessageErrorMonth() {
        Card card = new Card(DataGeneration.getApprovedNumber(), "13", DataGeneration.getYear(), DataGeneration.getValidName(), DataGeneration.getValidCvc());
        startPage.inputData(card);
        startPage.checkInvalidDate();
    }

    // Ввести несуществующий номер месяца (00)
    @Test
    void shouldMessageNonMonth() {
        Card card = new Card(DataGeneration.getApprovedNumber(), "00", DataGeneration.getYear(), DataGeneration.getValidName(), DataGeneration.getValidCvc());
        startPage.inputData(card);
        startPage.checkInvalidDate();
    }

    // Ввести прошедший год (19)
    @Test
    void shouldMessagelastYear() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getLastYear(), DataGeneration.getValidName(), DataGeneration.getValidCvc());
        startPage.inputData(card);
        startPage.checkExpiredDate();
    }

    // Ввести далекий год (99)
    @Test
    void shouldMessageDistantYear() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getDistantYear(), DataGeneration.getValidName(), DataGeneration.getValidCvc());
        startPage.inputData(card);
        startPage.checkExpiredDate();
    }

    // Ввести в поле Владелец цифры
    @Test
    void shouldMessageErrorOwnerNumbers() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getNumbers(), DataGeneration.getValidCvc());
        startPage.inputData(card);
        startPage.checkDeclineNotification();
    }

    // Ввести в поле владелец данные на кириллице
    @Test
    void shouldMessageErrorOwnerCyrillic() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getOwnerCyrillic(), DataGeneration.getValidCvc());
        startPage.inputData(card);
        startPage.checkDeclineNotification();
    }

    // Ввести в поле владелец только фамилию
    @Test
    void shouldMessageErrorOwnerOnlySurname() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getOnlySurname(), DataGeneration.getValidCvc());
        startPage.inputData(card);
        startPage.checkDeclineNotification();
    }

    //  Ввести в поле владелец одину букву
    @Test
    void shouldMessageErrorOwnerOneLetter() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getNameOneLetter(), DataGeneration.getValidCvc());
        startPage.inputData(card);
        startPage.checkDeclineNotification();
    }

    // Ввести в поле владелец много букв
    @Test
    void shouldMessageErrorOwnerManyLetter() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getManyLetter(), DataGeneration.getValidCvc());
        startPage.inputData(card);
        startPage.checkDeclineNotification();
    }

    // Ввести не корректые данные CVC (000)
    @Test
    void shouldMessageErrorCvc() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getValidName(), DataGeneration.getErrorCvc());
        startPage.inputData(card);
        startPage.checkDeclineNotification();
    }

    // Ввести не корректые данные CVC (22)
    @Test
    void shouldMessageNonCVC() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getValidName(), DataGeneration.getNonCvc());
        startPage.inputData(card);
        startPage.checkInvalidFormat();
    }

    // Ввести в поле Номер карты буквы
    @Test
    void shouldMessageErrorCardLetters() {
        Card card = new Card(DataGeneration.getCardLetters(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getValidName(), DataGeneration.getValidCvc());
        startPage.inputData(card);
        startPage.checkInvalidFormat();
    }

    // Направление пустой формы заявки
    @Test
    void shouldMessageErrorEmptyAapplicationForm() {
        Card card = new Card(null, null, null, null, null);
        startPage.inputData(card);
        startPage.checkAllFieldsAreRequired();
    }
}
