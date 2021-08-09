package it.uknowngino.moddedintegration.functions;

import org.bukkit.Bukkit;

import java.util.logging.Level;

public class LogUtils {

    public static void log(Level level, String message) {

        Bukkit.getLogger().log(level, "[ModdedIntegration] " + message);

    }

}
