package dtos.gridView;

public class EntityInstanceDTO {
    private final String entityName;
    private final int x;
    private final int y;

    public EntityInstanceDTO(String entityName, int x, int y) {
        this.entityName = entityName;
        this.x = x;
        this.y = y;
    }

    public String getEntityName() {
        return entityName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
