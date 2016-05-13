package protocolsupport.protocol.transformer.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleRespawn;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.StartGamePacket;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class Respawn extends MiddleRespawn<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public boolean needsPlayer() {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		return RecyclableSingletonList.create(new StartGamePacket(
			player.getWorld().getEnvironment().getId(),
			player.getGameMode().getValue(),
			player.getEntityId(),
			player.getWorld().getSpawnLocation(),
			player.getLocation()
		));
	}

}
