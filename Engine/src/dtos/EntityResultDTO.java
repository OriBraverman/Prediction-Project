package dtos;

public class EntityResultDTO {
    private final String entityName;
    private final int startingPopulation;
    private final int endingPopulation;

    public EntityResultDTO(String entityName, int startingPopulation, int endingPopulation) {
        this.entityName = entityName;
        this.startingPopulation = startingPopulation;
        this.endingPopulation = endingPopulation;
    }

    public String getEntityName() {
        return entityName;
    }

    public int getStartingPopulation() {
        return startingPopulation;
    }

    public int getEndingPopulation() {
        return endingPopulation;
    }
}
