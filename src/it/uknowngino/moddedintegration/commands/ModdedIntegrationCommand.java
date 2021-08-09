package it.uknowngino.moddedintegration.commands;

import it.uknowngino.moddedintegration.config.Config;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import it.uknowngino.moddedintegration.functions.ChatUtils;
import it.uknowngino.moddedintegration.functions.IntegrationUtils;
import it.uknowngino.moddedintegration.main.ModdedIntegration;
import org.bukkit.entity.Player;

public class ModdedIntegrationCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if(sender instanceof Player && cmd.getName().equalsIgnoreCase("moddedintegration")) {

			Player player = (Player) sender;

			if(player.hasPermission("moddedintegration.command.moddedintegration")) {

				String useMessage = "\n &b» &b&lMODDEDINTEGRATION COMMANDS\n \n&b&l ■ &7/moddedintegration info &fPrints Plugin Information.\n&b&l ■ &7/moddedintegration reset &fResets items.csv.\n&b&l ■ &7/moddedintegration populate &fForces the population.\n&b&l ■ &7/moddedintegration reload &fReloads the config.\n ";

				if(args.length > 0) {

					if(args[0].equalsIgnoreCase("info")) {

						player.sendMessage(ChatUtils.color("&8(&b&l!&8) &7This server is running &fModdedIntegration &7version &f" + ModdedIntegration.getInstance().getDescription().getVersion() + " &7by &fUknownGino&7 with &f" + IntegrationUtils.newItems.size() + " newly loaded materials&7."));
						player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);

					} else if(args[0].equalsIgnoreCase("reset")) {

						if(IntegrationUtils.resetCSV()) {

							player.sendMessage(ChatUtils.color("&a&l» SUCCESS! &7The Essentials' &fitems.csv file&7 has been reset successfully."));
							player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);

						} else {

							player.sendMessage(ChatUtils.color("&c&l» ERROR! &7An error occurred while resetting the Essentials' &fitems.csv file&7! Check the Console for more information."));
							player.playSound(player.getLocation(), Sound.ENTITY_BAT_DEATH, 100, 1);

						}

					} else if(args[0].equalsIgnoreCase("populate")) {

						IntegrationUtils.populateCSV();
						player.sendMessage(ChatUtils.color("&8(&b&l!&8) &7The &fEssentialsX's items.csv&7 has been forcibly populated with &f" + IntegrationUtils.newItems.size() + " newly loaded materials&7."));
						player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);

					} else if(args[0].equalsIgnoreCase("reload")) {

						Config.reload();
						player.sendMessage(ChatUtils.color("&8(&b&l!&8) &7The config has been reloaded &fsuccessfully&7."));
						player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 100, 1);

					} else {

						player.sendMessage(ChatUtils.color(useMessage));

					}

				} else {

					player.sendMessage(ChatUtils.color(useMessage));

				}

			} else {

				player.sendMessage(ChatUtils.color("&8[&4⚠&8] &7You do not have permission to use that command!"));

			}

		}
		
		return false;
		
	}

}
