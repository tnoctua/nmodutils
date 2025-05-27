package me.tnoctua.nmodutils.init.impl.block;

import net.minecraft.block.Block;

import java.util.ArrayList;

public class GenericBlock extends Block {

    public static final ArrayList<Block> BLOCKS = new ArrayList<>();

    /**
     * Generic block with a "cube_all" model, adds instances to a list for data generation purposes.
     * @param settings block settings
     */
    public GenericBlock(Settings settings) {
        super(settings);
        BLOCKS.add(this);
    }

}
