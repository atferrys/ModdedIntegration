package it.uknowngino.moddedintegration.config;

import it.uknowngino.moddedintegration.utils.LogUtils;
import it.uknowngino.moddedintegration.utils.YMLUtils;

import java.util.logging.Level;

public class Config {

    public static int reload_delay = 10;

    public static void reload() {

        if(YMLUtils.readInteger("config.yml", "reload-delay") == 0) {

            YMLUtils.writeInteger("config.yml", "reload-delay", 10);
            reload();

        } else {

            reload_delay = Math.abs(YMLUtils.readInteger("config.yml", "reload-delay"));
            LogUtils.log(Level.INFO, "The configuration has been reloaded. EssentialsX's Reload Delay: " + reload_delay + " ticks");

        }

    }

}
