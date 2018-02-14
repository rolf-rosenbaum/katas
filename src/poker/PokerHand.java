package poker;

import java.util.ArrayList;
import java.util.List;

public class PokerHand {

    private List<Integer> pairs = new ArrayList<>();

    private List<Card> cards = new ArrayList<>();

    private int threesValue;

    public PokerHand(String cardString) {
        cards = parseToCards(cardString);
        cards.sort((o1, o2) -> o2.value - o1.value);
        findThreeOfAKind();
        findPairs();
    }

    List<Card> parseToCards(String cardString) {
        String[] cardArray = cardString.split(",");

        List<Card> hand = new ArrayList<>();
        for (String s : cardArray) {
            Card card = Card.valueOf(s);
            hand.add(card);
        }

        return hand;
    }

    private void findPairs() {
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).value == cards.get(i + 1).value) {
                pairs.add(cards.get(i).value);
            }
        }
    }

    boolean hasPair() {
        return !pairs.isEmpty();
    }

    public boolean winsOver(PokerHand other) {
        return compare(other) > 0;
    }

    public boolean isDrawWith(PokerHand other) {
        return compare(other) == 0;
    }

    public int compare(PokerHand other) {

        if (hasThreeOfAKind()) {
            return compareThrees(other);
        }

        if (hasPair()) {
            return comparePairs(other);
        }

        return compareHighCard(other);
    }

    private int compareThrees(PokerHand other) {
        return threesValue - other.threesValue;
    }

    private boolean hasThreeOfAKind() {
        return threesValue > 0;

    }

    private void findThreeOfAKind() {
        for (int i = 0; i < cards.size() - 2; i++) {
            if (cards.get(i).value == cards.get(i + 1).value && cards.get(i).value == cards.get(i + 2).value) {
                threesValue = cards.get(i).value;
            }
        }
    }

    private int compareHighCard(PokerHand other) {
        for (int i = 0; i < cards.size(); i++) {
            int comparison = cards.get(i).value - other.cards.get(i).value;
            if (comparison != 0) {
                return comparison;
            }
        }
        return 0;
    }

    private int comparePairs(PokerHand otherHand) {
        int comp = pairs.size() - otherHand.pairs.size();
        if (comp != 0) {
            return comp;
        }

        for (int i = 0; i < pairs.size(); i++) {
            comp = pairs.get(i) - otherHand.pairs.get(i);
            if (comp != 0) {
                return comp;
            }
        }
        return comp;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Card card : cards) {
            builder.append(card).append(",");
        }

        builder.deleteCharAt(builder.lastIndexOf(","));
        return builder.toString();
    }
}
