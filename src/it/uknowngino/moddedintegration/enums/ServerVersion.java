package it.uknowngino.moddedintegration.enums;

import it.uknowngino.moddedintegration.implementation.PopulationImplementation;
import it.uknowngino.moddedintegration.implementation.V1_12_Implementation;
import it.uknowngino.moddedintegration.implementation.V1_16_Implementation;
import it.uknowngino.moddedintegration.main.ModdedIntegration;
import it.uknowngino.moddedintegration.utils.LogUtils;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.logging.Level;

public enum ServerVersion {

    V1_12(V1_12_Implementation.class),
    V1_16(V1_16_Implementation.class);

    private final Class<? extends PopulationImplementation> implementationClass;

    ServerVersion(Class<? extends PopulationImplementation> implementationClass) {

        this.implementationClass = implementationClass;

    }

    @Nonnull
    public PopulationImplementation getImplementation() {

        try {

            return implementationClass.newInstance();

        } catch (IllegalAccessException | InstantiationException e) {

            LogUtils.log(Level.SEVERE, "Unable to obtain " + name() + " implementation instance: " + e.getMessage());
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(ModdedIntegration.getInstance());

            return null;

        }

    }

    public static ServerVersion getCurrentServerVersion() {

        String[] versionString = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].toUpperCase().split("_");

        try {

            return valueOf(String.join("_", Arrays.copyOf(versionString, versionString.length - 1)));

        } catch (IllegalArgumentException e) {

            LogUtils.log(Level.SEVERE, "The server version in use isn't supported by ModdedIntegration! You can open an Issue on GitHub and ask for support.");
            Bukkit.getPluginManager().disablePlugin(ModdedIntegration.getInstance());
            return null;

        }

    }

}
