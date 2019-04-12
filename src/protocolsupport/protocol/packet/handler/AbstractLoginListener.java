package protocolsupport.protocol.packet.handler;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.PrivateKey;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
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
import protocolsupport.api.events.PlayerLoginStartEvent;
import protocolsupport.api.events.PlayerProfileCompleteEvent;
import protocolsupport.api.utils.Profile;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middleimpl.serverbound.handshake.v_pe.ClientLogin;
import protocolsupport.protocol.utils.MinecraftEncryption;
import protocolsupport.protocol.utils.authlib.GameProfile;
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
		new LinkedBlockingQueue<Runnable>(),
		r -> new Thread(r, "LoginProcessingThread")
	);

	protected final NetworkManagerWrapper networkManager;
	protected final ConnectionImpl connection;
	protected final String hostname;
	protected final byte[] randomBytes = new byte[4];
	protected LoginState state = LoginState.HELLO;

	public AbstractLoginListener(NetworkManagerWrapper networkmanager, String hostname) {
		this.networkManager = networkmanager;
		this.connection = ConnectionImpl.getFromChannel(networkmanager.getChannel());
		this.hostname = hostname;
		ThreadLocalRandom.current().nextBytes(randomBytes);
	}

	protected int loginTicks;
	public void loginTick() {
		if (loginTicks++ == 600) {
			disconnect("Took too long to log in");
		}
	}

	@Override
	public void disconnect(String s) {
		try {
			Bukkit.getLogger().info("Disconnecting " + getConnectionRepr() + ": " + s);
			networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createLoginDisconnectPacket(s), future -> networkManager.close(s));
		} catch (Throwable exception) {
			Bukkit.getLogger().log(Level.SEVERE, "Error whilst disconnecting player", exception);
			networkManager.close("Error whilst disconnecting player, force closing connection");
		}
	}

	protected String getConnectionRepr() {
		return (connection.getProfile().getName() != null) ? (connection.getProfile() + " (" + networkManager.getAddress() + ")") : networkManager.getAddress().toString();
	}

	protected UUID forcedUUID = null;
	public void handleLoginStart(String name) {
		Validate.isTrue(state == LoginState.HELLO, "Unexpected hello packet");
		state = LoginState.ONLINEMODERESOLVE;
		loginprocessor.execute(() -> {
			try {
				GameProfile profile = connection.getProfile();
				profile.setOriginalName(name);

				PlayerLoginStartEvent event = new PlayerLoginStartEvent(connection, hostname);
				Bukkit.getPluginManager().callEvent(event);
				if (event.isLoginDenied()) {
					AbstractLoginListener.this.disconnect(event.getDenyLoginMessage());
					return;
				}

				profile.setOnlineMode(event.isOnlineMode());

				forcedUUID = event.getForcedUUID();
				if ((forcedUUID == null) && profile.isOnlineMode() && !event.useOnlineModeUUID()) {
					forcedUUID = Profile.generateOfflineModeUUID(profile.getName());
				}
				if (profile.isOnlineMode()) {
					switch (connection.getVersion().getProtocolType()) {
						case PC: {
							state = LoginState.KEY;
							networkManager.sendPacket(ServerPlatform.get().getPacketFactory().createLoginEncryptionBeginPacket(ServerPlatform.get().getMiscUtils().getEncryptionKeyPair().getPublic(), randomBytes));
							break;
						}
						case PE: {
							loginOnlinePE();
							break;
						}
						default: {
							throw new IllegalArgumentException(MessageFormat.format("Unknown protocol type {0}", connection.getVersion().getProtocolType()));
						}
					}
				} else {
					loginOffline();
				}
			} catch (Throwable t) {
				AbstractLoginListener.this.disconnect("Error occured while logging in");
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
				AbstractLoginListener.this.disconnect("Error occured while logging in");
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
			GameProfile profile = connection.getProfile();
			profile.setOriginalUUID(networkManager.getSpoofedUUID() != null ? networkManager.getSpoofedUUID() : Profile.generateOfflineModeUUID(profile.getName()));
			networkManager.getSpoofedProperties().forEach(profile::addProperty);
			finishLogin();
		} catch (Exception exception) {
			disconnect("Failed to verify username!");
			Bukkit.getLogger().log(Level.SEVERE, "Exception verifying " + connection.getProfile().getOriginalName(), exception);
		}
	}

	public void loginOnline(SecretKey key) {
		try {
			MinecraftSessionService.checkHasJoinedServerAndUpdateProfile(
				connection.getProfile(),
				new BigInteger(MinecraftEncryption.createHash(ServerPlatform.get().getMiscUtils().getEncryptionKeyPair().getPublic(), key)).toString(16),
				ServerPlatform.get().getMiscUtils().isProxyPreventionEnabled() ? networkManager.getAddress().getHostString() : null
			);
			finishLogin();
		} catch (AuthenticationUnavailableException authenticationunavailableexception) {
			disconnect("Authentication servers are down. Please try again later, sorry!");
			Bukkit.getLogger().severe("Couldn't verify username because servers are unavailable");
		} catch (Exception exception) {
			disconnect("Failed to verify username!");
			Bukkit.getLogger().log(Level.SEVERE, "Exception verifying " + connection.getProfile().getOriginalName(), exception);
		}
	}

	public void loginOnlinePE() {
		try {
			String xuid = (String) connection.getMetadata(ClientLogin.XUID_METADATA_KEY);
			if (xuid == null) {
				disconnect("This server is in online mode, but no valid XUID was found (XBOX live auth required)");
				return;
			}
			connection.getProfile().setOriginalUUID(new UUID(0, Long.parseLong(xuid)));
			finishLogin();
		} catch (Exception exception) {
			disconnect("Failed to verify username!");
			Bukkit.getLogger().log(Level.SEVERE, "Exception verifying " + connection.getProfile().getOriginalName(), exception);
		}
	}

	protected void finishLogin() throws InterruptedException, ExecutionException  {
		if (!networkManager.isConnected()) {
			return;
		}

		GameProfile profile = connection.getProfile();
		InetSocketAddress saddress = networkManager.getAddress();

		InetAddress address = saddress.getAddress();

		//profile complete event
		PlayerProfileCompleteEvent event = new PlayerProfileCompleteEvent(connection);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isLoginDenied()) {
			disconnect(event.getDenyLoginMessage());
			return;
		}
		if (event.getForcedName() != null) {
			profile.setName(event.getForcedName());
		}
		if (event.getForcedUUID() != null) {
			profile.setUUID(event.getForcedUUID());
		}
		event.getProperties().values().forEach(c -> c.forEach(profile::addProperty));

		//bukkit async prelogin event, no uuid and name modifications sohuld be done after it, so this event must be always fired after profile complete
		AsyncPlayerPreLoginEvent asyncEvent = new AsyncPlayerPreLoginEvent(profile.getName(), address, profile.getUUID());
		Bukkit.getPluginManager().callEvent(asyncEvent);

		//legacy bukkit sync prelogin event, fire only if has listeners to avoid waiting for main thread
		//the kick result is inherited from async pre login event
		PlayerPreLoginEvent syncEvent = new PlayerPreLoginEvent(profile.getName(), address, profile.getUUID());
		if (asyncEvent.getResult() != PlayerPreLoginEvent.Result.ALLOWED) {
			syncEvent.disallow(asyncEvent.getResult(), asyncEvent.getKickMessage());
		}
		if (PlayerPreLoginEvent.getHandlerList().getRegisteredListeners().length != 0) {
			if (ServerPlatform.get().getMiscUtils().callSyncTask(() -> {
				Bukkit.getPluginManager().callEvent(syncEvent);
				return syncEvent.getResult();
			}).get() != PlayerPreLoginEvent.Result.ALLOWED) {
				disconnect(syncEvent.getKickMessage());
				return;
			}
		}

		//kick if not allowed
		if (syncEvent.getResult() != PlayerPreLoginEvent.Result.ALLOWED) {
			disconnect(syncEvent.getKickMessage());
			return;
		}

		Bukkit.getLogger().info("UUID of player " + connection.getProfile().getName() + " is " + connection.getProfile().getUUID());

		//enable compression if version does support it
		if (hasCompression(connection.getVersion())) {
			int threshold = ServerPlatform.get().getMiscUtils().getCompressionThreshold();
			if (threshold >= 0) {
				networkManager.sendPacket(
					ServerPlatform.get().getPacketFactory().createSetCompressionPacket(threshold),
					future -> ServerPlatform.get().getMiscUtils().enableCompression(networkManager.getChannel().pipeline(), threshold)
				);
			}
		}

		//switch to login listener play which allows infinitely waiting on login screen if needed
		AbstractLoginListenerPlay listener = getLoginListenerPlay();
		networkManager.setPacketListener(listener);
		listener.finishLogin();
	}

	protected abstract AbstractLoginListenerPlay getLoginListenerPlay();

	public enum LoginState {
		HELLO, ONLINEMODERESOLVE, KEY, AUTHENTICATING;
	}

	protected static boolean isFullEncryption(ProtocolVersion version) {
		return version.getProtocolType() == ProtocolType.PC && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_7_5);
	}

	protected static boolean hasCompression(ProtocolVersion version) {
		return version.getProtocolType() == ProtocolType.PC && version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_8);
	}

}
