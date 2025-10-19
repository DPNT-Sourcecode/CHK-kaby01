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
//    Our price table and offers:
//            +------+-------+----------------+
//            | Item | Price | Special offers |
//            +------+-------+----------------+
//            | A    | 50    | 3A for 130     |
//            | B    | 30    | 2B for 45      |
//            | C    | 20    |                |
//            | D    | 15    |                |
//            +------+-------+----------------+

    @Test
    void validItem() {
        var items = "ABCD";
        var result = checkoutSolution.checkout(items);
        assertThat(result, equalTo(115));

    }

}

