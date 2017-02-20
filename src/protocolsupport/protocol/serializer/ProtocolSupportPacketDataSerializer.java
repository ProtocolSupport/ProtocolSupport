package protocolsupport.protocol.serializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.bukkit.Bukkit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.ItemStackWriteEvent;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.utils.types.MerchantData;
import protocolsupport.protocol.utils.types.MerchantData.TradeOffer;
import protocolsupport.protocol.utils.types.Position;
import protocolsupport.utils.netty.WrappingBuffer;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

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
			ItemStackWrapper itemstack = ServerPlatform.get().getWrapperFactory().createItemStack(type);
			itemstack.setAmount(readByte());
			itemstack.setData(readShort());
			itemstack.setTag(readTag());
			return ItemStackRemapper.remapServerbound(getVersion(), itemstack.cloneItemStack());
		}
		return ServerPlatform.get().getWrapperFactory().createNullItemStack();
	}

	public void writeItemStack(ItemStackWrapper itemstack) {
		if (itemstack.isNull()) {
			writeShort(-1);
			return;
		}
		ItemStackWrapper remapped = ItemStackRemapper.remapClientbound(getVersion(), itemstack.cloneItemStack());
		if (ItemStackWriteEvent.getHandlerList().getRegisteredListeners().length > 0) {
			ItemStackWriteEvent event = new InternalItemStackWriteEvent(getVersion(), itemstack, remapped);
			Bukkit.getPluginManager().callEvent(event);
		}
		writeShort(ItemStackRemapper.ITEM_ID_REMAPPING_REGISTRY.getTable(version).getRemap(remapped.getTypeId()));
		writeByte(remapped.getAmount());
		writeShort(remapped.getData());
		writeTag(remapped.getTag());
	}

	public NBTTagCompoundWrapper readTag() {
		try {
			if (getVersion().isBefore(ProtocolVersion.MINECRAFT_1_8)) {
				final short length = readShort();
				if (length < 0) {
					return ServerPlatform.get().getWrapperFactory().createNullNBTCompound();
				}
				try (InputStream inputstream = new GZIPInputStream(new ByteBufInputStream(readSlice(length)))) {
					return ServerPlatform.get().getWrapperFactory().createNBTCompoundFromStream(inputstream);
				}
			} else {
				markReaderIndex();
				if (readByte() == 0) {
					return ServerPlatform.get().getWrapperFactory().createNullNBTCompound();
				}
				resetReaderIndex();
				try (DataInputStream datainputstream = new DataInputStream(new ByteBufInputStream(this))) {
					return ServerPlatform.get().getWrapperFactory().createNBTCompoundFromStream(datainputstream);
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
					try (OutputStream outputstream = new GZIPOutputStream(new ByteBufOutputStream(this))) {
						tag.writeToStream(outputstream);
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
			ItemStackWrapper itemstack2 = ServerPlatform.get().getWrapperFactory().createNullItemStack();
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