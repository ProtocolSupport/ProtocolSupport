package protocolsupport.protocol.packet.handler;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.PlayerLoginFinishEvent;
import protocolsupport.api.events.PlayerSyncLoginEvent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.common.SimpleReadTimeoutHandler;
import protocolsupport.protocol.utils.authlib.GameProfile;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkManagerWrapper;
import protocolsupport.zplatform.network.NetworkState;

public abstract class AbstractLoginListenerPlay implements IHasProfile {

	protected final NetworkManagerWrapper networkManager;
	protected final GameProfile profile;
	protected final boolean onlineMode;
	protected final String hostname;

	protected AbstractLoginListenerPlay(NetworkManagerWrapper networkmanager, GameProfile profile, boolean onlineMode, String hostname) {
		this.networkManager = networkmanager;
		this.profile = profile;
		this.onlineMode = onlineMode;
		this.hostname = hostname;
	}

	@Override
	public GameProfile getProfile() {
		return profile;
	}

	@SuppressWarnings("unchecked")
	public void finishLogin() {
		if (!networkManager.isConnected()) {
			return;
		}

		//send login success and wait for finish
		CountDownLatch waitpacketsend = new CountDownLatch(1);
		networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createLoginSuccessPacket(profile), new GenericFutureListener<Future<? super Void>>() {
			@Override
			public void operationComplete(Future<? super Void> p0) throws Exception {
				waitpacketsend.countDown();
			}
		});
		try {
			if (!waitpacketsend.await(5, TimeUnit.SECONDS)) {
				disconnect("Timeout while waiting for login success send");
				return;
			}
		} catch (InterruptedException e) {
			disconnect("Exception while waiting for login success send");
			return;
		}
		//set network state to game
		networkManager.setProtocol(NetworkState.PLAY);
		//tick connection keep now
		keepConnection();
		//now fire login event
		PlayerLoginFinishEvent event = new PlayerLoginFinishEvent(ConnectionImpl.getFromChannel(networkManager.getChannel()), profile.getName(), profile.getUUID(), onlineMode);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isLoginDenied()) {
			disconnect(event.getDenyLoginMessage());
			return;
		}
		ready = true;
	}

	protected boolean ready;

	protected int keepAliveTicks = 1;

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

	private void tryJoin() {
		//no longer attempt to join
		ready = false;

		//kick players with same uuid
		Bukkit.getOnlinePlayers().stream()
		.filter(player -> player.getUniqueId().equals(profile.getUUID()))
		.forEach(player -> player.kickPlayer("You logged in from another location"));

		//get player
		JoinData joindata = createJoinData();

		//ps sync login event
		PlayerSyncLoginEvent syncloginevent = new PlayerSyncLoginEvent(ConnectionImpl.getFromChannel(networkManager.getChannel()), joindata.player);
		Bukkit.getPluginManager().callEvent(syncloginevent);
		if (syncloginevent.isLoginDenied()) {
			disconnect(syncloginevent.getDenyLoginMessage());
			joindata.close();
			return;
		}

		//bukkit sync login event
		PlayerLoginEvent bukkitevent = new PlayerLoginEvent(joindata.player, hostname, networkManager.getAddress().getAddress(), networkManager.getRawAddress().getAddress());
		checkBans(bukkitevent, joindata.data);
		Bukkit.getPluginManager().callEvent(bukkitevent);
		if (bukkitevent.getResult() != PlayerLoginEvent.Result.ALLOWED) {
			disconnect(bukkitevent.getKickMessage());
			joindata.close();
			return;
		}

		//send packet to notify about actual login phase finished
		networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createEmptyCustomPayloadPacket("PS|FinishLogin"));
		//add player to game
		joinGame(joindata.data);
	}

	protected void keepConnection() {
		//custom payload does nothing on a client when sent with invalid tag,
		//but it resets client readtimeouthandler, and that is exactly what we need
		networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createEmptyCustomPayloadPacket("PS|KeepAlive"));
		//we also need to reset server readtimeouthandler (may be null if netty already teared down the pipeline)
		SimpleReadTimeoutHandler timeouthandler = ChannelHandlers.getTimeoutHandler(networkManager.getChannel().pipeline());
		if (timeouthandler != null) {
			timeouthandler.setLastRead();
		}
	}

	protected String getConnectionRepr() {
		return (profile != null) ? (profile + " (" + networkManager.getAddress() + ")") : networkManager.getAddress().toString();
	}

	public void disconnect(final String s) {
		try {
			Bukkit.getLogger().info("Disconnecting " + getConnectionRepr() + ": " + s);
			if (ConnectionImpl.getFromChannel(networkManager.getChannel()).getVersion().isBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_7_10)) {
				//first send join game that will make client actually switch to game state
				networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createFakeJoinGamePacket());
				//send disconnect with a little delay
				networkManager.getChannel().eventLoop().schedule(() -> disconnect0(s), 50, TimeUnit.MILLISECONDS);
			} else {
				disconnect0(s);
			}
		} catch (Exception exception) {
			Bukkit.getLogger().log(Level.SEVERE, "Error whilst disconnecting player", exception);
		}
	}

	@SuppressWarnings("unchecked")
	protected void disconnect0(String s) {
		networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createPlayDisconnectPacket(s), new GenericFutureListener<Future<? super Void>>() {
			@Override
			public void operationComplete(Future<? super Void> future) {
				networkManager.close(s);
			}
		});
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
