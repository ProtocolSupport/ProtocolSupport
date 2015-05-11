package protocolsupport.protocol.transformer.v_1_8;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

import java.net.InetSocketAddress;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.crypto.SecretKey;

import net.minecraft.server.v1_8_R2.ChatComponentText;
import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.MinecraftServer;
import net.minecraft.server.v1_8_R2.NetworkManager;
import net.minecraft.server.v1_8_R2.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_8_R2.PacketLoginInStart;
import net.minecraft.server.v1_8_R2.PacketLoginOutDisconnect;
import net.minecraft.server.v1_8_R2.PacketLoginOutEncryptionBegin;
import net.minecraft.server.v1_8_R2.PacketLoginOutSetCompression;
import net.minecraft.server.v1_8_R2.PacketLoginOutSuccess;

import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;

import protocolsupport.api.events.PlayerLoginStartEvent;
import protocolsupport.protocol.transformer.ILoginListener;
import protocolsupport.protocol.transformer.LoginState;
import protocolsupport.protocol.transformer.ThreadPlayerLookupUUID;

import com.google.common.base.Charsets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class LoginListener extends net.minecraft.server.v1_8_R2.LoginListener implements ILoginListener {

	private static final Logger logger = LogManager.getLogger();
	private static final AtomicInteger authThreadsCounter = new AtomicInteger(0);
	private static final Random random = new Random();

	private final byte[] randomBytes = new byte[4];
	private int loginTicks;
	private SecretKey loginKey;
	private LoginState state = LoginState.HELLO;
	private GameProfile profile;

	private boolean isOnlineMode = false;
	private boolean useOnlineModeUUID = false;

	public LoginListener(final NetworkManager networkmanager) {
		super(MinecraftServer.getServer(), networkmanager);
		random.nextBytes(randomBytes);
		isOnlineMode = MinecraftServer.getServer().getOnlineMode() && !networkManager.c();
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
			LoginListener.logger.info("Disconnecting " + d() + ": " + s);
			final ChatComponentText chatcomponenttext = new ChatComponentText(s);
			networkManager.handle(new PacketLoginOutDisconnect(chatcomponenttext));
			networkManager.close(chatcomponenttext);
		} catch (Exception exception) {
			LoginListener.logger.error("Error whilst disconnecting player", exception);
		}
	}

	@Override
	public void initUUID() {
		UUID uuid;
		if (networkManager.spoofedUUID != null) {
			uuid = networkManager.spoofedUUID;
		} else {
			uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + profile.getName()).getBytes(Charsets.UTF_8));
		}
		profile = new GameProfile(uuid, profile.getName());
		if (networkManager.spoofedProfile != null) {
			for (final Property property : networkManager.spoofedProfile) {
				profile.getProperties().put(property.getName(), property);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void b() {
		if (isOnlineMode && !useOnlineModeUUID) {
			initUUID();
		}
		final EntityPlayer s = MinecraftServer.getServer().getPlayerList().attemptLogin(this, profile, hostname);
		if (s != null) {
			state = LoginState.ACCEPTED;
			if (MinecraftServer.getServer().aJ() >= 0 && !this.networkManager.c()) {
				this.networkManager.a(
					new PacketLoginOutSetCompression(MinecraftServer.getServer().aJ()),
					new ChannelFutureListener() {
						@Override
						public void operationComplete(ChannelFuture future) throws Exception {
							networkManager.a(MinecraftServer.getServer().aJ());
						}
					}
				);
			}
			networkManager.handle(new PacketLoginOutSuccess(profile));
			MinecraftServer.getServer().getPlayerList().a(networkManager, MinecraftServer.getServer().getPlayerList().processLogin(profile, s));
		}
	}

	@Override
	public void a(final IChatBaseComponent ichatbasecomponent) {
		LoginListener.logger.info(d() + " lost connection: " + ichatbasecomponent.c());
	}

	@Override
	public String d() {
		return (profile != null) ? (profile.toString() + " (" + networkManager.getSocketAddress().toString() + ")") : String.valueOf(networkManager.getSocketAddress());
	}

	@Override
	public void a(final PacketLoginInStart packetlogininstart) {
		Validate.validState(state == LoginState.HELLO, "Unexpected hello packet");
		state = LoginState.ONLINEMODERESOLVE;
		profile = packetlogininstart.a();
		PlayerLoginStartEvent event = new PlayerLoginStartEvent(
			(InetSocketAddress) networkManager.getSocketAddress(),
			profile.getName(),
			isOnlineMode,
			hostname
		);
		Bukkit.getPluginManager().callEvent(event);
		isOnlineMode = event.isOnlineMode();
		useOnlineModeUUID = event.useOnlineModeUUID();
		if (isOnlineMode) {
			state = LoginState.KEY;
			networkManager.handle(new PacketLoginOutEncryptionBegin("", MinecraftServer.getServer().P().getPublic(), randomBytes));
		} else {
			new ThreadPlayerLookupUUID(this, "User Authenticator #" + LoginListener.authThreadsCounter.incrementAndGet(), isOnlineMode).start();
		}
	}

	@Override
	public void a(final PacketLoginInEncryptionBegin packetlogininencryptionbegin) {
		Validate.validState(state == LoginState.KEY, "Unexpected key packet");
		final PrivateKey privatekey = MinecraftServer.getServer().P().getPrivate();
		if (!Arrays.equals(randomBytes, packetlogininencryptionbegin.b(privatekey))) {
			throw new IllegalStateException("Invalid nonce!");
		}
		loginKey = packetlogininencryptionbegin.a(privatekey);
		state = LoginState.AUTHENTICATING;
		networkManager.a(loginKey);
		new ThreadPlayerLookupUUID(this, "User Authenticator #" + LoginListener.authThreadsCounter.incrementAndGet(), isOnlineMode).start();
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	@Override
	public GameProfile getProfile() {
		return profile;
	}

	@Override
	public void setProfile(GameProfile profile) {
		this.profile = profile;
	}

	@Override
	public GameProfile generateOfflineProfile(GameProfile current) {
		return a(current);
	}

	@Override
	public void setLoginState(LoginState state) {
		this.state = state;
	}

	@Override
	public SecretKey getLoginKey() {
		return loginKey;
	}

	@Override
	public NetworkManager getNetworkManager() {
		return networkManager;
	}

}