package steven.dev;

import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.List;

public class Spawn {
    private EntityType entityType;
    private List<Biome> biomes = null;
    private boolean canSpawn;

    public Spawn(EntityType entityType, boolean canSpawn, Biome ...biome) {
        this.entityType = entityType;
        this.canSpawn = canSpawn;
        if (biome != null) {
            this.biomes = Arrays.asList(biome);
        }
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public List<Biome> getBiomes() {
        return biomes;
    }

    public boolean canSpawn() {
        return canSpawn;
    }
}
