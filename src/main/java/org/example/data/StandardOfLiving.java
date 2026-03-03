package org.example.data;

public enum StandardOfLiving {
    HIGH(0),
    MEDIUM(1),
    LOW(2),
    ULTRA_LOW(3),
    NIGHTMARE(4);

    private final int rank;

    StandardOfLiving(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }
}
