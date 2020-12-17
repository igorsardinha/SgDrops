package br.com.sgcraft.drops.comandos;

import org.bukkit.event.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import java.io.*;
import org.bukkit.configuration.file.*;
import org.bukkit.inventory.*;
import java.util.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;

import br.com.sgcraft.drops.utils.*;

public class ComandoEditarDrops implements Listener, CommandExecutor
{
    public boolean onCommand(final CommandSender s, final Command cmd, final String lbl, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("editardrop")) {
            if (!(s instanceof Player)) {
                s.sendMessage("§cO console nao pode utilizar este comando!");
                return false;
            }
            if (args.length < 2) {
                s.sendMessage("§cComando incorreto, use: ");
                s.sendMessage("§c/editardrop <id> itens §8-§7 Para editar os itens.");
                s.sendMessage("§c/editardrop <id> icone §8-§7 Para editar o icone do drop.");
                s.sendMessage("§c/editardrop <id> nome <nome> §8-§7 Para editar o nome do drop.");
                s.sendMessage("§c/editardrop <id> removelore §8-§7 Para remover a lore do drop.");
                s.sendMessage("§c/editardrop <id> addlore <frase> §8-§7 Para adicionar uma linha de lore.");
                return false;
            }
            final String caixa = args[0].toLowerCase();
            final File file = DataManager.getFile(caixa, "caixas");
            if (!file.exists()) {
                s.sendMessage("§cO Drop '" + caixa + "' n\u00e3o existe!");
                ComandoDrops.ListCaixas(s);
                return false;
            }
            final FileConfiguration config = DataManager.getConfiguration(file);
            if (args[1].equalsIgnoreCase("itens")) {
                final Player p = (Player)s;
                final Set<String> ITENS = (Set<String>)config.getConfigurationSection("Itens").getKeys(false);
                final int n = ITENS.size();
                final Inventory inv = Bukkit.getServer().createInventory((InventoryHolder)p, 36, "§8Editar Drop " + caixa);
                for (int i = 0; n > i; ++i) {
                    final ItemStack item = config.getItemStack("Itens." + i);
                    if (item != null) {
                        inv.setItem(i, item);
                    }
                }
                p.openInventory(inv);
                return false;
            }
            if (args[1].equalsIgnoreCase("nome")) {
                if (args.length < 3) {
                    s.sendMessage("§cComando incorreto, use /editardrop <id> nome <nome>");
                    return false;
                }
                String nome = "";
                for (int j = 2; j < args.length; ++j) {
                    nome = String.valueOf(nome) + args[j] + " ";
                }
                config.set("Nome", (Object)nome.replace("&", "§"));
                try {
                    config.save(file);
                    s.sendMessage("§aDrop '" + caixa + "' editado com sucesso.");
                    Drops.carregarCaixas();
                }
                catch (IOException e) {
                    Bukkit.getConsoleSender().sendMessage("§cNao foi possivel salvar o arquivo do drop '" + caixa + "'.");
                }
                return false;
            }
            else {
                if (args[1].equalsIgnoreCase("icone")) {
                    this.abrirMenuEditarIcone((Player)s, caixa);
                    return false;
                }
                if (args[1].equalsIgnoreCase("removelore")) {
                    config.set("Lore", (Object)null);
                    try {
                        config.save(file);
                        s.sendMessage("§aDrop '" + caixa + "' editado com sucesso.");
                        Drops.carregarCaixas();
                    }
                    catch (IOException e2) {
                        Bukkit.getConsoleSender().sendMessage("§cNao foi possivel salvar o arquivo do drop '" + caixa + "'.");
                    }
                    return false;
                }
                if (args[1].equalsIgnoreCase("addlore")) {
                    if (args.length < 3) {
                        s.sendMessage("§cComando incorreto, use /editardrop <id> addlore <frase>");
                        return false;
                    }
                    final List<String> lore = new ArrayList<String>();
                    lore.addAll(config.getStringList("Lore"));
                    String novaLinha = "";
                    for (int k = 2; k < args.length; ++k) {
                        novaLinha = String.valueOf(novaLinha) + args[k] + " ";
                    }
                    lore.add(novaLinha.replace("&", "§"));
                    config.set("Lore", (Object)lore);
                    try {
                        config.save(file);
                        s.sendMessage("§aDrop '" + caixa + "' editado com sucesso.");
                        Drops.carregarCaixas();
                    }
                    catch (IOException e3) {
                        Bukkit.getConsoleSender().sendMessage("§cNao foi possivel salvar o arquivo do Drop '" + caixa + "'.");
                    }
                    return false;
                }
                else {
                    s.sendMessage("§cComando incorreto, use: ");
                    s.sendMessage("§c/editardrop <id> itens §8-§7 Para editar os itens.");
                    s.sendMessage("§c/editardrop <id> icone §8-§7 Para editar o icone do drop.");
                    s.sendMessage("§c/editardrop <id> nome <nome> §8-§7 Para editar o nome do drop.");
                    s.sendMessage("§c/editardrop <id> removelore §8-§7 Para remover a lore do drop.");
                    s.sendMessage("§c/editardrop <id> addlore <frase> §8-§7 Para adicionar uma linha de lore.");
                }
            }
        }
        return false;
    }
    
    private void abrirMenuEditarIcone(final Player p, final String caixa) {
        final File file = DataManager.getFile(caixa, "caixas");
        final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 27, "§8Icone do Drop " + caixa);
        final ItemStack atual = DataManager.getConfiguration(file).getItemStack("Icone");
        final ItemStack vidro = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15);
        final ItemMeta atualMeta = atual.getItemMeta();
        final ItemMeta vidroMeta = vidro.getItemMeta();
        atualMeta.setDisplayName("§aIcone atual do drop");
        vidroMeta.setDisplayName("§8-/-");
        atual.setItemMeta(atualMeta);
        vidro.setItemMeta(vidroMeta);
        for (int i = 0; i < 27; ++i) {
            inv.setItem(i, vidro);
        }
        inv.setItem(12, atual);
        inv.setItem(14, (ItemStack)null);
        p.openInventory(inv);
    }
}
