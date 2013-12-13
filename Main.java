package me.PimpDuck.InfiniteAnvils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" } )
public class Main extends JavaPlugin
{
  private static final int[] toggleIds = { Material.ANVIL.getId() };

  public List<Vector> infiniteAnvils = new ArrayList();
  public String name;
  public String version;
  
public void onEnable()
  {
    this.name = getDescription().getName();
    this.version = getDescription().getVersion();

    IALogger.initialize(this);
    IALogger.setPrefix(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_BLUE + this.name + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE);
    IALogger.info(this.name + " version " + this.version + " is enabled!");

    getServer().getPluginManager().registerEvents(new IAListener(this), this);

    this.infiniteAnvils = IAfile.load(this);
    if (this.infiniteAnvils == null)
      this.infiniteAnvils = new ArrayList();
  }

  public void onDisable()
  {
    IALogger.info(this.name + " has been disabled!");
    IAfile.save(this.infiniteAnvils, this);
  }

public boolean onCommand(CommandSender sender, Command cmd, String message, String[] args)
  {
    if (message.equalsIgnoreCase("iatoggle")) {
      if ((sender instanceof Player)) {
        Player player = (Player)sender;
        if (player.hasPermission("ia.toggle")) {
          Block block = BukkitUtil.getTargetBlock(player);
		int blockId = block.getTypeId();
          boolean canInf = false;
          for (int id : toggleIds) {
            if (blockId == id) {
              canInf = true;
            }
          }
          if (canInf) {
            Vector blockVector = new Vector(block.getX(), block.getY(), block.getZ());
            if (this.infiniteAnvils.contains(blockVector)) {
              this.infiniteAnvils.remove(blockVector);
              tellPlayer(player, block.getType().name() + " is no longer infinite");
            }
            else {
              this.infiniteAnvils.add(blockVector);
              tellPlayer(player, block.getType().name() + " is now infinite");

              if (block.getType() == Material.ANVIL) {
                byte data = block.getData();
                byte newData = 0;
                boolean dir;
                if ((data - 1 == 12) || (data - 1 == 8) || (data - 1 == 4)) {
                  dir = true;
                }
                else {
                  dir = false;
                }
                newData = (byte)(dir ? 1 : 0);
                block.setData(newData);
              }

            }

            IAfile.save(this.infiniteAnvils, this);
          }
          else {
            tellPlayer(player, "Sorry but " + block.getType().name() + " can not be made infinite");
          }
        }
        else {
          tellPlayerNoPermission(player, "Sorry you don't have permission to do that!");
        }
      }
      else {
        tellPlayerNoPermission(sender, "Sorry I don't know who you are!");
      }
    }
    else if (message.equalsIgnoreCase("iaview")) {
      if ((sender instanceof Player)) {
        Player player = (Player)sender;
        if (player.hasPermission("ia.view"))
        {
          Block block = BukkitUtil.getTargetBlock(player);

          Vector blockVector = new Vector(block.getX(), block.getY(), block.getZ());

          if (this.infiniteAnvils.contains(blockVector)) {
            tellPlayer(player, block.getType().name() + " is infinite");
          }
          else {
            tellPlayer(player, block.getType().name() + " is not infinite");
          }
        }
        else
        {
          tellPlayerNoPermission(player, "Sorry you don't have permission to do that!");
        }
      }
      else {
        tellPlayerNoPermission(sender, "Sorry I don't know who you are!");
      }
    }
    return true;
  }

  public void tellConsole(String msg)
  {
    getLogger().info("[InfiniteAnvils] " + msg);
  }

  public void tellPlayer(CommandSender player, String msg) {
    player.sendMessage(ChatColor.AQUA + "[" + ChatColor.YELLOW + "InfiniteAnvils" + ChatColor.AQUA + "]" + ChatColor.GOLD + " " + msg);
  }

  public void tellPlayerNoPermission(CommandSender player, String msg) {
    player.sendMessage(ChatColor.AQUA + "[" + ChatColor.YELLOW + "InfiniteAnvils" + ChatColor.AQUA + "]" + ChatColor.DARK_RED + " " + msg);
  }

  public void cleanup() {
    List newList = new ArrayList();
    for (Vector vector : this.infiniteAnvils) {
      if (!newList.contains(vector)) {
        newList.add(vector);
      }
    }
    this.infiniteAnvils = newList;
  }
}
