package protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.both.MovePlayerPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.MoveEntityPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleEntityTeleport;
import protocolsupport.protocol.typeremapper.watchedentity.remapper.SpecificRemapper;
import protocolsupport.protocol.typeremapper.watchedentity.types.WatchedEntity;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class EntityTeleport extends MiddleEntityTeleport<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		WatchedEntity wentity = storage.getWatchedEntity(entityId);
		if (wentity != null && wentity.getType() == SpecificRemapper.PLAYER) {
			return RecyclableSingletonList.create(new MovePlayerPacket(
				entityId,
				(float) x, (float) y, (float) z,
				yaw / 256.0F * 360.0F, yaw / 256.0F * 360.0F, pitch / 256.0F * 360.0F,
				false
			));
		} else {
			return RecyclableSingletonList.create(new MoveEntityPacket(
				entityId,
				(float) x, (float) y, (float) z,
				yaw / 256.0F * 360.0F, pitch / 256.0F * 360.0F
			));
		}
	}

}
