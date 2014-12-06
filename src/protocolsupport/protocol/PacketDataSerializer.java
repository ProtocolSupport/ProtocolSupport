package protocolsupport.protocol;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.spigotmc.LimitStream;
import org.spigotmc.SneakyThrow;

import net.minecraft.server.v1_8_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R1.NBTReadLimiter;
import net.minecraft.server.v1_8_R1.NBTTagCompound;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

public class PacketDataSerializer extends net.minecraft.server.v1_8_R1.PacketDataSerializer {

	private int version;

	public PacketDataSerializer(ByteBuf buf, int version) {
		super(buf);
		this.version = version;
	}

	public int getVersion() {
		return version;
	}

	@Override
	public void a(final NBTTagCompound nbttagcompound) {
		if (this.getVersion() != ServerConnectionChannel.CLIENT_1_8_PROTOCOL_VERSION) {
			if (nbttagcompound == null) {
				this.writeShort(-1);
			} else {
				final byte[] abyte = write(nbttagcompound);
				this.writeShort((short) abyte.length);
				this.writeBytes(abyte);
			}
		} else if (nbttagcompound == null) {
			this.writeByte(0);
		} else {
			final ByteBufOutputStream out = new ByteBufOutputStream(Unpooled.buffer());
			NBTCompressedStreamTools.a(nbttagcompound, (DataOutput) new DataOutputStream(out));
			this.writeBytes(out.buffer());
			out.buffer().release();
		}
	}

	@Override
	public NBTTagCompound h() {
		if (this.getVersion() != ServerConnectionChannel.CLIENT_1_8_PROTOCOL_VERSION) {
			final short short1 = this.readShort();
			if (short1 < 0) {
				return null;
			}
			final byte[] abyte = new byte[short1];
			this.readBytes(abyte);
			return read(abyte, new NBTReadLimiter(2097152L));
		} else {
			final int index = this.readerIndex();
			if (this.readByte() == 0) {
				return null;
			}
			this.readerIndex(index);
			return NBTCompressedStreamTools.a(new DataInputStream(new ByteBufInputStream(this)), new NBTReadLimiter(2097152L));
		}
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

	public static byte[] write(final NBTTagCompound nbttagcompound) {
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
