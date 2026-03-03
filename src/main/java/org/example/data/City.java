package org.example.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class City implements Comparable<City>{

    private final Long id;
    private final String name;
    private final Coordinates coordinates;
    private final java.util.Date creationDate;
    private final Double area;
    private final int population;
    private final int metersAboveSeaLevel;
    private final Climate climate;
    private final Government government;
    private final StandardOfLiving standardOfLiving;
    private final Human governor;

    public City(Long id, String name, Coordinates coordinates,
                java.util.Date creationDate, Double area,
                int population, int metersAboveSeaLevel, Climate climate,
                Government government, StandardOfLiving standardOfLiving,
                Human governor) {

        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.climate = climate;
        this.government = government;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;

    }

    @Override
    public int compareTo(City other) {
        if (other == null) return 1;
        return this.id.compareTo(other.id);
    }
}