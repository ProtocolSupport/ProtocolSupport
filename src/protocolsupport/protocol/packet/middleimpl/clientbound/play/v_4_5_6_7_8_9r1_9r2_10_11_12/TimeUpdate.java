package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8_9r1_9r2_10_11_12;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleTimeUpdate;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class TimeUpdate extends MiddleTimeUpdate {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_UPDATE_TIME_ID, version);
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_4_7)) {
			timeOfDay = Math.abs(timeOfDay);
		}
		serializer.writeLong(worldAge);
		serializer.writeLong(timeOfDay);
		return RecyclableSingletonList.create(serializer);
	}

}
