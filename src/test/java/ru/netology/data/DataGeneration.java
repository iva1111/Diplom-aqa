package ru.netology.data;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.time.LocalDate;
import java.time.Year;
import java.util.Locale;

public class DataGeneration {
    public static Faker faker = new Faker(new Locale("en"));

    public static String getApprovedNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getErrorCardNumber() {
        return "0000 0000 0000 0000";
    }

    public static String getShortCardNumber() {
        return "4444";
    }

    public static String getCardLetters() {
        return "юююююю";
    }


    public static String getMonth() {
        LocalDate localDate = LocalDate.now();
        return String.format("%02d", localDate.getMonthValue());
    }

    public static String getYear() {
        return String.format("%ty", Year.now());
    }

    public static String getLastYear() {
        LocalDate localDate = LocalDate.now();
        return String.format("%ty", localDate.minusYears(2));
    }

    public static String getDistantYear() {
        LocalDate localDate = LocalDate.now();
        return String.format("%ty", localDate.plusYears(78));
    }

    public static String getValidName() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String getOnlySurname() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().lastName();
    }

    public static String getOwnerCyrillic() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String getNumbers() {
        return faker.number().digits(10);
    }

    public static String getManyLetter() {
        return faker.lorem().fixedString(100);
    }

    public static String getNameOneLetter() {
        return faker.lorem().characters(1);
    }

    public static String getErrorCvc() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en"), new RandomService());
        return fakeValuesService.numerify("000");
    }

    public static String getNonCvc() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en"), new RandomService());
        return fakeValuesService.numerify("##");
    }

    public static String getValidCvc() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en"), new RandomService());
        return fakeValuesService.numerify("###");
    }

}
