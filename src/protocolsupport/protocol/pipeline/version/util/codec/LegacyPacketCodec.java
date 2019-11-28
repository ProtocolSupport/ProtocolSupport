package protocolsupport.protocol.pipeline.version.util.codec;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.PacketData;
import protocolsupport.protocol.pipeline.IPacketIdCodec;

public abstract class LegacyPacketCodec extends IPacketIdCodec {

	@Override
	public int readPacketId(ByteBuf from) {
		return from.readUnsignedByte();
	}

	@Override
	protected void writePacketId(PacketData<?> to, int packetId) {
		to.writeHeadSpace(Byte.BYTES, packetId, (lTo, lPacketId) -> lTo.writeByte(lPacketId));
	}

}
