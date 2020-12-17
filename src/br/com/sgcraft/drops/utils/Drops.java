package br.com.sgcraft.drops.utils;

import java.util.concurrent.*;
import org.bukkit.inventory.*;
import java.io.*;
import org.bukkit.configuration.file.*;
import java.util.*;
import org.bukkit.inventory.meta.*;

public class Drops
{
    public static ConcurrentHashMap<ItemStack, String> caixas;
    
    static {
        Drops.caixas = new ConcurrentHashMap<ItemStack, String>();
    }
    
    public static void carregarCaixas() {
        final File folder = DataManager.getFolder("caixas");
        final File[] files = folder.listFiles();
        for (int i = 0; i < files.length; ++i) {
            if (files[i].isFile()) {
                final File file = files[i];
                final String id = file.getName().replace(".yml", "");
                final FileConfiguration config = DataManager.getConfiguration(file);
                final ItemStack caixa = caixa(config);
                Drops.caixas.put(caixa, id);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
	private static ItemStack caixa(final FileConfiguration config) {
        final ItemStack caixa = config.getItemStack("Icone");
        final ItemMeta meta = caixa.getItemMeta();
        meta.setDisplayName(config.getString("Nome"));
        final List<String> lore = (List<String>)config.getStringList("Lore");
        if (!lore.isEmpty()) {
            meta.setLore((List)lore);
        }
        caixa.setItemMeta(meta);
        return caixa;
    }
}
