package dtos;

public class EntityPopulationDTO {
    private final String name;
    private final String population;
    private final boolean hasValue;


    public EntityPopulationDTO(String name, String population, boolean hasValue) {
        this.name = name;
        this.population = population;
        this.hasValue = hasValue;
    }

    public boolean hasValue() {
        return hasValue;
    }

    public String getName() {
        return name;
    }

    public String getPopulation() { return population; }
}
