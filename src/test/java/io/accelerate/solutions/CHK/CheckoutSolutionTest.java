package io.accelerate.solutions.CHK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CheckoutSolutionTest {
    private CheckoutSolution checkoutSolution;

    @BeforeEach
    void setUp() {
        checkoutSolution = new CheckoutSolution();
    }

    @Test
    void invalidItem() {
        var item = "E";
        var result = checkoutSolution.checkout(item);
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

    @Tes
    t
    void singleItems() {

    }



}



