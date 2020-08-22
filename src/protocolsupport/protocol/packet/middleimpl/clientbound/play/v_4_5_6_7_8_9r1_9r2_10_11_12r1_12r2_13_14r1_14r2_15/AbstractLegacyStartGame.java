package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import protocolsupport.protocol.packet.middle.clientbound.play.MiddleStartGame;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;

public abstract class AbstractLegacyStartGame extends MiddleStartGame {

	public AbstractLegacyStartGame(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeToClient() {
		int newDimensionId = LegacyDimension.getIntId(dimension);
		if ((oldDimension != null) && (LegacyDimension.getIntId(oldDimension) == newDimensionId)) {
			writeStartGame(LegacyDimension.getAlternativeIntId(newDimensionId));
			writeStartGame(newDimensionId);
		} else {
			writeStartGame(newDimensionId);
		}
	}

	protected abstract void writeStartGame(int dimensionId);

}
