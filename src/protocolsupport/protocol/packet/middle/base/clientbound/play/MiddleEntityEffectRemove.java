package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.MiddlePacketCancelException;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.ArrayBasedIntSkippingTable;

public abstract class MiddleEntityEffectRemove extends MiddleEntityData {

	protected MiddleEntityEffectRemove(IMiddlePacketInit init) {
		super(init);
	}

	protected final ArrayBasedIntSkippingTable effectSkipTable = GenericIdSkipper.EFFECT.getTable(version);

	protected int effectId;

	@Override
	protected void decodeData(ByteBuf serverdata) {
		effectId = VarNumberCodec.readVarInt(serverdata);
	}

	@Override
	protected void decodeDataLast(ByteBuf serverdata) {
		if (effectSkipTable.isSet(effectId)) {
			throw MiddlePacketCancelException.INSTANCE;
		}
	}

}
