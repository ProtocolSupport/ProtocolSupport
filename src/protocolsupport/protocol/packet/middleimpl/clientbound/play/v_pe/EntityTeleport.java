package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.legacyremapper.pe.PEPacketIDs;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityTeleport;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityTeleport extends MiddleEntityTeleport {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		WatchedEntity wentity = cache.getWatchedEntity(entityId);
		if ((wentity != null) && (wentity.getType() == SpecificRemapper.PLAYER)) {
			return RecyclableSingletonList.create(Position.create(version, entityId, x, y, z, pitch, yaw, Position.ANIMATION_MODE_ALL));
		} else {
			ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.ENTITY_TELEPORT, version);
			VarNumberSerializer.writeSVarLong(serializer, entityId);
			MiscSerializer.writeLFloat(serializer, (float) x);
			MiscSerializer.writeLFloat(serializer, (float) y);
			MiscSerializer.writeLFloat(serializer, (float) z);
			serializer.writeByte(pitch);
			serializer.writeByte(yaw); //head yaw actually
			serializer.writeByte(yaw);
			return RecyclableSingletonList.create(serializer);
		}
	}

}
