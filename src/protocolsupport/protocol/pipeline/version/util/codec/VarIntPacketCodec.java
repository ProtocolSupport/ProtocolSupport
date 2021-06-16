package protocolsupport.protocol.pipeline.version.util.codec;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.PacketData;
import protocolsupport.protocol.pipeline.IPacketIdCodec;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class VarIntPacketCodec extends IPacketIdCodec {

	@Override
	public int readPacketId(ByteBuf from) {
		return VarNumberSerializer.readVarInt(from);
	}

	@Override
	protected void writePacketId(PacketData<?, ?> to, int packetId) {
		to.writeHeadSpace(VarNumberSerializer.calculateVarIntSize(packetId), packetId, (lTo, lPacketId) -> VarNumberSerializer.writeVarInt(lTo, lPacketId));
	}

}
