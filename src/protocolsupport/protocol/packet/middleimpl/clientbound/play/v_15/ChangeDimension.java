package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_15;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15.AbstractLegacyChangeDimension;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;

public class ChangeDimension extends AbstractLegacyChangeDimension {

	public ChangeDimension(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void writeChangeDimension(int dimensionId) {
		ClientBoundPacketData changedimension = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_RESPAWN);
		changedimension.writeInt(dimensionId);
		changedimension.writeLong(hashedSeed);
		changedimension.writeByte(gamemodeCurrent.getId());
		StringSerializer.writeVarIntUTF8String(changedimension, LegacyDimension.getWorldType(worldFlat));
		codec.writeClientbound(changedimension);
	}

}
