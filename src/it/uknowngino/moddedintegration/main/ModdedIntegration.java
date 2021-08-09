package it.uknowngino.moddedintegration.main;

import it.uknowngino.moddedintegration.commands.ModdedIntegrationCommand;
import it.uknowngino.moddedintegration.config.Config;
import it.uknowngino.moddedintegration.functions.IntegrationUtils;
import it.uknowngino.moddedintegration.functions.LogUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class ModdedIntegration extends JavaPlugin {
	
	static ModdedIntegration plugin;
	
	public void onEnable() {
		
		plugin = this;
		
		if(Bukkit.getPluginManager().isPluginEnabled("Essentials")) {

			Config.reload();
			IntegrationUtils.populateCSV();
			registerCommands();
			LogUtils.log(Level.INFO, "The plugin has been enabled successfully.");

		} else {

			LogUtils.log(Level.SEVERE, "EssentialsX was not found in enabled plugins! Disabling ModdedIntegration...");
			Bukkit.getPluginManager().disablePlugin(this);
			
		}

	}
	
	public void onDisable() {

		LogUtils.log(Level.INFO, "The plugin has been disabled.");
		
	}

	private void registerCommands() {

		getCommand("moddedintegration").setExecutor(new ModdedIntegrationCommand());

	}

	public static ModdedIntegration getInstance() {
		
		return plugin;
		
	}

}
