package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockChangeSingle;
import protocolsupport.protocol.types.chunk.ChunkConstants;

public abstract class AbstractLimitedHeightBlockChangeSingle extends MiddleBlockChangeSingle {

	protected AbstractLimitedHeightBlockChangeSingle(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void handle() {
		int y = position.getY();
		if ((y < 0) || ((y >> 4) >= ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK_SECTIONS)) {
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

}
