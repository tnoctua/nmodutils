package me.tnoctua.nmodutils.datagen;

import me.tnoctua.nmodutils.init.impl.block.GenericBlock;
import me.tnoctua.nmodutils.init.impl.item.CustomModelItem;
import me.tnoctua.nmodutils.init.impl.item.GenericItem;
import me.tnoctua.nmodutils.init.impl.item.HandheldItem;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

import static net.minecraft.client.data.BlockStateModelGenerator.*;

public abstract class NModModelProvider extends FabricModelProvider {

    /**
     * Model provider that generates common block and item models.
     * @param output data generator
     */
    protected NModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        GenericBlock.BLOCKS.forEach(generator::registerSimpleCubeAll);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        GenericItem.ITEMS.forEach(item -> generator.register(item, Models.GENERATED));
        HandheldItem.ITEMS.forEach(item -> generator.register(item, Models.HANDHELD));
        CustomModelItem.ITEMS.forEach(item -> generator.register(item));
    }

    /**
     * Registers models for a block family.
     * @param generator model generator
     * @param block base block
     * @param stair stair block
     * @param slab slab block
     * @param wall wall block
     */
    public void registerBlockFamily(BlockStateModelGenerator generator, Block block, Block stair, Block slab, Block wall) {
        generator.registerCubeAllModelTexturePool(block).family(new BlockFamily.Builder(block).stairs(stair).slab(slab).wall(wall).build());
    }

    /**
     * Registers a variant model with a single texture key.
     * @param generator model generator
     * @param block block reference
     * @param model model reference
     * @param variant block to use for variant textures
     */
    public void registerAllTextureVariant(BlockStateModelGenerator generator, Block block, Model model, Block variant) {
        TextureMap textureMap = new TextureMap()
                .put(TextureKey.PARTICLE, TextureMap.getId(variant))
                .put(TextureKey.ALL, TextureMap.getId(variant));
        registerVariantModel(generator, block, model, textureMap, true);
    }

    /**
     * Registers a variant model.
     * @param generator model generator
     * @param block block reference
     * @param model model reference
     * @param textureMap texture map for model
     * @param horizontalRotation whether the block has horizontal rotation (north default)
     */
    public void registerVariantModel(BlockStateModelGenerator generator, Block block, Model model, TextureMap textureMap, boolean horizontalRotation) {
        WeightedVariant variant = BlockStateModelGenerator.createWeightedVariant(model.upload(block, textureMap, generator.modelCollector));
        if (horizontalRotation) {
            generator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block, variant)
                    .coordinate(BlockStateVariantMap.operations(Properties.HORIZONTAL_FACING)
                    .register(Direction.EAST, ROTATE_Y_90)
                    .register(Direction.SOUTH, ROTATE_Y_180)
                    .register(Direction.WEST, ROTATE_Y_270)
                    .register(Direction.NORTH, NO_OP)));
        } else {
            generator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block, variant));
        }
        generator.itemModelOutput.accept(block.asItem(), ItemModels.basic(ModelIds.getBlockModelId(block)));
    }

}
