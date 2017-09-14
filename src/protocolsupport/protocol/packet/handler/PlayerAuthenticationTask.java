package protocolsupport.protocol.packet.handler;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import protocolsupport.api.events.PlayerPropertiesResolveEvent;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.utils.MinecraftEncryption;
import protocolsupport.protocol.utils.authlib.MinecraftSessionService;
import protocolsupport.protocol.utils.authlib.MinecraftSessionService.AuthenticationUnavailableException;
import protocolsupport.zplatform.ServerPlatform;

@SuppressWarnings("deprecation")
public class PlayerAuthenticationTask {

	private final AbstractLoginListener listener;
	private final boolean isOnlineMode;

	public PlayerAuthenticationTask(AbstractLoginListener listener, boolean isOnlineMode) {
		this.listener = listener;
		this.isOnlineMode = isOnlineMode;
	}

	public void run() {
		String joinName = listener.profile.getName();
		try {
			if (!isOnlineMode) {
				listener.initOfflineModeGameProfile();
				fireLoginEvents();
				return;
			}
			String hash = new BigInteger(MinecraftEncryption.createHash(ServerPlatform.get().getMiscUtils().getEncryptionKeyPair().getPublic(), listener.loginKey)).toString(16);
			String ip = ServerPlatform.get().getMiscUtils().isProxyPreventionEnabled() ? listener.networkManager.getAddress().getHostString() : null;
			listener.profile = MinecraftSessionService.hasJoinedServer(joinName, hash, ip);
			fireLoginEvents();
		} catch (AuthenticationUnavailableException authenticationunavailableexception) {
			listener.disconnect("Authentication servers are down. Please try again later, sorry!");
			Bukkit.getLogger().severe("Couldn't verify username because servers are unavailable");
		} catch (Exception exception) {
			listener.disconnect("Failed to verify username!");
			Bukkit.getLogger().log(Level.SEVERE, "Exception verifying " + joinName, exception);
		}
	}

	private void fireLoginEvents() throws InterruptedException, ExecutionException  {
		if (!listener.networkManager.isConnected()) {
			return;
		}

		String playerName = listener.profile.getName();
		InetSocketAddress saddress = listener.networkManager.getAddress();

		InetAddress address = saddress.getAddress();

		PlayerPropertiesResolveEvent propResolve = new PlayerPropertiesResolveEvent(
			ConnectionImpl.getFromChannel(listener.networkManager.getChannel()),
			playerName, listener.profile.getProperties().values()
		);
		Bukkit.getPluginManager().callEvent(propResolve);
		listener.profile.clearProperties();
		for (ProfileProperty property : propResolve.getProperties().values()) {
			listener.profile.addProperty(property);
		}
		UUID uniqueId = listener.profile.getUUID();

		AsyncPlayerPreLoginEvent asyncEvent = new AsyncPlayerPreLoginEvent(playerName, address, uniqueId);
		Bukkit.getPluginManager().callEvent(asyncEvent);

		PlayerPreLoginEvent syncEvent = new PlayerPreLoginEvent(playerName, address, uniqueId);
		if (asyncEvent.getResult() != PlayerPreLoginEvent.Result.ALLOWED) {
			syncEvent.disallow(asyncEvent.getResult(), asyncEvent.getKickMessage());
		}

		if (PlayerPreLoginEvent.getHandlerList().getRegisteredListeners().length != 0) {
			if (ServerPlatform.get().getMiscUtils().callSyncTask(() -> {
				Bukkit.getPluginManager().callEvent(syncEvent);
				return syncEvent.getResult();
			}).get() != PlayerPreLoginEvent.Result.ALLOWED) {
				listener.disconnect(syncEvent.getKickMessage());
				return;
			}
		}

		if (syncEvent.getResult() != PlayerPreLoginEvent.Result.ALLOWED) {
			listener.disconnect(syncEvent.getKickMessage());
			return;
		}

		Bukkit.getLogger().info("UUID of player " + listener.profile.getName() + " is " + listener.profile.getUUID());
		listener.setReadyToAccept();
	}

}
