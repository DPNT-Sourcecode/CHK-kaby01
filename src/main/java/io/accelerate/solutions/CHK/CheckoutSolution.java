package io.accelerate.solutions.CHK;

import java.util.HashMap;
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
    private static Map<Character, Integer> prices = Map.of('A', 50, 'B', 30, 'C', 20, 'D', 15);
    public Integer checkout(String skus) {
        if (skus == null || skus.isBlank()) {
            return -1;
        }

        Map<Character, Integer> itemCounts = new HashMap<>();
        for (var item : skus.toCharArray()) {
            if (!prices.containsKey(item)) {
                return -1;
            }
            itemCounts.put(item, itemCounts.getOrDefault(item, 0) + 1);
        }
        // speical offer A 3 for 130, special offer B 2 for 45
        var totalPrice = 0;
        for (var entry : itemCounts.entrySet())
            if (entry.getKey().equals('A')) {
                var numberOfItemsEligibleForOffer = entry.getValue() / 3;
                var numberOfItemsNotEligibleForOffer = entry.getValue() % 3;
                totalPrice += (numberOfItemsEligibleForOffer * 130) + numberOfItemsNotEligibleForOffer * prices.get('A');
            } else if (entry.getKey().equals('B')) {
                var numberOfItemsEligibleForOffer = entry.getValue() / 2;
                var numberOfItemsNotEligibleForOffer = entry.getValue() % 2;
                totalPrice += (numberOfItemsEligibleForOffer * 45) + (numberOfItemsNotEligibleForOffer * prices.get('B'));
            } else {
                totalPrice += prices.get(entry.getKey()) * entry.getValue();
            }

        return totalPrice;
    }
}


