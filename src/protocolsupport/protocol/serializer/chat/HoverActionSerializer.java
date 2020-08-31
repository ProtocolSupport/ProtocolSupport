package protocolsupport.protocol.serializer.chat;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.UUID;

import org.bukkit.Registry;
import org.bukkit.inventory.ItemStack;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.modifiers.HoverAction;
import protocolsupport.api.chat.modifiers.HoverAction.EntityInfo;
import protocolsupport.protocol.types.NetworkBukkitItemStack;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.mojangson.MojangsonParser;
import protocolsupport.protocol.types.nbt.mojangson.MojangsonSerializer;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.NamespacedKeyUtils;
import protocolsupport.protocol.utils.json.SimpleJsonObjectSerializer;
import protocolsupport.protocol.utils.json.SimpleJsonTreeSerializer;
import protocolsupport.utils.JsonUtils;

public class HoverActionSerializer implements JsonDeserializer<HoverAction>, SimpleJsonObjectSerializer<HoverAction, String> {

	public static final HoverActionSerializer INSTANCE = new HoverActionSerializer();

	protected HoverActionSerializer() {
	}

	protected static final String key_action = "action";
	protected static final String key_contents = "contents";

	@SuppressWarnings("deprecation")
	@Override
	public HoverAction deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) {
		JsonObject rootJson = element.getAsJsonObject();

		JsonPrimitive actionJson = rootJson.getAsJsonPrimitive(key_action);
		if (actionJson == null) {
			return null;
		}
		HoverAction.Type atype = HoverAction.Type.valueOf(actionJson.getAsString().toUpperCase());

		JsonElement contentsJson = rootJson.get(key_contents);
		if (contentsJson != null) {
			switch (atype) {
				case SHOW_TEXT: {
					return new HoverAction((BaseComponent) ctx.deserialize(contentsJson, BaseComponent.class));
				}
				case SHOW_ENTITY: {
					JsonObject entityinfoJson = JsonUtils.getAsJsonObject(contentsJson, "hover contents");
					return new HoverAction(new EntityInfo(
						Registry.ENTITY_TYPE.get(NamespacedKeyUtils.fromString(JsonUtils.getString(entityinfoJson, "type"))),
						UUID.fromString(JsonUtils.getString(entityinfoJson, "id")),
						(BaseComponent) (entityinfoJson.has("name") ? ctx.deserialize(JsonUtils.getJsonObject(entityinfoJson, "name"), BaseComponent.class) : null)
					));
				}
				case SHOW_ITEM: {
					JsonObject itemstackJson = JsonUtils.getAsJsonObject(contentsJson, "hover contents");
					NetworkItemStack itemstack = new NetworkItemStack();
					itemstack.setTypeId(ItemMaterialLookup.getRuntimeId(ItemMaterialLookup.getByKey(JsonUtils.getString(itemstackJson, "id"))));
					if (itemstackJson.has("count")) {
						itemstack.setAmount(JsonUtils.getInt(itemstackJson, "count"));
					} else {
						itemstack.setAmount(1);
					}
					if (itemstackJson.has("tag")) {
						try {
							itemstack.setNBT(MojangsonParser.parse(JsonUtils.getString(itemstackJson, "tag")));
						} catch (IOException e) {
							throw new JsonParseException("Error parsing itemstack tag json", e);
						}
					}
					return new HoverAction(new NetworkBukkitItemStack(itemstack));
				}
			}
		}

		JsonElement valueJson = rootJson.get("value");
		if (valueJson != null) {
			BaseComponent value = ctx.deserialize(valueJson, BaseComponent.class);
			switch (atype) {
				case SHOW_TEXT: {
					return new HoverAction(value);
				}
				case SHOW_ENTITY:
				case SHOW_ITEM: {
					//TODO: add toLegacyUnformattedText to BaseComponent and use it instead
					return new HoverAction(atype, org.bukkit.ChatColor.stripColor(value.toLegacyText()));
				}
			}
		}
		return null;
	}

	@Override
	public JsonElement serialize(SimpleJsonTreeSerializer<String> serializer, HoverAction action, String locale) {
		JsonObject json = new JsonObject();

		json.addProperty(key_action, action.getType().toString().toLowerCase());

		switch (action.getType()) {
			case SHOW_TEXT: {
				json.add(key_contents, serializer.serialize(action.getContents(), locale));
				break;
			}
			case SHOW_ENTITY: {
				EntityInfo einfo = (EntityInfo) action.getContents();
				JsonObject einfoJson = new JsonObject();
				einfoJson.addProperty("type", einfo.getType().getKey().toString());
				einfoJson.addProperty("id", einfo.getUUID().toString());
				BaseComponent displayname = einfo.getDisplayName();
				if (displayname != null) {
					einfoJson.add("name", serializer.serialize(displayname, locale));
				}
				json.add(key_contents, einfoJson);
				break;
			}
			case SHOW_ITEM: {
				NetworkItemStack itemstack = NetworkBukkitItemStack.create((ItemStack) action.getContents());
				JsonObject itemstackJson = new JsonObject();
				itemstackJson.addProperty("id", ItemMaterialLookup.getByRuntimeId(itemstack.getTypeId()).getKey().toString());
				itemstackJson.addProperty("count", itemstack.getAmount());
				NBTCompound nbt = itemstack.getNBT();
				if (nbt != null) {
					itemstackJson.addProperty("tag", MojangsonSerializer.serialize(nbt));
				}
				json.add(key_contents, itemstackJson);
				break;
			}
		}

		return json;
	}

}
