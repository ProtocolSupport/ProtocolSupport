package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChangeDimension;

public abstract class AbstractChangeDimension extends MiddleChangeDimension {
	private boolean ignore = true;

	public AbstractChangeDimension(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public boolean postFromServerRead() {
		super.postFromServerRead();
		if (ignore) {
			ignore = false;
		} else {
			cache.getChunkCache().clear();
		}
		return true;
	}

}
