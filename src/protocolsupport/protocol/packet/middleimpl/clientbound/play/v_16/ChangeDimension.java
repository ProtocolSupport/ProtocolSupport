package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_16;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChangeDimension;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;

public class ChangeDimension extends MiddleChangeDimension {

	public ChangeDimension(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData changedimension = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_RESPAWN);
		StringSerializer.writeVarIntUTF8String(changedimension, dimension);
		StringSerializer.writeVarIntUTF8String(changedimension, world);
		changedimension.writeLong(hashedSeed);
		changedimension.writeByte(gamemodeCurrent.getId());
		changedimension.writeByte(gamemodePrevious.getId());
		changedimension.writeBoolean(worldDebug);
		changedimension.writeBoolean(worldFlat);
		changedimension.writeBoolean(keepEntityMetadata);
		codec.write(changedimension);
	}

}
