package protocolsupport.protocol.packet.handler;

import java.net.InetAddress;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import protocolsupport.ProtocolSupport;
import protocolsupport.api.Connection;
import protocolsupport.api.events.ServerPingResponseEvent;
import protocolsupport.api.events.ServerPingResponseEvent.ProtocolInfo;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.utils.JavaSystemProperty;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkManagerWrapper;
import protocolsupportbuildprocessor.Preload;

@Preload
public abstract class AbstractStatusListener {

	private static final int statusThreads = JavaSystemProperty.getValue("statusthreads", 2, Integer::parseInt);
	private static final int statusThreadKeepAlive = JavaSystemProperty.getValue("statusthreadskeepalive", 60, Integer::parseInt);

	static {
		ProtocolSupport.logInfo(MessageFormat.format("Status threads max count: {0}, keep alive time: {1}", statusThreads, statusThreadKeepAlive));
	}

	protected static final Executor statusprocessor = new ThreadPoolExecutor(
		1, statusThreads,
		statusThreadKeepAlive, TimeUnit.SECONDS,
		new LinkedBlockingQueue<Runnable>(),
		r -> new Thread(r, "StatusProcessingThread")
	);

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

		statusprocessor.execute(() -> {
			ServerPingResponseEvent revent = createResponse(networkManager.getChannel());
			networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createStausServerInfoPacket(
				revent.getPlayers(), revent.getProtocolInfo(),
				revent.getIcon(), revent.getMotd(), revent.getMaxPlayers()
			));
		});
	}

	public static ServerPingResponseEvent createResponse(Channel channel) {
		Connection connection = ConnectionImpl.getFromChannel(channel);

		ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

		String motd = Bukkit.getMotd();
		int maxPlayers = Bukkit.getMaxPlayers();

		InternalServerListPingEvent bevent = new InternalServerListPingEvent(connection.getAddress().getAddress(), motd, maxPlayers, players);
		bevent.setServerIcon(Bukkit.getServerIcon());
		Bukkit.getPluginManager().callEvent(bevent);

		ServerPingResponseEvent revent = new ServerPingResponseEvent(
			connection,
			new ProtocolInfo(connection.getVersion(), ServerPlatform.get().getMiscUtils().getModName() + " " + ServerPlatform.get().getMiscUtils().getVersionName()),
			bevent.getIcon() != null ? ServerPlatform.get().getMiscUtils().convertBukkitIconToBase64(bevent.getIcon()) : null,
			bevent.getMotd(), bevent.getMaxPlayers(),
			bevent.players.stream().map(Player::getName).collect(Collectors.toList())
		);
		Bukkit.getPluginManager().callEvent(revent);

		return revent;
	}

	public void handlePing(long pingId) {
		networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createStatusPongPacket(pingId), ChannelFutureListener.CLOSE);
	}

	public static class InternalServerListPingEvent extends ServerListPingEvent {

		protected final List<Player> players;
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
		public Iterator<Player> iterator() {
			return players.iterator();
		}

	}

}
