package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSpawnObject;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.pe.PEDataValues;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnObject extends MiddleSpawnObject {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		return RecyclableSingletonList.create(SpawnLiving.create(
				version,
				entity.getId(), x, y, z,
				motX / 8.000F, motY / 8000.F, motZ / 8000.F, pitch, yaw,
				null, PEDataValues.getObjectEntityTypeId(IdRemapper.ENTITY.getTable(version).getRemap(entity.getType()))
			));
		//TODO: Implement Objectdata
	}

}
