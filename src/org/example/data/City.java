package org.example.data;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

public class City implements Comparable<City>{

    private Long id;
    private String name;
    private Coordinates coordinates;
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