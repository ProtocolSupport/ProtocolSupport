package protocolsupport.listeners;

import java.text.MessageFormat;

import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolType;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;

public class PocketFixer implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onTeleport(PlayerTeleportEvent event) {
		System.out.println("Teleport " + event.getCause() + " from " + event.getFrom().getWorld().getEnvironment() + " to: " + event.getTo().getWorld().getEnvironment());
		if(!event.getFrom().getWorld().getEnvironment().equals(event.getTo().getWorld().getEnvironment())) {
			Connection connection = ProtocolSupportAPI.getConnection(event.getPlayer());
			if (
				(connection != null) &&
				(connection.getVersion().getProtocolType() == ProtocolType.PE)
			) {
				sendDimChange(connection, event.getTo());
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onRespawn(PlayerRespawnEvent event) {
		System.out.println("Respawn from " + event.getPlayer().getWorld().getEnvironment() + " to: " + event.getRespawnLocation().getWorld().getEnvironment());
		if(!event.getPlayer().getWorld().getEnvironment().equals(event.getRespawnLocation().getWorld().getEnvironment())) {
			Connection connection = ProtocolSupportAPI.getConnection(event.getPlayer());
			if (
				(connection != null) &&
				(connection.getVersion().getProtocolType() == ProtocolType.PE)
			) {
				sendDimChange(connection, event.getRespawnLocation());
			}
		}
	}
	
	private void sendDimChange(Connection connection, Location newloc) {
		ByteBuf changedim = Unpooled.buffer();
		VarNumberSerializer.writeVarInt(changedim, PEPacketIDs.CHANGE_DIMENSION);
		changedim.writeByte(0);
		changedim.writeByte(0);
		VarNumberSerializer.writeSVarInt(changedim, bukkitToPeDimension(newloc.getWorld().getEnvironment()));
		MiscSerializer.writeLFloat(changedim, (float) newloc.getX());
		MiscSerializer.writeLFloat(changedim, (float) newloc.getY());
		MiscSerializer.writeLFloat(changedim, (float) newloc.getZ());
		changedim.writeBoolean(true); //unused value
		connection.sendRawPacket(MiscSerializer.readAllBytes(changedim));
	}
	
	private static int bukkitToPeDimension(Environment dimId) {
		switch (dimId) {
			case NETHER: {
				return 1;
			}
			case THE_END: {
				return 2;
			}
			case NORMAL: {
				return 0;
			}
			default: {
				throw new IllegalArgumentException(MessageFormat.format("Uknown dim id {0}", dimId));
			}
		}
	}

}
