package it.uknowngino.moddedintegration.main;

import com.earth2me.essentials.Essentials;
import it.uknowngino.moddedintegration.commands.ModdedIntegrationCommand;
import it.uknowngino.moddedintegration.config.Config;
import it.uknowngino.moddedintegration.enums.ServerVersion;
import it.uknowngino.moddedintegration.utils.IntegrationUtils;
import it.uknowngino.moddedintegration.utils.LogUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class ModdedIntegration extends JavaPlugin {

	public static final ServerVersion SERVER_VERSION = ServerVersion.getCurrentServerVersion();
	public static final PluginManager PLUGIN_MANAGER = Bukkit.getPluginManager();

	private static ModdedIntegration plugin;
	private static Essentials essentials;

	public void onEnable() {
		
		plugin = this;
		
		if(PLUGIN_MANAGER.isPluginEnabled("Essentials")) {

			essentials = (Essentials) PLUGIN_MANAGER.getPlugin("Essentials");

			Config.reload();
			IntegrationUtils.populateItemsFile();

			registerCommands();
			LogUtils.log(Level.INFO, "The plugin has been enabled successfully with Implementation " + SERVER_VERSION + ".");

		} else {

			LogUtils.log(Level.SEVERE, "EssentialsX was not found in enabled plugins! Disabling ModdedIntegration...");
			PLUGIN_MANAGER.disablePlugin(this);
			
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

	public static Essentials getEssentials() {

		return essentials;

	}

}
