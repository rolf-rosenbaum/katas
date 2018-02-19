package poker;

public enum Card {

    S2(2, "2", "S"),
    S3(3, "3", "S"),
    S4(4, "4", "S"),
    S5(5, "5", "S"),
    S6(6, "6", "S"),
    S7(7, "7", "S"),
    S8(8, "8", "S"),
    S9(9, "9", "S"),
    ST(10, "T", "S"),
    SJ(11, "J", "S"),
    SQ(12, "Q", "S"),
    SK(13, "K", "S"),
    SA(14, "A", "S"),
    C2(2, "2", "C"),
    C3(3, "3", "C"),
    C4(4, "4", "C"),
    C5(5, "5", "C"),
    C6(6, "6", "C"),
    C7(7, "7", "C"),
    C8(8, "8", "C"),
    C9(9, "9", "C"),
    CT(10, "T", "C"),
    CJ(11, "J", "C"),
    CQ(12, "Q", "C"),
    CK(13, "K", "C"),
    CA(14, "A", "C"),
    D2(2, "2", "D"),
    D3(3, "3", "D"),
    D4(4, "4", "D"),
    D5(5, "5", "D"),
    D6(6, "6", "D"),
    D7(7, "7", "D"),
    D8(8, "8", "D"),
    D9(9, "9", "D"),
    DT(10, "T", "D"),
    DJ(11, "J", "D"),
    DQ(12, "Q", "D"),
    DK(13, "K", "D"),
    DA(14, "A", "D"),
    H2(2, "2", "H"),
    H3(3, "3", "H"),
    H4(4, "4", "H"),
    H5(5, "5", "H"),
    H6(6, "6", "H"),
    H7(7, "7", "H"),
    H8(8, "8", "H"),
    H9(9, "9", "H"),
    HT(10, "T", "H"),
    HJ(11, "J", "H"),
    HQ(12, "Q", "H"),
    HK(13, "K", "H"),
    HA(14, "A", "H");

    final int rank;

    final String name;

    final String suit;

    @Override
    public String toString() {
        return suit + name;
    }

    Card(int rank, String name, String suit) {
        this.rank = rank;
        this.name = name;
        this.suit = suit;
    }

}

