package br.com.sgcraft.drops;

import org.bukkit.plugin.java.*;

import br.com.sgcraft.drops.comandos.*;
import br.com.sgcraft.drops.eventos.*;
import br.com.sgcraft.drops.utils.*;

import org.bukkit.event.*;

import java.io.File;

import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.command.*;

public class Main extends JavaPlugin implements Listener
{
    public static Main aqui;
	public static Plugin plugin;
	public MySQL mysql;

    public void onEnable() {
        this.instanceMain();
        this.registrarEventos();
        this.registrarComandos();
        this.salvarConfiguracao();
        final File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                this.saveResource("config_template.yml", false);
                final File file2 = new File(this.getDataFolder(), "config_template.yml");
                file2.renameTo(new File(this.getDataFolder(), "config.yml"));
            }
            catch (Exception ex) {}
        }
        Drops.carregarCaixas();
            Main.aqui.mysql = new MySQL(this.getConfig().getString("MySQL.Usuario"), this.getConfig().getString("MySQL.Senha"), this.getConfig().getString("MySQL.Database"), this.getConfig().getString("MySQL.Host"));
            Main.aqui.getLogger().info("MySQL Habilitado!");
    }
    
    public void onDisable() {
        HandlerList.unregisterAll((Listener)this);
		this.reloadConfig();
    }
    
    public void instanceMain() {
        Main.aqui = this;
    }
    
	public void salvarConfiguracao() {
        DataManager.createFolder("caixas");
	}
    
    public void registrarEventos() {
        final PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents((Listener)new ComandosListener(), (Plugin)this);
        pm.registerEvents((Listener)new AbrirDrop(), (Plugin)this);
        pm.registerEvents((Listener)new EventoEntregarVIP(), (Plugin)this);
    }
    
    public void registrarComandos() {
        this.getCommand("verdrops").setExecutor((CommandExecutor)new ComandoDrops());
        this.getCommand("deldrop").setExecutor((CommandExecutor)new ComandoDelDrops());
        this.getCommand("editardrop").setExecutor((CommandExecutor)new ComandoEditarDrops());
        this.getCommand("givedrop").setExecutor((CommandExecutor)new ComandoGiveDrop());
        this.getCommand("criardrop").setExecutor((CommandExecutor)new ComandoCriarDrops());
        this.getCommand("pegardrop").setExecutor((CommandExecutor)new ComandoPegarDrop());
    }
    public MySQL getMysql() {
        return this.mysql;
    }
}
