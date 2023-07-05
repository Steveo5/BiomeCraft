package steven.dev;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class BiomeCraftEntityListener implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent evt) {
        for (Spawn spawn : BiomeCraft.getSpawns()) {
            if (evt.getEntityType() == spawn.getEntityType()) {
                if (spawn.getBiomes() == null) {
                    evt.setCancelled(true);
                } else if (spawn.getBiomes().contains(evt.getLocation().getBlock().getBiome())) {
                    evt.setCancelled(true);
                }
            }
        }
    }
}
