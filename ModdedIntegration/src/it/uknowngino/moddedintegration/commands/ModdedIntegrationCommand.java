package it.uknowngino.moddedintegration.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import it.uknowngino.moddedintegration.functions.ChatUtils;
import it.uknowngino.moddedintegration.functions.IntegrationUtils;
import it.uknowngino.moddedintegration.main.ModdedIntegration;

public class ModdedIntegrationCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if(cmd.getName().equalsIgnoreCase("moddedintegration")) {
			
			sender.sendMessage(ChatUtils.color("&8(&b&l!&8) &7This server is running &fModdedIntegration &7version &f" + ModdedIntegration.getInstance().getDescription().getVersion() + " &7by &fUknownGino&7 with &f" + IntegrationUtils.loadedMaterials + " materials&7 currently loaded."));
			
		}
		
		return false;
		
	}

}
