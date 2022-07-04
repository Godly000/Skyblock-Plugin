package godly.skyblock;

import io.papermc.paper.event.packet.PlayerChunkLoadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.Chunk;

import static godly.skyblock.Skyblock.ClearList;
import static godly.skyblock.Skyblock.list;

public class WorldModifier implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerChunkLoad(PlayerChunkLoadEvent event) {
        Chunk temp = event.getChunk();
        String marker = ""+temp.getX()+"\t"+temp.getZ()+"\t"+temp.getWorld().getName();
        if(!list.contains(marker)) {
            ClearList.add(marker);
            // Bukkit.getLogger().info("Added chunk " + marker + " to queue");
        }
    }
}
