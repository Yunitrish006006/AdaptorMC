package net.yunitrish.adaptor.world.biome;

import net.yunitrish.adaptor.Adaptor;
import net.yunitrish.adaptor.world.biome.surface.ModMaterialRules;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class ModTerrablenderAPI implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new ModOverworldRegion(Adaptor.modIdentifier("overworld"), 4));

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, Adaptor.MOD_ID, ModMaterialRules.makeRules());
    }
}
