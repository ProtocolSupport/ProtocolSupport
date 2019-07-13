package protocolsupport.api.chat.modifiers;

import java.io.IOException;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.types.nbt.mojangson.MojangsonParser;
import protocolsupport.protocol.types.nbt.mojangson.MojangsonSerializer;
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.ServerPlatform;

@SuppressWarnings("deprecation")
public class HoverAction {

	private final Type type;
	private final String value;

	public HoverAction(Type type, String value) {
		this.type = type;
		this.value = value;
	}

	public HoverAction(BaseComponent component) {
		this.type = Type.SHOW_TEXT;
		this.value = ChatAPI.toJSON(component);
	}

	public HoverAction(ItemStack itemstack) {
		this.type = Type.SHOW_ITEM;
		this.value = ServerPlatform.get().getMiscUtils().serializeItemStackToNBTJson(itemstack);
	}

	public HoverAction(Entity entity) {
		this(new EntityInfo(entity));
	}

	public HoverAction(EntityInfo entityinfo) {
		this.type = Type.SHOW_ENTITY;
		NBTCompound compound = new NBTCompound();
		EntityType etype = entityinfo.getType();
		UUID euuid = entityinfo.getUUID();
		String ename = entityinfo.getName();
		if (etype != null) {
			compound.setTag("type", new NBTString(etype.getName()));
		}
		if (euuid != null) {
			compound.setTag("id", new NBTString(euuid.toString()));
		}
		if (ename != null) {
			compound.setTag("name", new NBTString(ename));
		}
		this.value = MojangsonSerializer.serialize(compound);
	}

	public Type getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public BaseComponent getText() {
		validateAction(type, Type.SHOW_TEXT);
		return ChatAPI.fromJSON(value);
	}

	public ItemStack getItemStack() {
		validateAction(type, Type.SHOW_ITEM);
		return ServerPlatform.get().getMiscUtils().deserializeItemStackFromNBTJson(value);
	}

	public EntityInfo getEntity() {
		validateAction(type, Type.SHOW_ENTITY);
		try {
			NBTCompound compound = MojangsonParser.parse(value);
			NBTString etype = compound.getTagOfType("type", NBTType.STRING);
			NBTString euuid = compound.getTagOfType("id", NBTType.STRING);
			return new EntityInfo(
				etype != null ? EntityType.fromName(etype.getValue()) : null,
				euuid != null ? UUID.fromString(euuid.getValue()) : null,
				NBTString.getValueOrNull(compound.getTagOfType("name", NBTType.STRING))
			);
		} catch (IOException e) {
			throw new RuntimeException("Unable to parse value", e);
		}
	}

	static void validateAction(Type current, Type expected) {
		if (current != expected) {
			throw new IllegalStateException(current + " is not an " + expected);
		}
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

	public static enum Type {
		SHOW_TEXT, SHOW_ITEM, SHOW_ENTITY;
	}

	public static class EntityInfo {
		private final EntityType etype;
		private final UUID uuid;
		private final String name;

		public EntityInfo(EntityType etype, UUID uuid, String name) {
			this.etype = etype;
			this.uuid = uuid;
			this.name = name;
		}

		public EntityInfo(Entity entity) {
			this(entity.getType(), entity.getUniqueId(), entity.getName());
		}

		public EntityType getType() {
			return etype;
		}

		public UUID getUUID() {
			return uuid;
		}

		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}
	}

}
