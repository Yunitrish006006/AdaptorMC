//package net.yunitrish.adaptor.recipe;
//
//import com.mojang.serialization.Codec;
//import com.mojang.serialization.DataResult;
//import com.mojang.serialization.codecs.RecordCodecBuilder;
//import net.minecraft.inventory.SimpleInventory;
//import net.minecraft.item.ItemStack;
//import net.minecraft.network.PacketByteBuf;
//import net.minecraft.network.RegistryByteBuf;
//import net.minecraft.network.codec.PacketCodec;
//import net.minecraft.recipe.Ingredient;
//import net.minecraft.recipe.Recipe;
//import net.minecraft.recipe.RecipeSerializer;
//import net.minecraft.recipe.RecipeType;
//import net.minecraft.registry.RegistryWrapper;
//import net.minecraft.util.collection.DefaultedList;
//import net.minecraft.util.dynamic.Codecs;
//import net.minecraft.world.World;
//
//import java.util.List;
//
//public class StoneMillRecipe implements Recipe<SimpleInventory> {
//
//    public static final Serializer INSTANCE = new Serializer();
//    public static final String ID = "stone_mill";
//
//    private final ItemStack output;
//    private final List<Ingredient> recipeItems;
//
//    public StoneMillRecipe(List<Ingredient> recipeItems, ItemStack output) {
//        this.output = output;
//        this.recipeItems = recipeItems;
//    }
//
//    @Override
//    public boolean matches(SimpleInventory inventory, World world) {
//        if(world.isClient()) return false;
//        return recipeItems.getFirst().test(inventory.getStack(0));
//    }
//
//    @Override
//    public ItemStack craft(SimpleInventory inventory, RegistryWrapper.WrapperLookup lookup) {
//        return output;
//    }
//
//    @Override
//    public boolean fits(int width, int height) {
//        return true;
//    }
//
//    @Override
//    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
//        return output;
//    }
//
//    @Override
//    public DefaultedList<Ingredient> getIngredients() {
//        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.recipeItems.size());
//        list.addAll(recipeItems);
//        return list;
//    }
//
//    @Override
//    public RecipeSerializer<?> getSerializer() {
//        return Serializer.INSTANCE;
//    }
//
//    @Override
//    public RecipeType<?> getType() {
//        return Type.INSTANCE;
//    }
//
//    public static class Type implements  RecipeType<StoneMillRecipe> {
//        public static final Type INSTANCE = new Type();
//        public static final String ID = StoneMillRecipe.ID;
//    }
//
//    public static class Serializer implements RecipeSerializer<StoneMillRecipe> {
//
//        public static final Serializer INSTANCE = new Serializer();
//        public static final String ID = StoneMillRecipe.ID;
//
//        public static final Codec<StoneMillRecipe> CODEC = RecordCodecBuilder.create(in -> in.group(
//                validateAmount(Ingredient.DISALLOW_EMPTY_CODEC, 9).fieldOf("ingredients").forGetter(StoneMillRecipe::getIngredients),
//                ItemStack.RECIPE_RESULT_CODEC.fieldOf("output").forGetter(r -> r.output)
//        ).apply(in, StoneMillRecipe::new));
//
//        private static Codec<List<Ingredient>> validateAmount(Codec<Ingredient> delegate, int max) {
//            return Codecs.validate(Codecs.validate(
//                    delegate.listOf(), list -> list.size() > max ? DataResult.error(() -> "Recipe has too many ingredients!") : DataResult.success(list)
//            ), list -> list.isEmpty() ? DataResult.error(() -> "Recipe has no ingredients!") : DataResult.success(list));
//        }
//
//        @Override
//        public Codec<StoneMillRecipe> codec() {
//            return CODEC;
//        }
//
//        @Override
//        public PacketCodec<RegistryByteBuf, StoneMillRecipe> packetCodec() {
//            return null;
//        }
//
//        @Override
//        public StoneMillRecipe read(PacketByteBuf buf) {
//            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);
//            inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));
//            ItemStack output = buf.readItemStack();
//            return new StoneMillRecipe(inputs, output);
//        }
//
//        @Override
//        public void write(PacketByteBuf buf, StoneMillRecipe recipe) {
//            buf.writeInt(recipe.getIngredients().size());
//
//            for (Ingredient ingredient : recipe.getIngredients()) {
//                ingredient.write(buf);
//            }
//            buf.writeItemStack(recipe.getResult(null));
//        }
//    }
//}
