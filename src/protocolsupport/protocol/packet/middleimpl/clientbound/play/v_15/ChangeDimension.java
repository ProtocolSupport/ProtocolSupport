package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_15;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChangeDimension;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacyDimension;

public class ChangeDimension extends MiddleChangeDimension {

	public ChangeDimension(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData changedimension = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_RESPAWN);
		changedimension.writeInt(LegacyDimension.getId(dimension));
		changedimension.writeLong(hashedSeed);
		changedimension.writeByte(gamemodeCurrent.getId());
		StringSerializer.writeVarIntUTF8String(changedimension, "default");
		codec.write(changedimension);
	}

}
