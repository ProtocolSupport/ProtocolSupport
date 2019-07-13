package protocolsupport.listeners.internal;


import java.util.function.BiConsumer;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import io.netty.buffer.ByteBuf;
import protocolsupport.ProtocolSupport;
import protocolsupport.api.Connection;
import protocolsupport.listeners.InternalPluginMessageRequest.PluginMessageData;
import protocolsupport.protocol.types.ChunkCoord;
import protocolsupport.zplatform.ServerPlatform;

public class ChunkUpdateRequest extends PluginMessageData {

	public static final BiConsumer<Connection, ChunkUpdateRequest> handler = (connection, request) -> {
		Bukkit.getScheduler().runTask(ProtocolSupport.getInstance(), () -> {
			Chunk chunk = connection.getPlayer().getWorld().getChunkAt(request.getChunk().getX(), request.getChunk().getZ());
			if (chunk.isLoaded()) {
				connection.sendPacket(ServerPlatform.get().getPacketFactory().createUpdateChunkPacket(chunk));
			}
		});
	};

	protected ChunkCoord chunk;

	public ChunkUpdateRequest() {
		this(null);
	}

	public ChunkUpdateRequest(ChunkCoord chunk) {
		this.chunk = chunk;
	}

	@Override
	protected void read(ByteBuf from) {
		chunk = new ChunkCoord(from.readInt(), from.readInt());
	}

	@Override
	protected void write(ByteBuf to) {
		to.writeInt(chunk.getX());
		to.writeInt(chunk.getZ());
	}

	public ChunkCoord getChunk() {
		return chunk;
	}

}
