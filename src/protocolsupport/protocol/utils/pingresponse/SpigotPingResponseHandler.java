package protocolsupport.protocol.utils.pingresponse;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;
import org.spigotmc.SpigotConfig;

import protocolsupport.api.Connection;
import protocolsupport.api.events.ServerPingResponseEvent;
import protocolsupport.api.events.ServerPingResponseEvent.ProtocolInfo;
import protocolsupport.zplatform.ServerPlatform;

public class SpigotPingResponseHandler extends PingResponseHandler {

	@SuppressWarnings("deprecation")
	@Override
	public ServerPingResponseEvent createResponse(Connection connection) {
		List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

		String motd = Bukkit.getMotd();
		int numPlayers = Bukkit.getOnlinePlayers().size();
		int maxPlayers = Bukkit.getMaxPlayers();

		InternalServerListPingEvent bevent = new InternalServerListPingEvent(connection.getVirtualHost().toString(), connection.getAddress().getAddress(), motd, numPlayers, maxPlayers, players);
		bevent.setServerIcon(Bukkit.getServerIcon());
		Bukkit.getPluginManager().callEvent(bevent);

		ServerPingResponseEvent revent = new ServerPingResponseEvent(
			connection,
			new ProtocolInfo(connection.getVersion(), createServerVersionString()),
			bevent.getIcon() != null ? ServerPlatform.get().getMiscUtils().convertBukkitIconToBase64(bevent.getIcon()) : null,
			bevent.getMotd(),
			bevent.getNumPlayers(), bevent.getMaxPlayers(),
			bevent.players.stream()
			.limit(SpigotConfig.playerSample)
			.map(Player::getName)
			.toList()
		);
		Bukkit.getPluginManager().callEvent(revent);

		return revent;
	}

	protected static class InternalServerListPingEvent extends ServerListPingEvent {

		protected final List<Player> players;

		protected InternalServerListPingEvent(String hostname, InetAddress address, String motd, int numPlayers, int maxPlayers, List<Player> players) {
			super(hostname, address, motd, numPlayers, maxPlayers);
			this.players = players;
		}

		protected CachedServerIcon icon;
		public CachedServerIcon getIcon() {
			return icon;
		}

		@Override
		public void setServerIcon(CachedServerIcon icon) {
			this.icon = icon;
		}

		@Override
		public Iterator<Player> iterator() {
			return players.iterator();
		}

	}

}
