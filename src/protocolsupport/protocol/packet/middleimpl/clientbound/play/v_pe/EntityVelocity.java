package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityVelocity;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityVelocity extends MiddleEntityVelocity {

	public EntityVelocity(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		return RecyclableSingletonList.create(create(entityId, motX, motY, motZ));
	}

	public static ClientBoundPacketData create(int entityId, float motX, float motY, float motZ) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_VELOCITY);
		VarNumberSerializer.writeVarLong(serializer, entityId);
		serializer.writeFloatLE(motX / 8000.0F);
		serializer.writeFloatLE(motY / 8000.0F);
		serializer.writeFloatLE(motZ / 8000.0F);
		return serializer;
	}

}
