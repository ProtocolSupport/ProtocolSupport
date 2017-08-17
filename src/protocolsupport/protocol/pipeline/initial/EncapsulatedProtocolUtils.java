package protocolsupport.protocol.pipeline.initial;

import java.net.InetAddress;
import java.net.UnknownHostException;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class EncapsulatedProtocolUtils {

	public static EncapsulatedProtocolInfo readInfo(ByteBuf from) {
		int encapVersion = VarNumberSerializer.readVarInt(from);
		switch (encapVersion) {
			case 0x00: {
				try {
					InetAddress address = InetAddress.getByAddress(MiscSerializer.readBytes(from, VarNumberSerializer.readVarInt(from)));
					boolean hasCompression = from.readBoolean();
					int protocoltype = VarNumberSerializer.readVarInt(from);
					int protocolversion = VarNumberSerializer.readVarInt(from);
					return new EncapsulatedProtocolInfo(getVersion(protocoltype, protocolversion), hasCompression, address);
				} catch (UnknownHostException e) {
					throw new DecoderException("Invalid ip address");
				}
			}
			default: {
				throw new DecoderException("Unknown encapsulated protocol version " + encapVersion);
			}
		}
	}

	private static ProtocolVersion getVersion(int protocoltype, int protocolversion) {
		switch (protocoltype) {
			case 0: {
				return ProtocolUtils.getOldProtocolVersion(protocolversion);
			}
			case 1: {
				return ProtocolUtils.getNewProtocolVersion(protocolversion);			
			}
			default: {
				throw new IllegalArgumentException("Unknown protocol type: " + protocoltype);
			}
		}
	}

	public static class EncapsulatedProtocolInfo {
		private final ProtocolVersion version;
		private final boolean hasCompression;
		private final InetAddress address;
		public EncapsulatedProtocolInfo(ProtocolVersion version, boolean hasCompression, InetAddress address) {
			this.version = version;
			this.hasCompression = hasCompression;
			this.address = address;
		}
		public InetAddress getAddress() {
			return address;
		}
		public boolean hasCompression() {
			return hasCompression;
		}
		public ProtocolVersion getVersion() {
			return version;
		}
	}

}
