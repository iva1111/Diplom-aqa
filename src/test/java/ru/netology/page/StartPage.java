package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;

public class StartPage {
    private final SelenideElement buyButton = $$("button").findBy(Condition.text("Купить"));
    private final SelenideElement buttonBuyByCredit = $$("button").findBy(Condition.text("Купить в кредит"));


    public BuyPage buy() {
        buyButton.click();
        return new BuyPage();
    }

    public CreditPage buyCredit() {
        buttonBuyByCredit.click();
        return new CreditPage();
    }
}
