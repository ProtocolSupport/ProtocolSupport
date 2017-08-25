package protocolsupport.zplatform.pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.bukkit.Bukkit;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.IntTuple;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

import java.util.Map;
import java.util.UUID;

public class PECraftingManager {
    private static PECraftingManager instance = null;

    private static final org.bukkit.inventory.ItemStack AIR = new org.bukkit.inventory.ItemStack(0, 0);

    private ByteBuf byteBuf = Unpooled.buffer();
    private int recipeCount = 0;

    public static PECraftingManager getInstance()    {
        if (instance == null)   {
            instance = new PECraftingManager();
        }
        return instance;
    }

    public PECraftingManager()  {
    }

    public ByteBuf getAllRecipes()   {
        return byteBuf;
    }

    public void registerRecipes()  {
        Bukkit.recipeIterator().forEachRemaining(recipe -> {
            if (recipe instanceof ShapedRecipe) {
                ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
                Map<Character, org.bukkit.inventory.ItemStack> map = shapedRecipe.getIngredientMap(); //caching for SPEEEEEEED
                String[] pattern = shapedRecipe.getShape(); //caching for SPEEEEEEED
                int width = pattern[0].length(), height = pattern.length;
                ItemStackWrapper[] required = new ItemStackWrapper[width * height];
                for (int z = 0; z < height; ++z) {
                    for (int x = 0; x < width; ++x) {
                        int i = z * x;
                        char key = pattern[z].charAt(x);
                        try {
                            org.bukkit.inventory.ItemStack stack = map.get(key);
                            required[i] = stack == null || stack.getTypeId() < 1 ? ServerPlatform.get().getWrapperFactory().createItemStack(0) : fromBukkitStack(stack);
                        } catch (NullPointerException e)    {
                            return;
                        }
                    }
                }
                addRecipeShaped(fromBukkitStack(shapedRecipe.getResult()), width, height, required);
            } else if (recipe instanceof ShapelessRecipe)   {
                ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
                ItemStackWrapper[] required = new ItemStackWrapper[shapelessRecipe.getIngredientList().size()];
                for (int i = 0; i < required.length; i++)   {
                    required[i] = fromBukkitStack(shapelessRecipe.getIngredientList().get(i));
                }
                addRecipeShapeless(fromBukkitStack(recipe.getResult()), required);
            } else if (recipe instanceof FurnaceRecipe) {
                FurnaceRecipe shapelessRecipe = (FurnaceRecipe) recipe;
                addRecipeFurnace(fromBukkitStack(shapelessRecipe.getResult()), fromBukkitStack(shapelessRecipe.getInput()));
            } else {
            }
        });

        byte[] cached = byteBuf.array();
        byteBuf.clear();

        VarNumberSerializer.writeVarInt(byteBuf, recipeCount);
        byteBuf.writeBytes(cached);

    }

    public ItemStackWrapper fromBukkitStack(org.bukkit.inventory.ItemStack stack) {
        return ServerPlatform.get().getWrapperFactory().createItemStack(stack.getType());
    }

    public void addRecipeShaped(ItemStackWrapper output, int width, int height, ItemStackWrapper[] required)    {
        recipeCount++;
        VarNumberSerializer.writeSVarInt(byteBuf, 1); //recipe type
        VarNumberSerializer.writeSVarInt(byteBuf, width);
        VarNumberSerializer.writeSVarInt(byteBuf, height);
        for (ItemStackWrapper stack : required) {
            ItemStackSerializer.writeItemStack(byteBuf, ProtocolVersion.MINECRAFT_PE, I18NData.DEFAULT_LOCALE, stack, true);
        }
        VarNumberSerializer.writeVarInt(byteBuf, 1); // result item count (PC only supports one itemstack output, so hardcoded to 1)
        ItemStackSerializer.writeItemStack(byteBuf, ProtocolVersion.MINECRAFT_PE, I18NData.DEFAULT_LOCALE, output, true);
        MiscSerializer.writeUUID(byteBuf, UUID.nameUUIDFromBytes(byteBuf.array()));
    }

    public void addRecipeShapeless(ItemStackWrapper output, ItemStackWrapper[] required)    {
        recipeCount++;
        VarNumberSerializer.writeSVarInt(byteBuf, 0); //recipe type
        VarNumberSerializer.writeVarInt(byteBuf, required.length);
        for (ItemStackWrapper stack : required) {
            ItemStackSerializer.writeItemStack(byteBuf, ProtocolVersion.MINECRAFT_PE, I18NData.DEFAULT_LOCALE, stack, true);
        }
        VarNumberSerializer.writeVarInt(byteBuf, 1); // result item count (PC only supports one itemstack output, so hardcoded to 1)
        ItemStackSerializer.writeItemStack(byteBuf, ProtocolVersion.MINECRAFT_PE, I18NData.DEFAULT_LOCALE, output, true);
        MiscSerializer.writeUUID(byteBuf, UUID.nameUUIDFromBytes(byteBuf.array()));
    }

    public void addRecipeFurnace(ItemStackWrapper output, ItemStackWrapper input)   {
        IntTuple iddata = ItemStackRemapper.ID_DATA_REMAPPING_REGISTRY.getTable(ProtocolVersion.MINECRAFT_PE).getRemap(input.getTypeId(), input.getData());
        if (iddata != null) {
            input.setTypeId(iddata.getI1());
            if (iddata.getI2() != -1) {
                input.setData(iddata.getI2());
            }
        }

        if (input.getData() == 0) {
            VarNumberSerializer.writeSVarInt(byteBuf, 2); //recipe type
            VarNumberSerializer.writeSVarInt(byteBuf, input.getTypeId());
            ItemStackSerializer.writeItemStack(byteBuf, ProtocolVersion.MINECRAFT_PE, I18NData.DEFAULT_LOCALE, output, true);
        } else { //meta recipe
            VarNumberSerializer.writeSVarInt(byteBuf, 3); //recipe type
            VarNumberSerializer.writeSVarInt(byteBuf, input.getTypeId());
            VarNumberSerializer.writeSVarInt(byteBuf, input.getData());
            ItemStackSerializer.writeItemStack(byteBuf, ProtocolVersion.MINECRAFT_PE, I18NData.DEFAULT_LOCALE, output, true);
        }
    }
}
