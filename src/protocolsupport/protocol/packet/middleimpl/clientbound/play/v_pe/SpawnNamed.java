package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class SpawnNamed extends MiddleSpawnNamed {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SPAWN_PLAYER, version);
		MiscSerializer.writeUUID(serializer, connection.getVersion(), entity.getUUID());
		StringSerializer.writeString(serializer, version, name);
		VarNumberSerializer.writeSVarLong(serializer, entity.getId());
		VarNumberSerializer.writeVarLong(serializer, entity.getId());
		serializer.writeFloatLE((float) x);
		serializer.writeFloatLE((float) y);
		serializer.writeFloatLE((float) z);
		serializer.writeFloatLE(0F); //mot x
		serializer.writeFloatLE(0F); //mot y
		serializer.writeFloatLE(0F); //mot z
		serializer.writeFloatLE(pitch);
		serializer.writeFloatLE(yaw); //head yaw actually
		serializer.writeFloatLE(yaw);
		ItemStackSerializer.writeItemStack(serializer, version, cache.getLocale(), ItemStackWrapper.NULL, true);
		EntityMetadata.encodeMeta(serializer, version, cache.getLocale(), EntityMetadata.transform(entity, metadata.getRemapped(), version));
		VarNumberSerializer.writeVarInt(serializer, 0); //?
		VarNumberSerializer.writeVarInt(serializer, 0); //?
		VarNumberSerializer.writeVarInt(serializer, 0); //?
		VarNumberSerializer.writeVarInt(serializer, 0); //?
		VarNumberSerializer.writeVarInt(serializer, 0); //?
		serializer.writeLongLE(0); //?
		VarNumberSerializer.writeVarInt(serializer, 0); //entity links
		return RecyclableSingletonList.create(serializer);
	}

}
