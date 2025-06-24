package me.tnoctua.nmodutils.datagen;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.tnoctua.nmodutils.util.ModRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.registry.RegistryWrapper;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static me.tnoctua.nmodutils.util.Utils.TRANSLATIONS;
import static me.tnoctua.nmodutils.util.Utils.addTranslation;

public abstract class NModLangProvider extends FabricLanguageProvider {

    private final ModRegistry REGISTRY;
    private final FabricDataOutput OUTPUT;
    private final String LANG;

    /**
     * Generic language provider.
     * <p> Will check for {@code /assets/{modid}/lang/provided/{lang}.provided.json>} and add translations to the mod.
     * @param modRegistry mod registry
     * @param output data output
     * @param languageCode language code
     * @param registries registry lookup
     */
    protected NModLangProvider(ModRegistry modRegistry, FabricDataOutput output, String languageCode, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
        super(output, languageCode, registries);
        REGISTRY = modRegistry;
        OUTPUT = output;
        LANG = languageCode;
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registries, TranslationBuilder builder) {
        // Load translations from existing files
        try {
            Optional<ModContainer> mod = FabricLoader.getInstance().getModContainer(OUTPUT.getModId());
            if (mod.isPresent()) {
                Optional<Path> langPath = mod.get().findPath("assets/%s/lang/provided/%s.provided.json".formatted(OUTPUT.getModId(), LANG));
                if (langPath.isPresent()) {
                    try (Reader reader = Files.newBufferedReader(langPath.get())) {
                        JsonObject translations = JsonParser.parseReader(reader).getAsJsonObject();
                        for (String key : translations.keySet()) {
                            TRANSLATIONS.put(key, translations.get(key).getAsString());
                        }
                    }
                }
            }
        } catch (IOException ignored) {}

        // Add missing translations
        addMissingTranslations();

        // Add all translations to data generator
        TRANSLATIONS.forEach(builder::add);
    }

    /**
     * This method registers translations if they are missing, called after provided translations are added.
     * <p> This method should call super and additionally make calls to {@code LangProvider#addTranslation(String)}.
     * @see me.tnoctua.nmodutils.util.Utils#addTranslation(String)
     */
    protected void addMissingTranslations() {
        REGISTRY.BLOCKS.forEach(block -> addTranslation(block.getTranslationKey()));
        REGISTRY.ITEMS.forEach(item -> addTranslation(item.getTranslationKey()));
        REGISTRY.BLOCK_TAGS.forEach(blockTag -> addTranslation(blockTag.getTranslationKey()));
        REGISTRY.ITEM_TAGS.forEach(itemTag -> addTranslation(itemTag.getTranslationKey()));
        REGISTRY.ENTITY_TAGS.forEach(entityTag -> addTranslation(entityTag.getTranslationKey()));
    }

}
