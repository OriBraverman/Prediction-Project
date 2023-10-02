package dto.world;

import java.util.List;

public class WorldsDTO {
    private List<WorldDTO> worlds;

    public WorldsDTO(List<WorldDTO> worlds){
        this.worlds = worlds;
    }

    public List<WorldDTO> getWorlds() { return this.worlds; }
}
