package protocolsupport.protocol.packet.handler;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;

import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.events.PlayerLoginFinishEvent;
import protocolsupport.api.events.PlayerSyncLoginEvent;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.common.SimpleReadTimeoutHandler;
import protocolsupport.utils.MiscUtils;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public abstract class AbstractLoginListenerPlay implements IPacketListener {

	protected final NetworkManagerWrapper networkManager;
	protected final ConnectionImpl connection;

	protected final Object keepConnectionLock = new Object();
	protected ScheduledFuture<?> keepConnectionTask;

	protected AbstractLoginListenerPlay(NetworkManagerWrapper networkmanager) {
		this.networkManager = networkmanager;
		this.connection = ConnectionImpl.getFromChannel(networkmanager.getChannel());

		synchronized (keepConnectionLock) {
			this.keepConnectionTask = connection.getIOExecutor().scheduleWithFixedDelay(this::keepConnection, 4, 4, TimeUnit.SECONDS);
		}
	}

	protected void cancelKeepConnectionTask() {
		synchronized (keepConnectionLock) {
			if (keepConnectionTask != null) {
				keepConnectionTask.cancel(false);
				keepConnectionTask = null;
			}
		}
	}

	@Override
	public void destroy() {
		cancelKeepConnectionTask();
	}

	public void finishLogin() {
		if (!networkManager.isConnected()) {
			return;
		}

		try {
			networkManager.sendPacketBlocking(ServerPlatform.get().getPacketFactory().createLoginSuccessPacket(connection.getProfile()), 5, TimeUnit.MINUTES);
		} catch (Throwable t) {
			disconnect(new TextComponent("Error while waiting for login success send"));
			MiscUtils.rethrowThreadException(t);
			return;
		}
		networkManager.setProtocol(NetworkState.PLAY);

		keepConnection();

		PlayerLoginFinishEvent event = new PlayerLoginFinishEvent(connection);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isLoginDenied()) {
			disconnect(event.getDenyLoginMessageJson());
			return;
		}

		ServerPlatform.get().getMiscUtils().callSyncTask(() -> {
			cancelKeepConnectionTask();
			joinWorld();
			return null;
		});
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

	@SuppressWarnings("deprecation")
	protected void joinWorld() {
		if (!ServerPlatform.get().getMiscUtils().isRunning()) {
			disconnect(BaseComponent.fromMessage(org.spigotmc.SpigotConfig.restartMessage));
			return;
		}

		UUID uuid = connection.getProfile().getUUID();
		for (Player player : new ArrayList<>(Bukkit.getOnlinePlayers())) {
			if (player.getUniqueId().equals(uuid)) {
				player.kickPlayer("You logged in from another location");
			}
		}

		JoinData joindata = createJoinData();

		connection.setWrappedProfile(joindata.player);

		PlayerSyncLoginEvent syncloginevent = new PlayerSyncLoginEvent(connection, joindata.player);
		Bukkit.getPluginManager().callEvent(syncloginevent);
		if (syncloginevent.isLoginDenied()) {
			disconnect(syncloginevent.getDenyLoginMessageJson());
			joindata.close();
			return;
		}

		PlayerLoginEvent bukkitevent = new PlayerLoginEvent(
			joindata.player,
			connection.getVirtualHost().toString(),
			networkManager.getAddress().getAddress(),
			networkManager.getRawAddress().getAddress()
		);
		checkBans(bukkitevent, joindata.data);
		Bukkit.getPluginManager().callEvent(bukkitevent);
		if (bukkitevent.getResult() != PlayerLoginEvent.Result.ALLOWED) {
			disconnect(BaseComponent.fromMessage(bukkitevent.getKickMessage()));
			joindata.close();
			return;
		}

		networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createEmptyCustomPayloadPacket("finishlogin"));

		joinGame(joindata.data);
	}

	protected String getConnectionRepr() {
		return (connection.getProfile() + " (" + networkManager.getAddress() + ")");
	}

	@Override
	public void disconnect(BaseComponent message) {
		try {
			Bukkit.getLogger().info(() -> "Disconnecting " + getConnectionRepr() + ": " + message.toLegacyText());
			ProtocolVersion version = connection.getVersion();
			if ((version.getProtocolType() == ProtocolType.PC) && version.isBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_7_10)) {
				//first send join game that will make client actually switch to game state
				networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createFakeJoinGamePacket());
				//send disconnect with a little delay
				connection.getIOExecutor().schedule(() -> {
					try {
						disconnectNormal(message);
					} catch (Throwable t) {
						disconnectError(t);
					}
				}, 50, TimeUnit.MILLISECONDS);
			} else {
				disconnectNormal(message);
			}
		} catch (Throwable t) {
			disconnectError(t);
		}
	}

	protected void disconnectNormal(BaseComponent message) throws TimeoutException, InterruptedException {
		networkManager.sendPacket(
			ServerPlatform.get().getPacketFactory().createPlayDisconnectPacket(message),
			future -> networkManager.close(message), 5, TimeUnit.SECONDS,
			() -> networkManager.close(new TextComponent("Packet send timed out whilst disconnecting player, force closing connection"))
		);
	}

	protected void disconnectError(Throwable t) {
		Bukkit.getLogger().log(Level.SEVERE, "Error whilst disconnecting player", t);
		networkManager.close(new TextComponent("Error whilst disconnecting player, force closing connection"));
		MiscUtils.rethrowThreadException(t);
	}

	protected abstract JoinData createJoinData();

	protected abstract void checkBans(PlayerLoginEvent event, Object[] data);

	protected abstract void joinGame(Object[] data);

	protected abstract static class JoinData {

		public final Player player;
		public final Object[] data;

		protected JoinData(Player player, Object... data) {
			this.player = player;
			this.data = data;
		}

		protected abstract void close();

	}

}
