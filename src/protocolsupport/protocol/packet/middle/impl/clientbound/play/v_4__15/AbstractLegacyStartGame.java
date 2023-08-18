package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__15;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleStartGame;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;
import protocolsupport.protocol.types.nbt.NBTCompound;

public abstract class AbstractLegacyStartGame extends MiddleStartGame {

	protected AbstractLegacyStartGame(IMiddlePacketInit init) {
		super(init);
	}

	protected NBTCompound oldDimension;

	protected int dimensionId;

	@Override
	protected void handle() {
		oldDimension = clientCache.getDimension();
		super.handle();
		dimensionId = LegacyDimension.getIntId(clientCache.getDimension());
	}

	@Override
	protected void write() {
		if ((oldDimension != null) && (LegacyDimension.getIntId(oldDimension) == dimensionId)) {
			writeStartGame(LegacyDimension.getAlternativeIntId(dimensionId));
			writeStartGame(dimensionId);
		} else {
			writeStartGame(dimensionId);
		}
	}

	protected abstract void writeStartGame(int dimensionId);

}
