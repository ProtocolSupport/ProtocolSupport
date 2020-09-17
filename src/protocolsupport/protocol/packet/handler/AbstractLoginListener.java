package protocolsupport.protocol.packet.handler;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.PrivateKey;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.crypto.SecretKey;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import io.netty.channel.ChannelPipeline;
import protocolsupport.ProtocolSupport;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.components.TextComponent;
import protocolsupport.api.events.PlayerLoginStartEvent;
import protocolsupport.api.events.PlayerProfileCompleteEvent;
import protocolsupport.api.utils.Profile;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.utils.MinecraftEncryption;
import protocolsupport.protocol.utils.authlib.LoginProfile;
import protocolsupport.protocol.utils.authlib.MinecraftSessionService;
import protocolsupport.protocol.utils.authlib.MinecraftSessionService.AuthenticationUnavailableException;
import protocolsupport.utils.JavaSystemProperty;
import protocolsupport.zplatform.ServerPlatform;
import protocolsupport.zplatform.network.NetworkManagerWrapper;
import protocolsupportbuildprocessor.Preload;

@SuppressWarnings("deprecation")
@Preload
public abstract class AbstractLoginListener implements IPacketListener {

	protected static final int loginThreadKeepAlive = JavaSystemProperty.getValue("loginthreadskeepalive", 60, Integer::parseInt);

	static {
		ProtocolSupport.logInfo(MessageFormat.format("Login threads keep alive time: {0}", loginThreadKeepAlive));
	}

	protected static final Executor loginprocessor = new ThreadPoolExecutor(
		1, Integer.MAX_VALUE,
		loginThreadKeepAlive, TimeUnit.SECONDS,
		new LinkedBlockingQueue<>(),
		r -> new Thread(r, "LoginProcessingThread")
	);

	protected final NetworkManagerWrapper networkManager;
	protected final ConnectionImpl connection;
	protected final byte[] randomBytes = new byte[4];
	protected LoginState state = LoginState.HELLO;

	protected final Object timeoutTaskLock = new Object();
	protected ScheduledFuture<?> timeoutTask;

	public AbstractLoginListener(NetworkManagerWrapper networkmanager) {
		this.networkManager = networkmanager;
		this.connection = ConnectionImpl.getFromChannel(networkmanager.getChannel());
		ThreadLocalRandom.current().nextBytes(randomBytes);

		synchronized (timeoutTaskLock) {
			this.timeoutTask = connection.getIOExecutor().schedule(() -> disconnect(new TextComponent("Took too long to log in")), 30, TimeUnit.SECONDS);
		}
	}

	@Override
	public void destroy() {
		cancelTimeoutTask();
	}

	protected void cancelTimeoutTask() {
		synchronized (timeoutTaskLock) {
			if (timeoutTask != null) {
				timeoutTask.cancel(false);
				timeoutTask = null;
			}
		}
	}

	@Override
	public void disconnect(BaseComponent message) {
		try {
			Bukkit.getLogger().info("Disconnecting " + getConnectionRepr() + ": " + message.toLegacyText());
			networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createLoginDisconnectPacket(message), future -> networkManager.close(message));
		} catch (Throwable exception) {
			Bukkit.getLogger().log(Level.SEVERE, "Error whilst disconnecting player", exception);
			networkManager.close(new TextComponent("Error whilst disconnecting player, force closing connection"));
		}
	}

	protected String getConnectionRepr() {
		return (connection.getProfile().getName() != null) ? (connection.getProfile() + " (" + networkManager.getAddress() + ")") : networkManager.getAddress().toString();
	}

	public void handleLoginStart(String name) {
		Validate.isTrue(state == LoginState.HELLO, "Unexpected hello packet");
		state = LoginState.ONLINEMODERESOLVE;
		loginprocessor.execute(() -> {
			try {
				LoginProfile profile = connection.getLoginProfile();
				profile.setOriginalName(name);

				PlayerLoginStartEvent event = new PlayerLoginStartEvent(connection);
				Bukkit.getPluginManager().callEvent(event);
				if (event.isLoginDenied()) {
					AbstractLoginListener.this.disconnect(event.getDenyLoginMessageJson());
					return;
				}

				profile.setOnlineMode(event.isOnlineMode());

				if (profile.isOnlineMode()) {
					state = LoginState.KEY;
					networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createLoginEncryptionBeginPacket(ServerPlatform.get().getMiscUtils().getEncryptionKeyPair().getPublic(), randomBytes));
				} else {
					loginOffline();
				}
			} catch (Throwable t) {
				AbstractLoginListener.this.disconnect(new TextComponent("Error occured while logging in"));
				if (ServerPlatform.get().getMiscUtils().isDebugging()) {
					t.printStackTrace();
				}
			}
		});
	}

	public static interface EncryptionPacketWrapper {

		public byte[] getNonce(PrivateKey key);

		public SecretKey getSecretKey(PrivateKey key);

	}

	public void handleEncryption(EncryptionPacketWrapper encryptionpakcet) {
		Validate.isTrue(state == LoginState.KEY, "Unexpected key packet");
		state = LoginState.AUTHENTICATING;
		loginprocessor.execute(() -> {
			try {
				final PrivateKey privatekey = ServerPlatform.get().getMiscUtils().getEncryptionKeyPair().getPrivate();
				if (!Arrays.equals(randomBytes, encryptionpakcet.getNonce(privatekey))) {
					throw new IllegalStateException("Invalid nonce!");
				}
				SecretKey loginKey = encryptionpakcet.getSecretKey(privatekey);
				enableEncryption(loginKey);
				loginOnline(loginKey);
			} catch (Throwable t) {
				AbstractLoginListener.this.disconnect(new TextComponent("Error occured while logging in"));
				if (ServerPlatform.get().getMiscUtils().isDebugging()) {
					t.printStackTrace();
				}
			}
		});
	}

	protected void enableEncryption(SecretKey key) {
		ChannelPipeline pipeline = networkManager.getChannel().pipeline();
		ServerPlatform.get().getMiscUtils().enableEncryption(pipeline, key, isFullEncryption(connection.getVersion()));
	}

	public void loginOffline() {
		try {
			LoginProfile profile = connection.getLoginProfile();
			profile.setOriginalUUID(networkManager.getSpoofedUUID() != null ? networkManager.getSpoofedUUID() : Profile.generateOfflineModeUUID(profile.getName()));
			networkManager.getSpoofedProperties().forEach(profile::addProperty);
			finishLogin();
		} catch (Exception exception) {
			disconnect(new TextComponent("Failed to verify username!"));
			Bukkit.getLogger().log(Level.SEVERE, "Exception verifying " + connection.getProfile().getOriginalName(), exception);
		}
	}

	public void loginOnline(SecretKey key) {
		try {
			MinecraftSessionService.checkHasJoinedServerAndUpdateProfile(
				connection.getLoginProfile(),
				new BigInteger(MinecraftEncryption.createHash(ServerPlatform.get().getMiscUtils().getEncryptionKeyPair().getPublic(), key)).toString(16),
				ServerPlatform.get().getMiscUtils().isProxyPreventionEnabled() ? networkManager.getAddress().getHostString() : null
			);
			finishLogin();
		} catch (AuthenticationUnavailableException authenticationunavailableexception) {
			disconnect(new TextComponent("Authentication servers are down. Please try again later, sorry!"));
			Bukkit.getLogger().severe("Couldn't verify username because servers are unavailable");
		} catch (Exception exception) {
			disconnect(new TextComponent("Failed to verify username!"));
			Bukkit.getLogger().log(Level.SEVERE, "Exception verifying " + connection.getProfile().getOriginalName(), exception);
		}
	}

	protected void finishLogin() throws InterruptedException, ExecutionException  {
		if (!networkManager.isConnected()) {
			return;
		}

		cancelTimeoutTask();

		LoginProfile profile = connection.getLoginProfile();
		InetSocketAddress saddress = networkManager.getAddress();

		InetAddress address = saddress.getAddress();

		PlayerProfileCompleteEvent event = new PlayerProfileCompleteEvent(connection);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isLoginDenied()) {
			disconnect(event.getDenyLoginMessageJson());
			return;
		}
		if (event.getForcedName() != null) {
			profile.setName(event.getForcedName());
		}
		if (event.getForcedUUID() != null) {
			profile.setUUID(event.getForcedUUID());
		}
		event.getProperties().values().forEach(c -> c.forEach(profile::addProperty));

		AsyncPlayerPreLoginEvent asyncEvent = new AsyncPlayerPreLoginEvent(profile.getName(), address, profile.getUUID());
		Bukkit.getPluginManager().callEvent(asyncEvent);

		PlayerPreLoginEvent syncEvent = new PlayerPreLoginEvent(profile.getName(), address, profile.getUUID());
		if (asyncEvent.getResult() != PlayerPreLoginEvent.Result.ALLOWED) {
			syncEvent.disallow(asyncEvent.getResult(), asyncEvent.getKickMessage());
		}
		if (PlayerPreLoginEvent.getHandlerList().getRegisteredListeners().length != 0) {
			ServerPlatform.get().getMiscUtils().callSyncTask(() -> {
				Bukkit.getPluginManager().callEvent(syncEvent);
				return null;
			}).get();
		}

		if (syncEvent.getResult() != PlayerPreLoginEvent.Result.ALLOWED) {
			disconnect(BaseComponent.fromMessage(syncEvent.getKickMessage()));
			return;
		}

		Bukkit.getLogger().info("UUID of player " + connection.getProfile().getName() + " is " + connection.getProfile().getUUID());

		if (hasCompression(connection.getVersion())) {
			int threshold = ServerPlatform.get().getMiscUtils().getCompressionThreshold();
			if (threshold >= 0) {
				CountDownLatch waitpacketsend = new CountDownLatch(1);
				connection.submitIOTask(() -> networkManager.sendPacket(
					ServerPlatform.get().getPacketFactory().createSetCompressionPacket(threshold),
					future -> {
						ServerPlatform.get().getMiscUtils().enableCompression(networkManager.getChannel().pipeline(), threshold);
						waitpacketsend.countDown();
					}
				));
				try {
					if (!waitpacketsend.await(5, TimeUnit.SECONDS)) {
						disconnect(new TextComponent("Timeout while waiting for login success send"));
						return;
					}
				} catch (InterruptedException e) {
					disconnect(new TextComponent("Interrupt while waiting for login success send"));
					return;
				}
			}
		}

		AbstractLoginListenerPlay listener = getLoginListenerPlay();
		networkManager.setPacketListener(listener);
		listener.finishLogin();
	}

	protected abstract AbstractLoginListenerPlay getLoginListenerPlay();

	public enum LoginState {
		HELLO, ONLINEMODERESOLVE, KEY, AUTHENTICATING;
	}

	protected static boolean isFullEncryption(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_7_5);
	}

	protected static boolean hasCompression(ProtocolVersion version) {
		return (version.getProtocolType() == ProtocolType.PC) && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_8);
	}

}
