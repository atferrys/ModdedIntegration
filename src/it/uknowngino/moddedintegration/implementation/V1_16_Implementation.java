package it.uknowngino.moddedintegration.implementation;

import it.uknowngino.moddedintegration.utils.IntegrationUtils;
import net.minecraft.item.Item;
import org.bukkit.Material;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class V1_16_Implementation extends V1_12_Implementation {

    @SuppressWarnings("unchecked")
    @Override
    public List<Material> getForgeItems() {

        try {

            Object itemsRegistry = Class.forName("net.minecraftforge.registries.ForgeRegistries").getField("ITEMS").get(null);
            Method entriesMethod = itemsRegistry.getClass().getDeclaredMethod("getEntries");
            Set<Map.Entry<Object, Item>> registryEntries = (Set<Map.Entry<Object, Item>>) entriesMethod.invoke(itemsRegistry);

            return registryEntries
                    .stream()
                    .map(itemEntry -> Material.getMaterial(IntegrationUtils.normalizeMaterialName(itemEntry.getKey().toString()).replace("RESOURCEKEYMINECRAFT_ITEM__", "")))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {

            e.printStackTrace();
            return Collections.emptyList();

        }

    }

}
