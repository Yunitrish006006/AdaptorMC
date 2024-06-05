package net.yunitrish.adaptor.entity;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.block.plant.ChestnutSeries;

public class ModBoats {
    public static final Identifier CHESTNUT_BOAT_ID = Adaptor.modIdentifier("chestnut_boat");
    public static final Identifier CHESTNUT_CHEST_BOAT_ID = Adaptor.modIdentifier("chestnut_chest_boat");

    public static final RegistryKey<TerraformBoatType> CHESTNUT_BOAT_KEY = TerraformBoatTypeRegistry.createKey(CHESTNUT_BOAT_ID);

    public static void registerBoats() {
        TerraformBoatType chestnutBoat = new TerraformBoatType.Builder()
                .item(ChestnutSeries.CHESTNUT_BOAT)
                .chestItem(ChestnutSeries.CHESTNUT_CHEST_BOAT)
                .planks(ChestnutSeries.CHESTNUT_PLANKS.asItem())
                .build();
        Registry.register(TerraformBoatTypeRegistry.INSTANCE,CHESTNUT_BOAT_KEY,chestnutBoat);
    }
}
