package me.tnoctua.nmodutils.datagen;

import me.tnoctua.nmodutils.util.ModRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class NModEmptyLangProvider extends NModLangProvider {

    /**
     * Template language provider.
     * <p> Used to create a file with all the keys present, but translations missing.
     *
     * @param modRegistry  mod registry
     * @param output       data output
     * @param registries   registry lookup
     */
    public NModEmptyLangProvider(ModRegistry modRegistry, FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(modRegistry, output, "translate_me", registries);
    }

}
