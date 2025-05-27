package me.tnoctua.nmodutils.util;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ModRegistry {

    private final String MOD_ID;

    public final ArrayList<Block> BLOCKS = new ArrayList<>();
    public final HashSet<ItemGroup> ITEM_GROUPS = new HashSet<>();
    public final ArrayList<Item> ITEMS = new ArrayList<>();
    public final ArrayList<SoundEvent> SOUNDS = new ArrayList<>();
    public final ArrayList<TagKey<Item>> ITEM_TAGS = new ArrayList<>();
    public final ArrayList<TagKey<Block>> BLOCK_TAGS = new ArrayList<>();
    public final ArrayList<RegistryKey<DamageType>> DAMAGE_TYPES = new ArrayList<>();
    public final ArrayList<BlockEntityType<?>> BLOCK_ENTITIES = new ArrayList<>();
    public final ArrayList<ComponentType<?>> COMPONENTS = new ArrayList<>();

    /**
     * Implements various registry helper methods to add content to the game.
     *
     * @param modIdentifier identifier to use for the registry (which should always be your mod's ID)
     */
    public ModRegistry(String modIdentifier) {
        MOD_ID = modIdentifier;
    }

    /**
     * Registers a block entity to the game.
     *
     * @param key block entity ID/key
     * @param factory block entity factory
     * @param blocks blocks applicable to the block entity
     * @return block entity type
     * @param <T> block entity subclass
     */
    public <T extends BlockEntity> BlockEntityType<T> register(String key, FabricBlockEntityTypeBuilder.Factory<? extends T> factory, Block... blocks) {
        BlockEntityType<T> blockEntity = FabricBlockEntityTypeBuilder.<T>create(factory, blocks).build();
        BLOCK_ENTITIES.add(blockEntity);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(MOD_ID, key), blockEntity);
    }

    /**
     * Registers a block to the game.
     *
     * @param key block ID/key
     * @return the registered block
     */
    public Block register(String key, Function<AbstractBlock.Settings, ? extends Block> factory, AbstractBlock.Settings settings) {
        return register(true, true, key, factory, settings);
    }

    /**
     * Registers a block to the game.
     *
     * @param enabled whether the block should be registered
     * @param key block ID/key
     * @return the registered block
     */
    public Block register(boolean enabled, String key, Function<AbstractBlock.Settings, ? extends Block> factory, AbstractBlock.Settings settings) {
        return register(enabled, true, key, factory, settings);
    }

    /**
     * Registers a block to the game.
     *
     * @param enabled whether the block should be registered
     * @param item whether the block should have an item registered
     * @param key block ID/key
     * @return the registered block
     */
    public Block register(boolean enabled, boolean item, String key, Function<AbstractBlock.Settings, ? extends Block> factory, AbstractBlock.Settings settings) {
        Block block = null;

        if (enabled) {
            // Create the block key.
            RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(MOD_ID, key));

            // Create the block instance.
            block = factory.apply(settings.registryKey(blockKey));

            // Register the block.
            Registry.register(Registries.BLOCK, blockKey, block);
            if (item) {
                Items.register(block);
            }
            BLOCKS.add(block);
        }

        return block;
    }

    /**
     * Registers an item group to the game.
     *
     * @param key key/ID of the item group
     * @param itemGroup item group instance
     * @return the registered item group's registry key
     */
    public RegistryKey<ItemGroup> register(String key, ItemGroup itemGroup) {
        return register(true, key, itemGroup);
    }

    /**
     * Registers an item group to the game.
     *
     * @param enabled condition for registering the item group
     * @param key key/ID of the item group
     * @param itemGroup item group instance
     * @return the registered item group's registry key
     */
    public RegistryKey<ItemGroup> register(boolean enabled, String key, ItemGroup itemGroup) {
        RegistryKey<ItemGroup> registryKey = null;
        if (enabled) {
            registryKey = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MOD_ID, key));
            Registry.register(Registries.ITEM_GROUP, registryKey, itemGroup);
            ITEM_GROUPS.add(itemGroup);
        }
        return registryKey;
    }

    /**
     * Registers an item to the game.
     *
     * @param key item ID/key
     * @return the registered item
     */
    public Item register(String key, Function<Item.Settings, Item> factory, Item.Settings settings) {
        return register(true, key, factory, settings);
    }

    /**
     * Registers an item to the game.
     *
     * @param enabled whether the item should be registered
     * @param key item ID/key
     * @return the registered item
     */
    public Item register(boolean enabled, String key, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = null;

        if (enabled) {
            // Create the item key.
            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, key));

            // Create the item instance.
            item = factory.apply(settings.registryKey(itemKey));

            // Register the item.
            Registry.register(Registries.ITEM, itemKey, item);
            ITEMS.add(item);
        }

        return item;
    }
    
    /**
     * Registers a sound event to the game.
     *
     * @param key sound event ID/key
     * @return the registered sound event
     */
    public SoundEvent soundEvent(String key) {
        return soundEvent(true, key);
    }

    /**
     * Registers a sound event to the game.
     *
     * @param enabled whether the sound event should be registered
     * @param key sound event ID/key
     * @return the registered sound event
     */
    public SoundEvent soundEvent(boolean enabled, String key) {
        SoundEvent soundEvent = null;

        if (enabled) {
            // Create the sound event key.
            RegistryKey<SoundEvent> soundEventKey = RegistryKey.of(RegistryKeys.SOUND_EVENT, Identifier.of(MOD_ID, key));

            // Create the sound event instance.
            soundEvent = SoundEvent.of(Identifier.of(MOD_ID, key));

            // Register the sound event.
            Registry.register(Registries.SOUND_EVENT, soundEventKey, soundEvent);
            SOUNDS.add(soundEvent);
        }

        return soundEvent;
    }

    /**
     * Registers a block tag to the game.
     *
     * @param key block tag ID/key
     * @return the block tag key
     */
    public TagKey<Block> blockTag(String key) {
        TagKey<Block> tagKey = TagKey.of(RegistryKeys.BLOCK, Identifier.of(MOD_ID, key));
        BLOCK_TAGS.add(tagKey);
        return tagKey;
    }

    /**
     * Registers an item tag to the game.
     *
     * @param key item tag ID/key
     * @return the item tag key
     */
    public TagKey<Item> itemTag(String key) {
        TagKey<Item> tagKey = TagKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, key));
        ITEM_TAGS.add(tagKey);
        return tagKey;
    }

    /**
     * Registers a damage type to the game.
     * <p> This method does not create the resource required in /data/modid/damage_type
     *
     * @param key damage type ID/key
     * @return the damage type key
     */
    public RegistryKey<DamageType> damageType(String key) {
        RegistryKey<DamageType> type = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(MOD_ID, key));
        DAMAGE_TYPES.add(type);
        return type;
    }

    /**
     * Registers a component type to the game.
     *
     * @param key component type ID/key
     * @param builder component builder
     * @return component type instance
     * @param <T> type of component
     */
    public <T> ComponentType<T> register(String key, UnaryOperator<ComponentType.Builder<T>> builder) {
        ComponentType<T> component = (builder.apply(ComponentType.builder())).build();
        COMPONENTS.add(component);
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(MOD_ID, key), component);
    }
    
}
