package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldCustomSound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldCustomSound extends MiddleWorldCustomSound {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableSingletonList.create(create(connection.getVersion(), id, x, y, z, volume, pitch));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String id, int x, int y, int z, float volume, float pitch) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.PLAY_SOUND, version);
		StringSerializer.writeString(serializer, version, id);
		VarNumberSerializer.writeSVarInt(serializer, x);
		VarNumberSerializer.writeVarInt(serializer, y);
		VarNumberSerializer.writeSVarInt(serializer, z);
		serializer.writeFloatLE(volume);
		serializer.writeFloatLE(pitch);
		return serializer;
	}

}