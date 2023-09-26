package dtos;

public class ActivationDTO {
    private int ticks;
    private double probability;

    public ActivationDTO(int ticks, double probability) {
        this.ticks = ticks;
        this.probability = probability;
    }

    public int getTicks() {
        return ticks;
    }

    public double getProbability() {
        return probability;
    }
}
