package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__16r2;

import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleBlockChangeSingle;
import protocolsupport.protocol.types.chunk.ChunkConstants;

public abstract class AbstractLimitedHeightBlockChangeSingle extends MiddleBlockChangeSingle {

	protected AbstractLimitedHeightBlockChangeSingle(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void handle() {
		int y = position.getY();
		if ((y < 0) || (y >= ChunkConstants.LEGACY_LIMITED_HEIGHT_CHUNK_BLOCK)) {
			throw MiddlePacketCancelException.INSTANCE;
		}
	}

}
