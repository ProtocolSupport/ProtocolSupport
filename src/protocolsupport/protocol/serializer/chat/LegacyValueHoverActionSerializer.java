package protocolsupport.protocol.serializer.chat;

import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.modifiers.HoverAction;
import protocolsupport.api.chat.modifiers.HoverAction.EntityInfo;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry.EntityRemappingTable;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.NetworkBukkitItemStack;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTByte;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTShort;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.mojangson.LegacyMojangsonSerializer;
import protocolsupport.protocol.types.nbt.mojangson.MojangsonSerializer;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.ItemStackWriteEventHelper;
import protocolsupport.protocol.utils.json.SimpleJsonTreeSerializer;
import protocolsupport.utils.JsonUtils;

public class LegacyValueHoverActionSerializer extends HoverActionSerializer {

	protected final ProtocolVersion version;
	protected final EntityRemappingTable entityRemapTable;

	public LegacyValueHoverActionSerializer(ProtocolVersion version) {
		this.version = version;
		this.entityRemapTable = EntityRemappersRegistry.REGISTRY.getTable(version);
	}

	@Override
	public JsonElement serialize(SimpleJsonTreeSerializer<String> serializer, HoverAction action, String locale) {
		JsonObject json = new JsonObject();

		json.addProperty(key_action, action.getType().toString().toLowerCase());

		switch (action.getType()) {
			case SHOW_TEXT: {
				json.add("value", serializer.serialize(action.getContents(), locale));
				break;
			}
			case SHOW_ENTITY: {
				EntityInfo entityinfo = (EntityInfo) action.getContents();
				NetworkEntityType etype = entityRemapTable.getRemap(NetworkEntityType.getByBukkitType(entityinfo.getType())).getKey();
				NBTCompound rootTag = new NBTCompound();
				rootTag.setTag("type", new NBTString(version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_11) ? etype.getKey() : LegacyEntityId.getStringId(etype)));
				rootTag.setTag("id", new NBTString(entityinfo.getUUID().toString()));
				BaseComponent name = entityinfo.getDisplayName();
				if (name != null) {
					rootTag.setTag("name", new NBTString(JsonUtils.GSON.toJson(serializer.serialize(name, locale))));
				}
				json.addProperty("value", serializeTagToMojanson(rootTag));
				break;
			}
			case SHOW_ITEM: {
				NetworkItemStack itemstack = NetworkBukkitItemStack.create((ItemStack) action.getContents());
				ItemStackWriteEventHelper.callEvent(version, locale, itemstack);
				itemstack = ItemStackRemapper.remapToClient(version, locale, itemstack);
				NBTCompound rootTag = new NBTCompound();
				if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_13)) {
					rootTag.setTag("id", new NBTString(ItemMaterialLookup.getByRuntimeId(itemstack.getTypeId()).getKey().toString()));
				} else {
					if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_8)) {
						rootTag.setTag("id", new NBTString(String.valueOf(itemstack.getTypeId())));
					} else {
						rootTag.setTag("id", new NBTShort((short) itemstack.getTypeId()));
					}
					rootTag.setTag("Damage", new NBTShort((short) itemstack.getLegacyData()));
				}
				rootTag.setTag("Count", new NBTByte((byte) itemstack.getAmount()));
				rootTag.setTag("tag", itemstack.getNBT());
				json.addProperty("value", serializeTagToMojanson(rootTag));
			}
		}

		return json;
	}

	protected String serializeTagToMojanson(NBTCompound tag) {
		return version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_12) ? MojangsonSerializer.serialize(tag) : LegacyMojangsonSerializer.serialize(tag);
	}

}
