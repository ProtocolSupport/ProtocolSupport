package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import org.bukkit.Material;

import gnu.trove.map.TIntObjectMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public class SpawnObject extends MiddleSpawnObject {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		if(entity.getType() == NetworkEntityType.ITEM) {
			return RecyclableSingletonList.create(createItem(version, entity.getId(), x, y, z, motX / 8.000F, motY / 8000.F, motZ / 8000.F,	null));
		} else { 
			return RecyclableSingletonList.create(SpawnLiving.create(
				version,
				entity.getId(), x, y, z,
				motX / 8.000F, motY / 8000.F, motZ / 8000.F, pitch, yaw,
				null, PEDataValues.getObjectEntityTypeId(IdRemapper.ENTITY.getTable(version).getRemap(entity.getType()))
			));
		}
		//TODO: Implement Objectdata
	}
	
	public static ClientBoundPacketData createItem(ProtocolVersion version,
			int entityId, double x, double y, double z,
			float motX, float motY, float motZ,
			TIntObjectMap<DataWatcherObject<?>> metadata) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ADD_ITEM_ENTITY, version);
		VarNumberSerializer.writeSVarLong(serializer, entityId);
		VarNumberSerializer.writeVarLong(serializer, entityId);
		//Prepare a fake itemStack to send. The next metadata update will fix it. (We don't know anything about the item yet).
		ItemStackWrapper fakestack = ServerPlatform.get().getWrapperFactory().createItemStack(Material.STONE);
		fakestack.setData(0); fakestack.setAmount(1);
		ItemStackSerializer.writePeSlot(serializer, version, fakestack);
		MiscSerializer.writeLFloat(serializer, (float) x);
		MiscSerializer.writeLFloat(serializer, (float) y);
		MiscSerializer.writeLFloat(serializer, (float) z);
		MiscSerializer.writeLFloat(serializer, motX); 
		MiscSerializer.writeLFloat(serializer, motY);
		MiscSerializer.writeLFloat(serializer, motZ);
		if (metadata == null) {
			VarNumberSerializer.writeVarInt(serializer, 0);
		} else {
			VarNumberSerializer.writeVarInt(serializer, 0); //TODO: metadata
		}
		return serializer;
	}
	
	

}
