package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_7_1_8;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleWorldSound;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldSound extends MiddleWorldSound<RecyclableCollection<PacketData>> {

	//TODO: map sounds
	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) {
		return RecyclableEmptyList.get();
		/*PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_WORLD_SOUND_ID, version);
		serializer.writeString(name);
		serializer.writeInt(x);
		serializer.writeInt(y);
		serializer.writeInt(z);
		serializer.writeFloat(volume);
		serializer.writeByte(pitch);
		return RecyclableSingletonList.create(serializer);*/
	}

}
