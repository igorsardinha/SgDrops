package br.com.sgcraft.drops.eventos;

import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import java.io.*;
import org.bukkit.configuration.file.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;

import br.com.sgcraft.drops.utils.*;

public class ComandosListener implements Listener
{
    @EventHandler
    public void InventoryClose(final InventoryCloseEvent e) {
        if (e.getInventory().getName().contains("§8Criando Drop ")) {
            final Inventory inv = e.getInventory();
            final Player p = (Player)e.getPlayer();
            this.criarCaixa(inv, p);
            return;
        }
        if (e.getInventory().getName().contains("§8Editar Drop ")) {
            final Player p2 = (Player)e.getPlayer();
            final Inventory inv2 = e.getInventory();
            this.editarCaixaItens(inv2, p2);
            return;
        }
        if (e.getInventory().getName().contains("§8Icone do Drop ")) {
            final Player p2 = (Player)e.getPlayer();
            final Inventory inv2 = e.getInventory();
            this.editarCaixaIcone(inv2, p2);
        }
    }
    
    @EventHandler
    public void InventoryClick(final InventoryClickEvent e) {
        if (e.getInventory().getName().contains("§8Icone do Drop ") && e.getRawSlot() != 14 && e.getRawSlot() < 27) {
            e.setCancelled(true);
        }
    }
    
    private void criarCaixa(final Inventory inv, final Player p) {
        final ItemStack[] itens = inv.getContents();
        int j = 0;
        final String caixa = inv.getName().substring(15, inv.getName().length());
        final File file = DataManager.getListFiles(caixa, "caixas");
        final FileConfiguration config = DataManager.getConfiguration(file);
        DataManager.createFile(file);
        final List<String> lore = new ArrayList<String>();
        lore.add("§aLore padr\u00e3o - Caixa §n" + caixa);
        lore.add("§eEdite o drop usando /editardrop!");
        config.set("Icone", (Object)this.iconePadrao(caixa));
        config.set("Nome", (Object)("§aDrop §n" + caixa));
        config.set("Lore", (Object)lore);
        config.createSection("Itens");
        ItemStack[] array;
        for (int length = (array = itens).length, i = 0; i < length; ++i) {
            final ItemStack item = array[i];
            if (item != null) {
                config.set("Itens." + j, (Object)item);
                ++j;
            }
        }
        try {
            config.save(file);
            p.sendMessage("§aDrop '" + caixa + "' criado com sucesso.");
            Drops.carregarCaixas();
        }
        catch (IOException ex) {
            Bukkit.getConsoleSender().sendMessage("§cNao foi possivel salvar o arquivo do drop '" + caixa + "'.");
        }
    }
    
    private void editarCaixaItens(final Inventory inv, final Player p) {
        final ItemStack[] itens = inv.getContents();
        int j = 0;
        final String caixa = inv.getName().substring(14, inv.getName().length());
        final File file = DataManager.getListFiles(caixa, "caixas");
        final FileConfiguration config = DataManager.getConfiguration(file);
        config.set("Itens", (Object)null);
        config.createSection("Itens");
        ItemStack[] array;
        for (int length = (array = itens).length, i = 0; i < length; ++i) {
            final ItemStack item = array[i];
            if (item != null) {
                config.set("Itens." + j, (Object)item);
                ++j;
            }
        }
        try {
            config.save(file);
            p.sendMessage("§aDrop '" + caixa + "' editado com sucesso.");
            Drops.carregarCaixas();
        }
        catch (IOException ex) {
            Bukkit.getConsoleSender().sendMessage("§cNao foi possivel salvar o arquivo do drop '" + caixa + "'.");
        }
    }
    
    private void editarCaixaIcone(final Inventory inv, final Player p) {
        final String caixa = inv.getName().substring(16, inv.getName().length());
        final File file = DataManager.getListFiles(caixa, "caixas");
        final FileConfiguration config = DataManager.getConfiguration(file);
        final ItemStack icone = inv.getItem(14);
        if (icone == null || icone.getType().equals((Object)Material.AIR)) {
            p.sendMessage("§cIcone invalido, o drop n\u00e3o foi editado.");
        }
        else {
            icone.setAmount(1);
            config.set("Icone", (Object)icone);
            try {
                config.save(file);
                p.sendMessage("§aDrop '" + caixa + "' editado com sucesso.");
                Drops.carregarCaixas();
            }
            catch (IOException ex) {
                Bukkit.getConsoleSender().sendMessage("§cNao foi possivel salvar o arquivo do drop '" + caixa + "'.");
            }
        }
    }
    
    private ItemStack iconePadrao(final String id) {
        final ItemStack icone = new ItemStack(Material.CHEST, 1);
        final ItemMeta meta = icone.getItemMeta();
        meta.setDisplayName("§aDrop §2" + id);
        icone.setItemMeta(meta);
        return icone;
    }
}
