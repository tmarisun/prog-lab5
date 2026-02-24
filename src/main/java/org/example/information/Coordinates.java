package org.example.information;

public class Coordinates {
    private final float x;
    private final double y;

    public Coordinates(float x, double y) {
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
