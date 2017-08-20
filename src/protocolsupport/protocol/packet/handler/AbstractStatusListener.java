package protocolsupport.protocol.packet.handler;

import java.net.InetAddress;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public abstract class AbstractStatusListener {

	private static final int statusThreads = Utils.getJavaPropertyValue("statusthreads", 2, Integer::parseInt);
	private static final int statusThreadKeepAlive = Utils.getJavaPropertyValue("statusthreadskeepalive", 60, Integer::parseInt);

	public static void init() {
		ProtocolSupport.logInfo(MessageFormat.format("Status threads max count: {0}, keep alive time: {1}", statusThreads, statusThreadKeepAlive));
	}

	private static final Executor statusprocessor = new ThreadPoolExecutor(
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

		executeTask(() -> {
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

		String icon = bevent.getIcon() != null ? ServerPlatform.get().getMiscUtils().convertBukkitIconToBase64(bevent.getIcon()) : null;
		motd = bevent.getMotd();
		maxPlayers = bevent.getMaxPlayers();

		ServerPingResponseEvent revent = new ServerPingResponseEvent(
			connection,
			new ProtocolInfo(connection.getVersion(), ServerPlatform.get().getMiscUtils().getModName() + " " + ServerPlatform.get().getMiscUtils().getVersionName()),
			icon, motd, maxPlayers, bevent.getSampleText()
		);
		Bukkit.getPluginManager().callEvent(revent);

		return revent;
	}

	public static void executeTask(Runnable runnable) {
		statusprocessor.execute(runnable);
	}

	@SuppressWarnings("unchecked")
	public void handlePing(long pingId) {
		networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createStatusPongPacket(pingId), ChannelFutureListener.CLOSE);
	}

	public static class InternalServerListPingEvent extends ServerListPingEvent {

		private final List<Player> players;
		private Set<String> sample;
		protected InternalServerListPingEvent(InetAddress address, String motd, int maxPlayers, List<Player> players) {
			super(address, motd, maxPlayers);
			this.players = players;
			this.sample = this.players.stream().map(Player::getName).collect(Collectors.toSet());
		}

		protected CachedServerIcon icon;
		public CachedServerIcon getIcon() {
			return icon;
		}

		@Override
		public void setServerIcon(CachedServerIcon icon) {
			this.icon = icon;
		}

		public List<String> getSampleText() {
			return new ArrayList<String>(sample);
		}

		@Override
		public Iterator<Player> iterator() {
			return new Iterator<Player>() {
				private final Iterator<Player> iterator = players.iterator();
				private Player player;
				@Override
				public boolean hasNext() {
					return iterator.hasNext();
				}

				@Override
				public Player next() {
					return (player = iterator.next());
				}

				@Override
				public void remove() {
					iterator.remove();
					sample.remove(player.getName());
				}
			};
		}

		public void setSampleText(List<String> sample) {
			this.sample = new HashSet<>(sample);
			this.players.removeIf(player -> !sample.contains(player.getName()));
		}

	}

}
