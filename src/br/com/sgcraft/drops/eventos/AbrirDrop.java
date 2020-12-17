package br.com.sgcraft.drops.eventos;

import org.bukkit.event.player.*;
import org.bukkit.event.block.*;

import java.io.*;
import org.bukkit.configuration.file.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.*;

import br.com.sgcraft.drops.*;
import br.com.sgcraft.drops.utils.*;

import java.util.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;

public class AbrirDrop implements Listener {
	private Inventory inv;

	@EventHandler
	public void PlayerInteract(final PlayerInteractEvent e) {
		if (e.getItem() == null) {
			return;
		}
		if (!e.getItem().hasItemMeta()) {
			return;
		}
		if (e.getAction().equals((Object) Action.RIGHT_CLICK_AIR)
				|| e.getAction().equals((Object) Action.RIGHT_CLICK_BLOCK)) {
			for (final ItemStack item : Drops.caixas.keySet()) {
				if (e.getItem().isSimilar(item)) {
					e.setCancelled(true);
					final Player p = e.getPlayer();
					final ItemStack caixa = e.getItem().clone();
					caixa.setAmount(1);
					final String id = Drops.caixas.get(caixa);
					final File file = DataManager.getFile(id, "caixas");
					final FileConfiguration config = DataManager.getConfiguration(file);
					if (p.getInventory().getItemInHand().getAmount() > 1) {
						p.sendMessage("§cVocê não pode ter mais de 1 DROP na mão!");
					}
					else {
					this.abrirCaixa(config, p);
				}
				}
			}
		}
	}

	private void abrirCaixa(final FileConfiguration config, final Player p) {
		inv = Bukkit.createInventory((InventoryHolder) null, 54, "§8Conteúdo do Drop...");

		final Set<String> ITENS = (Set<String>) config.getConfigurationSection("Itens").getKeys(false);
		final int nitens = ITENS.size();
		if (nitens < 1) {
			final ItemStack vazio = new ItemStack(Material.BARRIER, 1);
			final ItemMeta vazioMeta = (ItemMeta) vazio.getItemMeta();
			vazioMeta.setDisplayName("§cEsse Drop estava Vazio!");
			vazio.setItemMeta((ItemMeta) vazioMeta);
			inv.setItem(13, vazio);
			p.updateInventory();
			return;
		} else {
			int start = 1;
			for (start = 0; start < 36; start++) {
				inv.setItem(start, config.getItemStack("Itens." + start));
			}
			p.openInventory(inv);

			final ItemStack give_drop = new ItemStack(Material.STORAGE_MINECART, 1);
			final ItemMeta drops = (ItemMeta) give_drop.getItemMeta();
			drops.setDisplayName("§6Pegar os Itens do DROP");
			ArrayList<String> drops_lore = new ArrayList<>();
			drops_lore.add("§eClique aqui para pegar os Itens!");
			drops_lore.add("§4ATENÇÃO: §cVocê só pode pegar um Drop com Inventário Vazio!");
			drops.setLore(drops_lore);
			give_drop.setItemMeta((ItemMeta) drops);
			inv.setItem(48, give_drop);

			final ItemStack fechar_drop = new ItemStack(Material.BARRIER, 1);
			final ItemMeta fecharMeta = (ItemMeta) fechar_drop.getItemMeta();
			fecharMeta.setDisplayName("§4Fechar Pré-Visualização");
			fechar_drop.setItemMeta((ItemMeta) fecharMeta);
			inv.setItem(50, fechar_drop);
			p.updateInventory();
		}
	}

	@EventHandler
	public void InventoryClick(final InventoryClickEvent e) {

		Player player = (Player) e.getWhoClicked();
		if (e.getClickedInventory() != null) {
			if (e.getInventory().getName().contains("§8Conteúdo do Drop...")) {
				e.setCancelled(true);
			if (e.getCurrentItem() == null)
				return;
			if (e.getCurrentItem().getType() == Material.AIR)
				return;
			if (e.getCurrentItem().getItemMeta().getDisplayName() == null)
				return;
			if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§6Pegar os Itens do DROP")) {
				if (e.getCurrentItem() == null)
					return;
				if (e.getCurrentItem().getType() == Material.AIR)
					return;
				if (e.getCurrentItem().getItemMeta().getDisplayName() == null)
					return;
				if (getFreeSpaceInInventory(player) < 35) {
					player.sendMessage("§cSeu Inventário não tem espaço suficiente!");
				}
				else {
					this.removerCaixa(player);
					this.finalizarCaixa(player);
				}
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§4Fechar Pré-Visualização")) {
				player.closeInventory();
			}
		}
		}
	}

	private void finalizarCaixa(final Player p) {
		p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);

		p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
		int itensDrop = 1;
		for (itensDrop = 0; itensDrop < 36; itensDrop++) {
			final ItemStack itensReceber = inv.getItem(itensDrop);
			if (itensReceber != null) {
				p.getInventory().addItem(new ItemStack[] { itensReceber });
				p.closeInventory();
			}
		}
		p.sendMessage("§aVocê recebeu os itens do DROP!");
	}

	private void removerCaixa(final Player p) {
		final ItemStack ap = p.getItemInHand();
		if (ap.getAmount() == 1) {
			p.setItemInHand(new ItemStack(Material.AIR));
		} else {
			ap.setAmount(ap.getAmount() - 1);
			p.setItemInHand(ap);
		}
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
	
}
