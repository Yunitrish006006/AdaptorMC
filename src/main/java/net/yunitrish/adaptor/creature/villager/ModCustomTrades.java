package net.yunitrish.adaptor.creature.villager;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.yunitrish.adaptor.enchantment.ModEnchantments;
import net.yunitrish.adaptor.item.ModItems;

public class ModCustomTrades {
    public static void registerCustomTrades() {
        TradeOfferHelper.registerVillagerOffers(
                VillagerProfession.FARMER,
                1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.SOYBEAN,2),
                            new ItemStack(ModItems.SALT,1),
                            6,
                            5,
                            0.05f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.SOYBEAN,3),
                            new ItemStack(ModItems.SALT,1),
                            6,
                            5,
                            0.05f
                    ));
                }
        );
        TradeOfferHelper.registerVillagerOffers(
                ModVillagers.CHEF,
                1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.SOYBEAN,2),
                            new ItemStack(ModItems.SALT,1),
                            6,
                            5,
                            0.05f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(ModItems.CORN,3),
                            new ItemStack(ModItems.SALT,1),
                            6,
                            5,
                            0.05f
                    ));
                }
        );
        TradeOfferHelper.registerVillagerOffers(
                ModVillagers.SCAVENGER,
                1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.SPIDER_EYE,random.nextBetween(1,3)),
                            new ItemStack(Items.GOLD_NUGGET,random.nextBetween(1,2)),
                            6,
                            5,
                            0.05f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.ROTTEN_FLESH,random.nextBetween(1,6)),
                            new ItemStack(Items.GOLD_NUGGET,random.nextBetween(1,2)),
                            6,
                            5,
                            0.05f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.BONE,random.nextBetween(1,6)),
                            new ItemStack(Items.GOLD_NUGGET,random.nextBetween(1,2)),
                            6,
                            5,
                            0.05f
                    ));
                }
        );
        ItemStack item = new ItemStack(Items.IRON_SWORD);
        item.addEnchantment(ModEnchantments.LEACH,1);
        TradeOfferHelper.registerVillagerOffers(
                VillagerProfession.WEAPONSMITH,
                1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD,8),
                            item,
                            6,
                            22,
                            0.09f
                    ));
                }
        );
    }
}
