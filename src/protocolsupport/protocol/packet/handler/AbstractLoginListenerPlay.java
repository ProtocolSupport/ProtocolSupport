package protocolsupport.protocol.packet.handler;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.PlayerLoginFinishEvent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.utils.authlib.GameProfile;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

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

	public void finishLogin() {
		// send login success
		networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createLoginSuccessPacket(profile));
		// tick connection keep now
		keepConnection();
		// now fire login event
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
		if ((keepAliveTicks++ % 80) == 0) {
			keepConnection();
		}
		if (ready) {
			tryJoin();
		}
	}

	private void tryJoin() {
		//find players with same uuid
		List<Player> toKick = Bukkit.getOnlinePlayers().stream().filter(player -> player.getUniqueId().equals(profile.getUUID())).collect(Collectors.toList());
		//kick them
		if (!toKick.isEmpty()) {
			toKick.forEach(player -> player.kickPlayer("You logged in from another location"));
			return;
		}
		//attempt login (fire login events and check bans)
		Object loginplayer = attemptLogin();
		if (loginplayer != null) {
			//no longer attempt to join
			ready = false;
			//send packet to notify about actual login phase finished
			networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createEmptyCustomPayloadPacket("PS|FinishLogin"));
			//add player to game
			joinGame(loginplayer);
		}
	}

	private void keepConnection() {
		// custom payload does nothing on a client when sent with invalid tag,
		// but it resets client readtimeouthandler, and that is exactly what we need
		networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createEmptyCustomPayloadPacket("PS|KeepAlive"));
		// we also need to reset server readtimeouthandler
		ChannelHandlers.getTimeoutHandler(networkManager.getChannel().pipeline()).setLastRead();
	}

	protected String getConnectionRepr() {
		return (profile != null) ? (profile + " (" + networkManager.getAddress() + ")") : networkManager.getAddress().toString();
	}

	public void disconnect(final String s) {
		try {
			Bukkit.getLogger().info("Disconnecting " + getConnectionRepr() + ": " + s);
			if (ConnectionImpl.getFromChannel(networkManager.getChannel()).getVersion().isBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_7_10)) {
				// first send join game that will make client actually switch to game state
				networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createFakeJoinGamePacket());
				// send disconnect with a little delay
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

	protected abstract Object attemptLogin();

	protected abstract void joinGame(Object player);

}
