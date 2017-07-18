package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_12;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldSound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.legacy.LegacySound;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldSound extends MiddleWorldSound {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		String soundname = LegacySound.getSoundName(id);
		ClientBoundPacketData serializer = ClientBoundPacketData.create(ClientBoundPacket.PLAY_WORLD_CUSTOM_SOUND_ID, version);
		if(soundname == "entity.player.hurt_on_fire" && version.isBefore(ProtocolVersion.MINECRAFT_1_12)) soundname = "entity.player.hurt";
		StringSerializer.writeString(serializer, version, soundname);
		VarNumberSerializer.writeVarInt(serializer, category);
		serializer.writeInt(x);
		serializer.writeInt(y);
		serializer.writeInt(z);
		serializer.writeFloat(volume);
		serializer.writeFloat(pitch);
		return RecyclableSingletonList.create(serializer);
	}

}
