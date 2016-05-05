package protocolsupport.protocol;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack;
import org.spigotmc.LimitStream;
import org.spigotmc.SneakyThrow;

import com.mojang.authlib.GameProfile;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import net.minecraft.server.v1_9_R1.BlockPosition;
import net.minecraft.server.v1_9_R1.GameProfileSerializer;
import net.minecraft.server.v1_9_R1.Item;
import net.minecraft.server.v1_9_R1.ItemStack;
import net.minecraft.server.v1_9_R1.Items;
import net.minecraft.server.v1_9_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_9_R1.NBTReadLimiter;
import net.minecraft.server.v1_9_R1.NBTTagCompound;
import net.minecraft.server.v1_9_R1.NBTTagList;
import net.minecraft.server.v1_9_R1.NBTTagString;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.events.ItemStackWriteEvent;
import protocolsupport.protocol.transformer.utils.LegacyPotion;
import protocolsupport.protocol.transformer.utils.LegacyUtils;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeskipper.id.IdSkipper;
import protocolsupport.protocol.typeskipper.id.SkippingTable;
import protocolsupport.utils.netty.Allocator;
import protocolsupport.utils.netty.ChannelUtils;

public class PacketDataSerializer extends net.minecraft.server.v1_9_R1.PacketDataSerializer {

	private ProtocolVersion version;

	public PacketDataSerializer(ByteBuf buf, ProtocolVersion version) {
		this(buf);
		this.version = version;
	}

	public ProtocolVersion getVersion() {
		return version;
	}

	protected PacketDataSerializer(ByteBuf buf) {
		super(buf);
	}

	protected void setVersion(ProtocolVersion version) {
		this.version = version;
	}

	@Override
	public PacketDataSerializer a(ItemStack itemstack) {
		itemstack = transformItemStack(itemstack);
		if ((itemstack == null) || (itemstack.getItem() == null)) {
			writeShort(-1);
		} else {
			int itemId = Item.getId(itemstack.getItem());
			writeShort(IdRemapper.ITEM.getTable(getVersion()).getRemap(itemId));
			writeByte(itemstack.count);
			writeShort(itemstack.getData());
			a(itemstack.getTag());
		}
		return this;
	}

	@Override
	public PacketDataSerializer a(NBTTagCompound nbttagcompound) {
		if (getVersion().isBefore(ProtocolVersion.MINECRAFT_1_8)) {
			if (nbttagcompound == null) {
				writeShort(-1);
			} else {
				byte[] abyte = write(nbttagcompound);
				writeShort(abyte.length);
				writeBytes(abyte);
			}
		} else {
			if (nbttagcompound == null) {
				writeByte(0);
			} else {
				ByteBufOutputStream out = new ByteBufOutputStream(Allocator.allocateBuffer());
				try {
					NBTCompressedStreamTools.a(nbttagcompound, (DataOutput) new DataOutputStream(out));
					writeBytes(out.buffer());
				} catch (Throwable ioexception) {
					throw new EncoderException(ioexception);
				} finally {
					out.buffer().release();
				}
			}
		}
		return this;
	}

	private ItemStack transformItemStack(ItemStack original) {
		if (original == null) {
			return null;
		}
		ItemStack itemstack = original.cloneItemStack();
		Item item = itemstack.getItem();
		NBTTagCompound nbttagcompound = itemstack.getTag();
		if (nbttagcompound != null) {
			if (getVersion().isBefore(ProtocolVersion.MINECRAFT_1_8) && item == Items.WRITTEN_BOOK) {
				if (nbttagcompound.hasKeyOfType("pages", 9)) {
					NBTTagList pages = nbttagcompound.getList("pages", 8);
					NBTTagList newpages = new NBTTagList();
					for (int i = 0; i < pages.size(); i++) {
						newpages.add(new NBTTagString(LegacyUtils.toText(ChatAPI.fromJSON(pages.getString(i)))));
					}
					nbttagcompound.set("pages", newpages);
				}
			}
			if (getVersion().isBeforeOrEq(ProtocolVersion.MINECRAFT_1_7_5) && item == Items.SKULL) {
				transformSkull(nbttagcompound);
			}
			if (getVersion().isBeforeOrEq(ProtocolVersion.MINECRAFT_1_9)) {
				if (item == Items.POTION) {
					String potionType = nbttagcompound.getString("Potion");
					Integer pdata = LegacyPotion.toLegacyId(potionType, false);
					if (pdata != null) {
						itemstack.setData(pdata);
					}
				} else if (item == Items.SPLASH_POTION || item == Items.LINGERING_POTION) {
				}
			}
			if (nbttagcompound.hasKeyOfType("ench", 9)) {
				SkippingTable enchSkip = IdSkipper.ENCHANT.getTable(getVersion());
				NBTTagList enchList = nbttagcompound.getList("ench", 10);
				NBTTagList newList = new NBTTagList();
				for (int i = 0; i < enchList.size(); i++) {
					NBTTagCompound enchData = enchList.get(i);
					if (!enchSkip.shouldSkip(enchData.getInt("id") & 0xFFFF)) {
						newList.add(enchData);
					}
				}
				if (newList.size() > 0) {
					nbttagcompound.set("ench", newList);
				} else {
					nbttagcompound.remove("ench");
				}
			}
		}
		if (ItemStackWriteEvent.getHandlerList().getRegisteredListeners().length > 0) {
			ItemStackWriteEvent event = new InternalItemStackWriteEvent(getVersion(), original, itemstack);
			Bukkit.getPluginManager().callEvent(event);
		}
		return itemstack;
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

	@Override
	public NBTTagCompound j() {
		if (getVersion().isBefore(ProtocolVersion.MINECRAFT_1_8)) {
			final short length = readShort();
			if (length < 0) {
				return null;
			}
			final byte[] data = new byte[length];
			readBytes(data);
			return read(data, new NBTReadLimiter(2097152L));
		} else {
			int index = readerIndex();
			if (readByte() == 0) {
				return null;
			}
			readerIndex(index);
			try {
				return NBTCompressedStreamTools.a(new DataInputStream(new ByteBufInputStream(this)), new NBTReadLimiter(2097152L));
			} catch (IOException e) {
				SneakyThrow.sneaky(e);
				return null;
			}
		}
	}

	@Override
	public String e(int limit) {
		if (getVersion().isBeforeOrEq(ProtocolVersion.MINECRAFT_1_6_4)) {
			return new String(ChannelUtils.toArray(readBytes(readUnsignedShort() * 2)), StandardCharsets.UTF_16BE);
		} else {
			return super.e(limit);
		}
	}

	@Override
	public PacketDataSerializer a(String string) {
		if (getVersion().isBeforeOrEq(ProtocolVersion.MINECRAFT_1_6_4)) {
			writeShort(string.length());
			writeBytes(string.getBytes(StandardCharsets.UTF_16BE));
		} else {
			super.a(string);
		}
		return this;
	}

	@Override
	public byte[] b(int limit) {
		if (getVersion().isBeforeOrEq(ProtocolVersion.MINECRAFT_1_7_10)) {
			int size = readShort();
			if (size > limit) {
				throw new DecoderException("ByteArray with size " + size + " is bigger than allowed " + limit);
			}
			byte[] array = new byte[size];
			readBytes(array);
			return array;
		} else {
			return super.b(limit);
		}
	}

	@Override
	public PacketDataSerializer a(byte[] array) {
		if (getVersion().isBeforeOrEq(ProtocolVersion.MINECRAFT_1_7_10)) {
			if (array.length > 32767) {
				throw new IllegalArgumentException("Too big array length of "+array.length);
			}
			writeShort(array.length);
			writeBytes(array);
		} else {
			super.a(array);
		}
		return this;
	}

	public long readVarLong() {
		return h();
	}

	public void writeVarLong(long varLong) {
		b(varLong);
	}

	public int readVarInt() {
		return g();
	}

	public void writeVarInt(int varInt) {
		d(varInt);
	}

	public String readString(int limit) {
		return e(limit);
	}

	public void writeString(String string) {
		a(string);
	}

	public ItemStack readItemStack() throws IOException {
		return k();
	}

	public void writeItemStack(ItemStack itemstack) {
		a(itemstack);
	}

	public byte[] readArray() {
		return b(readableBytes());
	}

	public byte[] readArray(int limit) {
		return b(limit);
	}

	public void writeArray(byte[] array) {
		a(array);
	}

	public BlockPosition readPosition() {
		return e();
	}

	public void writePosition(BlockPosition position) {
		a(position);
	}

	public UUID readUUID() {
		return i();
	}

	public void writeUUID(UUID uuid) {
		a(uuid);
	}

	public NBTTagCompound readTag() throws IOException {
		return j();
	}

	public void writeTag(NBTTagCompound tag) {
		a(tag);
	}

	private static NBTTagCompound read(final byte[] data, final NBTReadLimiter nbtreadlimiter) {
		try {
			try (DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new LimitStream(new GZIPInputStream(new ByteArrayInputStream(data)), nbtreadlimiter)))) {
				return NBTCompressedStreamTools.a(datainputstream, nbtreadlimiter);
			}
		} catch (IOException ex) {
			SneakyThrow.sneaky(ex);
			return null;
		}
	}

	private static byte[] write(final NBTTagCompound nbttagcompound) {
		try {
			ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
			try (DataOutputStream dataoutputstream = new DataOutputStream(new GZIPOutputStream(bytearrayoutputstream))) {
				NBTCompressedStreamTools.a(nbttagcompound, (DataOutput) dataoutputstream);
			}
			return bytearrayoutputstream.toByteArray();
		} catch (IOException ex) {
			SneakyThrow.sneaky(ex);
			return null;
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
