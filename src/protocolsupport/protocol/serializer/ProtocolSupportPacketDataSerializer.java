package protocolsupport.protocol.serializer;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_10_R1.potion.CraftPotionUtil;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.spigotmc.LimitStream;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import net.minecraft.server.v1_10_R1.EntityTypes;
import net.minecraft.server.v1_10_R1.GameProfileSerializer;
import net.minecraft.server.v1_10_R1.Item;
import net.minecraft.server.v1_10_R1.ItemPotion;
import net.minecraft.server.v1_10_R1.ItemStack;
import net.minecraft.server.v1_10_R1.Items;
import net.minecraft.server.v1_10_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_10_R1.NBTReadLimiter;
import net.minecraft.server.v1_10_R1.NBTTagCompound;
import net.minecraft.server.v1_10_R1.NBTTagList;
import net.minecraft.server.v1_10_R1.NBTTagString;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.events.ItemStackWriteEvent;
import protocolsupport.protocol.legacyremapper.LegacyPotion;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeskipper.id.IdSkipper;
import protocolsupport.protocol.typeskipper.id.SkippingTable;
import protocolsupport.protocol.utils.types.ItemStackWrapper;
import protocolsupport.protocol.utils.types.MerchantData;
import protocolsupport.protocol.utils.types.MerchantData.TradeOffer;
import protocolsupport.protocol.utils.types.NBTTagCompoundWrapper;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.ChannelUtils;
import protocolsupport.utils.netty.WrappingBuffer;

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
		return ChannelUtils.readVarInt(this);
	}

	public void writeVarInt(int varint) {
		ChannelUtils.writeVarInt(this, varint);
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
			return new String(ChannelUtils.toArray(readBytes(length)), StandardCharsets.UTF_16BE);
		} else {
			int length = readVarInt();
			checkLimit(length, limit * 4);
			return new String(ChannelUtils.toArray(readBytes(length)), StandardCharsets.UTF_8);
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
		ItemStack itemstack = null;
		short type = this.readShort();
		if (type >= 0) {
			byte count = readByte();
			short data = readShort();
			itemstack = new ItemStack(Item.getById(type), count, data);
			itemstack.setTag(readTag().unwrap());
		}
		return new ItemStackWrapper(itemstack);
	}

	public void writeItemStack(ItemStackWrapper itemstack) {
		if (itemstack.isNull()) {
			writeShort(-1);
			return;
		}
		itemstack = new ItemStackWrapper(transformItemStack(itemstack.unwrap()));
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
				return NBTTagCompoundWrapper.wrap(readLegacyNBT(new ByteArrayInputStream(ChannelUtils.toArray(readBytes(length))), new NBTReadLimiter(2097152L)));
			} else {
				markReaderIndex();
				if (readByte() == 0) {
					return NBTTagCompoundWrapper.createNull();
				}
				resetReaderIndex();
				return NBTTagCompoundWrapper.wrap(NBTCompressedStreamTools.a(new DataInputStream(new ByteBufInputStream(this)), new NBTReadLimiter(2097152L)));
			}
		} catch (IOException e) {
			throw new DecoderException(e);
		}
	}

	public void writeTag(NBTTagCompoundWrapper tag) {
		ByteBuf buffer = Allocator.allocateBuffer();
		try {
			if (getVersion().isBefore(ProtocolVersion.MINECRAFT_1_8)) {
				if (tag.isNull()) {
					writeShort(-1);
				} else {
					encodeLegacyNBT(tag.unwrap(), buffer);
					writeShort(buffer.readableBytes());
					writeBytes(buffer);
				}
			} else {
				if (tag.isNull()) {
					writeByte(0);
				} else {
					NBTCompressedStreamTools.a(tag.unwrap(), (DataOutput) new DataOutputStream(new ByteBufOutputStream(buffer)));
					writeBytes(buffer);
				}
			}
		} catch (Throwable ioexception) {
			throw new EncoderException(ioexception);
		} finally {
			buffer.release();
		}
	}

	public MerchantData readMerchantData() {
		MerchantData merchdata = new MerchantData(readInt());
		int count = readUnsignedByte();
		for (int i = 0; i < count; i++) {
			ItemStackWrapper itemstack1 = readItemStack();
			ItemStackWrapper result = readItemStack();
			ItemStackWrapper itemstack2 = new ItemStackWrapper();
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

	//TODO: use wrapper
	private ItemStack transformItemStack(ItemStack original) {
		ItemStack itemstack = original.cloneItemStack();
		Item item = itemstack.getItem();
		if (getVersion().isBefore(ProtocolVersion.MINECRAFT_1_9) && (item == Items.SKULL) && (itemstack.getData() == 5)) {
			itemstack.setData(3);
			NBTTagCompound rootTag = new NBTTagCompound();
			rootTag.set("SkullOwner", createDragonHeadSkullTag());
			itemstack.setTag(rootTag);
		}
		NBTTagCompound nbttagcompound = itemstack.getTag();
		if (nbttagcompound != null) {
			if (getVersion().isBefore(ProtocolVersion.MINECRAFT_1_8) && (item == Items.WRITTEN_BOOK)) {
				if (nbttagcompound.hasKeyOfType("pages", 9)) {
					NBTTagList pages = nbttagcompound.getList("pages", 8);
					NBTTagList newpages = new NBTTagList();
					for (int i = 0; i < pages.size(); i++) {
						newpages.add(new NBTTagString(ChatAPI.fromJSON(pages.getString(i)).toLegacyText()));
					}
					nbttagcompound.set("pages", newpages);
				}
			}
			if (getVersion().isBeforeOrEq(ProtocolVersion.MINECRAFT_1_7_5) && (item == Items.SKULL)) {
				transformSkull(nbttagcompound);
			}
			if (getVersion().isBefore(ProtocolVersion.MINECRAFT_1_9) && (item instanceof ItemPotion)) {
				String potion = nbttagcompound.getString("Potion");
				if (!potion.isEmpty()) {
					NBTTagList tagList = nbttagcompound.getList("CustomPotionEffects", 10);
					if (!tagList.isEmpty()) {
						for (int i = 0; i < tagList.size(); i++) {
							NBTTagCompound nbtTag = tagList.get(i);
							Integer potionId = nbtTag.getInt("Id");
							PotionEffectType effectType = PotionEffectType.getById(potionId);
							PotionType type = PotionType.getByEffect(effectType);
							if (type != null) {
								PotionData data = new PotionData(type, false, false);
								potion = CraftPotionUtil.fromBukkit(data);
								break;
							}
						}
					}
					Integer data = LegacyPotion.toLegacyId(potion, item != Items.POTION);
					itemstack.setData(data);
					String basicTypeName = LegacyPotion.getBasicTypeName(potion);
					if (basicTypeName != null) {
						itemstack.c(basicTypeName);
					}
				}
			}
			if (getVersion().isBefore(ProtocolVersion.MINECRAFT_1_9) && (item == Items.SPAWN_EGG)) {
				String entityId = nbttagcompound.getCompound("EntityTag").getString("id");
				if (!entityId.isEmpty()) {
					itemstack.setData(EntityTypes.a(entityId));
				}
			}
			if (nbttagcompound.hasKeyOfType("ench", 9)) {
				nbttagcompound.set("ench", filterEnchantList(nbttagcompound.getList("ench", 10)));
			}
			if (nbttagcompound.hasKeyOfType("stored-enchants", 9)) {
				nbttagcompound.set("stored-enchants", filterEnchantList(nbttagcompound.getList("stored-enchants", 10)));
			}
		}
		if (ItemStackWriteEvent.getHandlerList().getRegisteredListeners().length > 0) {
			ItemStackWriteEvent event = new InternalItemStackWriteEvent(getVersion(), original, itemstack);
			Bukkit.getPluginManager().callEvent(event);
		}
		return itemstack;
	}

	private NBTTagList filterEnchantList(NBTTagList enchList) {
		SkippingTable enchSkip = IdSkipper.ENCHANT.getTable(getVersion());
		NBTTagList newList = new NBTTagList();
		for (int i = 0; i < enchList.size(); i++) {
			NBTTagCompound enchData = enchList.get(i);
			if (!enchSkip.shouldSkip(enchData.getInt("id") & 0xFFFF)) {
				newList.add(enchData);
			}
		}
		return enchList;
	}

	private static final GameProfile dragonHeadGameProfile = new GameProfile(UUIDTypeAdapter.fromString("d34aa2b831da4d269655e33c143f096c"), "EnderDragon");
	static {
		dragonHeadGameProfile.getProperties().put("textures", new Property(
			"textures", "eyJ0aW1lc3RhbXAiOjE0NzE0Mzg3NTEzMjYsInByb2ZpbGVJZCI6ImQzNGFhMmI4MzFkYTRkMjY5NjU1ZTMzYzE0M2YwOTZjIiwicHJvZmlsZU5hbWUiOiJFbmRlckRyYWdvbiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWRlYzdhNGRlMDdiN2VkZWFjZTliOWI5OTY2NDRlMjFkNThjYmRhYzM5MmViZWIyNDdhY2NkMTg1MzRiNTQifX19",
			"I/XY6is7j0RTGFwuVhc4PVuWUl5RRNToNUpw6jCF+V4TzXYzsBreiLQ6IM4NSJqlPXBmSlqibblDKVQL/4LnXt24/nRNVzChZv68HXB1quGxmYcSEvOLpoUk1cmXgqWJYHA68C+r1ytoJVerGWuIwuBzQZ9GpH0yb+qg7erFQgB24cbH6hh6uB6KYLwIYLQAg3TFjILv9sVJtC3FakXmtkCV3VfRUdjhigpfKP0JR3VhLVIWeW/7E+C4QCXnGlffc3Lz8PNahXtD4qRitVokId0t1qBcL8mM1qnZ/rHlNPLST61ycY9WNlRr6P83yDw2ha8QMiRH1vI5tvdXIwV7Dkn+JxfhOOeHtGunLBVe7ZEWBZfjePr/HqZGR6F7/cwZU32uH5MdTXQ+oKWUlb6HJOXxj7DfMr/uZWjrwjzmKpSDAinwvQM/8Sf96prufvcSfhZ0yopkumpnTjivgPxsJhwFXThIyFZ3ijTClMgm5NSzmB6hJ+HsBnVkDs7eyE5eI72/ES/6SksFezmBzDOqU31QbPA2mWoOYWdyZngtnf45oFnZ7NNpDW7ZKxY7FTsPEXoON/VX516KbnQ5OERI9YUpGzyKCjyMnf0L99gwgHpx5LpawdzIwk04sqFoy796BkGJf7xH6+h+AurMIenMt4on7T4FUE1ZaJvvaqexQME="
		));
	}

	public static NBTTagCompound createDragonHeadSkullTag() {
		return GameProfileSerializer.serialize(new NBTTagCompound(), dragonHeadGameProfile);
	}

	public static void transformSkull(NBTTagCompound nbttagcompound) {
		transformSkull(nbttagcompound, "SkullOwner", "SkullOwner");
		transformSkull(nbttagcompound, "Owner", "ExtraType");
	}

	private static void transformSkull(NBTTagCompound tag, String tagname, String newtagname) {
		if (tag.hasKeyOfType(tagname, 10)) {
			GameProfile gameprofile = GameProfileSerializer.deserialize(tag.getCompound(tagname));
			if (gameprofile.getName() != null) {
				tag.set(newtagname, new NBTTagString(gameprofile.getName()));
			} else {
				tag.remove(tagname);
			}
		}
	}

	private static NBTTagCompound readLegacyNBT(InputStream is, NBTReadLimiter nbtreadlimiter) {
		try {
			try (DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new LimitStream(new GZIPInputStream(is), nbtreadlimiter)))) {
				return NBTCompressedStreamTools.a(datainputstream, nbtreadlimiter);
			}
		} catch (IOException e) {
			throw new DecoderException(e);
		}
	}

	private static void encodeLegacyNBT(NBTTagCompound nbttagcompound, ByteBuf to) {
		try {
			try (DataOutputStream dataoutputstream = new DataOutputStream(new GZIPOutputStream(new ByteBufOutputStream(to)))) {
				NBTCompressedStreamTools.a(nbttagcompound, (DataOutput) dataoutputstream);
			}
		} catch (IOException e) {
			throw new EncoderException(e);
		}
	}

	public static class InternalItemStackWriteEvent extends ItemStackWriteEvent {

		private final CraftItemStack wrapped;
		public InternalItemStackWriteEvent(ProtocolVersion version, ItemStack original, ItemStack itemstack) {
			super(version, CraftItemStack.asCraftMirror(original));
			this.wrapped = CraftItemStack.asCraftMirror(itemstack);
		}

		@Override
		public org.bukkit.inventory.ItemStack getResult() {
			return wrapped;
		}

	}

}