package it.uknowngino.moddedintegration.commands;

import it.uknowngino.moddedintegration.config.Config;
import it.uknowngino.moddedintegration.constructors.PopulationResult;
import it.uknowngino.moddedintegration.main.ModdedIntegration;
import it.uknowngino.moddedintegration.utils.ChatUtils;
import it.uknowngino.moddedintegration.utils.IntegrationUtils;
import it.uknowngino.moddedintegration.utils.LogUtils;
import org.bukkit.Sound;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class ModdedIntegrationCommand implements CommandExecutor, TabCompleter {

	private static final String USE_MESSAGE = new StringBuilder()
			.append("\n &b» &b&lMODDEDINTEGRATION COMMANDS")
			.append("\n ")
			.append("\n&b&l ■ &7/moddedintegration info &fPrints Plugin Information.")
			.append("\n&b&l ■ &7/moddedintegration reset &fResets ").append(IntegrationUtils.IMPLEMENTATION.getItemsFileName()).append(".")
			.append("\n&b&l ■ &7/moddedintegration populate &fForces the population.")
			.append("\n&b&l ■ &7/moddedintegration reload &fReloads the config.")
			.append("\n ")
			.toString();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if(cmd.getName().equalsIgnoreCase("moddedintegration")) {

			if(sender instanceof Player) {

				Player player = (Player) sender;

				if(player.hasPermission("moddedintegration.command.moddedintegration")) {

					if(args.length > 0) {

						switch(args[0].toLowerCase()) {

							case "info":
								player.sendMessage(ChatUtils.color("&8(&b&l!&8) &7This server is running &fModdedIntegration &7version &f" + ModdedIntegration.getInstance().getDescription().getVersion() + " &7by &fUknownGino&7 on &fImplementation " + ModdedIntegration.SERVER_VERSION + "&7."));
								player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);
								break;

							case "reset":

								if(IntegrationUtils.resetItemsFile()) {

									player.sendMessage(ChatUtils.color("&a&l» SUCCESS! &7The EssentialsX's &fitems file&7 has been reset successfully."));
									player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);

								} else {

									player.sendMessage(ChatUtils.color("&c&l» ERROR! &7An error occurred while resetting the EssentialsX's &fitems file&7! Check the Console for more information."));
									player.playSound(player.getLocation(), Sound.ENTITY_BAT_DEATH, 100, 1);

								}

								break;

							case "populate":

								PopulationResult populationResult = IntegrationUtils.populateItemsFile();

								if(populationResult == null) {

									player.sendMessage(ChatUtils.color("&c&l» ERROR! &7An error occurred while populating the EssentialsX's &fitems file&7! Check the Console for more information."));
									player.playSound(player.getLocation(), Sound.ENTITY_BAT_DEATH, 100, 1);

								} else {

									player.sendMessage(ChatUtils.color("&8(&b&l!&8) &7The &fEssentialsX's items&7 has been forcibly populated with &f" + populationResult.getPopulatedItems() + " newly loaded materials&7 in &f" + populationResult.getTimeTook() + "ms&7."));
									player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1);

								}

								break;

							case "reload":
								Config.reload();
								player.sendMessage(ChatUtils.color("&8(&b&l!&8) &7The config has been reloaded &fsuccessfully&7."));
								player.playSound(player.getLocation(), Sound.BLOCK_NOTE_PLING, 100, 1);
								break;

							default:
								player.sendMessage(ChatUtils.color(USE_MESSAGE));
								break;

						}

					} else {

						player.sendMessage(ChatUtils.color(USE_MESSAGE));

					}

				} else {

					player.sendMessage(ChatUtils.color("&8[&4⚠&8] &7You do not have permission to use that command!"));

				}

			} else {

				LogUtils.log(Level.WARNING, "This command can only be run by players!");

			}

		}
		
		return false;
		
	}


	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		return args.length == 1 ? Arrays.asList("info", "reset", "populate", "reload") : Collections.emptyList();

	}

}
