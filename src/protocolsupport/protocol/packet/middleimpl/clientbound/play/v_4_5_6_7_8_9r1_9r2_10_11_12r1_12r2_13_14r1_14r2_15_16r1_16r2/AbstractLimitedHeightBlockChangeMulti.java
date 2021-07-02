package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.types.chunk.ChunkConstants;

public abstract class AbstractLimitedHeightBlockChangeMulti extends MiddleBlockChangeMulti {

	protected AbstractLimitedHeightBlockChangeMulti(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void handle() {
		if ((chunkSection < 0) || (chunkSection >= ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS)) {
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

}
