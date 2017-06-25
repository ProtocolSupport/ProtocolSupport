package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import org.bukkit.util.Vector;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityHeadRotation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.protocol.utils.types.NetworkEntityType;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityHeadRotation extends MiddleEntityHeadRotation {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		NetworkEntity entity = cache.getWatchedEntity(entityId);
		if(entity == null || entity.isOfType(NetworkEntityType.PLAYER)) {
			return RecyclableEmptyList.get();
		} else {
			Vector pos = entity.getPosition();
			return RecyclableSingletonList.create(EntityTeleport.create(entity, pos.getX(), pos.getY(), pos.getZ(), entity.getPitch(), headRot, entity.getYaw(), entity.getOnGround(), false, version));
		}
	}

}
