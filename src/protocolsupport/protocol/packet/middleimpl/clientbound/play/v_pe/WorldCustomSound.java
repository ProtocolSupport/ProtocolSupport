package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleWorldCustomSound;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.NamespacedKeyUtils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class WorldCustomSound extends MiddleWorldCustomSound {

	public WorldCustomSound(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableSingletonList.create(create(connection.getVersion(), id, x, y, z, volume, pitch));
	}

	public static ClientBoundPacketData create(ProtocolVersion version, String id, int x, int y, int z, float volume, float pitch) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.PLAY_SOUND);
		StringSerializer.writeString(serializer, version, NamespacedKeyUtils.fromString(id).getKey());
		VarNumberSerializer.writeSVarInt(serializer, x);
		VarNumberSerializer.writeVarInt(serializer, y);
		VarNumberSerializer.writeSVarInt(serializer, z);
		serializer.writeFloatLE(volume);
		serializer.writeFloatLE(pitch);
		return serializer;
	}

}
