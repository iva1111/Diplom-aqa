package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;

public class BuyPage {
    private final SelenideElement buyButton = $$("button").findBy(Condition.text("Купить"));

    public StartPage buy() {
        buyButton.click();
        return new StartPage();
    }
}
