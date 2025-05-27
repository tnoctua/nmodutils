package me.tnoctua.nmodutils.datagen;

import me.tnoctua.nmodutils.init.impl.block.GenericBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class NModBlockLootTableProvider extends FabricBlockLootTableProvider {

    /**
     * Block loot table provider that generates common blocks.
     * @param dataOutput data output
     * @param registries registry lookup
     */
    protected NModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(dataOutput, registries);
    }

    @Override
    public void generate() {
        // TODO: add survives explosion checks
        GenericBlock.BLOCKS.forEach(this::addDrop);
    }

}
