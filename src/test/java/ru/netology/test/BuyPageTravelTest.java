package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.Card;
import ru.netology.data.DataGeneration;
import ru.netology.data.DbUtils;
import ru.netology.page.BuyPage;
import ru.netology.page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyPageTravelTest {
    StartPage startPage = new StartPage();
    BuyPage buyPage = new BuyPage();

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
        startPage.buy();
    }

    // Переключение между вкладками Купить и Купить в кредит
    @Test
    void shouldCheckTransitionToCard() {
        var startPage = new StartPage();
        var creditPage = startPage.buyCredit();
        var buyPage = creditPage.transitionToCard();
        buyPage.transitionToCredit();
    }

    // успешная покупка одобренной картой
    @Test
    public void shouldBuySuccessApprovedCard() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getValidName(), DataGeneration.getValidCvc());
        buyPage.inputData(card);
        buyPage.checkSuccessNotification();
        assertEquals("APPROVED", DbUtils.getOrderStatus());
    }

    // не успешная покупка отклонненной картой
    @Test
    public void shouldNonBuySuccessDeclinedCard() {
        Card card = new Card(DataGeneration.getDeclinedNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getValidName(), DataGeneration.getValidCvc());
        buyPage.inputData(card);
        buyPage.checkDeclineNotification();
        assertEquals("DECLINED", DbUtils.getOrderStatus());
    }

    // Ввести номер несуществующей карты (короткий 4444)
    @Test
    void shouldMessageErrorShortCardNumber() {
        Card card = new Card(DataGeneration.getShortCardNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getValidName(), DataGeneration.getValidCvc());
        buyPage.inputData(card);
        buyPage.checkInvalidFormat();
    }

    // Ввести номер несуществующей карты (все 0)
    @Test
    void shouldMessageErrorNonCardNumber() {
        Card card = new Card(DataGeneration.getErrorCardNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getValidName(), DataGeneration.getValidCvc());
        buyPage.inputData(card);
        buyPage.checkDeclineNotification();
    }


    // Ввести несуществующий номер месяца (13)
    @Test
    void shouldMessageErrorMonth() {
        Card card = new Card(DataGeneration.getApprovedNumber(), "13", DataGeneration.getYear(), DataGeneration.getValidName(), DataGeneration.getValidCvc());
        buyPage.inputData(card);
        buyPage.checkInvalidDate();
    }

    // Ввести несуществующий номер месяца (00)
    @Test
    void shouldMessageNonMonth() {
        Card card = new Card(DataGeneration.getApprovedNumber(), "00", DataGeneration.getYear(), DataGeneration.getValidName(), DataGeneration.getValidCvc());
        buyPage.inputData(card);
        buyPage.checkInvalidDate();
    }

    // Ввести прошедший год (19)
    @Test
    void shouldMessagelastYear() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getLastYear(), DataGeneration.getValidName(), DataGeneration.getValidCvc());
        buyPage.inputData(card);
        buyPage.checkExpiredDate();
    }

    // Ввести далекий год (99)
    @Test
    void shouldMessageDistantYear() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getDistantYear(), DataGeneration.getValidName(), DataGeneration.getValidCvc());
        buyPage.inputData(card);
        buyPage.checkExpiredDate();
    }

    // Ввести в поле Владелец цифры
    @Test
    void shouldMessageErrorOwnerNumbers() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getNumbers(), DataGeneration.getValidCvc());
        buyPage.inputData(card);
        buyPage.checkDeclineNotification();
    }

    // Ввести в поле владелец данные на кириллице
    @Test
    void shouldMessageErrorOwnerCyrillic() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getOwnerCyrillic(), DataGeneration.getValidCvc());
        buyPage.inputData(card);
        buyPage.checkDeclineNotification();
    }

    // Ввести в поле владелец только фамилию
    @Test
    void shouldMessageErrorOwnerOnlySurname() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getOnlySurname(), DataGeneration.getValidCvc());
        buyPage.inputData(card);
        buyPage.checkDeclineNotification();
    }

    //  Ввести в поле владелец одину букву
    @Test
    void shouldMessageErrorOwnerOneLetter() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getNameOneLetter(), DataGeneration.getValidCvc());
        buyPage.inputData(card);
        buyPage.checkDeclineNotification();
    }

    // Ввести в поле владелец много букв
    @Test
    void shouldMessageErrorOwnerManyLetter() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getManyLetter(), DataGeneration.getValidCvc());
        buyPage.inputData(card);
        buyPage.checkDeclineNotification();
    }

    // Ввести не корректые данные CVC (000)
    @Test
    void shouldMessageErrorCvc() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getValidName(), DataGeneration.getErrorCvc());
        buyPage.inputData(card);
        buyPage.checkDeclineNotification();
    }

    // Ввести не корректые данные CVC (22)
    @Test
    void shouldMessageNonCVC() {
        Card card = new Card(DataGeneration.getApprovedNumber(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getValidName(), DataGeneration.getNonCvc());
        buyPage.inputData(card);
        buyPage.checkInvalidFormat();
    }

    // Ввести в поле Номер карты буквы
    @Test
    void shouldMessageErrorCardLetters() {
        Card card = new Card(DataGeneration.getCardLetters(), DataGeneration.getMonth(), DataGeneration.getYear(), DataGeneration.getValidName(), DataGeneration.getValidCvc());
        buyPage.inputData(card);
        buyPage.checkInvalidFormat();
    }

    // Направление пустой формы заявки
    @Test
    void shouldMessageErrorEmptyAapplicationForm() {
        Card card = new Card(null, null, null, null, null);
        buyPage.inputData(card);
        buyPage.checkAllFieldsAreRequired();
    }
}
