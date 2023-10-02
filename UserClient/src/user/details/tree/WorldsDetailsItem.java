package user.details.tree;

import dto.world.WorldDTO;
import dto.world.WorldsDTO;
import javafx.scene.control.TreeItem;

public class WorldsDetailsItem extends TreeItem<String> {
    private WorldsDTO worldsDTO;
    public WorldsDetailsItem(WorldsDTO worldsDTO) {
        super("Simulations Details:");
        this.worldsDTO = worldsDTO;
        for (WorldDTO worldDTO : worldsDTO.getWorlds()) {
            this.getChildren().add(new WorldDetailsItem(worldDTO));
        }
    }

    public WorldsDTO getWorldsDTO() {
        return worldsDTO;
    }
}
