package it.uknowngino.moddedintegration.utils;

import com.earth2me.essentials.Essentials;
import it.uknowngino.moddedintegration.config.Config;
import it.uknowngino.moddedintegration.constructors.PopulationResult;
import it.uknowngino.moddedintegration.implementation.PopulationImplementation;
import it.uknowngino.moddedintegration.main.ModdedIntegration;
//import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Level;

import static it.uknowngino.moddedintegration.main.ModdedIntegration.SERVER_VERSION;

public class IntegrationUtils {

	static { assert SERVER_VERSION != null; }
	public static final PopulationImplementation IMPLEMENTATION = SERVER_VERSION.getImplementation();

	public static final File ITEMS_FILE = new File(Bukkit.getPluginManager().getPlugin("Essentials").getDataFolder() + File.separator + IMPLEMENTATION.getItemsFileName());

	private static final BukkitScheduler SCHEDULER = Bukkit.getScheduler();
	private static final ConsoleCommandSender CONSOLE_SENDER = Bukkit.getConsoleSender();

	public static PopulationResult populateItemsFile() {

		if(!ITEMS_FILE.isDirectory() && ITEMS_FILE.isFile() && ITEMS_FILE.exists()) {

			try {

				PopulationResult populationResult = IMPLEMENTATION.populateFile();

				LogUtils.log(Level.INFO, populationResult.getPopulatedItems() + " new items have been loaded and added to the EssentialsX's " + IMPLEMENTATION.getItemsFileName() + " in " + populationResult.getTimeTook() + "ms. Reloading EssentialsX...");
				reloadEssentials();

				return populationResult;

			} catch (Exception e) {

				LogUtils.log(Level.SEVERE, "An error occurred while adding items to the EssentialsX's " + IMPLEMENTATION.getItemsFileName() + " file: " + e.getMessage());
				e.printStackTrace();

			}

		} else {
			
			LogUtils.log(Level.SEVERE, "EssentialsX's " + IMPLEMENTATION.getItemsFileName() + " can't be found! Disabling ModdedIntegration...");
			Bukkit.getPluginManager().disablePlugin(ModdedIntegration.getInstance());
			
		}

		return null;
		
	}

	public static boolean resetItemsFile() {

		try {

			InputStream defaultFile = Essentials.class.getResourceAsStream("/" + IMPLEMENTATION.getItemsFileName());

			if(defaultFile != null) {

				if(ITEMS_FILE.exists() && ITEMS_FILE.delete()) {

					Files.copy(defaultFile, ITEMS_FILE.toPath());
					//IOUtils.copy(defaultFile, new FileOutputStream(ITEMS_FILE));
					LogUtils.log(Level.INFO, "The EssentialsX's " + IMPLEMENTATION.getItemsFileName() + " file has been reset to the default values. Reloading EssentialsX...");
					reloadEssentials();

					return true;

				} else {

					LogUtils.log(Level.SEVERE, "Unable to delete the old EssentialsX's " + IMPLEMENTATION.getItemsFileName() + " file!");

				}

			} else {

				LogUtils.log(Level.SEVERE, "An error occurred while reading the default EssentialsX's " + IMPLEMENTATION.getItemsFileName() + " file!");

			}

		} catch (IOException e) {

			LogUtils.log(Level.SEVERE, "An error occurred while resetting to the default EssentialsX's " + IMPLEMENTATION.getItemsFileName() + " file: " + e.getMessage());

		}

		return false;

	}

	public static void reloadEssentials() {

		SCHEDULER.runTaskLater(ModdedIntegration.getInstance(), () -> Bukkit.dispatchCommand(CONSOLE_SENDER, "essentials reload"), Config.reload_delay);

	}

	public static String normalizeMaterialName(String materialName) {

		return materialName.toUpperCase().replaceAll("(:|\\s)", "_").replaceAll("\\W", "");

	}
	
}
