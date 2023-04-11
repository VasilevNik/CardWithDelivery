package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.conditions.Text;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardWithDeliveryTest {

    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void checkCorrectDataEntry() {
        open("http://localhost:9999/");
        Configuration.holdBrowserOpen = true;
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        String currentDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue("Русланов Руслан");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + currentDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void checkCorrectDataEntryWithComplexElements() {
        open("http://localhost:9999/");
        Configuration.holdBrowserOpen = true;
        String desiredCity = "Москва";
        $x("//*[contains(@placeholder,'Город')]").setValue(desiredCity);

        String currentDate = generateDate(10, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $x("//*[contains(@placeholder,'Дата встречи')]").setValue(currentDate);

        $("[data-test-id=name] input").setValue("Русланов Руслан");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + currentDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}
