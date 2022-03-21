package it.uknowngino.moddedintegration.implementation;

import it.uknowngino.moddedintegration.constructors.PopulationResult;
import org.bukkit.Material;

import java.io.IOException;
import java.util.List;

public interface PopulationImplementation {

    String getItemsFileName();

    List<Material> getOldItems();

    List<Material> getForgeItems();

    PopulationResult populateFile() throws IOException;

}
