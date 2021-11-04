package protocolsupport.protocol.pipeline.version.util.codec;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.pipeline.AbstractPacketIdCodec;

public abstract class BytePacketCodec extends AbstractPacketIdCodec {

	@Override
	public int readServerBoundPacketId(ByteBuf from) {
		return from.readUnsignedByte();
	}

	@Override
	protected void writeClientboundPacketId(ClientBoundPacketData to, int packetId) {
		to.writeHeadSpace(Byte.BYTES, packetId, (lTo, lPacketId) -> lTo.writeByte(lPacketId));
	}

}
