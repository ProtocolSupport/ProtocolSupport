package protocolsupport.zplatform.impl.pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.NumberConversions;

import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolType;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.Chunk;
import protocolsupport.protocol.pipeline.version.v_pe.PEPacketEncoder;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.storage.netcache.MovementCache;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.types.Position;

import java.util.Map;
import java.util.WeakHashMap;

//accurate center used for the ChunkPublisherUpdate packet
public class PEChunkPublisher implements Listener {

	protected final Map<Player, Long> lastChunkUpdate = new WeakHashMap<>();

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		sendChunkPublisherUpdate(event.getPlayer(), event.getPlayer().getLocation());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		sendChunkPublisherUpdate(event.getPlayer(), event.getTo());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerMove(PlayerMoveEvent event) {
		final Player player = event.getPlayer();
		final Long last = lastChunkUpdate.get(player);
		if (last == null || last != getChunkKey(event.getTo())) {
			sendChunkPublisherUpdate(player, event.getTo());
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerQuit(PlayerQuitEvent event) {
		lastChunkUpdate.remove(event.getPlayer());
	}

	protected void sendChunkPublisherUpdate(Player player, Location loc) {
		final ConnectionImpl connection = (ConnectionImpl) ProtocolSupportAPI.getConnection(player);
		if (connection == null || connection.getVersion().getProtocolType() != ProtocolType.PE) {
			return;
		}
		final ByteBuf publisherUpdate = Unpooled.buffer();
		PEPacketEncoder.sWritePacketId(publisherUpdate, PEPacketIDs.NETWORK_CHUNK_PUBLISHER_UPDATE_PACKET);
		Chunk.writeChunkPublisherUpdate(publisherUpdate, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		connection.sendRawPacket(MiscSerializer.readAllBytes(publisherUpdate));
		//update state
		lastChunkUpdate.put(player, getChunkKey(loc));
		final MovementCache mCache = connection.getCache().getMovementCache();
		mCache.setChunkPublisherPosition(new Position(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
	}

	protected static long getChunkKey(Location loc) {
		return getChunkKey(NumberConversions.floor(loc.getBlockX()) >> 4,
			NumberConversions.floor(loc.getBlockZ()) >> 4);
	}

	protected static long getChunkKey(int x, int z) {
		return (long)x & 0xFFFFFFFFL | ((long)z & 0xFFFFFFFFL) << 32;
	}

}
