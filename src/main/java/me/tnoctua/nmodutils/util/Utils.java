package me.tnoctua.nmodutils.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Optional;

public class Utils {

    /** Voxel width for use in VoxelShape math */
    public static double P = 0.0625;

    /** Common side translations for use in data generation. */
    public static final HashMap<String, String> TRANSLATIONS = new HashMap<>();

    /**
     * Add a translation key with a missing translation and returns the translated text.
     * <p> This should ideally be called once per unique translation key, other than those automatically registered.
     * <p> For example this call can be used in place of Minecraft's translatable text calls.
     * @param translationKey translation key
     */
    public static MutableText addTranslation(String translationKey) {
        String MISSING = "mistrans!";
        return addTranslation(translationKey, MISSING);
    }

    /**
     * Adds a translation key and returns the translated text.
     * @param translationKey translation key
     * @param value translation value
     */
    public static MutableText addTranslation(String translationKey, String value) {
        TRANSLATIONS.putIfAbsent(translationKey, value);
        return Text.translatable(translationKey);
    }

    /**
     * Returns an item stack from a provided nbt compound, without logging errors.
     * <p>NOTE: The error is important, so make sure the item stack you're setting is NEVER null or brave the desync.
     * @param registries registry lookup
     * @param nbt item stack as nbt compound
     * @return item stack if it could be parsed from nbt
     */
    public static Optional<ItemStack> fromNbt(RegistryWrapper.WrapperLookup registries, NbtElement nbt) {
        return ItemStack.CODEC.parse(registries.getOps(NbtOps.INSTANCE), nbt).resultOrPartial();
    }

}
