package io.accelerate.solutions.CHK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CheckoutSolutionTest {
    private CheckoutSolution checkoutSolution;

    @BeforeEach
    void setUp() {
        checkoutSolution = new CheckoutSolution();
    }

    @ParameterizedTest
    @CsvSource({"X", "a", "1", "ABCX", "ABCa"})
    void invalidItem(String invalidItem) {
        var result = checkoutSolution.checkout(invalidItem);
        assertThat(result, equalTo(-1));
    }

    @Test
    void validItem() {
        var items = "ABCD";
        var result = checkoutSolution.checkout(items);
        assertThat(result, equalTo(115));

    }

    @Test
    void emptyBasket() {
        var result = checkoutSolution.checkout(null);
        assertThat(result, equalTo(-1));
    }

    @Test
    void blankString() {
        var result = checkoutSolution.checkout(" ");
        assertThat(result, equalTo(-1));
    }

    @Test
    void emptyString() {
        var result = checkoutSolution.checkout("");
        assertThat(result, equalTo(0));
    }


    @ParameterizedTest
    @CsvSource({
            "A, 50", "B, 30", "C, 20", "D, 15", "E, 40", "F, 10",
            "G, 20", "H, 10", "I, 35", "J, 60", "K, 80", "L, 90",
            "M, 15", "N, 40", "O, 10", "P, 50", "Q, 30", "R, 50",
            "S, 30", "T, 20", "U, 40", "V, 50", "W, 20", "X, 90",
            "Y, 10", "Z, 50"
    })
    void singleItems(String item, Integer expectedPrice) {
        var result = checkoutSolution.checkout(item);
        assertThat(result, equalTo(expectedPrice));
    }

    @ParameterizedTest
    @CsvSource({
            "AB, 80",
            "ABCD, 115",
            "CDBA, 115",
            "AAAB, 160",
            "AAABBD, 190",
            "DABABA, 190",
            "EEB, 80", // 2E = 80, 1B free
            "EEEEBB, 160", // 4E = 160, 2B (2 free)
            "ABCDEFABCDEF, 300" // A(2) = 100, B (2, but 1 free) = 30, C(2) = 40, D(2) = 30, E(2) = 80, F (2) = 20
    })
    void mixedItems(String items, Integer expectedPrice) {
        var result = checkoutSolution.checkout(items);
        assertThat(result, equalTo(expectedPrice));
    }

    @ParameterizedTest
    @CsvSource({
            "AAA, 130",
            "AAAA, 180",
            "AAAAA, 200",
            "AAAAAA, 250"
    })
    void itemAWithSpecialOffers(String items, Integer expectedPrice) {
        var result = checkoutSolution.checkout(items);
        assertThat(result, equalTo(expectedPrice));
    }

    @ParameterizedTest
    @CsvSource({
            "BB, 45",
            "BBB, 75",
            "BBBB, 90"
    })
    void itemBWithSpecialOffer(String items, int expectedPrice) {
        var result = checkoutSolution.checkout(items);
        assertThat(result, equalTo(expectedPrice));
    }

    @ParameterizedTest
    @CsvSource({
            "EE, 80",
            "EEB, 80",
            "EEBB, 110",
            "EEEB, 120",
            "EEEEBB, 160",
            "EEEEBBB, 190"
    })
    void itemEWith2Get1BFree(String items, int expectedPrice) {
        var result = checkoutSolution.checkout(items);
        assertThat(result, equalTo(expectedPrice));
    }

    @ParameterizedTest
    @CsvSource({
            "F, 10",
            "FF, 20",
            "FFF, 20",
            "FFFF, 30",
            "FFFFF, 40",
            "FFFFFF, 40"
    })
    void itemFWith2FGet1FFree(String items, int expectedPrice) {
        var result = checkoutSolution.checkout(items);
        assertThat(result, equalTo(expectedPrice));
    }

}

