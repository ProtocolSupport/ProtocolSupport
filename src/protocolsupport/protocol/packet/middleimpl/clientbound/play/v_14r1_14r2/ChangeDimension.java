package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2;

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
	public void writeToClient() {
		ClientBoundPacketData changedimension = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_RESPAWN);
		changedimension.writeInt(dimension.getId());
		changedimension.writeByte(gamemode.getId());
		StringSerializer.writeVarIntUTF8String(changedimension, leveltype);
		codec.write(changedimension);
	}

}
