package protocolsupport.protocol.pipeline.version.util.codec;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.pipeline.AbstractPacketIdCodec;

public abstract class VarIntPacketCodec extends AbstractPacketIdCodec {

	@Override
	public int readServerBoundPacketId(ByteBuf from) {
		return VarNumberCodec.readVarInt(from);
	}

	@Override
	protected void writeClientboundPacketId(ClientBoundPacketData to, int packetId) {
		to.writeHeadSpace(VarNumberCodec.calculateVarIntSize(packetId), packetId, VarNumberCodec::writeVarInt);
	}

}
