package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_1_9_r1__1_9_r2;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.packet.middleimpl.PacketData;
import protocolsupport.protocol.typeremapper.watchedentity.WatchedDataRemapper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherDeserializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnNamed extends MiddleSpawnNamed<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_SPAWN_NAMED_ID, version);
		serializer.writeVarInt(playerEntityId);
		serializer.writeUUID(uuid);
		serializer.writeDouble(x);
		serializer.writeDouble(y);
		serializer.writeDouble(z);
		serializer.writeByte(yaw);
		serializer.writeByte(pitch);
		serializer.writeBytes(DataWatcherDeserializer.encodeData(version, WatchedDataRemapper.transform(wplayer, metadata, version)));
		return RecyclableSingletonList.create(serializer);
	}

}
