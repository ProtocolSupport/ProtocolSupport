package protocolsupport.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketData;

public interface IPacketIdCodec {

	public static void writeServerBoundPacketId(ServerBoundPacketData to) {
		int packetId = to.getPacketType().getId();
		to.writeHeadSpace(VarNumberCodec.calculateVarIntSize(packetId), packetId, VarNumberCodec::writeVarInt);
	}

	public static final IPacketIdCodec LATEST = new IPacketIdCodec() {

		@Override
		public int readServerBoundPacketId(ByteBuf from) {
			return VarNumberCodec.readVarInt(from);
		}

		@Override
		public void writeClientBoundPacketId(ClientBoundPacketData to) {
			int packetId = to.getPacketType().getId();
			to.writeHeadSpace(VarNumberCodec.calculateVarIntSize(packetId), packetId, VarNumberCodec::writeVarInt);
		}

	};

	public int readServerBoundPacketId(ByteBuf from);

	public void writeClientBoundPacketId(ClientBoundPacketData to);

}