package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_8;

import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV8;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__13.AbstractChunkCacheChunkUnload;

public class ChunkUnload extends AbstractChunkCacheChunkUnload implements IClientboundMiddlePacketV8 {

	public ChunkUnload(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData chunkunload = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_CHUNK_DATA);
		PositionCodec.writeIntChunkCoord(chunkunload, chunk);
		chunkunload.writeBoolean(true);
		chunkunload.writeShort(0);
		VarNumberCodec.writeVarInt(chunkunload, 0);
		io.writeClientbound(chunkunload);
	}

}
