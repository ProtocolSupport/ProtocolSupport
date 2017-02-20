package protocolsupport.api.chat.modifiers;

import java.util.UUID;

import org.bukkit.Achievement;
import org.bukkit.Statistic;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.utils.Any;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

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
		this.value = ServerPlatform.get().getMiscUtils().createNBTTagFromItemStack(itemstack).toString();
	}

	public HoverAction(Entity entity) {
		this(new EntityInfo(entity));
	}

	@SuppressWarnings("deprecation")
	public HoverAction(EntityInfo entityinfo) {
		this.type = Type.SHOW_ENTITY;
		NBTTagCompoundWrapper compound = ServerPlatform.get().getWrapperFactory().createEmptyNBTCompound();
		compound.setString("type", entityinfo.getType().getName());
		compound.setString("id", entityinfo.getUUID().toString());
		compound.setString("name", entityinfo.getName());
		this.value = compound.toString();
	}

	public HoverAction(Achievement achievment) {
		this.type = Type.SHOW_ACHIEVEMENT;
		this.value = ServerPlatform.get().getMiscUtils().getAchievmentName(achievment);
	}

	public HoverAction(Statistic stat) {
		this.type = Type.SHOW_ACHIEVEMENT;
		this.value = ServerPlatform.get().getMiscUtils().getStatisticName(stat);
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
		return ServerPlatform.get().getMiscUtils().createItemStackFromNBTTag(ServerPlatform.get().getWrapperFactory().createNBTCompoundFromJson(value));
	}

	@SuppressWarnings("deprecation")
	public EntityInfo getEntity() {
		validateAction(type, Type.SHOW_ENTITY);
		NBTTagCompoundWrapper compound = ServerPlatform.get().getWrapperFactory().createNBTCompoundFromJson(value);
		return new EntityInfo(EntityType.fromName(compound.getString("type")), UUID.fromString(compound.getString("id")), compound.getString("name"));
	}

	public Any<Achievement, Statistic> getAchievmentOrStat() {
		validateAction(type, Type.SHOW_ACHIEVEMENT);
		Achievement achievement = ServerPlatform.get().getMiscUtils().getAchievmentByName(value);
		Statistic stat = ServerPlatform.get().getMiscUtils().getStatisticByName(value);
		return new Any<>(achievement, stat);
	}

	static void validateAction(Type current, Type expected) {
		if (current != expected) {
			throw new IllegalStateException(current + " is not an "+expected);
		}
	}

	public static enum Type {
		SHOW_TEXT, SHOW_ACHIEVEMENT, SHOW_ITEM, SHOW_ENTITY;
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
	}

}
