package org.example.data;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;


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