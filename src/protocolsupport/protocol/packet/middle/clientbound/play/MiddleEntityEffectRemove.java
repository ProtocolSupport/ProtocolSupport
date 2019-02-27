package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;

public abstract class MiddleEntityEffectRemove extends MiddleEntity {

	public MiddleEntityEffectRemove(ConnectionImpl connection) {
		super(connection);
	}

	protected int effectId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		effectId = serverdata.readByte();
	}

	@Override
	public boolean postFromServerRead() {
		return !GenericIdSkipper.EFFECT.getTable(version).shouldSkip(effectId);
	}

}
