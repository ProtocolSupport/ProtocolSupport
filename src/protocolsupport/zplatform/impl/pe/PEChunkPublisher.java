package protocolsupport.zplatform.impl.pe;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.clientbound.login.v_pe.LoginSuccess;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.ChangeDimension;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.Chunk;
import protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe.CustomPayload;
import protocolsupport.protocol.pipeline.version.v_pe.PEPacketEncoder;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

import java.util.Map;
import java.util.WeakHashMap;

//manages PE ChunkPublisherUpdate and ChangeDimension packets
public class PEChunkPublisher implements Listener {

	protected final Map<Player, Long> lastChunkUpdate = new WeakHashMap<>();

	@EventHandler(priority = EventPriority.MONITOR)
	void onPlayerJoin(PlayerJoinEvent event) {
		sendChunkPublisherUpdate(event.getPlayer(), event.getPlayer().getLocation(), false, false);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	void onPlayerTeleport(PlayerTeleportEvent event) {
		sendChunkPublisherUpdate(event.getPlayer(), event.getTo(),
			event.getTo().getWorld().getEnvironment() != event.getFrom().getWorld().getEnvironment(),
			event.getTo().getWorld() != event.getFrom().getWorld());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	void onPlayerMove(PlayerMoveEvent event) {
		final Player player = event.getPlayer();
		final Long last = lastChunkUpdate.get(player);
		if (last == null || last != player.getChunk().getChunkKey()) {
			sendChunkPublisherUpdate(player, event.getTo(), false, false);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	void onPlayerQuit(PlayerQuitEvent event) {
		lastChunkUpdate.remove(event.getPlayer());
	}

	protected void sendChunkPublisherUpdate(Player player, Location loc, boolean dimChange, boolean worldChange) {
		ConnectionImpl connection = (ConnectionImpl) ProtocolSupportAPI.getConnection(player);
		if (connection == null || !connection.getVersion().isPE()) {
			return;
		}
		if (worldChange && !dimChange) {
			final World.Environment env = loc.getWorld().getEnvironment();
			final World.Environment altEnv;
			switch (env) {
				case NORMAL: altEnv = World.Environment.NETHER; break;
				default: altEnv = World.Environment.NORMAL; break;
			}
			sendDimSwitch(connection, altEnv);
			for (int x = -1; x <= 1; x++) {
				for (int z = -1; z <= 1; z++) {
					connection.sendRawPacket(Chunk.createRawEmptyChunk(x, z));
				}
			}
			connection.sendRawPacket(LoginSuccess.createRawPlayStatus(LoginSuccess.LOGIN_SUCCESS));
			connection.sendRawPacket(CustomPayload.createRawTag(
				ProtocolVersionsHelper.LATEST_PE, PEDimSwitchLock.AWAIT_DIM_ACK_MESSAGE));
			sendDimSwitch(connection, loc.getWorld().getEnvironment());
		} else if (dimChange) {
			sendDimSwitch(connection, loc.getWorld().getEnvironment());
		}
		ByteBuf serializer = Unpooled.buffer();
		PEPacketEncoder.sWritePacketId(serializer, PEPacketIDs.NETWORK_CHUNK_PUBLISHER_UPDATE_PACKET);
		Chunk.writeChunkPublisherUpdate(serializer, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
		connection.sendRawPacket(MiscSerializer.readAllBytes(serializer));
		lastChunkUpdate.put(player, player.getChunk().getChunkKey());
	}

	protected static void sendDimSwitch(ConnectionImpl connection, World.Environment env) {
		final int dim;
		switch (env) {
			case NETHER: dim = 1; break;
			case THE_END: dim = 2; break;
			default: dim = 0; break;
		}
		ByteBuf serializer = Unpooled.buffer();
		PEPacketEncoder.sWritePacketId(serializer, PEPacketIDs.CHANGE_DIMENSION);
		ChangeDimension.writeRaw(serializer, 8, 300, 8, dim);
		connection.sendRawPacket(MiscSerializer.readAllBytes(serializer));
	}
}
