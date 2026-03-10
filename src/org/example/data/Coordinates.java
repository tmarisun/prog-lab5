package org.example.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Coordinates {
    private float x;
    private double y;

    @Override
    public String toString() {
        return String.format("(x: %.2f, y: %.2f)", x, y);
    }
}