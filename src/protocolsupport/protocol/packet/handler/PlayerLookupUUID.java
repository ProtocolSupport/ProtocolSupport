package protocolsupport.protocol.packet.handler;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import protocolsupport.api.events.PlayerPropertiesResolveEvent;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.utils.MinecraftEncryption;
import protocolsupport.zplatform.server.MinecraftServerWrapper;

@SuppressWarnings("deprecation")
public class PlayerLookupUUID {

	private final AbstractLoginListener listener;
	private final boolean isOnlineMode;

	public PlayerLookupUUID(AbstractLoginListener listener, boolean isOnlineMode) {
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
			String hash = new BigInteger(MinecraftEncryption.createHash(MinecraftServerWrapper.getEncryptionKeyPair().getPublic(), listener.loginKey)).toString(16);
			listener.profile = MinecraftServerWrapper.getSessionService().hasJoinedServer(new GameProfile(null, joinName), hash, null);
			if (listener.profile != null) {
				fireLoginEvents();
			} else {
				listener.disconnect("Failed to verify username!");
				listener.getLogger().error("Username '" + joinName + "' tried to join with an invalid session");
			}
		} catch (AuthenticationUnavailableException authenticationunavailableexception) {
			listener.disconnect("Authentication servers are down. Please try again later, sorry!");
			listener.getLogger().error("Couldn't verify username because servers are unavailable");
		} catch (Exception exception) {
			listener.disconnect("Failed to verify username!");
			listener.getLogger().error("Exception verifying " + joinName, exception);
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
			ConnectionImpl.getFromChannel(listener.networkManager.getChannel()), playerName,
			listener.profile.getProperties().values().stream()
			.map(property -> new ProfileProperty(property.getName(), property.getValue(), property.getSignature()))
			.collect(Collectors.toList())
		);
		Bukkit.getPluginManager().callEvent(propResolve);
		PropertyMap propertymap = listener.profile.getProperties();
		propertymap.clear();
		for (ProfileProperty profileproperty : propResolve.getProperties().values()) {
			propertymap.put(profileproperty.getName(), new Property(profileproperty.getName(), profileproperty.getValue(), profileproperty.getSignature()));
		}

		UUID uniqueId = listener.profile.getId();

		AsyncPlayerPreLoginEvent asyncEvent = new AsyncPlayerPreLoginEvent(playerName, address, uniqueId);
		Bukkit.getPluginManager().callEvent(asyncEvent);

		PlayerPreLoginEvent syncEvent = new PlayerPreLoginEvent(playerName, address, uniqueId);
		if (asyncEvent.getResult() != PlayerPreLoginEvent.Result.ALLOWED) {
			syncEvent.disallow(asyncEvent.getResult(), asyncEvent.getKickMessage());
		}

		if (PlayerPreLoginEvent.getHandlerList().getRegisteredListeners().length != 0) {
			if (MinecraftServerWrapper.callSyncTask(() -> {
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

		listener.getLogger().info("UUID of player " + listener.profile.getName() + " is " + listener.profile.getId());
		listener.setReadyToAccept();
	}

}
