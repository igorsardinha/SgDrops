package br.com.sgcraft.drops.comandos;

import org.bukkit.event.*;

import br.com.sgcraft.drops.utils.*;

import org.bukkit.command.*;

import java.io.*;
import java.util.*;

public class ComandoDrops implements Listener, CommandExecutor
{
    public boolean onCommand(final CommandSender s, final Command cmd, final String lbl, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("verdrops")) {
            ListCaixas(s);
        }
        return false;
    }
    
    public static void ListCaixas(final CommandSender s) {
        final File folder = DataManager.getFolder("caixas");
        final File[] file = folder.listFiles();
        final List<String> caixasnome = new ArrayList<String>();
        if (file.length == 0) {
            s.sendMessage("§cNenhum drop foi criado até o momento.");
            return;
        }
        int cont = 0;
        for (int i = 0; i < file.length; ++i) {
            if (file[i].isFile()) {
                caixasnome.add(file[i].getName().replace(".yml", ""));
                ++cont;
            }
        }
        final String separador = "§8,§7";
        final String caixaslist = caixasnome.toString();
        final String caixas = "§eDrops disponiveis(" + cont + "): §7%caixas%";
        s.sendMessage(caixas.replace("%caixas%", caixaslist.substring(1, caixaslist.length() - 1)).replace(",", separador));
    }
}
