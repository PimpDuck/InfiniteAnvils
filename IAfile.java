package me.PimpDuck.InfiniteAnvils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.util.Vector;

public class IAfile
{
  @SuppressWarnings({ "unchecked", "rawtypes" })
public static List<Vector> load(Main main)
  {
    main.cleanup();
    String data = readFile(main);
    if ((data.trim() != "") && (data != null)) {
      List infBlocks = new ArrayList();
      String[] lines = data.split(":");
      boolean finished = false;
      for (String line : lines) {
        String[] coordsStr = line.trim().split(",");
        int[] coords = new int[3];
        for (int c = 0; c < 3; c++) {
          try {
            coords[c] = Integer.parseInt(coordsStr[c].trim());
          }
          catch (Exception e) {
            finished = true;
            break;
          }
        }
        if (finished) {
          break;
        }
        infBlocks.add(new Vector(coords[0], coords[1], coords[2]));
      }

      return infBlocks;
    }

    return null;
  }

  public static void save(List<Vector> inf, Main main)
  {
    String data = "";
    for (Vector v : inf) {
      data = data + v.getBlockX() + "," + v.getBlockY() + "," + v.getBlockZ() + ":";
    }
    writeToFile(data, main);
  }

  public static void writeToFile(String msg, Main main) {
    File dataFolder = main.getDataFolder();

    if (!dataFolder.exists()) {
      dataFolder.mkdir();
    }

    File dataFile = new File(main.getDataFolder(), "InfiniteAnvils.data");

    if (!dataFile.exists()) {
      try {
        dataFile.createNewFile();
      }
      catch (IOException ioe) {
        main.tellConsole("Error in creating data file");
      }
    }

    try
    {
      FileOutputStream erasor = new FileOutputStream(dataFile);
      erasor.write("".getBytes());
      erasor.close();

      FileWriter fw = new FileWriter(dataFile, true);
      PrintWriter pw = new PrintWriter(fw);

      pw.println(msg);

      pw.close();
    }
    catch (IOException e)
    {
      main.tellConsole("Error in writing to data file");
    }
  }

  @SuppressWarnings("resource")
public static String readFile(Main main)
  {
    try
    {
      File dataFolder = main.getDataFolder();

      if (!dataFolder.exists()) {
        dataFolder.mkdir();
      }

      File dataFile = new File(main.getDataFolder(), "InfiniteAnvils.data");

      if (!dataFile.exists()) {
        try {
          dataFile.createNewFile();
        }
        catch (IOException ioe) {
          main.tellConsole("Error in creating data file");
        }

      }

      String fileStr = "";
      BufferedReader reader = new BufferedReader(new FileReader(new File(main.getDataFolder(), "InfiniteAnvils.data")));
      String line;
      while ((line = reader.readLine()) != null)
      {
        fileStr = fileStr + line;
      }

      return fileStr;
    } catch (IOException e) {
      main.tellConsole("Error in reading data file - file missing?");
    }return null;
  }
}
