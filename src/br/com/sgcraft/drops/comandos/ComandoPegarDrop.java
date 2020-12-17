package br.com.sgcraft.drops.comandos;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.com.sgcraft.drops.Main;
import br.com.sgcraft.drops.utils.BDManager;

public class ComandoPegarDrop implements CommandExecutor {

	public boolean onCommand(final CommandSender sender, final Command cmd, final String lbl, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("pegardrop")) {
			Player p = ((OfflinePlayer) sender).getPlayer();
			if (!(sender instanceof Player)) {
				sender.sendMessage("§cO console nao pode utilizar este comando!");
				return false;
			} else {
				sender.sendMessage("§6[DROPS]§c Aguarde 3 segundos...");
				Main.aqui.getServer().getScheduler().scheduleSyncDelayedTask(Main.aqui, new Runnable() {
				    @Override
		    public void run() {
				    	if (getFreeSpaceInInventory(p) >= 0 && getFreeSpaceInInventory(p) <= 35) {
							p.sendMessage(
									"§cVocê não tem espaço no inventário para receber um Drop. Limpe todo o Inventário antes de Executar essa Ação");
						} else if (getFreeSpaceInInventory(p) >= 35) {
				    	BDManager.darDropPendente(sender.getName().toString());
						sender.sendMessage(
								"§aVocê recebeu todos os seus Drops Pendentes (OBS: Não é possível reverter esta Ação)");	
				    }
				    }
				}, 20*3);
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
}
