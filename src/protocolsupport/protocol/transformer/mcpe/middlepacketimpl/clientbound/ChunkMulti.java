package protocolsupport.protocol.transformer.mcpe.middlepacketimpl.clientbound;

import java.io.IOException;

import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import net.minecraft.server.v1_8_R3.Chunk;
import net.minecraft.server.v1_8_R3.World;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.ChunkPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleChunkMulti;
import protocolsupport.utils.recyclable.RecyclableArrayList;
import protocolsupport.utils.recyclable.RecyclableCollection;

public class ChunkMulti extends MiddleChunkMulti<RecyclableCollection<? extends ClientboundPEPacket>> {

	@Override
	public boolean needsPlayer() {
		return true;
	}

	@Override
	public RecyclableCollection<? extends ClientboundPEPacket> toData(ProtocolVersion version) throws IOException {
		World world = ((CraftWorld) player.getWorld()).getHandle();
		RecyclableArrayList<ClientboundPEPacket> packets = RecyclableArrayList.create();
		for (int i = 0; i < data.length; i++) {
			Chunk chunk = world.getChunkIfLoaded(chunkX[i], chunkZ[i]);
			if (chunk != null) {
				storage.getPEStorage().incLoadedChunkCount();
				packets.add(new ChunkPacket(chunk));
			}
		}
		return packets;
	}

}
