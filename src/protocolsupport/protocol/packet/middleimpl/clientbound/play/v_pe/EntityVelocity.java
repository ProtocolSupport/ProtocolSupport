package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityVelocity;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityVelocity extends MiddleEntityVelocity {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		return RecyclableSingletonList.create(create(version, entityId, motX, motY, motZ));
	}
	
	public static ClientBoundPacketData create(ProtocolVersion version, int entityId, float motX, float motY, float motZ) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_VELOCITY, version);
		VarNumberSerializer.writeVarLong(serializer, entityId);
		MiscSerializer.writeLFloat(serializer, motX / 8000.0F);
		MiscSerializer.writeLFloat(serializer, motY / 8000.0F);
		MiscSerializer.writeLFloat(serializer, motZ / 8000.0F);
		return serializer;
	}

}
