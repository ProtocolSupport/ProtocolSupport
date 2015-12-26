package protocolsupport.protocol.transformer.mcpe.packet.raknet;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import protocolsupport.utils.ChannelUtils;

import io.netty.buffer.ByteBuf;

public class RakNetDataSerializer {

	public static int readTriad(ByteBuf buf) {
		return buf.readUnsignedByte() | (buf.readUnsignedByte() << 8) | (buf.readUnsignedByte() << 16);
	}

	public static void writeTriad(ByteBuf buf, int triad) {
		buf.writeByte(triad & 0x0000FF);
		buf.writeByte((triad & 0x00FF00) >> 8);
		buf.writeByte((triad & 0xFF0000) >> 16);
	}

	public static InetSocketAddress readAddress(ByteBuf buf) throws UnknownHostException {
		byte[] addr = null;
		int type = buf.readByte();
		if ((type & 0xFF) == 4) {
			addr = ChannelUtils.toArray(buf.readBytes(4));
		} else {
			throw new RuntimeException("IPV6 is not supported yet");
		}
		int port = buf.readUnsignedShort();
		return new InetSocketAddress(InetAddress.getByAddress(addr), port);
	}

	public static void writeAddress(ByteBuf buf, InetSocketAddress address) {
		InetAddress addr = address.getAddress();
		if (addr instanceof Inet4Address) {
			buf.writeByte((byte) 4);
			byte[] data = addr.getAddress();
			buf.writeInt((data[0] << 24) | (data[1] << 16) | (data[2] << 8) | data[3]);
			buf.writeShort(address.getPort());
		} else {
			throw new RuntimeException("IPV6 is not supported yet");
		}
	}

}
