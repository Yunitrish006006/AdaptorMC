package net.yunitrish.adaptor.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.yunitrish.adaptor.Adaptor;

public class ModEnchantments {
    public static Enchantment LEACH = register("leach", new LeachEnchantment());
    public static Enchantment MANIC = register("manic", new ManicEnchantment());

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, Adaptor.modIdentifier(name),enchantment);
    }

    public static void registerModEnchantments() {
        System.out.println("Registering enchantments...");
    }
}
