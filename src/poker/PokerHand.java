package poker;

import java.util.ArrayList;
import java.util.Collection;
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

    public boolean winsOver(PokerHand other) {
        return compare(other) > 0;
    }

    public boolean isDrawWith(PokerHand other) {
        return compare(other) == 0;
    }

    private int compare(PokerHand other) {

        Collection<HandComparator> comparators = new ArrayList<>();

        comparators.add(new StraightFlushComparator());
        comparators.add(new FoursComparator());
        comparators.add(new FullHouseComparator());
        comparators.add(new FlushComparator());
        comparators.add(new StraightComparator());
        comparators.add(new ThreesComparator());
        comparators.add(new PairComparator());
        comparators.add(new HighCardComparator());

        int result = 0;
        for (HandComparator comparator : comparators) {
            result = comparator.compare(this, other);
            if (result != 0) {
                return result;
            }
        }
        return result;
    }

    boolean isStraightFlush() {
        return isFlush() && isStraight();
    }

    private boolean isFlush() {

        for (int i = 1; i < cards.size(); i++) {
            if (!cards.get(i).suit.equals(cards.get(0).suit)) {
                return false;
            }
        }
        return true;
    }

    private void findThreeOfAKind() {
        for (int i = 0; i < cards.size() - 2; i++) {
            if (cards.get(i).rank == cards.get(i + 1).rank && cards.get(i).rank == cards.get(i + 2).rank) {
                threesRank = cards.get(i).rank;
            }
        }
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

    private class FlushComparator implements HandComparator {

        @Override
        public int compare(PokerHand o1, PokerHand o2) {
            if (o1.isFlush() && o2.isFlush()) {
                return new HighCardComparator().compare(o1, o2);
            }
            if (o1.isFlush()) {
                return 1;
            }
            if (o2.isFlush()) {
                return -1;

            }
            return 0;
        }
    }

    private class FullHouseComparator implements HandComparator {

        @Override
        public int compare(PokerHand o1, PokerHand o2) {
            if (o1.fullHouseThreesRank - o2.fullHouseThreesRank == 0) {
                return o1.fullHousePairRank - o2.fullHousePairRank;
            }
            return o1.fullHouseThreesRank - o2.fullHouseThreesRank;
        }
    }

    private class FoursComparator implements HandComparator {

        @Override
        public int compare(PokerHand hand1, PokerHand hand2) {
            return hand1.foursRank - hand2.foursRank;
        }
    }

    private class StraightFlushComparator implements HandComparator {

        @Override
        public int compare(PokerHand o1, PokerHand o2) {
            if (o1.isStraightFlush() && o2.isStraightFlush()) {
                return new StraightComparator().compare(o1, o2);
            } else {
                if (o1.isStraightFlush()) {
                    return 1;
                }
                if (o2.isStraightFlush()) {
                    return -1;
                }
            }
            return 0;
        }
    }

    private class StraightComparator implements HandComparator {

        @Override
        public int compare(PokerHand o1, PokerHand o2) {
            return o1.straightStartingRank - o2.straightStartingRank;
        }
    }

    private class ThreesComparator implements HandComparator {

        @Override
        public int compare(PokerHand o1, PokerHand o2) {
            return o1.threesRank - o2.threesRank;
        }
    }

    private class PairComparator implements HandComparator {

        @Override
        public int compare(PokerHand o1, PokerHand o2) {
            int comp = o1.pairs.size() - o2.pairs.size();

            // two pairs beats one pair
            if (comp != 0) {
                return comp;
            }

            for (int i = 0; i < pairs.size(); i++) {
                comp = o1.pairs.get(i) - o2.pairs.get(i);
                if (comp != 0) {
                    return comp;
                }
            }
            return comp;
        }
    }

    private class HighCardComparator implements HandComparator {

        @Override
        public int compare(PokerHand o1, PokerHand o2) {
            for (int i = 0; i < cards.size(); i++) {
                int comparison = o1.cards.get(i).rank - o2.cards.get(i).rank;
                if (comparison != 0) {
                    return comparison;
                }
            }
            return 0;
        }
    }
}
