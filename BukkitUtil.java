package me.PimpDuck.InfiniteAnvils;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.Vector;

public class BukkitUtil
{
  public static Block getTargetBlock(Player player)
  {
    return getTargetBlock(player, 4);
  }

  @SuppressWarnings("deprecation")
public static Block getTargetBlock(Player player, int range) {
    for (int dis = 0; dis < range; dis++)
    {
      Block b = player.getTargetBlock(null, dis);
      if ((b.getType() != Material.SNOW) && (b.getType() != Material.LONG_GRASS) && (b.getType() != Material.WATER) && (b.getType() != Material.STATIONARY_WATER)) {
        return b;
      }
    }
    return null;
  }

  public static Block getBlock(Vector v, World world) {
    return world.getBlockAt(v.getBlockX(), v.getBlockY(), v.getBlockZ());
  }

  public static Vector createVector(Block b) {
    return new Vector(b.getX(), b.getY(), b.getZ());
  }

  public static int getEmptyInvSpace(Inventory i) {
    for (int ie = 0; ie < i.getSize(); ie++) {
      if (i.getItem(ie) == null) {
        return ie;
      }
    }
    return -1;
  }
}
