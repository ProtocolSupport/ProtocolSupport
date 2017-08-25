package protocolsupport.zplatform.pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_12_R1.Block;
import net.minecraft.server.v1_12_R1.Item;
import net.minecraft.server.v1_12_R1.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import protocolsupport.ProtocolSupport;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.utils.IntTuple;
import protocolsupport.zplatform.impl.spigot.itemstack.SpigotItemStackWrapper;

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
        ProtocolSupport.logInfo("Processing and caching crafting recipes...");
        Bukkit.recipeIterator().forEachRemaining(recipe -> {
            if (recipe instanceof ShapedRecipe) {
                ShapedRecipe shapedRecipe = (ShapedRecipe) recipe;
                Map<Character, org.bukkit.inventory.ItemStack> map = shapedRecipe.getIngredientMap(); //caching for SPEEEEEEED
                String[] pattern = shapedRecipe.getShape(); //caching for SPEEEEEEED
                int width = pattern[0].length(), height = pattern.length;
                SpigotItemStackWrapper[] required = new SpigotItemStackWrapper[width * height];
                for (int z = 0; z < height; ++z) {
                    for (int x = 0; x < width; ++x) {
                        int i = z * x;
                        char key = pattern[z].charAt(x);
                        try {
                            org.bukkit.inventory.ItemStack stack = map.get(key);
                            required[i] = stack == null || stack.getTypeId() < 1 ? (SpigotItemStackWrapper) SpigotItemStackWrapper.create(0) : fromBukkitStack(stack);
                        } catch (NullPointerException e)    {
                            ProtocolSupport.logInfo("[WARN] Unable to locate key " + key + " for recipe with output " + recipe.getResult().toString());
                            return;
                        }
                    }
                }
                addRecipeShaped(fromBukkitStack(shapedRecipe.getResult()), width, height, required);
            } else if (recipe instanceof ShapelessRecipe)   {
                ShapelessRecipe shapelessRecipe = (ShapelessRecipe) recipe;
                SpigotItemStackWrapper[] required = new SpigotItemStackWrapper[shapelessRecipe.getIngredientList().size()];
                for (int i = 0; i < required.length; i++)   {
                    required[i] = fromBukkitStack(shapelessRecipe.getIngredientList().get(i));
                }
                addRecipeShapeless(fromBukkitStack(recipe.getResult()), required);
            } else if (recipe instanceof FurnaceRecipe) {
                FurnaceRecipe shapelessRecipe = (FurnaceRecipe) recipe;
                addRecipeFurnace(fromBukkitStack(shapelessRecipe.getResult()), fromBukkitStack(shapelessRecipe.getInput()));
            } else {
                ProtocolSupport.logInfo("unknown recipe type: " + recipe.getClass().getCanonicalName());
            }
        });

        byte[] cached = byteBuf.array();
        byteBuf.clear();

        VarNumberSerializer.writeVarInt(byteBuf, recipeCount);
        byteBuf.writeBytes(cached);

        ProtocolSupport.logInfo("Done! Processed " + recipeCount + " recipes!");
    }

    public SpigotItemStackWrapper fromBukkitStack(org.bukkit.inventory.ItemStack stack) {
        //ProtocolSupport.logInfo(stack.toString());
        Item i = Item.getById(
                stack.getType().getId());
        if (i != null)  {
            return new SpigotItemStackWrapper(new ItemStack(i, stack.getAmount(), stack.getDurability()));
        }
        Block b = Block.getById(stack.getTypeId());
        return new SpigotItemStackWrapper(new ItemStack(b, stack.getAmount(), stack.getDurability()));
    }

    public int getId(Block b)   {
        return Block.getId(b);
    }

    public int getId(Item i)   {
        return Item.getId(i);
    }

    public int[] getIds(Object... required) {
        int[] result = new int[required.length];
        for (int i = 0; i < required.length; i++)   {
            Object o = required[i];
            if (o instanceof Block) {
                result[i] = getId((Block) o);
            } else if (o instanceof Item) {
                result[i] = getId((Item) o);
            } else {
                result[i] = 0;
            }
        }

        return result;
    }

    public void addRecipeShaped(SpigotItemStackWrapper output, int width, int height, SpigotItemStackWrapper[] required)    {
        recipeCount++;
        VarNumberSerializer.writeSVarInt(byteBuf, 1); //type
        VarNumberSerializer.writeSVarInt(byteBuf, width);
        VarNumberSerializer.writeSVarInt(byteBuf, height);
        for (SpigotItemStackWrapper stack : required) {
            ItemStackSerializer.writeItemStack(byteBuf, ProtocolVersion.MINECRAFT_PE, "en_US", stack, true);
        }
        VarNumberSerializer.writeVarInt(byteBuf, 1); //not sure but pocketmine has it
        ItemStackSerializer.writeItemStack(byteBuf, ProtocolVersion.MINECRAFT_PE, "en_US", output, true);
        MiscSerializer.writeUUID(byteBuf, UUID.nameUUIDFromBytes(byteBuf.array()));
    }

    public void addRecipeShapeless(SpigotItemStackWrapper output, SpigotItemStackWrapper[] required)    {
        recipeCount++;
        VarNumberSerializer.writeSVarInt(byteBuf, 0);
        VarNumberSerializer.writeVarInt(byteBuf, required.length);
        for (SpigotItemStackWrapper stack : required) {
            ItemStackSerializer.writeItemStack(byteBuf, ProtocolVersion.MINECRAFT_PE, "en_US", stack, true);
        }
        VarNumberSerializer.writeVarInt(byteBuf, 1); //not sure but pocketmine has it
        ItemStackSerializer.writeItemStack(byteBuf, ProtocolVersion.MINECRAFT_PE, "en_US", output, true);
        MiscSerializer.writeUUID(byteBuf, UUID.nameUUIDFromBytes(byteBuf.array()));
    }

    public void addRecipeFurnace(SpigotItemStackWrapper output, SpigotItemStackWrapper input)   {
        IntTuple iddata = ItemStackRemapper.ID_DATA_REMAPPING_REGISTRY.getTable(ProtocolVersion.MINECRAFT_PE).getRemap(input.getTypeId(), input.getData());
        if (iddata != null) {
            input.setTypeId(iddata.getI1());
            if (iddata.getI2() != -1) {
                input.setData(iddata.getI2());
            }
        }

        if (input.getData() == 0) {
            VarNumberSerializer.writeSVarInt(byteBuf, 2); //type
            VarNumberSerializer.writeSVarInt(byteBuf, input.getTypeId());
            ItemStackSerializer.writeItemStack(byteBuf, ProtocolVersion.MINECRAFT_PE, "en_US", output, true);
        } else { //meta recipe
            VarNumberSerializer.writeSVarInt(byteBuf, 3); //type
            VarNumberSerializer.writeSVarInt(byteBuf, input.getTypeId());
            VarNumberSerializer.writeSVarInt(byteBuf, input.getData());
            ItemStackSerializer.writeItemStack(byteBuf, ProtocolVersion.MINECRAFT_PE, "en_US", output, true);
        }
    }
}
