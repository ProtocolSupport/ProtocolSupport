package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import org.bukkit.util.Vector;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityRelMove;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.utils.types.NetworkEntity;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityRelMove extends MiddleEntityRelMove {

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData(ProtocolVersion version) {
		NetworkEntity entity = cache.getWatchedEntity(entityId);
		if(entity == null) {
			return RecyclableEmptyList.get();
		} else {
			Vector pos = entity.getPosition();
			return RecyclableSingletonList.create(EntityTeleport.create(entity, pos.getX(), pos.getY(), pos.getZ(), entity.getPitch(), entity.getHeadYaw(), entity.getYaw(), onGround, false, version));
		}
	}

}
