package it.uknowngino.moddedintegration.functions;

import it.uknowngino.moddedintegration.config.Config;
import it.uknowngino.moddedintegration.main.ModdedIntegration;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class IntegrationUtils {

	public static List<Material> newItems = new ArrayList<>();

	@SuppressWarnings("deprecation")
	public static void populateCSV() {
		
		File itemsFile = new File(Bukkit.getPluginManager().getPlugin("Essentials").getDataFolder() + File.separator + "items.csv");
		
		if(!itemsFile.isDirectory() && itemsFile.isFile() && itemsFile.exists()) {

			try {

				List<Material> oldItems = Files.readAllLines(itemsFile.toPath(), StandardCharsets.UTF_8).stream().map(line -> Material.getMaterial(line.split(",")[0].toUpperCase())).filter(Objects::nonNull).collect(Collectors.toList());
				newItems = ForgeRegistries.ITEMS.getEntries().stream().map(itemEntry -> Material.getMaterial(itemEntry.getKey().toString().toUpperCase().replaceAll("(:|\\s)", "_").replaceAll("\\W", ""))).filter(material -> material != null && !oldItems.contains(material)).collect(Collectors.toList());
				BufferedWriter writer = new BufferedWriter(new FileWriter(itemsFile, true));

				for(Material material : newItems) writer.append("\n" + material.toString().toLowerCase() + "," + material.getId() + ",0");

				writer.close();
				LogUtils.log(Level.INFO, newItems.size() + " new items have been loaded and added to the Essentials' items.csv. Reloading EssentialsX...");
				Bukkit.getScheduler().runTaskLater(ModdedIntegration.getInstance(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "essentials reload"), Config.reload_delay);

			} catch(IOException e) {

				LogUtils.log(Level.SEVERE, "An error occurred while adding items to the EssentialsX's items.csv file: " + e.getMessage());

			}

		} else {
			
			LogUtils.log(Level.SEVERE, "EssentialsX's items.csv can't be found! Disabling ModdedIntegration...");
			Bukkit.getPluginManager().disablePlugin(ModdedIntegration.getInstance());
			
		}
		
	}

	public static Boolean resetCSV() {

		try {

			InputStream inputStream = ModdedIntegration.getInstance().getClass().getResourceAsStream("/items.csv");

			if(inputStream != null) {

				File csvFile = new File(Bukkit.getPluginManager().getPlugin("Essentials").getDataFolder() + File.separator + "items.csv");

				if(csvFile.exists()) csvFile.delete();

				IOUtils.copy(inputStream, new FileOutputStream(csvFile));
				LogUtils.log(Level.INFO, "The EssentialsX's items.csv file has been reset to the default values. Reloading EssentialsX...");
				Bukkit.getScheduler().runTaskLater(ModdedIntegration.getInstance(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "essentials reload"), Config.reload_delay);

				return true;

			} else {

				LogUtils.log(Level.SEVERE, "An error occurred while reading the default EssentialsX's items.csv file!");

			}

		} catch (IOException e) {

			LogUtils.log(Level.SEVERE, "An error occurred while resetting to the default EssentialsX's items.csv file: " + e.getMessage());

		}

		return false;

	}
	
}
