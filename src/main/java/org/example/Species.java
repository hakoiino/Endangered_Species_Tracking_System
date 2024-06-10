package org.example;

public class Species {
    private int id;
    private String name;
    private String conservationStatus;
    private int population;
    private int habitatId;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConservationStatus() {
        return conservationStatus;
    }

    public void setConservationStatus(String conservationStatus) {
        this.conservationStatus = conservationStatus;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getHabitatId() {
        return habitatId;
    }

    public void setHabitatId(int habitatId) {
        this.habitatId = habitatId;
    }
}
