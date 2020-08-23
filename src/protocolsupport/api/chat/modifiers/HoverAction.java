package protocolsupport.api.chat.modifiers;

import java.io.IOException;
import java.util.UUID;

import org.bukkit.Registry;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.protocol.types.NetworkBukkitItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.types.nbt.mojangson.MojangsonParser;
import protocolsupport.protocol.types.nbt.mojangson.MojangsonSerializer;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.NamespacedKeyUtils;
import protocolsupport.utils.Utils;

public class HoverAction {

	private final Type type;
	private final Object contents;

	@Deprecated
	public HoverAction(Type type, String value) {
		this.type = type;
		switch (type) {
			case SHOW_TEXT: {
				this.contents = ChatAPI.fromJSON(value);
				return;
			}
			case SHOW_ENTITY: {
				try {
					NBTCompound compound = MojangsonParser.parse(value);
					NBTString etype = compound.getTagOfTypeOrNull("type", NBTType.STRING);
					NBTString euuid = compound.getTagOfTypeOrNull("id", NBTType.STRING);
					this.contents = new EntityInfo(
						etype != null ? Registry.ENTITY_TYPE.get(NamespacedKeyUtils.fromString(etype.getValue())) : null,
						euuid != null ? UUID.fromString(euuid.getValue()) : null,
						compound.getStringTagValueOrNull("name")
					);
					return;
				} catch (IOException e) {
					throw new IllegalArgumentException("HoverAction value " + value + " can't be parsed as EntityInfo");
				}
			}
			case SHOW_ITEM: {
				try {
					this.contents = new NetworkBukkitItemStack(CommonNBT.deserializeItemStackFromNBT(MojangsonParser.parse(value)));
				} catch (IOException e) {
					throw new IllegalArgumentException("HoverAction value " + value + " can't be parsed as ItemStack");
				}
				return;
			}
		}
		throw new IllegalArgumentException("Unknown HoverAction Type " + type);
	}

	public HoverAction(BaseComponent component) {
		this.type = Type.SHOW_TEXT;
		this.contents = component;
	}

	public HoverAction(ItemStack itemstack) {
		this.type = Type.SHOW_ITEM;
		this.contents = itemstack;
	}

	public HoverAction(Entity entity) {
		this(new EntityInfo(entity));
	}

	public HoverAction(EntityInfo entityinfo) {
		this.type = Type.SHOW_ENTITY;
		this.contents = entityinfo;
	}

	public Type getType() {
		return type;
	}

	public Object getContents() {
		return contents;
	}

	@Deprecated
	public String getValue() {
		if (contents instanceof BaseComponent) {
			return ChatAPI.toJSON((BaseComponent) contents);
		} else if (contents instanceof EntityInfo) {
			EntityInfo entityinfo = (EntityInfo) contents;
			NBTCompound compound = new NBTCompound();
			String ename = entityinfo.getName();
			compound.setTag("type", new NBTString(entityinfo.getType().getKey().toString()));
			compound.setTag("id", new NBTString(entityinfo.getUUID().toString()));
			if (ename != null) {
				compound.setTag("name", new NBTString(ename));
			}
			return MojangsonSerializer.serialize(compound);
		} else if (contents instanceof ItemStack) {
			return MojangsonSerializer.serialize(CommonNBT.serializeItemStackToNBT(NetworkBukkitItemStack.create(getItemStack())));
		} else {
			return contents.toString();
		}
	}

	public BaseComponent getText() {
		validateAction(type, Type.SHOW_TEXT);
		return (BaseComponent) contents;
	}

	public ItemStack getItemStack() {
		validateAction(type, Type.SHOW_ITEM);
		return (ItemStack) contents;
	}

	public EntityInfo getEntity() {
		validateAction(type, Type.SHOW_ENTITY);
		return (EntityInfo) contents;
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
		private final BaseComponent name;

		public EntityInfo(EntityType etype, UUID uuid, String name) {
			this(etype, uuid, name != null ? ChatAPI.fromJSON(name, true) : null);
		}

		public EntityInfo(EntityType etype, UUID uuid, BaseComponent displayname) {
			this.etype = etype != null ? etype : EntityType.PIG;
			this.uuid = uuid != null ? uuid : new UUID(0, 0);
			this.name = displayname;
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

		public BaseComponent getDisplayName() {
			return name;
		}

		@Deprecated
		public String getName() {
			return ChatAPI.toJSON(name);
		}

		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}

	}

}
