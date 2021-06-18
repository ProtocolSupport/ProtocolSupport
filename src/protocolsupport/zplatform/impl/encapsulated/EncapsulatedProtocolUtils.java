package protocolsupport.zplatform.impl.encapsulated;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.text.MessageFormat;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.VarNumberCodec;

public class EncapsulatedProtocolUtils {

	private EncapsulatedProtocolUtils() {
	}

	private static final int CURRENT_VERSION = 1;

	public static EncapsulatedProtocolInfo readInfo(ByteBuf from) {
		int encapVersion = VarNumberCodec.readVarInt(from);
		if (encapVersion > CURRENT_VERSION) {
			throw new DecoderException(MessageFormat.format("Unsupported encapsulation protocol version {0}", encapVersion));
		}
		InetSocketAddress remoteaddress = null;
		if (from.readBoolean()) {
			try {
				InetAddress address = InetAddress.getByAddress(ArrayCodec.readVarIntByteArray(from));
				remoteaddress = new InetSocketAddress(address, VarNumberCodec.readVarInt(from));
			} catch (UnknownHostException e) {
				throw new DecoderException("Invalid ip address");
			}
		}
		boolean hasCompression = from.readBoolean();
		if (encapVersion == 0) {
			VarNumberCodec.readVarInt(from);
			VarNumberCodec.readVarInt(from);
		}
		return new EncapsulatedProtocolInfo(remoteaddress, hasCompression);
	}

	public static void writeInfo(ByteBuf to, EncapsulatedProtocolInfo info) {
		VarNumberCodec.writeVarInt(to, CURRENT_VERSION);
		if (info.getAddress() != null) {
			to.writeBoolean(true);
			byte[] addr = info.getAddress().getAddress().getAddress();
			VarNumberCodec.writeVarInt(to, addr.length);
			to.writeBytes(addr);
			VarNumberCodec.writeVarInt(to, info.getAddress().getPort());
		} else {
			to.writeBoolean(false);
		}
		to.writeBoolean(info.hasCompression());
	}

}
