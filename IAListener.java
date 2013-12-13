package me.PimpDuck.InfiniteAnvils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class IAListener
  implements Listener
{
  private Main main;

  public IAListener(Main main)
  {
    this.main = main;
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent e) {
    if (this.main.infiniteAnvils.contains(BukkitUtil.createVector(e.getBlock())))
      this.main.infiniteAnvils.remove(BukkitUtil.createVector(e.getBlock()));
  }

  @SuppressWarnings("deprecation")
@EventHandler
  public void onPlayerInteract(PlayerInteractEvent e)
  {
    if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
      Block block = e.getClickedBlock();
      if ((block.getType() == Material.ANVIL) && 
        (this.main.infiniteAnvils.contains(new Vector(block.getX(), block.getY(), block.getZ()))))
      {
        byte data = block.getData();

        if ((data == 4) || (data == 5))
        {
          byte newData = 0;
          boolean dir;
          if ((data - 1 == 12) || (data - 1 == 8) || (data - 1 == 4)) {
            dir = true;
          }
          else {
            dir = false;
          }

          System.out.println(data);

          newData = (byte)(12 + (dir ? 1 : 0));

          block.setData(newData);
        }
      }
    }
  }
}
