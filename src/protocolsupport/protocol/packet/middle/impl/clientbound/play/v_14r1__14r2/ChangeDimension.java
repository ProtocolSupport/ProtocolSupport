package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_14r1__14r2;

import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.play.v_4__15.AbstractLegacyChangeDimension;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;

public class ChangeDimension extends AbstractLegacyChangeDimension implements
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2 {

	public ChangeDimension(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeChangeDimension(int dimensionId) {
		ClientBoundPacketData changedimension = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_RESPAWN);
		changedimension.writeInt(dimensionId);
		changedimension.writeByte(gamemodeCurrent.getId());
		StringCodec.writeVarIntUTF8String(changedimension, LegacyDimension.getWorldType(worldFlat));
		io.writeClientbound(changedimension);
	}

}
