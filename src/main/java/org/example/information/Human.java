package org.example.information;

import java.time.LocalDate;

public class Human {
    private final LocalDate birthday;

    public Human(LocalDate birthday) {
        if (birthday == null) throw new IllegalArgumentException("Birthday cannot be null");
        this.birthday = birthday;
    }

    public LocalDate getBirthday() { return birthday; }

    @Override
    public String toString() {
        return "Governor born on " + birthday;
    }
}