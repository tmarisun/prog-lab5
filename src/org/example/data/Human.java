package org.example.data;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record Human(@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date birthday) {
    public Human {
        if (birthday == null) throw new IllegalArgumentException("Birthday cannot be null");
    }

    @Override
    public String toString() {
        return "Governor born on " + birthday;
    }
}