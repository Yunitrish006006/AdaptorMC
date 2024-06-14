package net.yunitrish.adaptor.world.generation;

import net.yunitrish.adaptor.datagen.ModEntitySpawn;

public class ModWorldGeneration {
    public static void generateModWorldGeneration() {
        ModOreGeneration.generateOres();
        ModTreeGeneration.generateTrees();
        ModEntitySpawn.addEntitySpawn();
    }
}
