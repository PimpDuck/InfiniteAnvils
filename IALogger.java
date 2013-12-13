package me.PimpDuck.InfiniteAnvils;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public class IALogger
{
  private static ConsoleCommandSender log;
  private static Main plugin;
  private static String prefix;

  public static void initialize(Main instance)
  {
    plugin = instance;
    prefix = "";
    log = plugin.getServer().getConsoleSender();
  }

  public static void disable() {
    log = null;
  }

  public static ConsoleCommandSender getLogger() {
    return log;
  }

  public static String getPrefix() {
    return prefix;
  }

  public static void setPrefix(String p) {
    prefix = p;
  }

  public static void info(String message) {
    log.sendMessage(prefix + message);
  }

  public static void error(String message) {
    log.sendMessage(prefix + ChatColor.RED + message);
  }

  public static void warning(String message) {
    log.sendMessage(prefix + ChatColor.GOLD + message);
  }

  public static void config(String message) {
    log.sendMessage(prefix + ChatColor.DARK_BLUE + message);
  }
}
