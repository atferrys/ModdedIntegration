package it.uknowngino.moddedintegration.enums;

import it.uknowngino.moddedintegration.implementation.PopulationImplementation;
import it.uknowngino.moddedintegration.implementation.V1_12_Implementation;
import it.uknowngino.moddedintegration.implementation.V1_16_Implementation;
import it.uknowngino.moddedintegration.main.ModdedIntegration;
import it.uknowngino.moddedintegration.utils.LogUtils;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.logging.Level;

import static it.uknowngino.moddedintegration.main.ModdedIntegration.PLUGIN_MANAGER;

public enum ServerVersion {

    V1_12(V1_12_Implementation.class),
    V1_16(V1_16_Implementation.class);

    private final Class<? extends PopulationImplementation> implementationClass;
    private final Supplier<Boolean> customVersionCheck;

    ServerVersion(Class<? extends PopulationImplementation> implementationClass, Supplier<Boolean> customVersionCheck) {

        this.implementationClass = implementationClass;
        this.customVersionCheck = customVersionCheck;

    }

    ServerVersion(Class<? extends PopulationImplementation> implementationClass) {

        this(implementationClass, null);

    }

    public Supplier<Boolean> getCustomVersionCheck() {

        return customVersionCheck;

    }

    @Nonnull
    public PopulationImplementation getImplementation() {

        try {

            return implementationClass.newInstance();

        } catch (IllegalAccessException | InstantiationException e) {

            LogUtils.log(Level.SEVERE, "Unable to obtain " + name() + " implementation instance: " + e.getMessage());
            e.printStackTrace();
            PLUGIN_MANAGER.disablePlugin(ModdedIntegration.getInstance());

            return null;

        }

    }

    public boolean isCurrentServerVersion() {

        String[] versionString = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].toUpperCase().split("_");

        return String.join("_", Arrays.copyOf(versionString, versionString.length - 1)).equalsIgnoreCase(name());

    }

    public static ServerVersion getCurrentServerVersion() {

        // Custom Version Check

        for(ServerVersion serverVersion : values()) {

            Supplier<Boolean> customVersionCheck = serverVersion.getCustomVersionCheck();

            if(customVersionCheck != null && customVersionCheck.get()) return serverVersion;

        }

        // Server Version Check

        return Arrays.stream(values())
                .filter(ServerVersion::isCurrentServerVersion)
                .findFirst()
                .orElseGet(() -> {

                    LogUtils.log(Level.SEVERE, "The server version in use isn't supported by ModdedIntegration! You can open an Issue on GitHub and ask for support.");
                    PLUGIN_MANAGER.disablePlugin(ModdedIntegration.getInstance());

                    return null;

                });

    }

}
