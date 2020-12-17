package br.com.sgcraft.drops.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
public class EventoEntregarVIP implements Listener {
	@EventHandler
	public static void aoEntrar(final PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		if (BDManager.checarAtivo(p.toString()) != null) {
			if (BDManager.checarAtivo(p.getName().toString()) == 0)
				BDManager.ativarVIP(p.getName().toString());
		}
		if (!BDManager.checarContaExiste(p.getName())) {
			try {
				final PreparedStatement ps = MySQL.connection
						.prepareStatement("INSERT INTO SgDROPS_PENDENTES (Nick,MEGA,EPIC,ULTRA,MVP) VALUES (?,?,?,?,?);");
				ps.setString(1, p.getName().toLowerCase());
				ps.setInt(2, 0);
				ps.setInt(3, 0);
				ps.setInt(4, 0);
				ps.setInt(5, 0);
				ps.executeUpdate();
			} catch (SQLException ev) {
				ev.printStackTrace();
			}
		}
	}
}
