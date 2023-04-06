package ru.netology;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

public class CardWithDeliveryTest {

    @Test
    void test1() {
        open("http://localhost:9999/");
    }
}
