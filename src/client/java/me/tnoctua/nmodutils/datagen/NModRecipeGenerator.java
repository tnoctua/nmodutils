package me.tnoctua.nmodutils.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

public abstract class NModRecipeGenerator extends RecipeGenerator {

    /**
     * Recipe generator with methods for common recipe shapes.
     * @param registries registry lookup
     * @param exporter recipe exporter
     */
    protected NModRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
        super(registries, exporter);
    }

    /**
     * Registers a colored block family recipe.
     * @param block base block in the family (e.g. stone_bricks)
     * @param stair stair block in the family (e.g. stone_brick_stairs)
     * @param slab slab block in the family (e.g. stone_brick_slab)
     * @param wall wall block in the family (e.g. stone_brick_wall)
     * @param base block used with dye to craft the base block
     * @param dye dye item used to craft the base block
     */
    protected void registerColoredBlockFamily(Block block, Block stair, Block slab, Block wall, Block base, Item dye) {
        createShaped(RecipeCategory.BUILDING_BLOCKS, block, 8)
                .pattern("###")
                .pattern("#D#")
                .pattern("###")
                .input('#', base)
                .input('D', dye)
                .criterion(hasItem(base), conditionsFromItem(base))
                .offerTo(exporter);
        registerBlockFamily(block, stair, slab, wall);
    }

    /**
     * Registers a block family recipe.
     * @param block base block in the family (e.g. stone_bricks)
     * @param stair stair block in the family (e.g. stone_brick_stairs)
     * @param slab slab block in the family (e.g. stone_brick_slab)
     * @param wall wall block in the family (e.g. stone_brick_wall)
     */
    protected void registerBlockFamily(Block block, Block stair, Block slab, Block wall) {
        createShaped(RecipeCategory.BUILDING_BLOCKS, stair, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .input('#', block)
                .criterion(hasItem(block), conditionsFromItem(block))
                .offerTo(exporter);
        createShaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                .pattern("###")
                .input('#', block)
                .criterion(hasItem(block), conditionsFromItem(block))
                .offerTo(exporter);
        createShaped(RecipeCategory.BUILDING_BLOCKS, wall, 6)
                .pattern("###")
                .pattern("###")
                .input('#', block)
                .criterion(hasItem(block), conditionsFromItem(block))
                .offerTo(exporter);
    }

}
