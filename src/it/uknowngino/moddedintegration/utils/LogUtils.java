package it.uknowngino.moddedintegration.utils;

import org.bukkit.Bukkit;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtils {

    private static final Logger LOGGER = Bukkit.getLogger();

    public static void log(Level level, String message) {

        LOGGER.log(level, "[ModdedIntegration] " + message);

    }

}
