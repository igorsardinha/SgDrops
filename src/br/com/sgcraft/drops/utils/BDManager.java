package br.com.sgcraft.drops.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
public class BDManager {

	private static int giveMega;
	private static int giveUltra;
	private static int giveEpic;
	private static int giveMVP;
	private static int diasMega;
	private static int diasEpic;
	private static int diasUltra;
	private static int diasMVP;
	private static int qtdMega;
	private static int restMega;
	private static int qtdEpic;
	private static int restEpic;
	private static int qtdUltra;
	private static int restUltra;
	private static int qtdMVP;
	private static int restMVP;
	private static int pdtMega;
	private static int pdtEpic;
	private static int pdtUltra;
	private static int pdtMVP;

	
    public static boolean checarContaExiste(final String nome) {
        try {
            final PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM SgDROPS_PENDENTES WHERE Nick=?");
            ps.setString(1, nome.toLowerCase());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
	
    public static Integer checarAtivo(final String nome) {
        try {
            final PreparedStatement ps = MySQL.connection.prepareStatement("SELECT * FROM SgDROPS_IMPORT WHERE Nick=?");
            ps.setString(1, nome.toLowerCase());
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                final int n = rs.getInt("RECEBIDO");
                return n;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
		return 1;
    }

	public static void ativarVIP(final String Nick) {
		try {
			final PreparedStatement ps = MySQL.connection
					.prepareStatement("SELECT * FROM SgDROPS_IMPORT WHERE Nick=?;");
			ps.setString(1, Nick.toLowerCase());
			final ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				diasMega = rs.getInt("MEGA");
				diasEpic = rs.getInt("EPIC");
				diasUltra = rs.getInt("ULTRA");
				diasMVP = rs.getInt("MVP");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// ATIVAR MEGA
		qtdMega = diasMega / 30;
		restMega = diasMega % 30;

		if (qtdMega != 0) {
			for (int loop = 1; loop <= qtdMega; loop++) {
				Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
						"darvip " + Nick + " mega " + 30 + "");
			}
		}
		if (restMega > 0) {
			Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
					"darvip " + Nick + " mega " + restMega + "");
		}

		// ATIVAR EPIC
		qtdEpic = diasEpic / 30;
		restEpic = diasEpic % 30;

		if (qtdEpic != 0) {
			for (int loop = 1; loop <= qtdEpic; loop++) {
				Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
						"darvip " + Nick + " epic " + 30 + "");
			}
		}
		if (restEpic > 0) {
			Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
					"darvip " + Nick + " epic " + restEpic + "");
		}

		// ATIVAR Ultra
		qtdUltra = diasUltra / 30;
		restUltra = diasUltra % 30;

		if (qtdUltra != 0) {
			for (int loop = 1; loop <= qtdUltra; loop++) {
				Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
						"darvip " + Nick + " ultra " + 30 + "");
			}
		}
		if (restUltra > 0) {
			Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
					"darvip " + Nick + " ultra " + restUltra + "");
		}
		// ATIVAR MVP
		qtdMVP = diasMVP / 30;
		restMVP = diasMVP % 30;
		if (qtdMVP != 0) {
			for (int loop = 1; loop <= qtdMVP; loop++) {
				Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), "darvip " + Nick + " mvp " + 30 + "");
			}
		}
		if (restMVP > 0) {
			Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
					"darvip " + Nick + " mvp " + restMVP + "");
		}
		BDManager.changeAtivado(Nick);
	}

	public static void changeAtivado(final String Nick) {
		try {
			final PreparedStatement ps = MySQL.connection
					.prepareStatement("UPDATE SgDROPS_IMPORT SET RECEBIDO = ? WHERE nick = ?;");
			ps.setInt(1, 1);
			ps.setString(2, Nick);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void pegarPendentes(String nome) {
		try {
			final PreparedStatement ps = MySQL.connection
					.prepareStatement("SELECT * FROM SgDROPS_PENDENTES WHERE Nick=?;");
			ps.setString(1, nome.toLowerCase());
			final ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				pdtMega = rs.getInt("MEGA");
				pdtEpic = rs.getInt("EPIC");
				pdtUltra = rs.getInt("ULTRA");
				pdtMVP = rs.getInt("MVP");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void atualizarPendentes(final String nick, final String drop, final int qtd) {
		BDManager.pegarPendentes(nick);
		if (drop.equalsIgnoreCase("mega")) {
			try {
				final PreparedStatement ps = MySQL.connection
						.prepareStatement("UPDATE SgDROPS_PENDENTES SET MEGA = ? WHERE nick = ?;");
				ps.setInt(1, pdtMega + qtd);
				ps.setString(2, nick);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (drop.equalsIgnoreCase("epic")) {
			try {
				final PreparedStatement ps = MySQL.connection
						.prepareStatement("UPDATE SgDROPS_PENDENTES SET EPIC = ? WHERE nick = ?;");
				ps.setInt(1, pdtEpic + qtd);
				ps.setString(2, nick);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (drop.equalsIgnoreCase("ultra")) {
			try {
				final PreparedStatement ps = MySQL.connection
						.prepareStatement("UPDATE SgDROPS_PENDENTES SET ULTRA = ? WHERE nick = ?;");
				ps.setInt(1, pdtUltra + qtd);
				ps.setString(2, nick);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (drop.equalsIgnoreCase("mvp")) {
			try {
				final PreparedStatement ps = MySQL.connection
						.prepareStatement("UPDATE SgDROPS_PENDENTES SET MVP = ? WHERE nick = ?;");
				ps.setInt(1, pdtMVP + qtd);
				ps.setString(2, nick);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void zerarPendentes(final String nick, final String drop) {
		if (drop.equalsIgnoreCase("mega")) {
			try {
				final PreparedStatement ps = MySQL.connection
						.prepareStatement("UPDATE SgDROPS_PENDENTES SET MEGA = ? WHERE nick = ?;");
				ps.setInt(1, 0);
				ps.setString(2, nick);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (drop.equalsIgnoreCase("epic")) {
			try {
				final PreparedStatement ps = MySQL.connection
						.prepareStatement("UPDATE SgDROPS_PENDENTES SET EPIC = ? WHERE nick = ?;");
				ps.setInt(1, 0);
				ps.setString(2, nick);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (drop.equalsIgnoreCase("ultra")) {
			try {
				final PreparedStatement ps = MySQL.connection
						.prepareStatement("UPDATE SgDROPS_PENDENTES SET ULTRA = ? WHERE nick = ?;");
				ps.setInt(1, 0);
				ps.setString(2, nick);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (drop.equalsIgnoreCase("mvp")) {
			try {
				final PreparedStatement ps = MySQL.connection
						.prepareStatement("UPDATE SgDROPS_PENDENTES SET MVP = ? WHERE nick = ?;");
				ps.setInt(1, 0);
				ps.setString(2, nick);
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void darDropPendente(String nome) {
		try {
			final PreparedStatement ps = MySQL.connection
					.prepareStatement("SELECT * FROM SgDROPS_PENDENTES WHERE Nick=?;");
			ps.setString(1, nome.toLowerCase());
			final ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				giveMega = rs.getInt("MEGA");
				giveEpic = rs.getInt("EPIC");
				giveUltra = rs.getInt("ULTRA");
				giveMVP = rs.getInt("MVP");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// dar drop MEGA
		if (giveMega >= 1) {
			Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
					"givedrop Mega " + giveMega + " " + nome + "");
			BDManager.zerarPendentes(nome, "Mega");
		}
		// dar drop EPIC
		if (giveEpic >= 1) {
			Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
					"givedrop epic " + giveEpic + " " + nome + "");
			BDManager.zerarPendentes(nome, "Epic");
		}

		// dar drop ULTRA
		if (giveUltra >= 1) {
			Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
					"givedrop ultra " + giveUltra + " " + nome + "");
			BDManager.zerarPendentes(nome, "Ultra");
		}
		// dar drop MVP
		if (giveMVP >= 1) {
			Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(),
					"givedrop mvp " + giveMVP + " " + nome + "");
			BDManager.zerarPendentes(nome, "MVP");
		}

	}

}
