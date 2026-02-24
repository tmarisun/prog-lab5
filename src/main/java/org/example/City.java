package org.example;

import org.example.information.*;

import java.text.SimpleDateFormat;

public class City implements Comparable<City>{

    private final Long id;
    private final String name;
    private final Coordinates coordinates;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
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


        if (id == null || id <= 0) throw new IllegalArgumentException("ID must be >0");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        if (coordinates == null) throw new IllegalArgumentException("Coordinates cannot be null");
        if (creationDate == null) throw new IllegalArgumentException("Creation date cannot be null");
        if (area == null || area <= 0) throw new IllegalArgumentException("Area must be >0");
        if (population <= 0) throw new IllegalArgumentException("Population must be >0");
        if (government == null) throw new IllegalArgumentException("Government cannot be null");


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

    public Long getId() {return id;}
    public String getName() {return name;}
    public Coordinates getCoordinates() {return coordinates;}
    public java.util.Date getCreationDate() {return creationDate;}
    public Double getArea() {return area;}
    public int getPopulation() {return population;}
    public Coordinates getCoordinatesInWorld() {return coordinates;}
    public Climate getClimate() {return climate;}
    public Government getGovernment() {return government;}
    public StandardOfLiving getStandardOfLiving() {return standardOfLiving;}
    public Human getGovernor() {return governor;}


    @Override
    public int compareTo(City other) {
        if (other == null) return 1;
        return this.id.compareTo(other.id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("City[ID=").append(id)
                .append(", Name='").append(name).append('\'')
                .append(", Coordinates=").append(coordinates)
                .append(", Created=").append(Main.DATE_FORMAT.format(creationDate))
                .append(", Area=").append(area)
                .append(", Population=").append(population)
                .append(", MetersAboveSeaLevel=").append(metersAboveSeaLevel);
        if (climate != null) sb.append(", Climate=").append(climate);
        sb.append(", Government=").append(government);
        if (standardOfLiving != null) sb.append(", StandardOfLiving=").append(standardOfLiving);
        if (governor != null) sb.append(", Governor=").append(governor.getBirthday());
        sb.append(']');
        return sb.toString();
    }
}