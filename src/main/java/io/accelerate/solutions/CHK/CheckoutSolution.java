package io.accelerate.solutions.CHK;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CheckoutSolution {
    record SpecialOffer(int quantity, int price){};
    private static final Map<Character, Integer> PRICES = Map.of('A', 50, 'B', 30, 'C', 20, 'D', 15);
    private static final Map<Character, List<SpecialOffer>> SPECIAL_OFFERS = Map.of(
            'A', List.of(
                    new SpecialOffer(3, 130),
                    new SpecialOffer(5, 200)
                ),
            'B', List.of(new SpecialOffer(2, 45))
    );

//    new offer 3A for 130, 5A for 200
//
//            2E get 1 B free. Make sure customer laways gets the lower price
    public Integer checkout(String skus) {
        if (skus == null) {
            return -1;
        }
        if (skus.isEmpty()) {
            return 0;
        }
        var itemCounts = countItems(skus);

        if (itemCounts.isEmpty()) {
            return -1;
        }
        return calculateTotalPrice(itemCounts.get());
    }

    private Optional<Map<Character, Integer>> countItems(final String skus) {
        Map<Character, Integer> itemCounts = new HashMap<>();
        for (var item : skus.toCharArray()) {
            if (!PRICES.containsKey(item)) {
                return Optional.empty();
            }
            itemCounts.put(item, itemCounts.getOrDefault(item, 0) + 1);
        }
        return Optional.ofNullable(itemCounts);
    }

    private int calculateTotalPrice(final Map<Character, Integer> itemCounts) {
        // calculate free items
        int freeItemsB = 0;
        if (itemCounts.containsKey('E')) {
            freeItemsB = itemCounts.get('E') / 2;
        }

        int totalPrice = 0;
        for (var entry : itemCounts.entrySet()) {
            if (SPECIAL_OFFERS.containsKey(entry.getKey())) {
                var setOfItemsEligibleForOffer = entry.getValue() / SPECIAL_OFFERS.get(entry.getKey()).quantity;
                var numberOfItemsNotEligibleForOffer = entry.getValue() % SPECIAL_OFFERS.get(entry.getKey()).quantity;
                totalPrice += (setOfItemsEligibleForOffer * SPECIAL_OFFERS.get(entry.getKey()).price)
                        + numberOfItemsNotEligibleForOffer * PRICES.get(entry.getKey());
            } else {
                totalPrice += PRICES.get(entry.getKey()) * entry.getValue();
            }
        }
        return totalPrice;
    }

}
