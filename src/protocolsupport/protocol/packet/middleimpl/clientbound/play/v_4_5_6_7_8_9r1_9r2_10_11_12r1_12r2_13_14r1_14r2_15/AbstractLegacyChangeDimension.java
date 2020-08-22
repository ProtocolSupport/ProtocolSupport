package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChangeDimension;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;

public abstract class AbstractLegacyChangeDimension extends MiddleChangeDimension {

	public AbstractLegacyChangeDimension(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		int newDimensionId = LegacyDimension.getIntId(dimension);
		if (LegacyDimension.getIntId(oldDimension) == newDimensionId) {
			writeChangeDimension(LegacyDimension.getAlternativeIntId(newDimensionId));
			writeChangeDimension(newDimensionId);
		} else {
			writeChangeDimension(newDimensionId);
		}
	}

	protected abstract void writeChangeDimension(int dimensionId);

}
