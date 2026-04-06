package org.example.data;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.example.data.Climate;
import org.example.data.StandardOfLiving;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString

public class City implements Comparable<City>{

    private Long id;
    private String name;
    private Coordinates coordinates;
    /** Локальное время системы при создании; формат yyyy-MM-dd'T'HH:mm:ss в зоне JVM по умолчанию. */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private java.util.Date creationDate;
    private Double area;
    private int population;
    private int metersAboveSeaLevel;
    private Climate climate;
    private Government government;
    private StandardOfLiving standardOfLiving;
    private Human governor;

    @Override
    public int compareTo(City other) {
        if (other == null) return 1;
        return this.id.compareTo(other.id);
    }
}