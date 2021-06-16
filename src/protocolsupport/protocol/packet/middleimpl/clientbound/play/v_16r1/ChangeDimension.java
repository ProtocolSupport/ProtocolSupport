package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_16r1;

import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChangeDimension;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;

public class ChangeDimension extends MiddleChangeDimension {

	public ChangeDimension(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData changedimension = ClientBoundPacketData.create(ClientBoundPacketType.CLIENTBOUND_PLAY_RESPAWN);
		StringSerializer.writeVarIntUTF8String(changedimension, LegacyDimension.getStringId(dimension));
		StringSerializer.writeVarIntUTF8String(changedimension, world);
		changedimension.writeLong(hashedSeed);
		changedimension.writeByte(gamemodeCurrent.getId());
		changedimension.writeByte(gamemodePrevious.getId());
		changedimension.writeBoolean(worldDebug);
		changedimension.writeBoolean(worldFlat);
		changedimension.writeBoolean(keepEntityMetadata);
		codec.writeClientbound(changedimension);
	}

}
