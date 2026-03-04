package org.example.data;


public class Human {
    private final java.util.Date birthday;

    public Human(java.util.Date birthday) {
        if (birthday == null) throw new IllegalArgumentException("Birthday cannot be null");
        this.birthday = birthday;
    }

    public java.util.Date getBirthday() { return birthday; }

    @Override
    public String toString() {
        return "Governor born on " + birthday;
    }
}