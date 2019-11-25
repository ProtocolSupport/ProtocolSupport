package protocolsupport.protocol.pipeline.version.util.codec;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.pipeline.IPacketIdCodec;

public abstract class LegacyPacketCodec extends IPacketIdCodec {

	@Override
	public int readPacketId(ByteBuf from) {
		return from.readUnsignedByte();
	}

	@Override
	protected void writePacketId(ByteBuf to, int packetId) {
		to.writeByte(packetId);
	}

}
