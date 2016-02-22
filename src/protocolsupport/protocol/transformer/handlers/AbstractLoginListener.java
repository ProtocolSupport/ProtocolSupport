package protocolsupport.protocol.transformer.handlers;

import java.net.InetSocketAddress;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_8_R3.PacketLoginInStart;
import net.minecraft.server.v1_8_R3.PacketLoginOutDisconnect;
import net.minecraft.server.v1_8_R3.PacketLoginOutEncryptionBegin;
import net.minecraft.server.v1_8_R3.PacketLoginOutSetCompression;
import net.minecraft.server.v1_8_R3.PacketLoginOutSuccess;
import protocolsupport.ProtocolSupport;
import protocolsupport.api.events.PlayerLoginStartEvent;
import protocolsupport.utils.Utils;
import protocolsupport.utils.Utils.Converter;

public abstract class AbstractLoginListener extends net.minecraft.server.v1_8_R3.LoginListener {

	private static final int loginThreads = Utils.getJavaPropertyValue("protocolsupport.loginthreads", 8, Converter.STRING_TO_INT);
	private static final int loginThreadKeepAlive = Utils.getJavaPropertyValue("protocolsupport.loginthreadskeepalive", 60, Converter.STRING_TO_INT);

	public static void init() {
		ProtocolSupport.logInfo("Login threads max count: "+loginThreads+", keep alive time: "+loginThreadKeepAlive);
	}

	private static final Executor loginprocessor = new ThreadPoolExecutor(
		1, loginThreads,
		loginThreadKeepAlive, TimeUnit.SECONDS,
		new LinkedBlockingQueue<Runnable>(),
		new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				thread.setName("LoginProcessingThread");
				return thread;
			}
		}
	);

	protected static final Logger logger = LogManager.getLogger();
	protected static final Random random = new Random();

	protected final byte[] randomBytes = new byte[4];
	protected int loginTicks;
	protected SecretKey loginKey;
	protected volatile LoginState state = LoginState.HELLO;
	protected GameProfile profile;

	protected boolean isOnlineMode;
	protected boolean useOnlineModeUUID;
	protected UUID forcedUUID;

	public AbstractLoginListener(final NetworkManager networkmanager) {
		super(MinecraftServer.getServer(), networkmanager);
		random.nextBytes(randomBytes);
		isOnlineMode = MinecraftServer.getServer().getOnlineMode() && !networkManager.c();
		useOnlineModeUUID = isOnlineMode;
		forcedUUID = null;
	}

	@Override
	public void c() {
		if (state == LoginState.READY_TO_ACCEPT) {
			b();
		}
		if (loginTicks++ == 600) {
			disconnect("Took too long to log in");
		}
	}

	@Override
	public void disconnect(final String s) {
		try {
			logger.info("Disconnecting " + d() + ": " + s);
			ChatComponentText chatcomponenttext = new ChatComponentText(s);
			networkManager.handle(new PacketLoginOutDisconnect(chatcomponenttext));
			networkManager.close(chatcomponenttext);
		} catch (Exception exception) {
			logger.error("Error whilst disconnecting player", exception);
		}
	}

	@Override
	public void initUUID() {
		profile = new GameProfile(networkManager.spoofedUUID != null ? networkManager.spoofedUUID : generateOffileModeUUID(), profile.getName());
		if (networkManager.spoofedProfile != null) {
			for (Property property : networkManager.spoofedProfile) {
				profile.getProperties().put(property.getName(), property);
			}
		}
	}

	protected UUID generateOffileModeUUID() {
		return UUID.nameUUIDFromBytes(("OfflinePlayer:" + profile.getName()).getBytes(Charsets.UTF_8));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void b() {
		EntityPlayer entityPlayer = MinecraftServer.getServer().getPlayerList().attemptLogin(this, profile, hostname);
		if (entityPlayer != null) {
			state = LoginState.ACCEPTED;
			if (hasCompression()) {
				final int threshold = MinecraftServer.getServer().aK();
				if (threshold >= 0 && !this.networkManager.c()) {
					this.networkManager.a(
						new PacketLoginOutSetCompression(threshold),
						new ChannelFutureListener() {
							@Override
							public void operationComplete(ChannelFuture future) throws Exception {
								enableCompresssion(threshold);
							}
						}
					);
				}
			}
			networkManager.handle(new PacketLoginOutSuccess(profile));
			MinecraftServer.getServer().getPlayerList().a(networkManager, MinecraftServer.getServer().getPlayerList().processLogin(profile, entityPlayer));
		}
	}

	protected abstract boolean hasCompression();

	protected void enableCompresssion(int compressionLevel) {
		Channel channel = networkManager.channel;
		if (compressionLevel >= 0) {
			channel.pipeline().addBefore("decoder", "decompress", new PacketDecompressor(compressionLevel));
			channel.pipeline().addBefore("encoder", "compress", new PacketCompressor(compressionLevel));
		}
	}

	@Override
	public void a(final IChatBaseComponent ichatbasecomponent) {
		logger.info(d() + " lost connection: " + ichatbasecomponent.c());
	}

	@Override
	public String d() {
		return (profile != null) ? (profile + " (" + networkManager.getSocketAddress() + ")") : networkManager.getSocketAddress().toString();
	}

	@Override
	public void a(final PacketLoginInStart packetlogininstart) {
		Validate.validState(state == LoginState.HELLO, "Unexpected hello packet");
		state = LoginState.ONLINEMODERESOLVE;
		loginprocessor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					profile = packetlogininstart.a();
					PlayerLoginStartEvent event = new PlayerLoginStartEvent(
						(InetSocketAddress) networkManager.getSocketAddress(),
						profile.getName(),
						isOnlineMode,
						useOnlineModeUUID,
						hostname
					);
					Bukkit.getPluginManager().callEvent(event);
					if (event.isLoginDenied()) {
						AbstractLoginListener.this.disconnect(event.getDenyLoginMessage());
						return;
					}
					isOnlineMode = event.isOnlineMode();
					useOnlineModeUUID = event.useOnlineModeUUID();
					forcedUUID = event.getForcedUUID();
					if (isOnlineMode) {
						state = LoginState.KEY;
						networkManager.handle(new PacketLoginOutEncryptionBegin("", MinecraftServer.getServer().Q().getPublic(), randomBytes));
					} else {
						new PlayerLookupUUID(AbstractLoginListener.this, isOnlineMode).run();
					}
				} catch (Throwable t) {
					AbstractLoginListener.this.disconnect("Error occured while logging in");
					if (MinecraftServer.getServer().isDebugging()) {
						t.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	public void a(final PacketLoginInEncryptionBegin packetlogininencryptionbegin) {
		Validate.validState(state == LoginState.KEY, "Unexpected key packet");
		state = LoginState.AUTHENTICATING;
		loginprocessor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					final PrivateKey privatekey = MinecraftServer.getServer().Q().getPrivate();
					if (!Arrays.equals(randomBytes, packetlogininencryptionbegin.b(privatekey))) {
						throw new IllegalStateException("Invalid nonce!");
					}
					loginKey = packetlogininencryptionbegin.a(privatekey);
					enableEncryption(loginKey);
					new PlayerLookupUUID(AbstractLoginListener.this, isOnlineMode).run();
				} catch (Throwable t) {
					AbstractLoginListener.this.disconnect("Error occured while logging in");
					if (MinecraftServer.getServer().isDebugging()) {
						t.printStackTrace();
					}
				}
			}
		});
	}

	protected abstract void enableEncryption(SecretKey key);

	public Logger getLogger() {
		return logger;
	}


	public GameProfile getProfile() {
		return profile;
	}

	public void setProfile(GameProfile profile) {
		this.profile = profile;
	}

	public GameProfile generateOfflineProfile(GameProfile current) {
		return a(current);
	}

	public void setReadyToAccept() {
		UUID newUUID = null;
		if (isOnlineMode && !useOnlineModeUUID) {
			newUUID = generateOffileModeUUID();
		}
		if (forcedUUID != null) {
			newUUID = forcedUUID;
		}
		if (newUUID != null) {
			GameProfile newProfile = new GameProfile(newUUID, profile.getName());
			newProfile.getProperties().putAll(profile.getProperties());
			profile = newProfile;
		}
		this.state = LoginState.READY_TO_ACCEPT;
	}

	public SecretKey getLoginKey() {
		return loginKey;
	}


	public NetworkManager getNetworkManager() {
		return networkManager;
	}

}
