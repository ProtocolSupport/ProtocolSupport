package protocolsupport.protocol.transformer.handlers;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.util.Waitable;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import net.minecraft.server.v1_8_R3.MinecraftEncryption;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import protocolsupport.api.events.PlayerPropertiesResolveEvent;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;

@SuppressWarnings("deprecation")
public class PlayerLookupUUID {

	private final AbstractLoginListener listener;
	private final boolean isOnlineMode;

	public PlayerLookupUUID(AbstractLoginListener listener, boolean isOnlineMode) {
		this.listener = listener;
		this.isOnlineMode = isOnlineMode;
	}

	public void run() {
		final GameProfile gameprofile = listener.getProfile();
		try {
			if (!isOnlineMode) {
				listener.initUUID();
				fireLoginEvents();
				return;
			}
			final String hash = new BigInteger(MinecraftEncryption.a("", MinecraftServer.getServer().Q().getPublic(), listener.getLoginKey())).toString(16);
			listener.setProfile(MinecraftServer.getServer().aD().hasJoinedServer(new GameProfile(null, gameprofile.getName()), hash));
			if (listener.getProfile() != null) {
				fireLoginEvents();
			} else {
				listener.disconnect("Failed to verify username!");
				listener.getLogger().error("Username '" + gameprofile.getName() + "' tried to join with an invalid session");
			}
		} catch (AuthenticationUnavailableException authenticationunavailableexception) {
			listener.disconnect("Authentication servers are down. Please try again later, sorry!");
			listener.getLogger().error("Couldn't verify username because servers are unavailable");
		} catch (Exception exception) {
			listener.disconnect("Failed to verify username!");
			MinecraftServer.getServer().server.getLogger().log(Level.WARNING, "Exception verifying " + gameprofile.getName(), exception);
		}
	}

	private void fireLoginEvents() throws Exception {
		if (!listener.getNetworkManager().g()) {
			return;
		}

		String playerName = listener.getProfile().getName();
		InetSocketAddress saddress = (InetSocketAddress) listener.getNetworkManager().getSocketAddress();
		InetAddress address = saddress.getAddress();

		List<ProfileProperty> properties = new ArrayList<ProfileProperty>();
		PropertyMap propertymap = listener.getProfile().getProperties();
		for (Property property : propertymap.values()) {
			properties.add(new ProfileProperty(property.getName(), property.getValue(), property.getSignature()));
		}
		PlayerPropertiesResolveEvent propResolve = new PlayerPropertiesResolveEvent(saddress, playerName, properties);
		Bukkit.getPluginManager().callEvent(propResolve);
		propertymap.clear();
		for (ProfileProperty profileproperty : propResolve.getProperties().values()) {
			propertymap.put(profileproperty.getName(), new Property(profileproperty.getName(), profileproperty.getValue(), profileproperty.getSignature()));
		}

		UUID uniqueId = listener.getProfile().getId();
		final CraftServer server = MinecraftServer.getServer().server;
		final AsyncPlayerPreLoginEvent asyncEvent = new AsyncPlayerPreLoginEvent(playerName, address, uniqueId);
		server.getPluginManager().callEvent(asyncEvent);
		if (PlayerPreLoginEvent.getHandlerList().getRegisteredListeners().length != 0) {
			final PlayerPreLoginEvent event = new PlayerPreLoginEvent(playerName, address, uniqueId);
			if (asyncEvent.getResult() != PlayerPreLoginEvent.Result.ALLOWED) {
				event.disallow(asyncEvent.getResult(), asyncEvent.getKickMessage());
			}
			final Waitable<PlayerPreLoginEvent.Result> waitable = new Waitable<PlayerPreLoginEvent.Result>() {
				@Override
				protected PlayerPreLoginEvent.Result evaluate() {
					server.getPluginManager().callEvent(event);
					return event.getResult();
				}
			};
			MinecraftServer.getServer().processQueue.add(waitable);
			if (waitable.get() != PlayerPreLoginEvent.Result.ALLOWED) {
				listener.disconnect(event.getKickMessage());
				return;
			}
		} else if (asyncEvent.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) {
			listener.disconnect(asyncEvent.getKickMessage());
			return;
		}
		listener.getLogger().info("UUID of player " + listener.getProfile().getName() + " is " + listener.getProfile().getId());
		listener.setReadyToAccept();
	}

}
