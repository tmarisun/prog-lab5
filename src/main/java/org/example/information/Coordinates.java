package org.example.information;

public class Coordinates {
    private final float x;
    private final double y;

    public Coordinates(float x, double y) {
        if (x > 959) throw new IllegalArgumentException("X coordinate exceeds maximum value (959)");
        if (y > 613) throw new IllegalArgumentException("Y coordinate exceeds maximum value (613)");
        this.x = x;
        this.y = y;
    }

    public float getX() { return x; }
    public double getY() { return y; }

    @Override
    public String toString() {
        return String.format("(x: %.2f, y: %.2f)", x, y);
    }
}
