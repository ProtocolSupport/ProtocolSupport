package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;

public abstract class MiddleEntityEffectRemove extends MiddleEntity {

	protected int effectId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		effectId = serverdata.readByte();
	}

	@Override
	public boolean postFromServerRead() {
		return !GenericIdSkipper.EFFECT.getTable(connection.getVersion()).shouldSkip(effectId);
	}

}
