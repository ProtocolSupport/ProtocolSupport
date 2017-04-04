package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.pe.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityStatus;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityStatus extends MiddleEntityStatus{

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_EVENT, version);
		VarNumberSerializer.writeSVarLong(serializer, entityId);
		
		//TODO: write as remapper.
		int code = -1;
		switch(cache.getWatchedEntity(entityId).getType()){
			case FISHING_FLOAT: {
				if(status == 31) code = 13;
				break;
			}
			case PLAYER: {
				if(status == 9) code = 9;
				break;
			}
			case BASE_HORSE: {
				if(status == 6) code = 6;
				if(status == 7) code = 7;
				break;
			}
			case SHEEP: {
				if(status == 10) code = 10;
				break;
			}
			case TAMEABLE: {
				if(status == 6) code = 6;
				if(status == 7) code = 7;
				break;
			}
			case WOLF: {
				if(status == 8) code = 8;
				break;
			}
				
			default: {
				break;
			}
		}
		//Applies to all entities that can send these codes.
		switch(status){
		case 2:
			code = 2;
			break;
		case 3:
			code = 3;
			break;
		}
		
		if(code != -1){
			serializer.writeByte((byte) code);
			VarNumberSerializer.writeVarInt(serializer, 0);
			return RecyclableSingletonList.create(serializer);
		}
		return RecyclableEmptyList.get();
	}

}
