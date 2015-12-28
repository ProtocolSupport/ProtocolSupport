package protocolsupport.protocol.transformer.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import net.minecraft.server.v1_8_R3.Chunk;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.ChunkPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleChunkSingle;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ChunkSingle extends MiddleChunkSingle<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public boolean needsPlayer() {
		return true;
	}

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		Chunk chunk = ((CraftWorld) player.getWorld()).getHandle().getChunkIfLoaded(chunkX, chunkZ);
		if (chunk != null && !(cont && bitmask == 0)) {
			storage.getPEStorage().incLoadedChunkCount();
			return RecyclableSingletonList.create(new ChunkPacket(chunk));
		} else {
			return RecyclableEmptyList.get();
		}
	}

}
