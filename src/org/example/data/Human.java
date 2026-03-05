package org.example.data;


import java.util.Date;

public record Human(Date birthday) {
    public Human {
        if (birthday == null) throw new IllegalArgumentException("Birthday cannot be null");
    }

    @Override
    public String toString() {
        return "Governor born on " + birthday;
    }
}