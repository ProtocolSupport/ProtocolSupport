package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6_7_8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldCustomSound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldCustomSound extends MiddleWorldCustomSound {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WORLD_CUSTOM_SOUND_ID, version);
		StringSerializer.writeString(serializer, version, id);
		serializer.writeInt(x);
		serializer.writeInt(y);
		serializer.writeInt(z);
		serializer.writeFloat(volume);
		serializer.writeByte((int) (pitch * 63.5));
		return RecyclableSingletonList.create(serializer);
	}

}
