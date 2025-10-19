package io.accelerate.solutions.CHK;

import io.accelerate.runner.SolutionNotImplementedException;

import java.util.Map;

public class CheckoutSolution {
//    Our price table and offers:
//            +------+-------+----------------+
//            | Item | Price | Special offers |
//            +------+-------+----------------+
//            | A    | 50    | 3A for 130     |
//            | B    | 30    | 2B for 45      |
//            | C    | 20    |                |
//            | D    | 15    |                |
//            +------+-------+----------------+
    Map<Character, Integer> prices = Map.of()
        
    public Integer checkout(String skus) {
        if (skus == null || skus.isBlank()) {
            return -1;
        }
        throw new SolutionNotImplementedException();
    }
}


