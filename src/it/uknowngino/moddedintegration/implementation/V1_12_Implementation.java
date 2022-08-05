package it.uknowngino.moddedintegration.implementation;

import it.uknowngino.moddedintegration.constructors.PopulationResult;
import it.uknowngino.moddedintegration.main.ModdedIntegration;
import it.uknowngino.moddedintegration.utils.IntegrationUtils;
import it.uknowngino.moddedintegration.utils.LogUtils;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.bukkit.Material;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class V1_12_Implementation implements PopulationImplementation {

    private final File itemsFile = new File(ModdedIntegration.getEssentials().getDataFolder() + File.separator + getItemsFileName());

    @Override
    public String getItemsFileName() {

        return "items.csv";

    }

    @Override
    public List<Material> getOldItems() {

        try {

            return Files.readAllLines(itemsFile.toPath(), StandardCharsets.UTF_8).stream()
                    .filter(line -> !line.startsWith("#"))
                    .map(line -> Material.getMaterial(line.split(",")[0].toUpperCase()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (IOException e) {

            LogUtils.log(Level.SEVERE, "Unable to read old items.csv file: " + e.getMessage());
            e.printStackTrace();

            return Collections.emptyList();

        }

    }

    @Override
    public List<Material> getForgeItems() {

        return ForgeRegistries.ITEMS.getEntries()
                .stream()
                .map(itemEntry -> Material.getMaterial(IntegrationUtils.normalizeMaterialName(itemEntry.getKey().toString())))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

    @SuppressWarnings("deprecation")
    @Override
    public PopulationResult populateFile() throws IOException {

        long startMillis = System.currentTimeMillis();
        List<Material> oldItems = getOldItems();
        List<Material> newItems = getForgeItems().stream()
                .filter(material -> !oldItems.contains(material))
                .collect(Collectors.toList());
        BufferedWriter writer = new BufferedWriter(new FileWriter(itemsFile, true));

        for(Material material : newItems) {

            writer.append("\n")
                    .append(material.toString().toLowerCase())
                    .append(",")
                    .append(String.valueOf(material.getId()))
                    .append(",0");

        }

        writer.close();

        return new PopulationResult(newItems.size(), System.currentTimeMillis() - startMillis);

    }

}
