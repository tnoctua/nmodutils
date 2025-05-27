package me.tnoctua.nmodutils.init.impl.item;

import net.minecraft.item.Item;

import java.util.ArrayList;

public class HandheldItem extends Item {

    public static final ArrayList<Item> ITEMS = new ArrayList<>();

    /**
     * Generic item with a "handheld" model, adds instances to a list for data generation purposes.
     * @param settings item settings
     */
    public HandheldItem(Settings settings) {
        super(settings);
        ITEMS.add(this);
    }

}
