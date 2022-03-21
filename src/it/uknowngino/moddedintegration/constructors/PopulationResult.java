package it.uknowngino.moddedintegration.constructors;

public class PopulationResult {

    private final int populatedItems;
    private final long timeTook;

    public PopulationResult(int populatedItems, long timeTook) {

        this.populatedItems = populatedItems;
        this.timeTook = timeTook;

    }

    public int getPopulatedItems() {

        return populatedItems;

    }

    public long getTimeTook() {

        return timeTook;

    }

}
