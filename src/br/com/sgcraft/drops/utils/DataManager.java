package br.com.sgcraft.drops.utils;

import org.bukkit.event.*;

import br.com.sgcraft.drops.*;

import org.bukkit.*;
import java.io.*;
import org.bukkit.configuration.file.*;

public class DataManager implements Listener
{
    public static void createFolder(final String folder) {
        try {
            final File pasta = new File(Main.aqui.getDataFolder() + File.separator + folder);
            if (!pasta.exists()) {
                pasta.mkdirs();
            }
        }
        catch (SecurityException e) {
            Bukkit.getConsoleSender().sendMessage(Main.aqui.getConfig().getString("Falha-Ao-Criar-Pasta").replace("&", "§").replace("%pasta%", folder));
        }
    }
    
    public static void createFile(final File file) {
        try {
            file.createNewFile();
        }
        catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(Main.aqui.getConfig().getString("Falha-Ao-Criar-Arquivo").replace("&", "§").replace("%arquivo%", file.getName()));
        }
    }
    
    public static File getFolder(final String folder) {
        final File Arquivo = new File(Main.aqui.getDataFolder() + File.separator + folder);
        return Arquivo;
    }
    
    public static File getFile(final String file, final String folder) {
        final File Arquivo = new File(Main.aqui.getDataFolder() + File.separator + folder, String.valueOf(file) + ".yml");
        return Arquivo;
    }
    
    public static File getFile(final String file) {
        final File Arquivo = new File(Main.aqui.getDataFolder() + "/" + file + ".yml");
        return Arquivo;
    }
    
    public static File getListFiles(final String file, final String folder) {
        final File Arquivo = new File(Main.aqui.getDataFolder() + File.separator + folder, String.valueOf(file) + ".yml");
        return Arquivo;
    }
    
    public static FileConfiguration getConfiguration(final File file) {
        final FileConfiguration config = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
        return config;
    }
    
    public static void deleteFile(final File file) {
        file.delete();
    }
}
