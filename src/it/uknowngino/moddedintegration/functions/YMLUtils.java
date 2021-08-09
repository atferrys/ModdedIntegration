package it.uknowngino.moddedintegration.functions;

import it.uknowngino.moddedintegration.main.ModdedIntegration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class YMLUtils {

    public static Integer readInteger(String file, String node) {

        return YamlConfiguration.loadConfiguration(new File(ModdedIntegration.getInstance().getDataFolder() + File.separator + file)).getInt(node);

    }

    public static void writeInteger(String file, String node, Integer integer) {

        try {

            FileConfiguration yml = YamlConfiguration.loadConfiguration(new File(ModdedIntegration.getInstance().getDataFolder() + File.separator + file));
            yml.set(node, integer);
            yml.save(new File(ModdedIntegration.getInstance().getDataFolder() + File.separator + file));

        } catch (IOException e) {

            LogUtils.log(Level.SEVERE, "An error occurred while writing to an YML file:: " + e.getMessage());

        }

    }

}
