package berlin;

public class BerlinClock {

    private final int hours;

    private final int minutes;

    private final int seconds;

    public BerlinClock(String timeString) {
        String[] numbers = timeString.split(":");
        hours = Integer.parseInt(numbers[0]);
        minutes = Integer.parseInt(numbers[1]);
        seconds = Integer.parseInt(numbers[2]);
    }

    public String showTime() {
        return showSeconds() +
                showHours() +
                showMinutes();
    }

    String showHours() {

        StringBuilder hb = new StringBuilder();

        writeFiveHourIntervals(hb);
        hb.append("\n");

        writeHoursRest(hb);
        hb.append("\n");

        return hb.toString();
    }

    private void writeHoursRest(StringBuilder hb) {
        int rest = hours % 5;

        for (int i = 0; i < rest; i++) {
            hb.append("R");
        }

        for (int i = 0; i < 4 - rest; i++) {
            hb.append("O");
        }
    }

    private void writeFiveHourIntervals(StringBuilder hb) {
        int fiveHoursIntervals = hours / 5;

        for (int i = 0; i < fiveHoursIntervals; i++) {
            hb.append("R");
        }

        while (hb.length() < 4) {
            hb.append("O");
        }
    }

    String showMinutes() {

        StringBuilder min = new StringBuilder();

        writeFiveMinIntervals(min);
        fillFiveMinIntervals(min);

        writeMinutes(min);
        fillMinutes(min);

        return min.toString();
    }

    private void fillMinutes(StringBuilder min) {
        int rest = minutes % 5;
        for (int i = 0; i < 4 - rest; i++) {
            min.append("O");
        }
    }

    private void writeMinutes(StringBuilder min) {
        int rest = minutes % 5;
        for (int i = 0; i < rest; i++) {
            min.append("Y");
        }
    }

    private void fillFiveMinIntervals(StringBuilder min) {
        while (min.length() < 11) {
            min.append("O");
        }
        min.append("\n");
    }

    private void writeFiveMinIntervals(StringBuilder min) {
        int fiveMinIntervals = minutes / 5;
        for (int i = 1; i <= fiveMinIntervals; i++) {
            if (i % 3 == 0) {
                min.append("R");
            } else {
                min.append("Y");
            }
        }
    }

    String showSeconds() {
        if (seconds % 2 == 0) {
            return "Y\n";
        } else {
            return "O\n";
        }
    }
}
