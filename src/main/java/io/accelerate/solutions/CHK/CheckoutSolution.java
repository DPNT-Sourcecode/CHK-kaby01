package io.accelerate.solutions.CHK;

import java.util.HashMap;
import java.util.Map;

public class CheckoutSolution {
    record SpecialOffer(int quantity, int price){};
    private static final Map<Character, Integer> PRICES = Map.of('A', 50, 'B', 30, 'C', 20, 'D', 15);
    private static final Map<Character, SpecialOffer> SPECIAL_OFFERS = Map.of(
            'A', new SpecialOffer(3, 150),
            'B', new SpecialOffer(2, 45)
    );


    public Integer checkout(String skus) {
        if (skus == null) {
            return -1;
        }
        if (skus.isEmpty()) {
            return 0;
        }
        Map<Character, Integer> itemCounts = new HashMap<>();
        for (var item : skus.toCharArray()) {
            if (!PRICES.containsKey(item)) {
                return -1;
            }
            itemCounts.put(item, itemCounts.getOrDefault(item, 0) + 1);
        }
        var totalPrice = 0;
        for (var entry : itemCounts.entrySet())
            if (SPECIAL_OFFERS.containsKey(entry.getKey())) {
                var numberOfItemsEligibleForOffer = entry.getValue() / SPECIAL_OFFERS.get(entry.getKey()).quantity;
                var numberOfItemsNotEligibleForOffer = entry.getValue() % 3;
                totalPrice += (numberOfItemsEligibleForOffer * SPECIAL_OFFERS.get(entry.getKey()).price) + numberOfItemsNotEligibleForOffer * PRICES.get(entry.getKey());
            } else {
                totalPrice += PRICES.get(entry.getKey()) * entry.getValue();
            }

        return totalPrice;
    }
}
