package protocolsupport.protocol.v_1_5;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.logging.Level;

import net.minecraft.server.v1_8_R1.MinecraftEncryption;
import net.minecraft.server.v1_8_R1.MinecraftServer;

import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.craftbukkit.v1_8_R1.util.Waitable;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import protocolsupport.protocol.v_1_5.LoginListener.LoginState;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;

@SuppressWarnings("deprecation")
public class ThreadPlayerLookupUUID extends Thread {

	private final LoginListener listener;

	protected ThreadPlayerLookupUUID(final LoginListener loginlistener, final String threadName) {
		super(threadName);
		listener = loginlistener;
	}

	@Override
	public void run() {
		final GameProfile gameprofile = listener.profile;
		try {
			if (!MinecraftServer.getServer().getOnlineMode()) {
				listener.initUUID();
				fireLoginEvents();
				return;
			}
			final String hash = new BigInteger(MinecraftEncryption.a(listener.serverId, MinecraftServer.getServer().P().getPublic(), listener.loginKey)).toString(16);
			listener.profile = MinecraftServer.getServer().aB().hasJoinedServer(new GameProfile(null, gameprofile.getName()), hash);
			if (listener.profile != null) {
				fireLoginEvents();
			} else if (MinecraftServer.getServer().S()) {
				LoginListener.logger.warn("Failed to verify username but will let them in anyway!");
				listener.profile = listener.a(gameprofile);
				listener.state = LoginState.READY_TO_ACCEPT;
			} else {
				listener.disconnect("Failed to verify username!");
				LoginListener.logger.error("Username '" + listener.profile.getName() + "' tried to join with an invalid session");
			}
		} catch (AuthenticationUnavailableException authenticationunavailableexception) {
			if (MinecraftServer.getServer().S()) {
				LoginListener.logger.warn("Authentication servers are down but will let them in anyway!");
				listener.profile = listener.a(gameprofile);
				listener.state = LoginState.READY_TO_ACCEPT;
			} else {
				listener.disconnect("Authentication servers are down. Please try again later, sorry!");
				LoginListener.logger.error("Couldn't verify username because servers are unavailable");
			}
		} catch (Exception exception) {
			listener.disconnect("Failed to verify username!");
			MinecraftServer.getServer().server.getLogger().log(Level.WARNING, "Exception verifying " + listener.profile.getName(), exception);
		}
	}

	private void fireLoginEvents() throws Exception {
		if (!listener.networkManager.g()) {
			return;
		}
		final String playerName = listener.profile.getName();
		final InetAddress address = ((InetSocketAddress) listener.networkManager.getSocketAddress()).getAddress();
		final UUID uniqueId = listener.profile.getId();
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
		LoginListener.logger.info("UUID of player " + listener.profile.getName() + " is " + listener.profile.getId());
		listener.state = LoginState.READY_TO_ACCEPT;
	}
}
