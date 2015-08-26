package protocolsupport.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.handler.codec.EncoderException;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import net.minecraft.server.v1_8_R3.GameProfileSerializer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTReadLimiter;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagString;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.spigotmc.LimitStream;
import org.spigotmc.SneakyThrow;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.transformer.utils.LegacyUtils;
import protocolsupport.utils.Allocator;
import protocolsupport.utils.Utils;

import com.mojang.authlib.GameProfile;

public class PacketDataSerializer extends net.minecraft.server.v1_8_R3.PacketDataSerializer {

	private ProtocolVersion version;

	public PacketDataSerializer(ByteBuf buf, ProtocolVersion version) {
		super(buf);
		this.version = version;
	}

	public ProtocolVersion getVersion() {
		return version;
	}

	public void setVersion(ProtocolVersion version) {
		this.version = version;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void a(ItemStack itemstack) {
		if ((itemstack == null) || (itemstack.getItem() == null)) {
			writeShort(-1);
		} else {
			int itemId = Item.getId(itemstack.getItem());
			switch (getVersion()) {
				case MINECRAFT_1_6_4:
				case MINECRAFT_1_6_2: {
					writeShort(protocolsupport.protocol.transformer.v_1_6.remappers.ItemIDRemapper.replaceItemId(itemId));
					break;
				}
				case MINECRAFT_1_7_10:
				case MINECRAFT_1_7_5: {
					writeShort(protocolsupport.protocol.transformer.v_1_7.remappers.ItemIDRemapper.replaceItemId(itemId));
					break;
				}
				case MINECRAFT_1_5_2: {
					writeShort(protocolsupport.protocol.transformer.v_1_5.remappers.ItemIDRemapper.replaceItemId(itemId));
					break;
				}
				default: {
					writeShort(itemId);
					break;
				}
			}
			writeByte(itemstack.count);
			writeShort(itemstack.getData());
			NBTTagCompound nbttagcompound = null;
			if (itemstack.getItem().usesDurability() || itemstack.getItem().p()) {
				itemstack = itemstack.cloneItemStack();
				CraftItemStack.setItemMeta(itemstack, CraftItemStack.getItemMeta(itemstack));
				nbttagcompound = itemstack.getTag();
			}
			if (getVersion() != ProtocolVersion.MINECRAFT_1_8) {
				if ((nbttagcompound != null) && (itemId == Material.WRITTEN_BOOK.getId())) {
					if (nbttagcompound.hasKeyOfType("pages", 9)) {
						nbttagcompound = (NBTTagCompound) nbttagcompound.clone();
						NBTTagList pages = nbttagcompound.getList("pages", 8);
						NBTTagList newpages = new NBTTagList();
						for (int i = 0; i < pages.size(); i++) {
							newpages.add(new NBTTagString(LegacyUtils.fromComponent(ChatSerializer.a(pages.getString(i)))));
						}
						nbttagcompound.set("pages", newpages);
					}
				}
			}
			this.a(nbttagcompound);
		}
	}

	@Override
	public ItemStack i() throws IOException {
		switch (getVersion()) {
			case MINECRAFT_PE: {
				int id = readShort();
				if (id <= 0) {
					return null;
				}
				int count = readByte();
				int data = readShort();
				ItemStack itemstack = new ItemStack(Item.getById(id), count, data);
				itemstack.setTag(h());
				if (itemstack.getTag() != null) {
					CraftItemStack.setItemMeta(itemstack, CraftItemStack.getItemMeta(itemstack));
				}
				return itemstack;
			}
			default: {
				return super.i();
			}
		}
	}

	@Override
	public void a(NBTTagCompound nbttagcompound) {
		if (nbttagcompound != null) {
			switch (getVersion()) {
				case MINECRAFT_1_7_5:
				case MINECRAFT_1_6_4:
				case MINECRAFT_1_6_2:
				case MINECRAFT_1_5_2: {
					nbttagcompound = (NBTTagCompound) nbttagcompound.clone();
					transformSkull(nbttagcompound, "SkullOwner", "SkullOwner");
					transformSkull(nbttagcompound, "Owner", "ExtraType");
					break;
				}
				default: {
					break;
				}
			}
		}
		if (getVersion() != ProtocolVersion.MINECRAFT_1_8) {
			if (nbttagcompound == null) {
				writeShort(-1);
			} else {
				final byte[] abyte = write(nbttagcompound);
				writeShort(abyte.length);
				writeBytes(abyte);
			}
		} else if (nbttagcompound == null) {
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

	private void transformSkull(NBTTagCompound tag, String tagname, String newtagname) {
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
	public NBTTagCompound h() throws IOException {
		if (getVersion() != ProtocolVersion.MINECRAFT_1_8) {
			final short length = readShort();
			if (length < 0) {
				return null;
			}
			final byte[] abyte = new byte[length];
			this.readBytes(abyte);
			return read(abyte, new NBTReadLimiter(2097152L));
		} else {
			final int index = this.readerIndex();
			if (readByte() == 0) {
				return null;
			}
			this.readerIndex(index);
			return NBTCompressedStreamTools.a(new DataInputStream(new ByteBufInputStream(this)), new NBTReadLimiter(2097152L));
		}
	}

	@Override
	public String c(int limit) {
		switch (getVersion()) {
			case MINECRAFT_1_6_4:
			case MINECRAFT_1_6_2:
			case MINECRAFT_1_5_2: {
				return new String(Utils.toArray(readBytes(readUnsignedShort() * 2)), StandardCharsets.UTF_16BE);
			}
			case MINECRAFT_PE: {
				return new String(Utils.toArray(readBytes(readUnsignedShort())), StandardCharsets.UTF_8);
			}
			default: {
				return super.c(limit);
			}
		}
	}

	@Override
	public PacketDataSerializer a(String string) {
		switch (getVersion()) {
			case MINECRAFT_1_6_4:
			case MINECRAFT_1_6_2:
			case MINECRAFT_1_5_2: {
				writeShort(string.length());
				writeBytes(string.getBytes(StandardCharsets.UTF_16BE));
				break;
			}
			case MINECRAFT_PE: {
				writeShort(string.length());
				writeBytes(string.getBytes(StandardCharsets.UTF_8));
			}
			default: {
				super.a(string);
				break;
			}
		}
		return this;
	}

	@Override
	public byte[] a() {
		switch (getVersion()) {
			case MINECRAFT_1_7_10:
			case MINECRAFT_1_7_5:
			case MINECRAFT_1_6_4:
			case MINECRAFT_1_6_2:
			case MINECRAFT_1_5_2: {
				byte[] array = new byte[readShort()];
				readBytes(array);
				return array;
			}
			default: {
				return super.a();
			}
		}
	}

	@Override
	public void a(byte[] array) {
		switch (getVersion()) {
			case MINECRAFT_1_7_10:
			case MINECRAFT_1_7_5:
			case MINECRAFT_1_6_4:
			case MINECRAFT_1_6_2:
			case MINECRAFT_1_5_2: {
				if (array.length > 32767) {
					throw new IllegalArgumentException("Too big array length of "+array.length);
				}
				writeShort(array.length);
				writeBytes(array);
				break;
			}
			default: {
				super.a(array);
				break;
			}
		}
	}

	public int readVarInt() {
		return e();
	}

	public void writeVarInt(int varInt) {
		b(varInt);
	}

	public String readString() {
		return readString(Short.MAX_VALUE);
	}

	public String readString(int limit) {
		return c(limit);
	}

	public void writeString(String string) {
		a(string);
	}

	public ItemStack readItemStack() throws IOException {
		return i();
	}

	public void writeItemStack(ItemStack itemstack) {
		a(itemstack);
	}

	public byte[] readArray() {
		return a();
	}

	public UUID readUUID() {
		return g();
	}

	public void writeUUID(UUID uuid) {
		a(uuid);
	}

	public int readLTriad() {
		return readUnsignedByte() | (readUnsignedByte() << 8) | (readUnsignedByte() << 16);
	}

	public void writeLTriad(int triad) {
		writeByte(triad & 0x0000FF);
		writeByte((triad & 0x00FF00) >> 8);
		writeByte((triad & 0xFF0000) >> 16);
	}

	public InetSocketAddress readAddress() throws UnknownHostException {
		byte[] addr = null;
		int type = readByte();
		if ((type & 0xFF) == 4) {
			addr = readBytes(4).array();
		} else {
			throw new RuntimeException("IPV6 is not supported yet");
		}
		int port = readUnsignedShort();
        return new InetSocketAddress(InetAddress.getByAddress(addr), port);
	}

	public void writeAddress(InetSocketAddress address) {
		InetAddress addr = address.getAddress();
		if (addr instanceof Inet4Address) {
			writeByte((byte) 4);
			byte[] data = addr.getAddress();
			writeInt((data[0] << 24) | (data[1] << 16) | (data[2] << 8) | data[3]);
			writeShort(address.getPort());
		} else {
			throw new RuntimeException("IPV6 is not supported yet");
		}
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

}
