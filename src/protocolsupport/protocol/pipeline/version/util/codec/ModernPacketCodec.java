package protocolsupport.protocol.pipeline.version.util.codec;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.PacketData;
import protocolsupport.protocol.pipeline.IPacketIdCodec;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class ModernPacketCodec extends IPacketIdCodec {

	@Override
	public int readPacketId(ByteBuf from) {
		return VarNumberSerializer.readVarInt(from);
	}

	protected static final int packet_id_length = Byte.BYTES * 2;

	@Override
	protected void writePacketId(PacketData<?> to, int packetId) {
		to.writeHeadSpace(packet_id_length, packetId, (lTo, lPacketId) -> VarNumberSerializer.writeFixedSizeVarInt(lTo, lPacketId, packet_id_length));
	}

}
