package protocolsupport.protocol.packet.handler;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;

import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.PlayerLoginFinishEvent;
import protocolsupport.api.events.PlayerSyncLoginEvent;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.common.SimpleReadTimeoutHandler;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public abstract class AbstractLoginListenerPlay implements IPacketListener, IServerThreadTickListener {

	protected final NetworkManagerWrapper networkManager;
	protected final String hostname;
	protected final ConnectionImpl connection;

	protected AbstractLoginListenerPlay(NetworkManagerWrapper networkmanager, String hostname) {
		this.networkManager = networkmanager;
		this.connection = ConnectionImpl.getFromChannel(networkmanager.getChannel());
		this.hostname = hostname;
	}

	public void finishLogin() {
		if (!networkManager.isConnected()) {
			return;
		}

		CountDownLatch waitpacketsend = new CountDownLatch(1);
		networkManager.sendPacket(
			ServerPlatform.get().getPacketFactory().createLoginSuccessPacket(connection.getProfile()),
			future -> waitpacketsend.countDown()
		);
		try {
			if (!waitpacketsend.await(5, TimeUnit.SECONDS)) {
				disconnect("Timeout while waiting for login success send");
				return;
			}
		} catch (InterruptedException e) {
			disconnect("Exception while waiting for login success send");
			return;
		}

		networkManager.setProtocol(NetworkState.PLAY);

		keepConnection();

		PlayerLoginFinishEvent event = new PlayerLoginFinishEvent(connection);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isLoginDenied()) {
			disconnect(event.getDenyLoginMessage());
			return;
		}
		ready = true;
	}

	protected boolean ready;

	protected int keepAliveTicks = 1;

	@Override
	public void tick() {
		if (!ServerPlatform.get().getMiscUtils().isRunning()) {
			disconnect(org.spigotmc.SpigotConfig.restartMessage);
			return;
		}
		if ((keepAliveTicks++ % 80) == 0) {
			keepConnection();
		}
		if (ready) {
			tryJoin();
		}
	}

	protected void tryJoin() {
		ready = false;

		networkManager.cancelSyncTickTask();

		Bukkit.getOnlinePlayers().stream()
		.filter(player -> player.getUniqueId().equals(connection.getProfile().getUUID()))
		.forEach(player -> player.kickPlayer("You logged in from another location"));

		JoinData joindata = createJoinData();

		PlayerSyncLoginEvent syncloginevent = new PlayerSyncLoginEvent(connection, joindata.player);
		Bukkit.getPluginManager().callEvent(syncloginevent);
		if (syncloginevent.isLoginDenied()) {
			disconnect(syncloginevent.getDenyLoginMessage());
			joindata.close();
			return;
		}

		PlayerLoginEvent bukkitevent = new PlayerLoginEvent(joindata.player, hostname, networkManager.getAddress().getAddress(), networkManager.getRawAddress().getAddress());
		checkBans(bukkitevent, joindata.data);
		Bukkit.getPluginManager().callEvent(bukkitevent);
		if (bukkitevent.getResult() != PlayerLoginEvent.Result.ALLOWED) {
			disconnect(bukkitevent.getKickMessage());
			joindata.close();
			return;
		}

		networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createEmptyCustomPayloadPacket("finishlogin"));

		joinGame(joindata.data);
	}

	protected void keepConnection() {
		//custom payload does nothing on a client when sent with invalid tag,
		//but it resets client readtimeouthandler, and that is exactly what we need
		networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createEmptyCustomPayloadPacket("keepalive"));
		//we also need to reset server readtimeouthandler (may be null if netty already teared down the pipeline)
		SimpleReadTimeoutHandler timeouthandler = ChannelHandlers.getTimeoutHandler(networkManager.getChannel().pipeline());
		if (timeouthandler != null) {
			timeouthandler.setLastRead();
		}
	}

	protected String getConnectionRepr() {
		return (connection.getProfile() + " (" + networkManager.getAddress() + ")");
	}

	@Override
	public void disconnect(final String s) {
		try {
			Bukkit.getLogger().info("Disconnecting " + getConnectionRepr() + ": " + s);
			ProtocolVersion version = ConnectionImpl.getFromChannel(networkManager.getChannel()).getVersion();
			if ((version.getProtocolType() == ProtocolType.PC) && version.isBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_7_10)) {
				//first send join game that will make client actually switch to game state
				networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createFakeJoinGamePacket());
				//send disconnect with a little delay
				networkManager.getChannel().eventLoop().schedule(() -> disconnect0(s), 50, TimeUnit.MILLISECONDS);
			} else {
				disconnect0(s);
			}
		} catch (Throwable exception) {
			Bukkit.getLogger().log(Level.SEVERE, "Error whilst disconnecting player", exception);
			networkManager.close("Error whilst disconnecting player, force closing connection");
		}
	}

	protected void disconnect0(String s) {
		networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createPlayDisconnectPacket(s), future -> networkManager.close(s));
	}

	protected abstract JoinData createJoinData();

	protected abstract void checkBans(PlayerLoginEvent event, Object[] data);

	protected abstract void joinGame(Object[] data);

	protected abstract class JoinData {
		public final Player player;
		public final Object[] data;
		public JoinData(Player player, Object... data) {
			this.player = player;
			this.data = data;
		}
		protected abstract void close();
	}

}
