package it.uknowngino.moddedintegration.functions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import it.uknowngino.moddedintegration.main.ModdedIntegration;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class IntegrationUtils {
	
	public static Integer loadedMaterials = 0;

	@SuppressWarnings("deprecation")
	public static void populateCSV() {
		
		File itemsFile = new File(Bukkit.getPluginManager().getPlugin("Essentials").getDataFolder() + "/items.csv");
		
		if(!itemsFile.isDirectory() && itemsFile.isFile() && itemsFile.exists()) {
			
			ArrayList<String> oldItems = new ArrayList<String>();
			
			try {
				
				Scanner scanner = new Scanner(itemsFile);
				
				while(scanner.hasNextLine()) {
					
					oldItems.add(scanner.nextLine().split(",")[0].toUpperCase());
					
				}
				
				scanner.close();
				
				BufferedWriter writer = new BufferedWriter(new FileWriter(itemsFile, true));
				
				loadedMaterials = 0;
				
				for(Map.Entry<ResourceLocation, Item> itemEntry : ForgeRegistries.ITEMS.getEntries()) {
					
					Material item = Material.getMaterial(itemEntry.getKey().toString().toUpperCase().replaceAll("(:|\\s)", "_").replaceAll("\\W", ""));
					
					if(item != null) {
						
						loadedMaterials++;
						
						if(!oldItems.contains(item.toString())) {
							
							writer.append("\n" + item.toString().toLowerCase() + "," + item.getId() + ",0");
							
						}
	
					}
					
				}
				
				writer.close();
				
				Bukkit.getScheduler().runTaskLater(ModdedIntegration.getInstance(), new Runnable() {
					
		            @Override
		            public void run() {
		            	
		            	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "essentials reload");
		            	
		            }
		            
		        }, 20);
				
			} catch(Exception e) {
				
				e.printStackTrace();
				
			}
			
		} else {
			
			System.out.println("ModdedIntegration » EssentialsX's items.csv can't be found!");
			Bukkit.getPluginManager().disablePlugin(ModdedIntegration.getInstance());
			
		}
		
		
		
	}
	
}
