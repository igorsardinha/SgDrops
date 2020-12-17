package br.com.sgcraft.drops.comandos;

import org.bukkit.event.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.io.*;
import org.bukkit.inventory.*;

import br.com.sgcraft.drops.utils.*;

public class ComandoCriarDrops implements Listener, CommandExecutor
{
    public boolean onCommand(final CommandSender s, final Command cmd, final String lbl, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("criardrop")) {
            if (!(s instanceof Player)) {
                s.sendMessage("§cO console nao pode utilizar este comando!");
                return false;
            }
            if (args.length != 1) {
                s.sendMessage("§cComando incorreto, use /criardrop <id>");
                return false;
            }
            final String caixa = args[0].toLowerCase();
            if (args[0].length() > 5) {
                s.sendMessage("§cID invalido! Por favor, use no m\u00e1ximo 4 digitos.");
                return false;
            }
            final File file = DataManager.getFile(String.valueOf(caixa), "caixas");
            if (file.exists()) {
                s.sendMessage("§aUm drop ja foi criado com o id '" + caixa + "', por favor escolha outro id.");
                return false;
            }
            final Player p = (Player)s;
            final Inventory inv = Bukkit.getServer().createInventory((InventoryHolder)p, 36, "§8Criando Drop " + caixa);
            p.openInventory(inv);
        }
        return false;
    }
}
