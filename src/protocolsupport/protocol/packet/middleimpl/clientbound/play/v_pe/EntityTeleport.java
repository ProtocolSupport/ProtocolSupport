package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityTeleport;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityTeleport extends MiddleEntityTeleport {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		ProtocolVersion version = connection.getVersion();
		NetworkEntity wentity = cache.getWatchedEntity(entityId);
		if ((wentity != null) && (wentity.getType() == NetworkEntityType.PLAYER)) {
			return RecyclableSingletonList.create(Position.create(version, entityId, x, y, z, pitch, yaw, Position.ANIMATION_MODE_ALL));
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_TELEPORT, version);
			VarNumberSerializer.writeVarLong(serializer, entityId);
			serializer.writeFloatLE((float) x);
			serializer.writeFloatLE((float) y);
			serializer.writeFloatLE((float) z);
			serializer.writeByte(pitch);
			serializer.writeByte(yaw); //head yaw actually
			serializer.writeByte(yaw);
			serializer.writeBoolean(onGround);
			serializer.writeBoolean(false); //teleported
			return RecyclableSingletonList.create(serializer);
		}
	}

}
