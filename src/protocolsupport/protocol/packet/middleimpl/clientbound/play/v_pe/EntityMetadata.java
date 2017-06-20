package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityMetadata;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEMetaData;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIdRegistry;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityMetadata extends MiddleEntityMetadata {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		TIntObjectMap<DataWatcherObject<?>> remapped = PEMetaData.transform(cache.getWatchedEntity(entityId), metadata, version);
		if (remapped.isEmpty()) {
			return RecyclableEmptyList.get();
		} else {
			
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.SET_ENTITY_DATA, version);
			VarNumberSerializer.writeSVarLong(serializer, entityId);
			System.out.println("Entity: " + entityId);
			EntityMetadata.encode(serializer, version, remapped);
			return RecyclableEmptyList.get();
			//return RecyclableSingletonList.create(serializer);
		}
	}
	
	public static void encode(ByteBuf to, ProtocolVersion version, TIntObjectMap<DataWatcherObject<?>> peMetadata) {
		TIntObjectIterator<DataWatcherObject<?>> iterator = peMetadata.iterator();
		VarNumberSerializer.writeVarInt(to, peMetadata.size());
		System.out.println("	Size: " + peMetadata.size());
		while (iterator.hasNext()) {
			iterator.advance();
			DataWatcherObject<?> object = iterator.value();
			VarNumberSerializer.writeVarInt(to, iterator.key());
			System.out.println("	Key: " + iterator.key());
			int tk = DataWatcherObjectIdRegistry.getTypeId(object, version) ;
			VarNumberSerializer.writeVarInt(to, tk);
			System.out.println("		Type: " + tk);
			object.writeToStream(to, version);
			System.out.println("		Object: " + object.toString());
		}
	}
	
}
