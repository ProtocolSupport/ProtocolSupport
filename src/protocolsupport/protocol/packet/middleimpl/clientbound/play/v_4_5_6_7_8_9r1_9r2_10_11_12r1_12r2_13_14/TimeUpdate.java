package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12r1_12r2_13_14;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTimeUpdate;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class TimeUpdate extends MiddleTimeUpdate {

	public TimeUpdate(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_UPDATE_TIME_ID);
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_5_2)) {
			timeOfDay = Math.abs(timeOfDay);
		}
		serializer.writeLong(worldAge);
		serializer.writeLong(timeOfDay);
		return RecyclableSingletonList.create(serializer);
	}

}
