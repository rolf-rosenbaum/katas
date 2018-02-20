package poker;

import java.util.ArrayList;
import java.util.List;

public class PokerHand {

    private List<Integer> pairs = new ArrayList<>();

    private List<Card> cards = new ArrayList<>();

    private int threesRank;

    private int straightStartingRank;

    private int fullHouseThreesRank;

    private int fullHousePairRank;

    private int foursRank;


    public PokerHand(String cardString) {
        cards = parseToCards(cardString.split(","));
        cards.sort((o1, o2) -> o2.rank - o1.rank);

        findFours();
        findFullHouse();
        findStraight();
        findThreeOfAKind();
        findPairs();

    }

    private void findFours() {
        if (cards.get(0).rank == cards.get(3).rank || cards.get(1).rank == cards.get(4).rank) {
            foursRank = cards.get(1).rank;
        }
    }

    private void findStraight() {
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).rank - cards.get(i + 1).rank != 1) {
                return;
            }
        }
        straightStartingRank = cards.get(cards.size() - 1).rank;
    }

    private boolean isStraight() {
        return straightStartingRank > 0;
    }

    List<Card> parseToCards(String[] cards) {

        List<Card> hand = new ArrayList<>();
        for (String s : cards) {
            hand.add(Card.valueOf(s.trim()));
        }

        return hand;
    }

    private void findPairs() {
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).rank == cards.get(i + 1).rank) {
                pairs.add(cards.get(i).rank);
            }
        }
    }

    private boolean hasPair() {
        return !pairs.isEmpty();
    }

    public boolean winsOver(PokerHand other) {
        return compare(other) > 0;
    }

    public boolean isDrawWith(PokerHand other) {
        return compare(other) == 0;
    }

    private int compare(PokerHand other) {

        if (isStraightFlush() || other.isStraightFlush()) {
            return compareStraights(other);
        }

        if (hasFourOfAKind() || other.hasFourOfAKind()) {
            return compareFours(other);
        }

        if (isFullHouse() || other.isFullHouse()) {
            return compareFullHouse(other);
        }

        if (isFlush() || other.isFlush()) {
            return compareFlush(other);
        }

        if (isStraight() || other.isStraight()) {
            return compareStraights(other);
        }

        if (hasThreeOfAKind() || other.hasThreeOfAKind()) {
            return compareThrees(other);
        }

        if (hasPair() || other.hasPair()) {
            return comparePairs(other);
        }

        return compareHighCard(other);
    }

    private boolean isStraightFlush() {
        return isFlush() && isStraight();
    }

    private int compareFours(PokerHand other) {
        return foursRank - other.foursRank;
    }

    private boolean hasFourOfAKind() {
        return foursRank > 0;
    }

    private int compareFullHouse(PokerHand other) {
        if (fullHouseThreesRank - other.fullHouseThreesRank == 0) {
            return fullHousePairRank - other.fullHousePairRank;
        }
        return fullHouseThreesRank - other.fullHouseThreesRank;
    }

    private int compareFlush(PokerHand other) {
        if (isFlush() && other.isFlush()) {
            return compareHighCard(other);
        }
        if (isFlush()) {
            return 1;
        }
        return -1;
    }

    private boolean isFlush() {

        for (int i = 1; i < cards.size(); i++) {
            if (!cards.get(i).suit.equals(cards.get(0).suit)) {
                return false;
            }
        }
        return true;
    }

    private int compareStraights(PokerHand other) {
        return straightStartingRank - other.straightStartingRank;
    }

    private int compareThrees(PokerHand other) {
        return threesRank - other.threesRank;
    }

    private boolean hasThreeOfAKind() {
        return threesRank > 0;

    }

    private void findThreeOfAKind() {
        for (int i = 0; i < cards.size() - 2; i++) {
            if (cards.get(i).rank == cards.get(i + 1).rank && cards.get(i).rank == cards.get(i + 2).rank) {
                threesRank = cards.get(i).rank;
            }
        }
    }

    private int compareHighCard(PokerHand other) {
        for (int i = 0; i < cards.size(); i++) {
            int comparison = cards.get(i).rank - other.cards.get(i).rank;
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

    private void findFullHouse() {
        // since cards are ordered by rank, this means that all three cards from 0 to 2 have equal rank
        if (cards.get(0).rank == cards.get(2).rank) {
            if (cards.get(3).rank == cards.get(4).rank) {
                fullHouseThreesRank = cards.get(0).rank;
                fullHousePairRank = cards.get(3).rank;
            } else if (cards.get(0).rank == cards.get(1).rank) {
                // since cards are ordered by rank, this means that all three cards from 2 to 4 have equal rank
                if (cards.get(2).rank == cards.get(4).rank) {
                    fullHouseThreesRank = cards.get(2).rank;
                    fullHousePairRank = cards.get(0).rank;
                }
            }
        }

    }

    public boolean isFullHouse() {
        return fullHouseThreesRank > 0;
    }
}
