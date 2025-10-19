package io.accelerate.solutions.CHK;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CheckoutSolution {
    record SpecialOffer(int quantity, int price){};
    private static final Map<Character, Integer> PRICES = Map.of(
            'A', 50,
            'B', 30,
            'C', 20,
            'D', 15,
            'E', 40
    );
    private static final Map<Character, List<SpecialOffer>> SPECIAL_OFFERS = Map.of(
            'A', List.of(
                    new SpecialOffer(3, 130),
                    new SpecialOffer(5, 200)
                ),
            'B', List.of(new SpecialOffer(2, 45))
    );

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

        int total = 0;
        for (var entry : itemCounts.entrySet()) {
            char item = entry.getKey();
            int count = entry.getValue();

            if (item == 'B') {
                int actualBs = Math.max(0, count - freeItemsB);
                total += calculateItemPrice(item, actualBs);
            } else {
                total += calculateItemPrice(item, count);
            }
        }
        return total;
    }


    private int calculateItemPrice(int item, int count) {
        boolean hasMultiOffer = SPECIAL_OFFERS.get(item) != null && SPECIAL_OFFERS.get(item).size() > 1;
        if (hasMultiOffer) {
            int lowestPrice = Integer.MAX_VALUE;
            for (var specialOffer : SPECIAL_OFFERS.get(item)) {
                lowestPrice = Math.min(lowestPrice, findItemsCost(specialOffer.quantity, specialOffer.price, count, PRICES.get(item)));
            }
            return lowestPrice;
        }

        var offers = SPECIAL_OFFERS.get(item);
        if (offers != null && offers.size() >= 0) {
            var singleOffer = offers.get(0);
            var offerSets = count / singleOffer.quantity;
            var remainder = count % singleOffer.quantity;
            return (offerSets * singleOffer.price) + (remainder * PRICES.get(item));
        }
        return count * PRICES.get(item);


    }

    private int findItemsCost(int setOfItemsPerOffer, int offerPrice, int numberOfItems, int itemPrice) {
        var setOfItemsEligibleForOffer = numberOfItems / setOfItemsPerOffer;
        var itemsNotEligibleForOffer = numberOfItems % setOfItemsPerOffer;
        return (setOfItemsEligibleForOffer * offerPrice) + (itemsNotEligibleForOffer * itemPrice);
    }

}



