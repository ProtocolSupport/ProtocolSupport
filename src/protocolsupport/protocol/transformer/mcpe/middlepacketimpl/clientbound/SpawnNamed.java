package protocolsupport.protocol.transformer.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.storage.LocalStorage.PlayerListEntry;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.AddPlayerPacket;
import protocolsupport.protocol.transformer.mcpe.utils.PEWatchedPlayer;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleSpawnNamed;
import protocolsupport.protocol.typeremapper.watchedentity.WatchedDataRemapper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class SpawnNamed extends MiddleSpawnNamed<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public void handle() {
		wplayer = new PEWatchedPlayer(playerEntityId, uuid);
		storage.addWatchedEntity(wplayer);
		PlayerListEntry entry = storage.getPlayerListEntry(uuid);
		name = entry != null ? entry.getName() : "Unknown";
	}

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		return RecyclableSingletonList.create(new AddPlayerPacket(
			uuid, name, playerEntityId,
			(float) x, (float) y, (float) z,
			yaw / 256.0F * 360.0F, pitch / 256.0F * 360.0F,
			WatchedDataRemapper.transform(wplayer, metadata, version)
		));
	}

}
