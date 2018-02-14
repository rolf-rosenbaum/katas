package berlin;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BerlinClockTest {

    /*
    Create a representation of the Berlin Clock for a given time (hh:mm:ss).

    The Berlin Uhr (Clock) is a rather strange way to show the time.

    On the top of the clock there is a yellow lamp that blinks on/off every two seconds.

    The time is calculated by adding rectangular lamps.

    The top two rows of lamps are red. These indicate the hours of a day.
        In the top row there are 4 red lamps. Every lamp represents 5 hours.
        In the lower row of red lamps every lamp represents 1 hour.
        So if two lamps of the first row and three of the second row are switched on that indicates 5+5+3=13h or 1 pm.

    The two rows of lamps at the bottom count the minutes.
        The first of these rows has 11 lamps, the second 4.
        In the first row every lamp represents 5 minutes.
        In this first row the 3rd, 6th and 9th lamp are red and indicate the first quarter, half and last quarter of an hour. The other lamps are yellow.
        In the last row with 4 lamps every lamp represents 1 minute.

    The lamps are switched on from left to right.

    Y = Yellow
    R = Red
    O = Off

     */

    @Test
    public void midnight_should_be_all_Os() {
        BerlinClock clock = new BerlinClock("00:00:00");

        assertThat(clock.showTime(), is(
                "Y\n" +
                        "OOOO\n" +
                        "OOOO\n" +
                        "OOOOOOOOOOO\n" +
                        "OOOO"));
    }

    @Test
    public void test_seconds() {
        BerlinClock clock = new BerlinClock("00:00:00");
        assertThat(clock.showSeconds(), is("Y\n"));

        clock = new BerlinClock("00:00:01");
        assertThat(clock.showSeconds(), is("O\n"));

        clock = new BerlinClock("00:00:33");
        assertThat(clock.showSeconds(), is("O\n"));

        clock = new BerlinClock("00:00:46");
        assertThat(clock.showSeconds(), is("Y\n"));
    }

    @Test
    public void test_minutes() {
        BerlinClock clock = new BerlinClock("00:23:00");
        assertThat(clock.showMinutes(), is(
                "YYRYOOOOOOO\n" +
                        "YYYO"));

        clock = new BerlinClock("00:54:00");
        assertThat(clock.showMinutes(), is(
                "YYRYYRYYRYO\n" +
                        "YYYY"));

    }

    @Test
    public void testHours() {
        BerlinClock clock = new BerlinClock("19:00:00");
        assertThat(clock.showHours(), is("RRRO\nRRRR\n"));

        clock = new BerlinClock("23:00:00");
        assertThat(clock.showHours(), is("RRRR\nRRRO\n"));

        clock = new BerlinClock("05:00:00");
        assertThat(clock.showHours(), is("ROOO\nOOOO\n"));

    }

    @Test
    public void complete_test() {
        BerlinClock clock = new BerlinClock("21:57:33");
        assertThat(clock.showTime(), is(
                "O\n" +
                "RRRR\n" +
                "ROOO\n" +
                "YYRYYRYYRYY\n" +
                "YYOO"));
    }
}
