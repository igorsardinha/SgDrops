package br.com.sgcraft.drops.comandos;

import org.bukkit.event.*;

import br.com.sgcraft.drops.utils.*;

import org.bukkit.command.*;

import java.io.*;

public class ComandoDelDrops implements Listener, CommandExecutor
{
    public boolean onCommand(final CommandSender s, final Command cmd, final String lbl, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("deldrop")) {
            if (args.length != 1) {
                s.sendMessage("§cComando incorreto, use /deldrop <id>");
                return false;
            }
            final String caixa = args[0].toLowerCase();
            final File file = DataManager.getFile(caixa, "caixas");
            if (!file.exists()) {
                s.sendMessage("§cO Drop '" + caixa + "' n\u00e3o existe!");
                ComandoDrops.ListCaixas(s);
                return false;
            }
            DataManager.deleteFile(file);
            s.sendMessage("§aDrop '" + caixa + "' deletado com sucesso.");
        }
        return false;
    }
}
