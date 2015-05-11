package protocolsupport.protocol.transformer;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.logging.Level;

import net.minecraft.server.v1_8_R2.MinecraftEncryption;
import net.minecraft.server.v1_8_R2.MinecraftServer;

import org.bukkit.craftbukkit.v1_8_R2.CraftServer;
import org.bukkit.craftbukkit.v1_8_R2.util.Waitable;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;

@SuppressWarnings("deprecation")
public class ThreadPlayerLookupUUID extends Thread {

	private boolean isOnlineMode;
	private final ILoginListener listener;

	public ThreadPlayerLookupUUID(ILoginListener loginlistener, String threadName, boolean isOnlineMode) {
		super(threadName);
		listener = loginlistener;
		this.isOnlineMode = isOnlineMode;
	}

	@Override
	public void run() {
		final GameProfile gameprofile = listener.getProfile();
		try {
			if (!isOnlineMode) {
				listener.initUUID();
				fireLoginEvents();
				return;
			}
			final String hash = new BigInteger(MinecraftEncryption.a("", MinecraftServer.getServer().P().getPublic(), listener.getLoginKey())).toString(16);
			listener.setProfile(MinecraftServer.getServer().aC().hasJoinedServer(new GameProfile(null, gameprofile.getName()), hash));
			if (listener.getProfile() != null) {
				fireLoginEvents();
			} else if (MinecraftServer.getServer().S()) {
				listener.getLogger().warn("Failed to verify username but will let them in anyway!");
				listener.setProfile(listener.generateOfflineProfile(gameprofile));
				listener.setLoginState(LoginState.READY_TO_ACCEPT);
			} else {
				listener.disconnect("Failed to verify username!");
				listener.getLogger().error("Username '" + gameprofile.getName() + "' tried to join with an invalid session");
			}
		} catch (AuthenticationUnavailableException authenticationunavailableexception) {
			if (MinecraftServer.getServer().S()) {
				listener.getLogger().warn("Authentication servers are down but will let them in anyway!");
				listener.setProfile(listener.generateOfflineProfile(gameprofile));
				listener.setLoginState(LoginState.READY_TO_ACCEPT);
			} else {
				listener.disconnect("Authentication servers are down. Please try again later, sorry!");
				listener.getLogger().error("Couldn't verify username because servers are unavailable");
			}
		} catch (Exception exception) {
			listener.disconnect("Failed to verify username!");
			MinecraftServer.getServer().server.getLogger().log(Level.WARNING, "Exception verifying " + gameprofile.getName(), exception);
		}
	}

	private void fireLoginEvents() throws Exception {
		if (!listener.getNetworkManager().g()) {
			return;
		}
		final String playerName = listener.getProfile().getName();
		final InetAddress address = ((InetSocketAddress) listener.getNetworkManager().getSocketAddress()).getAddress();
		final UUID uniqueId = listener.getProfile().getId();
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
		listener.setLoginState(LoginState.READY_TO_ACCEPT);
	}
}
