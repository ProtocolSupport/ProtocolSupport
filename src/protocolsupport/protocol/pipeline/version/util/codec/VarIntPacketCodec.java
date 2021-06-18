package protocolsupport.protocol.pipeline.version.util.codec;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.PacketData;
import protocolsupport.protocol.pipeline.IPacketIdCodec;

public abstract class VarIntPacketCodec extends IPacketIdCodec {

	@Override
	public int readPacketId(ByteBuf from) {
		return VarNumberCodec.readVarInt(from);
	}

	@Override
	protected void writePacketId(PacketData<?, ?> to, int packetId) {
		to.writeHeadSpace(VarNumberCodec.calculateVarIntSize(packetId), packetId, (lTo, lPacketId) -> VarNumberCodec.writeVarInt(lTo, lPacketId));
	}

}
