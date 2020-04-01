package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTimeUpdate;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;

public class TimeUpdate extends MiddleTimeUpdate {

	public TimeUpdate(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData timeupdate = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_UPDATE_TIME);
		timeupdate.writeLong(Math.abs(timeOfDay));
		codec.write(timeupdate);
	}

}
