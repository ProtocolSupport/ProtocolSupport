package protocolsupport.protocol.packet.handler;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;

import io.netty.channel.ChannelFutureListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.ServerPingResponseEvent;
import protocolsupport.api.events.ServerPingResponseEvent.ProtocolInfo;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkManagerWrapper;
import protocolsupport.zplatform.network.PlatformPacketFactory;

public abstract class AbstractStatusListener {

	protected final NetworkManagerWrapper networkManager;
	public AbstractStatusListener(NetworkManagerWrapper networkmanager) {
		this.networkManager = networkmanager;
	}

	private boolean sentInfo = false;

	public void handleStatusRequest() {
		if (sentInfo) {
			networkManager.close("Status request has already been handled.");
		}
		sentInfo = true;

		InetSocketAddress addr = networkManager.getAddress();

		ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

		String motd = Bukkit.getMotd();
		int maxPlayers = Bukkit.getMaxPlayers();

		InternalServerListPingEvent bevent = new InternalServerListPingEvent(addr.getAddress(), motd, maxPlayers, players);
		bevent.setServerIcon(Bukkit.getServerIcon());
		Bukkit.getPluginManager().callEvent(bevent);

		String icon = bevent.getIcon() != null ? ServerPlatform.get().getMiscUtils().convertBukkitIconToBase64(bevent.getIcon()) : null;
		motd = bevent.getMotd();
		maxPlayers = bevent.getMaxPlayers();

		List<String> profiles = new ArrayList<>(players.size());
		for (Player player : players) {
			profiles.add(player.getName());
		}

		ServerPingResponseEvent revent = new ServerPingResponseEvent(
			ConnectionImpl.getFromChannel(networkManager.getChannel()),
			new ProtocolInfo(ProtocolVersion.getLatest(), ServerPlatform.get().getMiscUtils().getModName() + " " + ServerPlatform.get().getMiscUtils().getVersionName()),
			icon, motd, maxPlayers, profiles
		);
		Bukkit.getPluginManager().callEvent(revent);

		networkManager.sendPacket(PlatformPacketFactory.createStausServerInfoPacket(
			revent.getPlayers(), revent.getProtocolInfo(),
			revent.getIcon(), revent.getMotd(), revent.getMaxPlayers()
		));
	}

	@SuppressWarnings("unchecked")
	public void handlePing(long pingId) {
		networkManager.sendPacket(PlatformPacketFactory.createStatusPongPacket(pingId), ChannelFutureListener.CLOSE);
	}

	public static class InternalServerListPingEvent extends ServerListPingEvent {

		private List<Player> players;
		protected InternalServerListPingEvent(InetAddress address, String motd, int maxPlayers, List<Player> players) {
			super(address, motd, maxPlayers);
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
		public Iterator<Player> iterator() throws UnsupportedOperationException {
			return players.iterator();
		}

	}

}
