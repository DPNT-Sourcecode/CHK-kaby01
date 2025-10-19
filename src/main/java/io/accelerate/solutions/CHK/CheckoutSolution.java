package io.accelerate.solutions.CHK;

import java.util.*;


//Our price table and offers:
//        +------+-------+---------------------------------+
//        | Item | Price | Special offers                  |
//        +------+-------+---------------------------------+
//        | A    | 50    | 3A for 130, 5A for 200          |
//        | B    | 30    | 2B for 45                       |
//        | C    | 20    |                                 |
//        | D    | 15    |                                 |
//        | E    | 40    | 2E get one B free               |
//        | F    | 10    | 2F get one F free               |
//        | G    | 20    |                                 |
//        | H    | 10    | 5H for 45, 10H for 80           |
//        | I    | 35    |                                 |
//        | J    | 60    |                                 |
//        | K    | 70    | 2K for 120                      |
//        | L    | 90    |                                 |
//        | M    | 15    |                                 |
//        | N    | 40    | 3N get one M free               |
//        | O    | 10    |                                 |
//        | P    | 50    | 5P for 200                      |
//        | Q    | 30    | 3Q for 80                       |
//        | R    | 50    | 3R get one Q free               |
//        | S    | 20    | buy any 3 of (S,T,X,Y,Z) for 45 |
//        | T    | 20    | buy any 3 of (S,T,X,Y,Z) for 45 |
//        | U    | 40    | 3U get one U free               |
//        | V    | 50    | 2V for 90, 3V for 130           |
//        | W    | 20    |                                 |
//        | X    | 17    | buy any 3 of (S,T,X,Y,Z) for 45 |
//        | Y    | 20    | buy any 3 of (S,T,X,Y,Z) for 45 |
//        | Z    | 21    | buy any 3 of (S,T,X,Y,Z) for 45 |
//        +------+-------+---------------------------------+


public class CheckoutSolution {
    record SpecialOffer(int quantity, int price){}
    record FreeItemOffer(char triggerItem, int triggerQuantity, char freeItem){}
    record GroupOffer(Set<Character> items, int quantity, int price){}
    private static final Map<Character, Integer> PRICES = Map.ofEntries(
            Map.entry('A', 50), Map.entry('B', 30), Map.entry('C', 20),
            Map.entry('D', 15), Map.entry('E', 40), Map.entry('F', 10),
            Map.entry('G', 20), Map.entry('H', 10), Map.entry('I', 35),
            Map.entry('J', 60), Map.entry('K', 70), Map.entry('L', 90),
            Map.entry('M', 15), Map.entry('N', 40), Map.entry('O', 10),
            Map.entry('P', 50), Map.entry('Q', 30), Map.entry('R', 50),
            Map.entry('S', 20), Map.entry('T', 20), Map.entry('U', 40),
            Map.entry('V', 50), Map.entry('W', 20), Map.entry('X', 70),
            Map.entry('Y', 20), Map.entry('Z', 21)
    );
    private static final Map<Character, List<SpecialOffer>> SPECIAL_OFFERS = Map.of(
            'A', List.of(new SpecialOffer(3, 130), new SpecialOffer(5, 200)),
            'B', List.of(new SpecialOffer(2, 45)),
            'H', List.of(new SpecialOffer(10, 80), new SpecialOffer(5, 45)),
            'K', List.of(new SpecialOffer(2, 120)),
            'P', List.of(new SpecialOffer(5, 200)),
            'Q', List.of(new SpecialOffer(3, 80)),
            'V', List.of(new SpecialOffer(3, 130), new SpecialOffer(2, 90))
    );
    private static final List<FreeItemOffer> FREE_ITEM_OFFERS = List.of(
            new FreeItemOffer('E', 2, 'B'), // 2E get 1B Free
            new FreeItemOffer('F', 3, 'F'), // 2F get 1F free same as buy 3 and 1 is freenew FreeItemOffer('N', 3, 'F'),
            new FreeItemOffer('N', 3, 'M'), // 3N get 1M free
            new FreeItemOffer('R', 3, 'Q'), // 3R get 1Q free
            new FreeItemOffer('U', 4, 'U')  // 3U get 1U free
    );
    private static final List<GroupOffer> GROUP_OFFERS = List.of(
            new GroupOffer(Set.of('S', 'T', 'X', 'Y', 'Z'), 3, 45)
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
        var freeItems = calculateFreeItems(itemCounts);

        // calculate total items for items within the group offer.
        // remove the items from the map then calculate the rest.


        int total = 0;
        for (var entry : itemCounts.entrySet()) {
            char item = entry.getKey();
            int count = entry.getValue();

            int numberOfFreeItems = freeItems.getOrDefault(item, 0);
            int actualCount = Math.max(0, count - numberOfFreeItems);
            total += calculateItemPrice(item, actualCount);
        }
        return total;
    }

    private int applyGroupOffers(Map<Character, Integer> items) {
        // get a list of all items in the group offer.
        // get the number of actual items that can be used for groupOffer
        // remove the itmes that was used for group offer from the map
        // also return the cost
        int total = 0;
        var itemsEligibleForGroupOffer = new ArrayList<Character>();
        for (var groupOffer : GROUP_OFFERS) {
            for (char item : groupOffer.items) {
                if (items.containsKey(item)) {
                    for (int idx = 0; idx < items.get(item); idx++) {
                        itemsEligibleForGroupOffer.add(item);
                    }
                }
            }

            itemsEligibleForGroupOffer.sort((itemA, itemB) -> PRICES.get(itemB).compareTo(PRICES.get(itemA)));
            int numberOfItemsToApplyOfferTo = itemsEligibleForGroupOffer.size() / groupOffer.quantity;
            for (int idx = 0; idx < numberOfItemsToApplyOfferTo * groupOffer.quantity; idx++) {
                var itemToRemove = itemsEligibleForGroupOffer.get(idx);
                items.put(item, items.get(items) - 1);
            }

        }



    }

    private Map<Character, Integer> calculateFreeItems(Map<Character, Integer> itemCounts) {
        Map<Character, Integer> freeItems = new HashMap<>();

        for (var offer : FREE_ITEM_OFFERS) {
            if (itemCounts.containsKey(offer.triggerItem)) {
                int count = itemCounts.get(offer.triggerItem);
                int numberOfFreeItems = count / offer.triggerQuantity;

                freeItems.put(offer.freeItem, freeItems.getOrDefault(offer.freeItem, 0) + numberOfFreeItems);
            }
        }
        return freeItems;
    }


    private int calculateItemPrice(Character item, int count) {
        boolean hasMultiOffer = SPECIAL_OFFERS.get(item) != null && SPECIAL_OFFERS.get(item).size() > 1;
        if (hasMultiOffer) {
            return findBestPrice(count, SPECIAL_OFFERS.get(item), PRICES.get(item));
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

    /**
     * Recursively find best price based on offers, count and regular price.
     *
     * @param count number of specific items
     * @param offers number of offers for the item available
     * @param regularPrice price of single item
     * @return best price on the number of items
     */
    private int findBestPrice(int count, List<SpecialOffer> offers, int regularPrice) {
        if (count == 0) {
            return 0;
        }
        int minPrice = count * regularPrice;
        for (var offer : offers) {
            if (count >= offer.quantity) {
                int price = offer.price + findBestPrice(count - offer.quantity, offers, regularPrice);
                minPrice = Math.min(minPrice, price);
            }
        }
        return minPrice;
    }

}

