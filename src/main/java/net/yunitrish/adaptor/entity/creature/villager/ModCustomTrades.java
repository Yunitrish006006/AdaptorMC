package net.yunitrish.adaptor.entity.creature.villager;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import net.yunitrish.adaptor.enchantment.ModEnchantments;
import net.yunitrish.adaptor.item.ModItems;

import java.util.Optional;

public class ModCustomTrades {

    public static ItemStack randomCountItemStack(Item item, int min, int max) {
        return new ItemStack(item);
    }
    public static TradedItem randomCountTradeItem(Item item, int min, int max) {
        return new TradedItem(item,Random.create().nextBetween(min,max));
    }

    public static void registerCustomTrades() {
        TradeOfferHelper.registerVillagerOffers(
                VillagerProfession.FARMER,
                1,
                factories -> {
                    factories.add((entity, random) -> new TradeOffer(
                            randomCountTradeItem(ModItems.SOYBEAN,7,19),
                            new ItemStack(Items.EMERALD),
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
                            randomCountTradeItem(Items.WHEAT,1,3),
                            Optional.of(new TradedItem(Items.EMERALD)),
                            randomCountItemStack(Items.BREAD,1,2),
                            6,
                            5,
                            0.05f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            randomCountTradeItem(Items.WHEAT,2,6),
                            Optional.of(new TradedItem(Items.EMERALD)),
                            randomCountItemStack(Items.COOKIE,1,4),
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
                            randomCountTradeItem(Items.SPIDER_EYE,2,6),
                            randomCountItemStack(Items.GOLD_NUGGET,1,2),
                            6,
                            5,
                            0.05f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            randomCountTradeItem(Items.ROTTEN_FLESH,2,6),
                            randomCountItemStack(Items.GOLD_NUGGET,1,2),
                            6,
                            5,
                            0.05f
                    ));
                    factories.add((entity, random) -> new TradeOffer(
                            randomCountTradeItem(Items.BONE,2,6),
                            randomCountItemStack(Items.GOLD_NUGGET,1,2),
                            6,
                            5,
                            0.05f
                    ));
                }
        );

        TradeOfferHelper.registerVillagerOffers(
                ModVillagers.SCAVENGER,
                2,
                factories -> {

                    factories.add(((entity, random) -> {
                        ItemStack item = new ItemStack(Items.ENCHANTED_BOOK);
                        int book_level = random.nextBetween(1,4);
                        item.addEnchantment(ModEnchantments.MANIC,book_level);
                        return new TradeOffer(
                                new TradedItem(Items.GOLD_INGOT, random.nextBetween(book_level*3,book_level*5) + random.nextBetween(6,10)),
                                item,
                                2,
                                30,
                                0.04f

                        );
                    }));
                }
        );


        TradeOfferHelper.registerVillagerOffers(
                VillagerProfession.WEAPONSMITH,
                1,
                factories -> {
                    ItemStack item = new ItemStack(Items.IRON_SWORD);
                    item.addEnchantment(ModEnchantments.LEACH,1);
                    factories.add((entity, random) -> new TradeOffer(
                            new TradedItem(new ItemStack(Items.EMERALD).getItem(),8),
                            item,
                            6,
                            22,
                            0.09f
                    ));
                }
        );
    }
}
