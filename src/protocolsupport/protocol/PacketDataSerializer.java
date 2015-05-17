package protocolsupport.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.EncoderException;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
							newpages.add(new NBTTagString(Utils.fromComponent(ChatSerializer.a(pages.getString(i)))));
						}
						nbttagcompound.set("pages", newpages);
					}
				}
			}
			this.a(nbttagcompound);
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
				this.writeBytes(abyte);
			}
		} else if (nbttagcompound == null) {
			writeByte(0);
		} else {
			final ByteBufOutputStream out = new ByteBufOutputStream(Unpooled.buffer());
			try {
				NBTCompressedStreamTools.a(nbttagcompound, (DataOutput) new DataOutputStream(out));
			} catch (Exception ioexception) {
				throw new EncoderException(ioexception);
			}
			this.writeBytes(out.buffer());
			out.buffer().release();
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
			final short short1 = readShort();
			if (short1 < 0) {
				return null;
			}
			final byte[] abyte = new byte[short1];
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
				int length = readUnsignedShort();
				return new String(readBytes(length * 2).array(), StandardCharsets.UTF_16BE);
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
			default: {
				super.a(string);
				break;
			}
		}
		return this;
	}

	public int readVarInt() {
		return e();
	}

	public void writeVarInt(int varInt) {
		b(varInt);
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

	private static NBTTagCompound read(final byte[] data, final NBTReadLimiter nbtreadlimiter) {
		try {
			final DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new LimitStream(new GZIPInputStream(new ByteArrayInputStream(data)), nbtreadlimiter)));
			NBTTagCompound nbttagcompound;
			try {
				nbttagcompound = NBTCompressedStreamTools.a(datainputstream, nbtreadlimiter);
			} finally {
				datainputstream.close();
			}
			return nbttagcompound;
		} catch (IOException ex) {
			SneakyThrow.sneaky(ex);
			return null;
		}
	}

	private static byte[] write(final NBTTagCompound nbttagcompound) {
		try {
			final ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
			final DataOutputStream dataoutputstream = new DataOutputStream(new GZIPOutputStream(bytearrayoutputstream));
			try {
				NBTCompressedStreamTools.a(nbttagcompound, (DataOutput) dataoutputstream);
			} finally {
				dataoutputstream.close();
			}
			return bytearrayoutputstream.toByteArray();
		} catch (IOException ex) {
			SneakyThrow.sneaky(ex);
			return null;
		}
	}

}
