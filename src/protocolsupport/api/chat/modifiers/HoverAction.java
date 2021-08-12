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
import protocolsupport.protocol.types.nbt.mojangson.MojangsonParser;
import protocolsupport.protocol.types.nbt.mojangson.MojangsonSerializer;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.NamespacedKeyUtils;
import protocolsupport.utils.reflection.ReflectionUtils;

public class HoverAction {

	protected final Type type;
	protected final Object contents;

	@Deprecated
	public HoverAction(Type type, String value) {
		this.type = type;
		this.contents = switch (type) {
			case SHOW_TEXT -> ChatAPI.fromJSON(value);
			case SHOW_ENTITY -> {
				try {
					NBTCompound compound = MojangsonParser.parse(value);
					NBTString etype = compound.getStringTagOrNull("type");
					NBTString euuid = compound.getStringTagOrNull("id");
					yield new EntityInfo(
						etype != null ? Registry.ENTITY_TYPE.get(NamespacedKeyUtils.fromString(etype.getValue())) : null,
						euuid != null ? UUID.fromString(euuid.getValue()) : null,
						compound.getStringTagValueOrNull("name")
					);
				} catch (IOException e) {
					throw new IllegalArgumentException("HoverAction value " + value + " can't be parsed as EntityInfo");
				}
			}
			case SHOW_ITEM -> {
				try {
					yield new NetworkBukkitItemStack(CommonNBT.deserializeItemStackFromNBT(MojangsonParser.parse(value)));
				} catch (IOException e) {
					throw new IllegalArgumentException("HoverAction value " + value + " can't be parsed as ItemStack");
				}
			}
			default -> throw new IllegalArgumentException("Unknown HoverAction Type " + type);
		};
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
		if (contents instanceof BaseComponent text) {
			return ChatAPI.toJSON(text);
		} else if (contents instanceof EntityInfo entityinfo) {
			NBTCompound compound = new NBTCompound();
			String ename = entityinfo.getName();
			compound.setTag("type", new NBTString(entityinfo.getType().getKey().toString()));
			compound.setTag("id", new NBTString(entityinfo.getUUID().toString()));
			if (ename != null) {
				compound.setTag("name", new NBTString(ename));
			}
			return MojangsonSerializer.serialize(compound);
		} else if (contents instanceof ItemStack itemstack) {
			return MojangsonSerializer.serialize(CommonNBT.serializeItemStackToNBT(NetworkBukkitItemStack.create(itemstack)));
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
	public HoverAction clone() {
		switch (type) {
			case SHOW_TEXT: {
				return new HoverAction(getText().clone());
			}
			case SHOW_ITEM: {
				return new HoverAction(getItemStack().clone());
			}
			case SHOW_ENTITY: {
				return new HoverAction(getEntity().clone());
			}
			default: {
				throw new IllegalStateException("Unknown hover type " + type);
			}
		}
	}

	@Override
	public String toString() {
		return ReflectionUtils.toStringAllFields(this);
	}

	public enum Type {
		SHOW_TEXT, SHOW_ITEM, SHOW_ENTITY;
	}

	public static class EntityInfo {

		protected final EntityType etype;
		protected final UUID uuid;
		protected final BaseComponent name;

		public EntityInfo(EntityType etype, UUID uuid, String name) {
			this(etype, uuid, name != null ? ChatAPI.fromJSON(name, true) : null);
		}

		public EntityInfo(EntityType etype, UUID uuid, BaseComponent displayname) {
			this.etype = ((etype != null) && (etype != EntityType.UNKNOWN)) ? etype : EntityType.PIG;
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

		@Override
		public EntityInfo clone() {
			BaseComponent displayName = getDisplayName();
			return new EntityInfo(getType(), getUUID(), displayName != null ? displayName.clone() : null);
		}

		@Deprecated
		public String getName() {
			return ChatAPI.toJSON(name);
		}

		@Override
		public String toString() {
			return ReflectionUtils.toStringAllFields(this);
		}

	}

}
