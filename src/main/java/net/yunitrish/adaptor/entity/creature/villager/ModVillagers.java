package net.yunitrish.adaptor.entity.creature.villager;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import net.yunitrish.adaptor.Adaptor;

public class ModVillagers {
    /*-----------------------------------------------------------------------------------------------*/
    public static final RegistryKey<PointOfInterestType> CHEF_POI_KEY = poiKey("chefpoi");
    public static final PointOfInterestType CHEF_POI = registerPoi("chefpoi", Blocks.CAMPFIRE);
    public static final VillagerProfession CHEF = registerProfession("chef",CHEF_POI_KEY);
    /*-----------------------------------------------------------------------------------------------*/
    public static final RegistryKey<PointOfInterestType> SCAVENGER_POI_KEY = poiKey("scavengerpoi");
    public static final PointOfInterestType SCAVENGER_POI = registerPoi("scavengerpoi", Blocks.CRAFTING_TABLE);
    public static final VillagerProfession SCAVENGER = registerProfession("scavenger",SCAVENGER_POI_KEY);
    /*-----------------------------------------------------------------------------------------------*/

    private static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type) {
        return Registry.register(
                Registries.VILLAGER_PROFESSION,
                Adaptor.id(name),
                new VillagerProfession(name,
                        entry -> entry.matchesKey(type),
                        entry -> entry.matchesKey(type),
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        SoundEvents.ENTITY_VILLAGER_WORK_FARMER
                )
        );
    }

    private static PointOfInterestType registerPoi(String name, Block block) {
        return PointOfInterestHelper.register(Adaptor.id(name), 1, 1, block);
    }

    private static RegistryKey<PointOfInterestType> poiKey(String name) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, Adaptor.id(name));
    }
    public static void registerVillagers() {
        Adaptor.LOGGER.info("Registering Villagers...");
    }
}
