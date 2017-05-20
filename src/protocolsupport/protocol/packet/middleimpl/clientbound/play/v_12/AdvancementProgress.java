package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_12;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleAdvancementProgress;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class AdvancementProgress extends MiddleAdvancementProgress {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_ADVANCEMENT_PROGRESS, version);
		if (identifier != null) {
			serializer.writeBoolean(true);
			StringSerializer.writeString(serializer, version, identifier);
		} else {
			serializer.writeBoolean(false);
		}
		return RecyclableSingletonList.create(serializer);
	}

}
