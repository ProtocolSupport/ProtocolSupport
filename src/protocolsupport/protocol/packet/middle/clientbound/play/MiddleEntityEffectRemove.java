package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.ArrayBasedIntSkippingTable;

public abstract class MiddleEntityEffectRemove extends MiddleEntityData {

	protected MiddleEntityEffectRemove(MiddlePacketInit init) {
		super(init);
	}

	protected final ArrayBasedIntSkippingTable effectSkipTable = GenericIdSkipper.EFFECT.getTable(version);

	protected int effectId;

	@Override
	protected void decodeData(ByteBuf serverdata) {
		effectId = serverdata.readByte();
	}

	@Override
	protected void decodeDataLast(ByteBuf serverdata) {
		if (effectSkipTable.isSet(effectId)) {
			throw CancelMiddlePacketException.INSTANCE;
		}
	}

}
