package it.uknowngino.moddedintegration.utils;

import it.uknowngino.moddedintegration.main.ModdedIntegration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class YMLUtils {

    public static File getConfigurationFile(String file) {

        return new File(ModdedIntegration.getInstance().getDataFolder() + File.separator + file);

    }

    public static FileConfiguration getConfiguration(String file) {

        return YamlConfiguration.loadConfiguration(getConfigurationFile(file));

    }

    public static int readInteger(String file, String node) {

        return getConfiguration(file).getInt(node);

    }

    public static void writeInteger(String file, String node, int integer) {

        try {

            FileConfiguration yml = getConfiguration(file);
            yml.set(node, integer);
            yml.save(getConfigurationFile(file));

        } catch (IOException e) {

            LogUtils.log(Level.SEVERE, "An error occurred while writing to an YML file: " + e.getMessage());

        }

    }

}
