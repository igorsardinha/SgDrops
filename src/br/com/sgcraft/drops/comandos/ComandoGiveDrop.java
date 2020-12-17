 package br.com.sgcraft.drops.comandos;

import org.bukkit.event.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import java.io.*;
import org.bukkit.configuration.file.*;
import org.bukkit.inventory.*;
import java.util.*;
import org.bukkit.inventory.meta.*;

import br.com.sgcraft.drops.utils.*;

public class ComandoGiveDrop implements Listener, CommandExecutor {
	public boolean onCommand(final CommandSender s, final Command cmd, final String lbl, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("givedrop")) {
			if (!(s instanceof Player)) {
				if (args.length != 3) {
					s.sendMessage("§cComando incorreto, use /givedrop <id> [quantia] [player]");
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
				int quantidade;
				try {
					quantidade = Integer.valueOf(args[1]);
				} catch (NumberFormatException e) {
					s.sendMessage("§cQuantidade invalida!");
					return false;
				}
				final ItemStack ItemCaixa = this.caixa(config);
				ItemCaixa.setAmount(quantidade);
				if (args[2].equalsIgnoreCase("all")) {
					for (final Player p : Bukkit.getOnlinePlayers()) {
						final PlayerInventory inv = p.getInventory();
						inv.addItem(new ItemStack[] { ItemCaixa });
					}
					s.sendMessage("§a" + quantidade + "§a drop(s) '" + caixa
							+ "' enviado(s) para todos os players do server.");
					return false;
				}
				Player p = Bukkit.getPlayer(args[2]);
				if (p == null) {
					s.sendMessage("§cEste player n\u00e3o esta online no momento ou n\u00e3o existe!");
					return false;
				}
				if (getFreeSpaceInInventory(p) == 0) {
					p.sendMessage(
							"§cVocê não tem espaço no inventário para receber um Drop. Limpe o Inventário e Digite /pegardrop.");
					BDManager.atualizarPendentes(args[2], caixa, quantidade);
				} else if (getFreeSpaceInInventory(p) >= 1) {
					final PlayerInventory inv2 = p.getInventory();
					inv2.addItem(new ItemStack[] { ItemCaixa });
					s.sendMessage("§a" + quantidade + "§a drop(s) '" + caixa + "' enviado(s) com sucesso para "
							+ p.getName() + ".");
					return false;
				}
			} else {
				if (args.length < 1 || args.length > 3) {
					s.sendMessage("§cComando incorreto, use /givedrop <id> [quantia] [player]");
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
				if (args.length == 2) {
					int quantidade;
					try {
						quantidade = Integer.valueOf(args[1]);
					} catch (NumberFormatException e) {
						s.sendMessage("§cQuantidade invalida!");
						return false;
					}
					final Player p2 = (Player) s;
					final PlayerInventory inv3 = p2.getInventory();
					final ItemStack ItemCaixa2 = this.caixa(config);
					ItemCaixa2.setAmount(quantidade);
					if (getFreeSpaceInInventory(p2) == 0) {
						p2.sendMessage(
								"§cVocê não tem espaço no inventário para receber um Drop. Limpe o Inventário e Digite /pegardrop.");
						BDManager.atualizarPendentes(args[2], caixa, quantidade);
					} else if (getFreeSpaceInInventory(p2) >= 1) {
						inv3.addItem(new ItemStack[] { ItemCaixa2 });
					}
					s.sendMessage(
							"§a" + quantidade + "§a drop(s) '" + caixa + "' enviado(s) para o seu invent\u00e1rio.");
					return false;
				}
				if (args.length == 3) {
					int quantidade;
					try {
						quantidade = Integer.valueOf(args[1]);
					} catch (NumberFormatException e) {
						s.sendMessage("§cQuantidade invalida!");
						return false;
					}
					final ItemStack ItemCaixa = this.caixa(config);
					ItemCaixa.setAmount(quantidade);
					if (args[2].equalsIgnoreCase("all")) {
						for (final Player p : Bukkit.getOnlinePlayers()) {
							if (getFreeSpaceInInventory(p) == 0) {
								p.sendMessage(
										"§cVocê não tem espaço no inventário para receber um Drop. Limpe o Inventário e Digite /pegardrop.");
								BDManager.atualizarPendentes(p.toString(), caixa, quantidade);
							} else if (getFreeSpaceInInventory(p) >= 1) {
								final PlayerInventory inv = p.getInventory();
								inv.addItem(new ItemStack[] { ItemCaixa });
							}
						}
						s.sendMessage("§a" + quantidade + "§a drop(s) '" + caixa
								+ "' enviado(s) para todos os players do server.");
						return false;
					}
					Player p = Bukkit.getPlayer(args[2]);
					if (p == null) {
						s.sendMessage("§cEste player n\u00e3o esta online no momento ou n\u00e3o existe!");
						return false;
					} else {
						if (getFreeSpaceInInventory(p) == 0) {
							p.sendMessage(
									"§cVocê não tem espaço no inventário para receber um Drop. Limpe o Inventário e Digite /pegardrop.");
							BDManager.atualizarPendentes(args[2], caixa, quantidade);
						} else if (getFreeSpaceInInventory(p) >= 1) {
							final PlayerInventory inv2 = p.getInventory();
							inv2.addItem(new ItemStack[] { ItemCaixa });
							s.sendMessage("§a" + quantidade + "§a drop(s) '" + caixa + "' enviado(s) com sucesso para "
									+ p.getName() + ".");
							p.sendMessage(caixa);
							return false;
						}
					}
				} else {
					final Player p3 = (Player) s;
					if (getFreeSpaceInInventory(p3) == 0) {
						p3.sendMessage(
								"§cVocê não tem espaço no inventário para receber um Drop. Limpe o Inventário e Digite /pegardrop.");
						BDManager.atualizarPendentes(args[2], caixa, 1);
					} else if (getFreeSpaceInInventory(p3) >= 1) {
						final PlayerInventory inv4 = p3.getInventory();
						inv4.addItem(new ItemStack[] { this.caixa(config) });
						s.sendMessage("§a1 Drop '" + caixa + "' enviado para o seu invent\u00e1rio.");
					}
				}
			}
		}
		return false;
	}

	// Método para pegar o número de slots livres no inv do player
	private int getFreeSpaceInInventory(Player p) {
		int free = 0;
		ItemStack[] itens = p.getInventory().getContents();
		for (ItemStack item : itens) {
			if (item == null || item.getType() == Material.AIR)
				free++;
		}
		return free;
	}

	private ItemStack caixa(final FileConfiguration config) {
		final ItemStack caixa = config.getItemStack("Icone");
		final ItemMeta meta = caixa.getItemMeta();
		meta.setDisplayName(config.getString("Nome"));
		final List<String> lore = (List<String>) config.getStringList("Lore");
		if (!lore.isEmpty()) {
			meta.setLore((List) lore);
		}
		caixa.setItemMeta(meta);
		return caixa;
	}
}
