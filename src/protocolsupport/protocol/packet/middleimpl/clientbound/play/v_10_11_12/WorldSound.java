package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_10_11_12;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.LegacySound;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldSound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldSound extends MiddleWorldSound {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WORLD_CUSTOM_SOUND_ID, version);
		StringSerializer.writeString(serializer, version, LegacySound.getSoundName(id));
		VarNumberSerializer.writeVarInt(serializer, category);
		serializer.writeInt(x);
		serializer.writeInt(y);
		serializer.writeInt(z);
		serializer.writeFloat(volume);
		serializer.writeFloat(pitch);
		return RecyclableSingletonList.create(serializer);
	}

}
