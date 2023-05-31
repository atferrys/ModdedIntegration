package it.uknowngino.moddedintegration.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.uknowngino.moddedintegration.constructors.PopulationResult;
import it.uknowngino.moddedintegration.main.ModdedIntegration;
import it.uknowngino.moddedintegration.utils.LogUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class V1_18_Implementation implements PopulationImplementation {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private final File itemsFile = new File(ModdedIntegration.getEssentials().getDataFolder() + File.separator + getItemsFileName());

    @Override
    public String getItemsFileName() {

        return "items.json";

    }

    @Override
    public List<Material> getOldItems() {

        try {

            String rawJSON = Files.readAllLines(itemsFile.toPath(), StandardCharsets.UTF_8).stream()
                    .filter(line -> !line.startsWith("#"))
                    .collect(Collectors.joining("\n"));
            JsonObject itemsJSON = GSON.fromJson(rawJSON, JsonObject.class);

            return itemsJSON.entrySet().stream()
                    .map(Map.Entry::getValue)
                    .filter(JsonElement::isJsonObject)
                    .map(JsonElement::getAsJsonObject)
                    .filter(json -> json.has("material"))
                    .map(json -> Material.getMaterial(json.get("material").getAsString().toUpperCase()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (IOException e) {

            LogUtils.log(Level.SEVERE, "Unable to read old items.json file: " + e.getMessage());
            e.printStackTrace();

            return Collections.emptyList();

        }

    }

    @Override
    public List<Material> getForgeItems() {

        return Arrays.asList(Material.values());

    }

    @Override
    public PopulationResult populateFile() throws IOException {

        long startMillis = System.currentTimeMillis();
        List<Material> oldItems = getOldItems();
        List<Material> newItems = getForgeItems().stream()
                .filter(material -> !oldItems.contains(material))
                .collect(Collectors.toList());

        List<String> rawJSON = Files.readAllLines(itemsFile.toPath(), StandardCharsets.UTF_8);
        List<String> comments = rawJSON.stream()
                .filter(line -> line.startsWith("#"))
                .collect(Collectors.toList());
        JsonObject oldJSON = GSON.fromJson(rawJSON.stream()
                .filter(line -> !line.startsWith("#"))
                .collect(Collectors.joining("\n")), JsonObject.class);

        BufferedWriter writer = new BufferedWriter(new FileWriter(itemsFile, false));

        // Add comments before JSON
        if(comments.size() >= 3) {
            writer.append(comments.get(0)).append("\n");
            writer.append(comments.get(1)).append("\n");
            writer.append(comments.get(2)).append("\n");
        }

        // Add and write all items
        newItems.forEach(material -> {

            try {

                NamespacedKey key = (NamespacedKey) Material.class.getDeclaredMethod("getKey").invoke(material);
                String compliantMaterial = key.getNamespace() + "_" + key.getKey();

                JsonObject materialJSON = new JsonObject();
                materialJSON.addProperty("material", compliantMaterial.toUpperCase());

                oldJSON.add(compliantMaterial.toLowerCase(), materialJSON);
                oldJSON.addProperty(key.getNamespace() + ":" + key.getKey(), compliantMaterial.toLowerCase());

            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ignored) {}

        });
        writer.append(GSON.toJson(oldJSON)).append("\n");

        // Add comments after JSON
        if(comments.size() >= 4) {
            writer.append(comments.get(3));
        }

        writer.close();

        return new PopulationResult(newItems.size(), System.currentTimeMillis() - startMillis);

    }

}
