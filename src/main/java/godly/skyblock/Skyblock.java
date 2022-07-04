package godly.skyblock;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

public final class Skyblock extends JavaPlugin {

    public static Set<String> list = new HashSet<>();
    public static Set<String> ClearList = new HashSet<>();

    public static boolean lock = false;

    BukkitRunnable runnable = new BukkitRunnable() {
        @Override
        public void run() {
            if((ClearList.size() > 0)) {
                Chunk chunk = null;
                for (String temp : ClearList) { // look through ClearList
                    // Bukkit.getLogger().info("Processing "+temp);
                    if (list.contains(temp)) { // another thread has queued same chunk before list added it
                        ClearList.remove(temp);
                        continue;
                    }
                    String[] tempList = temp.split("\t"); // unpack chunk hash to convert into chunk
                    chunk = getServer().getWorld(tempList[2]).getChunkAt(Integer.parseInt(tempList[0]), Integer.parseInt(tempList[1])); // chunk to be used for processing
                    break;
                }
                if (chunk == null) return; // everything in ClearList was already in list, so nothing to be done
                String marker = ""+chunk.getX()+"\t"+chunk.getZ()+"\t"+chunk.getWorld().getName(); // used for hashing the chunk
                list.add(marker);
                for(Player p : Bukkit.getOnlinePlayers()) { // message sent out to players about chunk modification info
                    p.sendActionBar(Component.text("Modifying terrain, please wait... Current queue: " + ClearList.size()));
                }
                Chunk tempChunk; // process borders on neighboring chunks, so that liquids do not flow back into the chunk post-processing
                // left edge
                tempChunk = getServer().getWorld(chunk.getWorld().getName()).getChunkAt(chunk.getX()-1, chunk.getZ());
                for(int x = 15; x <= 15; x++) {
                    for(int y = tempChunk.getWorld().getMinHeight(); y <= tempChunk.getWorld().getMaxHeight(); y++) {
                        for(int z = 0; z <= 15; z++) {
                            Block block = tempChunk.getBlock(x, y, z);
                            if (!block.isEmpty() && !keep(tempChunk, tempChunk.getX()*16+x, y, tempChunk.getZ()*16+z, block)) {
                                tempChunk.getBlock(x, y, z).setType(Material.AIR, false);
                            }
                        }
                    }
                }
                // top edge
                tempChunk = getServer().getWorld(chunk.getWorld().getName()).getChunkAt(chunk.getX(), chunk.getZ()-1);
                for(int x = 0; x <= 15; x++) {
                    for(int y = tempChunk.getWorld().getMinHeight(); y <= tempChunk.getWorld().getMaxHeight(); y++) {
                        for(int z = 15; z <= 15; z++) {
                            Block block = tempChunk.getBlock(x, y, z);
                            if (!block.isEmpty() && !keep(tempChunk, tempChunk.getX()*16+x, y, tempChunk.getZ()*16+z, block)) {
                                tempChunk.getBlock(x, y, z).setType(Material.AIR, false);
                            }
                        }
                    }
                }
                // right edge
                tempChunk = getServer().getWorld(chunk.getWorld().getName()).getChunkAt(chunk.getX()+1, chunk.getZ());
                for(int x = 0; x <= 0; x++) {
                    for(int y = tempChunk.getWorld().getMinHeight(); y <= tempChunk.getWorld().getMaxHeight(); y++) {
                        for(int z = 0; z <= 15; z++) {
                            Block block = tempChunk.getBlock(x, y, z);
                            if (!block.isEmpty() && !keep(tempChunk, tempChunk.getX()*16+x, y, tempChunk.getZ()*16+z, block)) {
                                tempChunk.getBlock(x, y, z).setType(Material.AIR, false);
                            }
                        }
                    }
                }
                // bottom edge
                tempChunk = getServer().getWorld(chunk.getWorld().getName()).getChunkAt(chunk.getX(), chunk.getZ()+1);
                for(int x = 0; x <= 15; x++) {
                    for(int y = tempChunk.getWorld().getMinHeight(); y <= tempChunk.getWorld().getMaxHeight(); y++) {
                        for(int z = 0; z <= 0; z++) {
                            Block block = tempChunk.getBlock(x, y, z);
                            if (!block.isEmpty() && !keep(tempChunk, tempChunk.getX()*16+x, y, tempChunk.getZ()*16+z, block)) {
                                tempChunk.getBlock(x, y, z).setType(Material.AIR, false);
                            }
                        }
                    }
                }
                // now that nothing can flow back into the chunk, process chunk itself
                for(int x = 0; x <= 15; x++) {
                    for(int y = chunk.getWorld().getMinHeight(); y <= chunk.getWorld().getMaxHeight(); y++) {
                        for(int z = 0; z <= 15; z++) {
                            Block block = chunk.getBlock(x, y, z);
                            if(!block.isEmpty() && !keep(chunk, chunk.getX()*16+x, y, chunk.getZ()*16+z, block)) {
                                chunk.getBlock(x, y, z).setType(Material.AIR, false);
                            }
                        }
                    }
                }
                ClearList.remove(marker);
                // Bukkit.getLogger().info("converted " + marker);
            }
        }
    };

    public static boolean keep(Chunk chunk, int x, int y, int z, Block block) {
        // for Skygrid
        // if (x % 4 == 0 && y % 4 == 0 && z % 4 == 0) return true;
        Material mat = block.getType();
        // keep these blocks always
        if(mat == Material.CHEST || mat == Material.TRAPPED_CHEST || mat == Material.END_GATEWAY || mat == Material.SPAWNER ||
                mat == Material.END_PORTAL_FRAME || mat == Material.END_PORTAL || mat == Material.NETHER_PORTAL)
            return true;
        if (chunk.getWorld().getName().equals("Skyblock_the_end")) // if chunk is in the end
        {
            if (mat == Material.BEDROCK) return true; // keep bedrock
            if (mat == Material.OBSIDIAN && Math.sqrt(x*x+z*z) < 500) return true; // keep obsidian if it is an end pillar or the spawn platform
        }
        else if (mat == Material.OBSIDIAN) // in the Overworld and Nether, keep obsidian if and only if it is next to a nether portal block
        {
            Bukkit.getLogger().info("Detected obsidian at "+x+", "+y+", "+z);
            for (int x_diff = -1; x_diff <= 1; x_diff++)
                for (int y_diff = -1; y_diff <= 1; y_diff++)
                    for (int z_diff = -1; z_diff <= 1; z_diff++)
                        if (Math.abs(x_diff)+Math.abs(y_diff)+Math.abs(z_diff) == 1 || Math.abs(x_diff)+Math.abs(y_diff)+Math.abs(z_diff) == 2) {
                            Bukkit.getLogger().info("Looking at block " + (x+x_diff) + ", " + (y+y_diff) + ", " + (z+z_diff) +
                                    "with material "+chunk.getWorld().getBlockAt(x+x_diff, y+y_diff, z+z_diff).getType());
                            if (chunk.getWorld().getBlockAt(x+x_diff, y+y_diff, z+z_diff).getType() == Material.NETHER_PORTAL)
                            {
                                Bukkit.getLogger().info("Found nearby nether portal!");
                                return true;
                            }
                        }
        }
        return false;
    }
    @Override
    public void onEnable() {
        for (World world : Bukkit.getWorlds()) {
            world.setSpawnLocation(0, world.getHighestBlockYAt(0, 0), 0);
        }
        try {
            File f = new File("./loaded.txt");
            if(f.exists()) {
                Scanner sc = new Scanner(f);
                while(sc.hasNextLine()) list.add(sc.nextLine());
                sc.close();
                Bukkit.getLogger().info("Loaded all chunks from loaded.txt into list");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bukkit.getServer().getPluginManager().registerEvents(new WorldModifier(), this);
        runnable.runTaskTimer(this, 100L, 0L);
    }

    @Override
    public void onDisable() {
        runnable.cancel();
        if(!lock) {
            try {
                lock = true;
                File f = new File("./loaded.txt");
                f.createNewFile();
                FileWriter fw = new FileWriter(f);
                for (String chunk : list) {
                    fw.write(chunk+"\n");
                }
                fw.flush();
                fw.close();
                Bukkit.getLogger().info("Wrote all chunks to file loaded.txt");
            } catch (IOException e) {
                e.printStackTrace();
                lock = false;
            } finally {
                lock = false;
            }
        }
    }
}
