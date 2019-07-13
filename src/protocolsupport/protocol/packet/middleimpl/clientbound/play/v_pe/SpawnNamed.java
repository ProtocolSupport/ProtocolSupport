package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.DataWatcherSerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.PlayerListCache.PlayerListEntry;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnNamed extends MiddleSpawnNamed {

	public SpawnNamed(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SPAWN_PLAYER);
		MiscSerializer.writePEUUID(serializer, entity.getUUID());
		PlayerListEntry entry = cache.getPlayerListCache().getEntry(entity.getUUID());
		StringSerializer.writeString(serializer, version, entry != null ? entry.getCurrentName(cache.getAttributesCache().getLocale()) : "UNKNOWN");
		VarNumberSerializer.writeSVarLong(serializer, entity.getId());
		VarNumberSerializer.writeVarLong(serializer, entity.getId());
		StringSerializer.writeString(serializer, version, ""); //Chat :F
		serializer.writeFloatLE((float) x);
		serializer.writeFloatLE((float) y);
		serializer.writeFloatLE((float) z);
		serializer.writeFloatLE(0F); //mot x
		serializer.writeFloatLE(0F); //mot y
		serializer.writeFloatLE(0F); //mot z
		serializer.writeFloatLE(pitch);
		serializer.writeFloatLE(yaw); //head yaw actually
		serializer.writeFloatLE(yaw);
		ItemStackSerializer.writeItemStack(serializer, version, cache.getAttributesCache().getLocale(), NetworkItemStack.NULL);
		DataWatcherSerializer.writePEData(serializer, version, cache.getAttributesCache().getLocale(), EntityMetadata.includeBaseFlags(version, entityRemapper.getRemappedMetadata(), entity));
		VarNumberSerializer.writeVarInt(serializer, 0); //?
		VarNumberSerializer.writeVarInt(serializer, 0); //?
		VarNumberSerializer.writeVarInt(serializer, 0); //?
		VarNumberSerializer.writeVarInt(serializer, 0); //?
		VarNumberSerializer.writeVarInt(serializer, 0); //?
		serializer.writeLongLE(0); //?
		VarNumberSerializer.writeVarInt(serializer, 0); //entity links
		StringSerializer.writeString(serializer, version, "");
		return RecyclableSingletonList.create(serializer);
	}

}
