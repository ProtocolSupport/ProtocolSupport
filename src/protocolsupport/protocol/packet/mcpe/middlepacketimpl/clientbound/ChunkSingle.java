package protocolsupport.protocol.packet.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;

import net.minecraft.server.v1_9_R2.Chunk;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.packet.mcpe.packet.mcpe.clientbound.ChunkPacket;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunk;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class ChunkSingle extends MiddleChunk<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public boolean needsPlayer() {
		return true;
	}

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		Chunk chunk = ((CraftWorld) player.getWorld()).getHandle().getChunkIfLoaded(chunkX, chunkZ);
		if (chunk != null && !(full && bitmask == 0)) {
			storage.getPEStorage().attemptPlayerChunkMatch(chunkX, chunkZ);
			return RecyclableSingletonList.create(new ChunkPacket(chunk));
		} else {
			return RecyclableEmptyList.get();
		}
	}

}
