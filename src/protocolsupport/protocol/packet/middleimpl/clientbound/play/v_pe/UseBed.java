package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleUseBed;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.DataWatcherSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVector3vi;
import protocolsupport.protocol.utils.networkentity.NetworkEntity;
import protocolsupport.utils.CollectionsUtils;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class UseBed extends MiddleUseBed {

	public UseBed(ConnectionImpl connection) {
		super(connection);
	}

	private static final int DATA_PLAYER_BED_POSITION = 28;
	private static final int TYPE_PLAYER_SPAWN = 0;
	private static final byte DATA_PLAYER_FLAG_SLEEP = 2;  // Nukkit has this as 1 << 1

	private ClientBoundPacketData createSingleAttributeDataPacket(NetworkEntity player, int key, DataWatcherObject<?> value) {
		CollectionsUtils.ArrayMap<DataWatcherObject<?>> peMetadata = new CollectionsUtils.ArrayMap<>(DataWatcherSerializer.MAX_USED_META_INDEX + 1);
		peMetadata.put(key, value);

		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SET_ENTITY_DATA);
		VarNumberSerializer.writeVarLong(serializer, player.getId());
		DataWatcherSerializer.writePEData(serializer, version, cache.getAttributesCache().getLocale(), peMetadata);
		return serializer;
	}

	private ClientBoundPacketData setPlayerSpawn() {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SPAWN_POS);
		VarNumberSerializer.writeSVarInt(serializer, TYPE_PLAYER_SPAWN);
		PositionSerializer.writePEPosition(serializer, bed);
		serializer.writeBoolean(false); // forced spawn, should be false for player spawn
		return serializer;
	}
	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		RecyclableArrayList<ClientBoundPacketData> packets = RecyclableArrayList.create();
		NetworkEntity player = cache.getWatchedEntityCache().getSelfPlayer();

		packets.add(createSingleAttributeDataPacket(player, DATA_PLAYER_BED_POSITION, new DataWatcherObjectVector3vi(bed)));
		packets.add(createSingleAttributeDataPacket(player, EntityMetadata.PeMetaBase.PLAYER_FLAGS, new DataWatcherObjectByte(DATA_PLAYER_FLAG_SLEEP)));
		packets.add(setPlayerSpawn());

		return packets;
	}
}