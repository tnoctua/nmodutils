package me.tnoctua.nmodutils.init.impl.block;

import net.minecraft.block.Block;

/**
 * Interface for blocks that have variants.
 */
public interface VariantBlock {

    /**
     * Returns the block used for the variant (e.g. wooden shelf returns the wood type)
     * @return block used for variant
     */
    Block getVariant();

}
