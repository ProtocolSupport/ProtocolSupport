package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStartGame;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;
import protocolsupport.protocol.types.nbt.NBTCompound;

public abstract class AbstractLegacyStartGame extends MiddleStartGame {

	public AbstractLegacyStartGame(MiddlePacketInit init) {
		super(init);
	}

	protected NBTCompound oldDimension;

	protected int dimensionId;

	@Override
	protected void handleReadData() {
		oldDimension = clientCache.getDimension();
		super.handleReadData();
		dimensionId = LegacyDimension.getIntId(dimension);
	}

	@Override
	protected void writeToClient() {
		if ((oldDimension != null) && (LegacyDimension.getIntId(oldDimension) == dimensionId)) {
			writeStartGame(LegacyDimension.getAlternativeIntId(dimensionId));
			writeStartGame(dimensionId);
		} else {
			writeStartGame(dimensionId);
		}
	}

	protected abstract void writeStartGame(int dimensionId);

}
