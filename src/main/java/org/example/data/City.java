package org.example.data;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

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

    public City(){
        this.id = null;
        this.name = null;
        this.coordinates = null;
        this.creationDate = null;
        this.area = null;
        this.population = 0;
        this.metersAboveSeaLevel = 0;
        this.climate = null;
        this.government = null;
        this.standardOfLiving = null;
        this.governor = null;
    }

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