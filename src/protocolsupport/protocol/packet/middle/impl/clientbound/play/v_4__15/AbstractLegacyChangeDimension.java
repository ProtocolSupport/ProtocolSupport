package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__15;

import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleChangeDimension;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;
import protocolsupport.protocol.types.nbt.NBTCompound;

public abstract class AbstractLegacyChangeDimension extends MiddleChangeDimension {

	protected AbstractLegacyChangeDimension(IMiddlePacketInit init) {
		super(init);
	}

	protected String oldWorld;
	protected NBTCompound oldDimension;

	protected boolean sameWorld;
	protected int dimensionId;

	@Override
	protected void handle() {
		oldWorld = clientCache.getWorld();
		oldDimension = clientCache.getDimension();
		super.handle();
		sameWorld = worldName.equals(oldWorld);
		if (sameWorld) {
			dimensionId = LegacyDimension.getIntId(oldDimension);
		} else {
			dimensionId = LegacyDimension.getIntId(clientCache.getDimension());
		}
	}

	@Override
	protected void write() {
		if (!sameWorld && (LegacyDimension.getIntId(oldDimension) == dimensionId)) {
			writeChangeDimension(LegacyDimension.getAlternativeIntId(dimensionId));
			writeChangeDimension(dimensionId);
		} else {
			writeChangeDimension(dimensionId);
		}
	}

	protected abstract void writeChangeDimension(int dimensionId);

}
