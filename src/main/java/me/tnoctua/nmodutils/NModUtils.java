package me.tnoctua.nmodutils;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NModUtils implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("NModUtils");
    public static final ModContainer MOD = FabricLoader.getInstance().getModContainer(ModConfig.MOD_ID).isPresent()
            ? FabricLoader.getInstance().getModContainer(ModConfig.MOD_ID).get() : null;

    @Override
    public void onInitialize() {}

}