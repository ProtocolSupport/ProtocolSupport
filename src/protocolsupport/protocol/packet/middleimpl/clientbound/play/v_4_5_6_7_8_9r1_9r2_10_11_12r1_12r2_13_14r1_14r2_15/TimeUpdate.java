package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15;

import protocolsupport.api.ProtocolVersion;
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
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_5_2)) {
			timeOfDay = Math.abs(timeOfDay);
		}
		timeupdate.writeLong(worldAge);
		timeupdate.writeLong(timeOfDay);
		codec.write(timeupdate);
	}

}
