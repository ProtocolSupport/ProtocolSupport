package protocolsupport.protocol.serializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.events.ItemStackWriteEvent;
import protocolsupport.protocol.legacyremapper.LegacyEntityType;
import protocolsupport.protocol.legacyremapper.LegacyPotion;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeskipper.id.IdSkipper;
import protocolsupport.protocol.typeskipper.id.SkippingTable.IntSkippingTable;
import protocolsupport.protocol.utils.GameProfileSerializer;
import protocolsupport.protocol.utils.types.MerchantData;
import protocolsupport.protocol.utils.types.MerchantData.TradeOffer;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.netty.WrappingBuffer;
import protocolsupport.zplatform.ServerImplementationType;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;

public class ProtocolSupportPacketDataSerializer extends WrappingBuffer {

	protected ProtocolVersion version;

	public ProtocolSupportPacketDataSerializer(ByteBuf buf, ProtocolVersion version) {
		this.buf = buf;
		this.version = version;
	}

	public ProtocolVersion getVersion() {
		return this.version;
	}

	public int readVarInt() {
		return readVarInt(this);
	}

	public void writeVarInt(int varint) {
		writeVarInt(this, varint);
	}

	public long readVarLong() {
		long varlong = 0L;
		int length = 0;
		byte part;
		do {
			part = this.readByte();
			varlong |= (part & 0x7F) << (length++ * 7);
			if (length > 10) {
				throw new RuntimeException("VarLong too big");
			}
		} while ((part & 0x80) == 0x80);
		return varlong;
	}

	public void writeVarLong(long varlong) {
		while ((varlong & 0xFFFFFFFFFFFFFF80L) != 0x0L) {
			this.writeByte((int) (varlong & 0x7FL) | 0x80);
			varlong >>>= 7;
		}
		this.writeByte((int) varlong);
	}

	public <T extends Enum<T>> T readEnum(Class<T> clazz) {
		return clazz.getEnumConstants()[readVarInt()];
	}

	public void writeEnum(Enum<?> e) {
		writeVarInt(e.ordinal());
	}

	public UUID readUUID() {
		return new UUID(readLong(), readLong());
	}

	public void writeUUID(UUID uuid) {
		writeLong(uuid.getMostSignificantBits());
		writeLong(uuid.getLeastSignificantBits());
	}

	public String readString() {
		return readString(Short.MAX_VALUE);
	}

	public String readString(int limit) {
		if (getVersion().isBeforeOrEq(ProtocolVersion.MINECRAFT_1_6_4)) {
			int length = readUnsignedShort() * 2;
			checkLimit(length, limit * 4);
			return new String(ProtocolSupportPacketDataSerializer.toArray(readSlice(length)), StandardCharsets.UTF_16BE);
		} else {
			int length = readVarInt();
			checkLimit(length, limit * 4);
			return new String(ProtocolSupportPacketDataSerializer.toArray(readSlice(length)), StandardCharsets.UTF_8);
		}
	}

	public void writeString(String string) {
		if (getVersion().isBeforeOrEq(ProtocolVersion.MINECRAFT_1_6_4)) {
			writeShort(string.length());
			writeBytes(string.getBytes(StandardCharsets.UTF_16BE));
		} else {
			byte[] data = string.getBytes(StandardCharsets.UTF_8);
			writeVarInt(data.length);
			writeBytes(data);
		}
	}

	public Position readPosition() {
		return Position.fromLong(readLong());
	}

	public Position readLegacyPositionB() {
		return new Position(readInt(), readUnsignedByte(), readInt());
	}

	public Position readLegacyPositionS() {
		return new Position(readInt(), readShort(), readInt());
	}

	public Position readLegacyPositionI() {
		return new Position(readInt(), readInt(), readInt());
	}

	public void writePosition(Position position) {
		writeLong(position.asLong());
	}

	public void writeLegacyPositionB(Position position) {
		writeInt(position.getX());
		writeByte(position.getY());
		writeInt(position.getZ());
	}

	public void writeLegacyPositionS(Position position) {
		writeInt(position.getX());
		writeShort(position.getY());
		writeInt(position.getZ());
	}

	public void writeLegacyPositionI(Position position) {
		writeInt(position.getX());
		writeInt(position.getY());
		writeInt(position.getZ());
	}

	public byte[] readByteArray() {
		return readByteArray(readableBytes());
	}

	public byte[] readByteArray(int limit) {
		int size = -1;
		if (getVersion().isBeforeOrEq(ProtocolVersion.MINECRAFT_1_7_10)) {
			size = readShort();
		} else {
			size = readVarInt();
		}
		checkLimit(size, limit);
		byte[] array = new byte[size];
		readBytes(array);
		return array;
	}

	public void writeByteArray(ByteBuf data) {
		if (getVersion().isBeforeOrEq(ProtocolVersion.MINECRAFT_1_7_10)) {
			writeShort(data.readableBytes());
		} else {
			writeVarInt(data.readableBytes());
		}
		writeBytes(data);
	}

	public void writeByteArray(byte[] data) {
		if (getVersion().isBeforeOrEq(ProtocolVersion.MINECRAFT_1_7_10)) {
			writeShort(data.length);
		} else {
			writeVarInt(data.length);
		}
		writeBytes(data);
	}

	public ItemStackWrapper readItemStack() {
		int type = readShort();
		if (type >= 0) {
			ItemStackWrapper itemstack = ItemStackWrapper.create(type);
			itemstack.setTypeId(type);
			itemstack.setAmount(readByte());
			itemstack.setData(readShort());
			itemstack.setTag(readTag());
			return itemstack;
		}
		return ItemStackWrapper.createNull();
	}

	public void writeItemStack(ItemStackWrapper itemstack) {
		if (itemstack.isNull()) {
			writeShort(-1);
			return;
		}
		itemstack = transformItemStack(itemstack);
		writeShort(IdRemapper.ITEM.getTable(version).getRemap(itemstack.getTypeId()));
		writeByte(itemstack.getAmount());
		writeShort(itemstack.getData());
		writeTag(itemstack.getTag());
	}

	public NBTTagCompoundWrapper readTag() {
		try {
			if (getVersion().isBefore(ProtocolVersion.MINECRAFT_1_8)) {
				final short length = readShort();
				if (length < 0) {
					return NBTTagCompoundWrapper.createNull();
				}
				try (DataInputStream datainputstream = new DataInputStream(new GZIPInputStream(new ByteBufInputStream(readSlice(length))))) {
					return NBTTagCompoundWrapper.fromStream(datainputstream);
				}
			} else {
				markReaderIndex();
				if (readByte() == 0) {
					return NBTTagCompoundWrapper.createNull();
				}
				resetReaderIndex();
				try (DataInputStream datainputstream = new DataInputStream(new ByteBufInputStream(this))) {
					return NBTTagCompoundWrapper.fromStream(datainputstream);
				}
			}
		} catch (IOException e) {
			throw new DecoderException(e);
		}
	}

	public void writeTag(NBTTagCompoundWrapper tag) {
		try {
			if (getVersion().isBefore(ProtocolVersion.MINECRAFT_1_8)) {
				if (tag.isNull()) {
					writeShort(-1);
				} else {
					int writerIndex = writerIndex();
					//fake length
					writeShort(0);
					//actual nbt
					try (DataOutputStream dataoutputstream = new DataOutputStream(new GZIPOutputStream(new ByteBufOutputStream(this)))) {
						tag.writeToStream(dataoutputstream);
					}
					//now replace fake length with real length
					setShort(writerIndex, writerIndex() - writerIndex - Short.BYTES);
				}
			} else {
				if (tag.isNull()) {
					writeByte(0);
				} else {
					try (DataOutputStream dataoutputstream = new DataOutputStream(new ByteBufOutputStream(this))) {
						tag.writeToStream(dataoutputstream);
					}
				}
			}
		} catch (Throwable ioexception) {
			throw new EncoderException(ioexception);
		}
	}

	public MerchantData readMerchantData() {
		MerchantData merchdata = new MerchantData(readInt());
		int count = readUnsignedByte();
		for (int i = 0; i < count; i++) {
			ItemStackWrapper itemstack1 = readItemStack();
			ItemStackWrapper result = readItemStack();
			ItemStackWrapper itemstack2 = ItemStackWrapper.createNull();
			if (readBoolean()) {
				itemstack2 = readItemStack();
			}
			boolean disabled = readBoolean();
			int uses = 0;
			int maxuses = 7;
			if (getVersion().isAfterOrEq(ProtocolVersion.MINECRAFT_1_8)) {
				uses = readInt();
				maxuses = readInt();
			}
			merchdata.addOffer(new TradeOffer(itemstack1, itemstack2, result, disabled ? maxuses : uses, maxuses));
		}
		return merchdata;
	}

	public void writeMerchantData(MerchantData merchdata) {
		writeInt(merchdata.getWindowId());
		writeByte(merchdata.getOffers().size());
		for (TradeOffer offer : merchdata.getOffers()) {
			writeItemStack(offer.getItemStack1());
			writeItemStack(offer.getResult());
			writeBoolean(offer.hasItemStack2());
			if (offer.hasItemStack2()) {
				writeItemStack(offer.getItemStack2());
			}
			writeBoolean(offer.isDisabled());
			if (getVersion().isAfterOrEq(ProtocolVersion.MINECRAFT_1_8)) {
				writeInt(offer.getUses());
				writeInt(offer.getMaxUses());
			}
		}
	}

	protected void checkLimit(int size, int limit) {
		if (size > limit) {
			throw new DecoderException("Size " + size + " is bigger than allowed " + limit);
		}
	}

	@SuppressWarnings("deprecation")
	private ItemStackWrapper transformItemStack(ItemStackWrapper original) {
		ItemStackWrapper itemstack = original.cloneItemStack();
		Material item = itemstack.getType();
		if (getVersion().isBefore(ProtocolVersion.MINECRAFT_1_9) && (item == Material.SKULL_ITEM) && (itemstack.getData() == 5)) {
			itemstack.setData(3);
			NBTTagCompoundWrapper wrapper = NBTTagCompoundWrapper.createEmpty();
			wrapper.setCompound("SkullOwner", createDragonHeadSkullTag());
			itemstack.setTag(wrapper);
		}
		NBTTagCompoundWrapper nbttagcompound = itemstack.getTag();
		if (!nbttagcompound.isNull()) {
			if (getVersion().isBefore(ProtocolVersion.MINECRAFT_1_8) && (item == Material.WRITTEN_BOOK)) {
				if (nbttagcompound.hasKeyOfType("pages", NBTTagCompoundWrapper.TYPE_LIST)) {
					NBTTagListWrapper pages = nbttagcompound.getList("pages", NBTTagCompoundWrapper.TYPE_STRING);
					NBTTagListWrapper newpages = NBTTagListWrapper.create();
					for (int i = 0; i < pages.size(); i++) {
						newpages.addString(ChatAPI.fromJSON(pages.getString(i)).toLegacyText());
					}
					nbttagcompound.setList("pages", newpages);
				}
			}
			if (getVersion().isBeforeOrEq(ProtocolVersion.MINECRAFT_1_7_5) && (item == Material.SKULL_ITEM)) {
				transformSkull(nbttagcompound);
			}
			if (getVersion().isBefore(ProtocolVersion.MINECRAFT_1_9) && ((item == Material.POTION) || (item == Material.SPLASH_POTION) || (item == Material.LINGERING_POTION))) {
				String potion = nbttagcompound.getString("Potion");
				if (!potion.isEmpty()) {
					NBTTagListWrapper tagList = nbttagcompound.getList("CustomPotionEffects", NBTTagCompoundWrapper.TYPE_COMPOUND);
					for (int i = 0; i < tagList.size(); i++) {
						potion = ServerImplementationType.get().getMiscUtils().getPotionEffectNameById(tagList.getCompound(i).getNumber("Id"));
					}
					Integer data = LegacyPotion.toLegacyId(potion, item != Material.POTION);
					itemstack.setData(data);
					String basicTypeName = LegacyPotion.getBasicTypeName(potion);
					if (basicTypeName != null) {
						itemstack.setDisplayName(basicTypeName);
					}
				}
			}
			if (getVersion().isBetween(ProtocolVersion.MINECRAFT_1_10, ProtocolVersion.MINECRAFT_1_9)) {
				NBTTagCompoundWrapper entitytag = nbttagcompound.getCompound("EntityTag");
				String entityId = entitytag.getString("id");
				if (!entityId.isEmpty()) {
					entitytag.setString("id", LegacyEntityType.getLegacyName(entityId));
				}
			}
			if (getVersion().isBefore(ProtocolVersion.MINECRAFT_1_9) && (item == Material.MONSTER_EGG)) {
				String entityId = nbttagcompound.getCompound("EntityTag").getString("id");
				if (!entityId.isEmpty()) {
					if (entityId.startsWith("minecraft:")) {
						entityId = entityId.substring("minecraft:".length());
					}
					EntityType type = EntityType.fromName(entityId);
					if (type != null) {
						itemstack.setData(type.getTypeId());
					}
				}
			}
			if (nbttagcompound.hasKeyOfType("ench", NBTTagCompoundWrapper.TYPE_LIST)) {
				nbttagcompound.setList("ench", filterEnchantList(nbttagcompound.getList("ench", NBTTagCompoundWrapper.TYPE_COMPOUND)));
			}
			if (nbttagcompound.hasKeyOfType("stored-enchants", NBTTagCompoundWrapper.TYPE_LIST)) {
				nbttagcompound.setList("stored-enchants", filterEnchantList(nbttagcompound.getList("stored-enchants", NBTTagCompoundWrapper.TYPE_COMPOUND)));
			}
		}
		if (ItemStackWriteEvent.getHandlerList().getRegisteredListeners().length > 0) {
			ItemStackWriteEvent event = new InternalItemStackWriteEvent(getVersion(), original, itemstack);
			Bukkit.getPluginManager().callEvent(event);
		}
		return itemstack;
	}

	private NBTTagListWrapper filterEnchantList(NBTTagListWrapper oldList) {
		IntSkippingTable enchSkip = IdSkipper.ENCHANT.getTable(getVersion());
		NBTTagListWrapper newList = NBTTagListWrapper.create();
		for (int i = 0; i < oldList.size(); i++) {
			NBTTagCompoundWrapper enchData = oldList.getCompound(i);
			if (!enchSkip.shouldSkip(enchData.getNumber("id") & 0xFFFF)) {
				newList.addCompound(enchData);
			}
		}
		return newList;
	}

	private static final GameProfile dragonHeadGameProfile = new GameProfile(UUIDTypeAdapter.fromString("d34aa2b831da4d269655e33c143f096c"), "EnderDragon");
	static {
		dragonHeadGameProfile.getProperties().put("textures", new Property(
			"textures", "eyJ0aW1lc3RhbXAiOjE0NzE0Mzg3NTEzMjYsInByb2ZpbGVJZCI6ImQzNGFhMmI4MzFkYTRkMjY5NjU1ZTMzYzE0M2YwOTZjIiwicHJvZmlsZU5hbWUiOiJFbmRlckRyYWdvbiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWRlYzdhNGRlMDdiN2VkZWFjZTliOWI5OTY2NDRlMjFkNThjYmRhYzM5MmViZWIyNDdhY2NkMTg1MzRiNTQifX19",
			"I/XY6is7j0RTGFwuVhc4PVuWUl5RRNToNUpw6jCF+V4TzXYzsBreiLQ6IM4NSJqlPXBmSlqibblDKVQL/4LnXt24/nRNVzChZv68HXB1quGxmYcSEvOLpoUk1cmXgqWJYHA68C+r1ytoJVerGWuIwuBzQZ9GpH0yb+qg7erFQgB24cbH6hh6uB6KYLwIYLQAg3TFjILv9sVJtC3FakXmtkCV3VfRUdjhigpfKP0JR3VhLVIWeW/7E+C4QCXnGlffc3Lz8PNahXtD4qRitVokId0t1qBcL8mM1qnZ/rHlNPLST61ycY9WNlRr6P83yDw2ha8QMiRH1vI5tvdXIwV7Dkn+JxfhOOeHtGunLBVe7ZEWBZfjePr/HqZGR6F7/cwZU32uH5MdTXQ+oKWUlb6HJOXxj7DfMr/uZWjrwjzmKpSDAinwvQM/8Sf96prufvcSfhZ0yopkumpnTjivgPxsJhwFXThIyFZ3ijTClMgm5NSzmB6hJ+HsBnVkDs7eyE5eI72/ES/6SksFezmBzDOqU31QbPA2mWoOYWdyZngtnf45oFnZ7NNpDW7ZKxY7FTsPEXoON/VX516KbnQ5OERI9YUpGzyKCjyMnf0L99gwgHpx5LpawdzIwk04sqFoy796BkGJf7xH6+h+AurMIenMt4on7T4FUE1ZaJvvaqexQME="
		));
	}

	public static NBTTagCompoundWrapper createDragonHeadSkullTag() {
		return GameProfileSerializer.serialize(dragonHeadGameProfile);
	}

	public static void transformSkull(NBTTagCompoundWrapper nbttagcompound) {
		transformSkull(nbttagcompound, "SkullOwner", "SkullOwner");
		transformSkull(nbttagcompound, "Owner", "ExtraType");
	}

	private static void transformSkull(NBTTagCompoundWrapper tag, String tagname, String newtagname) {
		if (tag.hasKeyOfType(tagname, NBTTagCompoundWrapper.TYPE_COMPOUND)) {
			GameProfile gameprofile = GameProfileSerializer.deserialize(tag.getCompound(tagname));
			if (gameprofile.getName() != null) {
				tag.setString(newtagname, gameprofile.getName());
			} else {
				tag.remove(tagname);
			}
		}
	}

	public static class InternalItemStackWriteEvent extends ItemStackWriteEvent {

		private final org.bukkit.inventory.ItemStack wrapped;
		public InternalItemStackWriteEvent(ProtocolVersion version, ItemStackWrapper original, ItemStackWrapper itemstack) {
			super(version, original.asBukkitMirror());
			this.wrapped = itemstack.asBukkitMirror();
		}

		@Override
		public org.bukkit.inventory.ItemStack getResult() {
			return wrapped;
		}

	}

	public static byte[] toArray(ByteBuf buf) {
		byte[] result = new byte[buf.readableBytes()];
		buf.readBytes(result);
		return result;
	}

	public static int readVarInt(ByteBuf from) {
		int value = 0;
		int length = 0;
		byte part;
		do {
			part = from.readByte();
			value |= (part & 0x7F) << (length++ * 7);
			if (length > 5) {
				throw new DecoderException("VarInt too big");
			}
		} while (part < 0);
		return value;
	}

	public static void writeVarInt(ByteBuf to, int i) {
		while ((i & 0xFFFFFF80) != 0x0) {
			to.writeByte(i | 0x80);
			i >>>= 7;
		}
		to.writeByte(i);
	}

}