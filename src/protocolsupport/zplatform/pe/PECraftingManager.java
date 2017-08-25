package protocolsupport.zplatform.pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.inventory.ItemStack;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.IntTuple;
import protocolsupport.zplatform.impl.spigot.itemstack.SpigotItemStackWrapper;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.util.UUID;

public class PECraftingManager {
    private static PECraftingManager instance = null;

    private final ClientBoundPacketData allRecipies;
    private ByteBuf byteBuf = ByteBufUtil.threadLocalDirectBuffer();
    private int recipeCount = 0;

    public static PECraftingManager getInstance()    {
        if (instance == null)   {
            instance = new PECraftingManager();
        }
        return instance;
    }

    public PECraftingManager()  {
        allRecipies = ClientBoundPacketData.create(PEPacketIDs.CRAFTING_DATA, ProtocolVersion.MINECRAFT_PE);
    }

    public ClientBoundPacketData getAllRecipies()   {
        return allRecipies;
    }

    public void registerRecipies()  {
        registerCrafting();
        registerFurnace();

        VarNumberSerializer.writeVarInt(allRecipies, recipeCount);
        allRecipies.writeBytes(byteBuf);
        byteBuf = null;
    }

    private void registerFurnace()  {
        addRecipeFurnace(getId(Blocks.GLASS), 1, getId(Blocks.SAND), 0);
    }

    private void registerCrafting()  {
        addRecipeShapeless(getId(Blocks.PLANKS), 4, getIds(Blocks.LOG));
        addRecipeShaped(getId(Blocks.CRAFTING_TABLE), 1, 2, 2, getIds(Blocks.PLANKS, Blocks.PLANKS, Blocks.PLANKS, Blocks.PLANKS));
        addRecipeShaped(getId(Blocks.FURNACE), 1, 3, 3, getIds(Blocks.COBBLESTONE, Blocks.COBBLESTONE, Blocks.COBBLESTONE, Blocks.COBBLESTONE, null, Blocks.COBBLESTONE, Blocks.COBBLESTONE, Blocks.COBBLESTONE, Blocks.COBBLESTONE));
        addRecipeShaped(getId(Items.WOODEN_PICKAXE), 1, 3, 3, getIds(Blocks.PLANKS, Blocks.PLANKS, Blocks.PLANKS, null, Items.STICK, null, null, Items.STICK, null));
        addRecipeShaped(getId(Items.STICK), 4, 1, 2, getIds(Blocks.PLANKS));
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

    public void addRecipeShaped(int output, int count, int width, int height, int[] required)   {
        recipeCount++;
        VarNumberSerializer.writeSVarInt(byteBuf, 1); //type
        VarNumberSerializer.writeSVarInt(byteBuf, width);
        VarNumberSerializer.writeSVarInt(byteBuf, height);
        for (int id : required) {
            ItemStackSerializer.writeItemStack(byteBuf, ProtocolVersion.MINECRAFT_PE, "en_US", SpigotItemStackWrapper.create(id), true);
        }
        VarNumberSerializer.writeVarInt(byteBuf, 1); //not sure but pocketmine has it
        ItemStackSerializer.writeItemStack(byteBuf, ProtocolVersion.MINECRAFT_PE, "en_US", new SpigotItemStackWrapper(new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(output), count)), true);
        MiscSerializer.writeUUID(byteBuf, UUID.nameUUIDFromBytes(byteBuf.array()));
    }

    public void addRecipeShapeless(int output, int count, int[] required)   {
        recipeCount++;
        VarNumberSerializer.writeSVarInt(byteBuf, 0); //type
        VarNumberSerializer.writeVarInt(byteBuf, required.length);
        for (int id : required) {
            ItemStackSerializer.writeItemStack(byteBuf, ProtocolVersion.MINECRAFT_PE, "en_US", SpigotItemStackWrapper.create(id), true);
        }
        VarNumberSerializer.writeVarInt(byteBuf, 1); //not sure but pocketmine has it
        ItemStackSerializer.writeItemStack(byteBuf, ProtocolVersion.MINECRAFT_PE, "en_US", new SpigotItemStackWrapper(new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(output), count)), true);
        MiscSerializer.writeUUID(byteBuf, UUID.nameUUIDFromBytes(byteBuf.array()));
    }

    public void addRecipeFurnace(int output, int count, int required, int meta)   {
        IntTuple iddata = ItemStackRemapper.ID_DATA_REMAPPING_REGISTRY.getTable(ProtocolVersion.MINECRAFT_PE).getRemap(required, meta == -1 ? 0 : meta);
        if (iddata != null) {
            required = iddata.getI1();
            if (iddata.getI2() != -1) {
               meta = iddata.getI2();
            } else {
                meta = -1;
            }
        }

        if (meta == -1 || meta == 0) {
            VarNumberSerializer.writeSVarInt(byteBuf, 2); //type
            VarNumberSerializer.writeSVarInt(byteBuf, required);
            ItemStackSerializer.writeItemStack(byteBuf, ProtocolVersion.MINECRAFT_PE, "en_US", new SpigotItemStackWrapper(new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(output), count)), true);
        } else { //meta recipe
            VarNumberSerializer.writeSVarInt(byteBuf, 3); //type
            VarNumberSerializer.writeSVarInt(byteBuf, required);
            VarNumberSerializer.writeSVarInt(byteBuf, meta);
            ItemStackSerializer.writeItemStack(byteBuf, ProtocolVersion.MINECRAFT_PE, "en_US", new SpigotItemStackWrapper(new net.minecraft.server.v1_12_R1.ItemStack(Item.getById(output), count)), true);
        }
    }
}
