package protocolsupport.zplatform.impl.pe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

import java.util.concurrent.atomic.AtomicInteger;

public class PECreativeInventory {
	
	private PECreativeInventory() { }
	private static final PECreativeInventory INSTANCE = new PECreativeInventory();
	
	public static PECreativeInventory getInstance() {
		return INSTANCE;
	}

	private byte[] creativeItems;
	private int itemCount;
	
	public byte[] getCreativeItems() {
		return creativeItems;
	}
	
	public int getItemCount() {
		return itemCount;
	}

	public void generateCreativeInventoryItems() {
		JsonArray array = new JsonParser().parse(Utils.getResource("pe/creativeitems.json")).getAsJsonArray();
		AtomicInteger itemcount = new AtomicInteger();
		ByteBuf itembuf = Unpooled.buffer();

		for (JsonElement _item : array) {
			JsonObject item = _item.getAsJsonObject();

			if (item.has("nbt_hex")) { // For now, let's ignore the NBT tag
				continue;
			}

			int id = item.get("id").getAsInt();
			int damage = item.has("damage") ? item.get("damage").getAsInt() : 0;
			int amount = 1;

			// You may say "wow MrPowerGamerBR, why aren't you using the ItemStackSerializer.writeItemStack(...)?"
			// Well, ItemStackSerializer requires an ItemStackWrapper instance, which would be fine, if some items didn't magically
			// transform to air after creating them, So, because our IDs are already for PE, we can just directly write them to the
			// ByteBuf, without any consequences. ;)
			VarNumberSerializer.writeSVarInt(itembuf, id);
			VarNumberSerializer.writeSVarInt(itembuf, ((damage & 0xFFFF) << 8) | amount);
			ItemStackSerializer.writeTag(itembuf, false, ProtocolVersion.MINECRAFT_PE, NBTTagCompoundWrapper.NULL);
			itembuf.writeByte(0); //TODO: CanPlaceOn PE
			itembuf.writeByte(0); //TODO: CanDestroy PE

			itemcount.incrementAndGet();
		}

		itemCount = itemcount.get();
		creativeItems = MiscSerializer.readAllBytes(itembuf);
	}
}