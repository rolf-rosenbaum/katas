package poker;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PokerTest {

    @Test
    public void isFullHouse() {
        PokerHand fullHouse = new PokerHand("SK,H3,DK,S3,HK");

        assertTrue(fullHouse.isFullHouse());
    }

    @Test
    public void flushWinsOverLowerFlush() {
        PokerHand flushA = new PokerHand("S6,SA,SQ,S2,ST");
        PokerHand flushB = new PokerHand("H6,HA,HQ,H2,H9");

        assertTrue(flushA.winsOver(flushB));
        assertFalse(flushB.winsOver(flushA));
    }

    @Test
    public void flushWinsOverStraight() {
        PokerHand flush = new PokerHand("S6,SA,SQ,S2,ST");
        PokerHand straight = new PokerHand("D5,H6,C7,S8,H9");

        assertTrue(flush.winsOver(straight));
        assertFalse(straight.winsOver(flush));

    }

    @Test
    public void straightWinsOverThrees() {
        PokerHand handA = new PokerHand("S6,D6,H4,C6,C3");
        PokerHand handB = new PokerHand("D5,H6,C7,S8,H9");

        assertTrue(handB.winsOver(handA));
        assertFalse(handA.winsOver(handB));
    }

    @Test
    public void straightWinsOverHighCard() {
        PokerHand handA = new PokerHand("SA,DT,H4,C6,C3");
        PokerHand handB = new PokerHand("D5,H6,C7,S8,H9");

        assertTrue(handB.winsOver(handA));
        assertFalse(handA.winsOver(handB));

    }

    @Test
    public void threeOfAKindWinsOverPairs() {
        PokerHand handA = new PokerHand("S6,D6,H4,C6,C3");
        PokerHand handB = new PokerHand("DA,D2,H7,C7,H2");

        assertTrue(handA.winsOver(handB));
        assertFalse(handB.winsOver(handA));
    }

    @Test
    public void higherTwoPairsWins() {
        PokerHand handA = new PokerHand("SA,DA,C2,C5,S2");
        PokerHand handB = new PokerHand("DA,D2,H7,C7,H2");

        assertTrue(handA.winsOver(handB));
        assertFalse(handB.winsOver(handA));
    }

    @Test
    public void twoPairsWinOverSinglePair() {
        PokerHand handA = new PokerHand("SA,DA,H4,C5,C3");
        PokerHand handB = new PokerHand("DA,D2,H7,C7,H2");

        assertTrue(handB.winsOver(handA));
        assertFalse(handA.winsOver(handB));
    }

    @Test
    public void pairOverLowerPair() {
        PokerHand handA = new PokerHand("SK,DQ,H3,C5,C3");
        PokerHand handB = new PokerHand("DA,DK,H7,C7,H5");

        assertTrue(handB.winsOver(handA));
        assertFalse(handA.winsOver(handB));

    }

    @Test
    public void pairOverHighCard() {
        PokerHand handA = new PokerHand("DA,DK,H2,C7,H5");
        PokerHand handB = new PokerHand("SK,DQ,H3,C5,C3");

        assertTrue(handB.winsOver(handA));
        assertFalse(handA.winsOver(handB));

    }

    @Test
    public void highCardFirstHandWins() {
        PokerHand handA = new PokerHand("DA,DK,H2,C7,H5");
        PokerHand handB = new PokerHand("SK,DQ,H3,C5,C9");

        assertTrue(handA.winsOver(handB));

    }

    @Test
    public void highCardDraw() {
        PokerHand handA = new PokerHand("SA,DK,H2,C7,H5");
        PokerHand handB = new PokerHand("SA,DK,H2,C7,H5");

        assertTrue(handA.isDrawWith(handB));
    }

    @Test
    public void highCardSecondHandWins() {
        PokerHand handA = new PokerHand("SK,DQ,H3,C5,C9");
        PokerHand handB = new PokerHand("SA,DK,H2,C7,H5");

        assertTrue(handB.winsOver(handA));
    }

    @Test
    public void allHandsAreSorted() {
        PokerHand pokerHand = new PokerHand("C3,H6,D2,CA,ST");

        assertThat(pokerHand.toString(), is("CA,ST,H6,C3,D2"));
    }

}
