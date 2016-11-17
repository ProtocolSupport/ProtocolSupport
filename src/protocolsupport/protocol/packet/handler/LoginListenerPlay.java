package protocolsupport.protocol.packet.handler;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerLoginEvent;
import org.spigotmc.SpigotConfig;

import com.mojang.authlib.GameProfile;

import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.server.v1_11_R1.ChatComponentText;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.EnumDifficulty;
import net.minecraft.server.v1_11_R1.EnumGamemode;
import net.minecraft.server.v1_11_R1.ExpirableListEntry;
import net.minecraft.server.v1_11_R1.GameProfileBanEntry;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.ITickable;
import net.minecraft.server.v1_11_R1.IpBanEntry;
import net.minecraft.server.v1_11_R1.LoginListener;
import net.minecraft.server.v1_11_R1.MinecraftServer;
import net.minecraft.server.v1_11_R1.NetworkManager;
import net.minecraft.server.v1_11_R1.PacketDataSerializer;
import net.minecraft.server.v1_11_R1.PacketListenerPlayIn;
import net.minecraft.server.v1_11_R1.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_11_R1.PacketLoginInListener;
import net.minecraft.server.v1_11_R1.PacketLoginInStart;
import net.minecraft.server.v1_11_R1.PacketLoginOutSuccess;
import net.minecraft.server.v1_11_R1.PacketPlayInAbilities;
import net.minecraft.server.v1_11_R1.PacketPlayInArmAnimation;
import net.minecraft.server.v1_11_R1.PacketPlayInBlockDig;
import net.minecraft.server.v1_11_R1.PacketPlayInBlockPlace;
import net.minecraft.server.v1_11_R1.PacketPlayInBoatMove;
import net.minecraft.server.v1_11_R1.PacketPlayInChat;
import net.minecraft.server.v1_11_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_11_R1.PacketPlayInCloseWindow;
import net.minecraft.server.v1_11_R1.PacketPlayInCustomPayload;
import net.minecraft.server.v1_11_R1.PacketPlayInEnchantItem;
import net.minecraft.server.v1_11_R1.PacketPlayInEntityAction;
import net.minecraft.server.v1_11_R1.PacketPlayInFlying;
import net.minecraft.server.v1_11_R1.PacketPlayInHeldItemSlot;
import net.minecraft.server.v1_11_R1.PacketPlayInKeepAlive;
import net.minecraft.server.v1_11_R1.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_11_R1.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_11_R1.PacketPlayInSettings;
import net.minecraft.server.v1_11_R1.PacketPlayInSpectate;
import net.minecraft.server.v1_11_R1.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_11_R1.PacketPlayInTabComplete;
import net.minecraft.server.v1_11_R1.PacketPlayInTeleportAccept;
import net.minecraft.server.v1_11_R1.PacketPlayInTransaction;
import net.minecraft.server.v1_11_R1.PacketPlayInUpdateSign;
import net.minecraft.server.v1_11_R1.PacketPlayInUseEntity;
import net.minecraft.server.v1_11_R1.PacketPlayInUseItem;
import net.minecraft.server.v1_11_R1.PacketPlayInVehicleMove;
import net.minecraft.server.v1_11_R1.PacketPlayInWindowClick;
import net.minecraft.server.v1_11_R1.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_11_R1.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_11_R1.PacketPlayOutLogin;
import net.minecraft.server.v1_11_R1.PlayerInteractManager;
import net.minecraft.server.v1_11_R1.PlayerList;
import net.minecraft.server.v1_11_R1.WorldType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.PlayerLoginFinishEvent;
import protocolsupport.api.events.PlayerSyncLoginEvent;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.utils.ServerPlatformUtils;

public class LoginListenerPlay implements PacketLoginInListener, PacketListenerPlayIn, ITickable, IHasProfile {

	protected static final Logger logger = LogManager.getLogger(LoginListener.class);
	protected static final MinecraftServer server = ServerPlatformUtils.getServer();

	protected final NetworkManager networkManager;
	protected final GameProfile profile;
	protected final boolean onlineMode;
	protected final String hostname;

	protected boolean ready;

	public LoginListenerPlay(NetworkManager networkmanager, GameProfile profile, boolean onlineMode, String hostname) {
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
		networkManager.sendPacket(new PacketLoginOutSuccess(profile));
		// tick connection keep now
		keepConnection();
		// now fire login event
		PlayerLoginFinishEvent event = new PlayerLoginFinishEvent(ConnectionImpl.getFromChannel(networkManager.channel), profile.getName(), profile.getId(), onlineMode);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isLoginDenied()) {
			disconnect(event.getDenyLoginMessage());
			return;
		}
		ready = true;
	}

	protected int keepAliveTicks = 1;

	@Override
	public void F_() {
		if ((keepAliveTicks++ % 80) == 0) {
			keepConnection();
		}
		if (ready) {
			tryJoin();
		}
	}

	private static final PacketDataSerializer fake = new PacketDataSerializer(Unpooled.EMPTY_BUFFER);

	private void keepConnection() {
		// custom payload does nothing on a client when sent with invalid tag,
		// but it resets client readtimeouthandler, and that is exactly what we need
		networkManager.sendPacket(new PacketPlayOutCustomPayload("PSFake", fake));
		// we also need to reset server readtimeouthandler
		ChannelHandlers.getTimeoutHandler(networkManager.channel.pipeline()).setLastRead();
	}

	private void tryJoin() {
		EntityPlayer loginplayer = attemptLogin(profile, hostname);
		if (loginplayer != null) {
			server.getPlayerList().a(networkManager, loginplayer);
			ready = false;
		}
	}

	//reimplement PlayerList login attempt logic to fire PlayerSyncLoginEvent before PlayerLognEvent
	//also delay login if there was a player with same uuid which was kicked when handling this
	private static final SimpleDateFormat banDateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
	public EntityPlayer attemptLogin(GameProfile gameprofile, String hostname) {
		PlayerList playerlist = server.getPlayerList();
		UUID uuid = gameprofile.getId();

		//find players with same uuid
		List<EntityPlayer> toKick = playerlist.players.stream().filter(entityplayer -> entityplayer.getUniqueID().equals(uuid)).collect(Collectors.toList());
		//kick them
		if (!toKick.isEmpty()) {
			toKick.forEach(entityplayer -> {
				playerlist.playerFileData.save(entityplayer);
				entityplayer.playerConnection.disconnect("You logged in from another location");
			});
			return null;
		}

		//prepare player entity
		EntityPlayer entity = new EntityPlayer(server, server.getWorldServer(0), gameprofile, new PlayerInteractManager(server.getWorldServer(0)));

		//ps sync login event
		PlayerSyncLoginEvent syncloginevent = new PlayerSyncLoginEvent(ConnectionImpl.getFromChannel(networkManager.channel), entity.getBukkitEntity());
		Bukkit.getPluginManager().callEvent(syncloginevent);
		if (syncloginevent.isLoginDenied()) {
			disconnect(syncloginevent.getDenyLoginMessage());
			return null;
		}

		//bukkit sync login event
		InetSocketAddress socketaddress = (InetSocketAddress) networkManager.getSocketAddress();
		PlayerLoginEvent event = new PlayerLoginEvent(entity.getBukkitEntity(), hostname, socketaddress.getAddress(), ((InetSocketAddress) networkManager.getRawAddress()).getAddress());
		if (playerlist.getProfileBans().isBanned(gameprofile)) {
			GameProfileBanEntry profileban = playerlist.getProfileBans().get(gameprofile);
			if (!hasExpired(profileban)) {
				String reason = "You are banned from this server!\nReason: " + profileban.getReason();
				if (profileban.getExpires() != null) {
					reason = reason + "\nYour ban will be removed on " + banDateFormat.format(profileban.getExpires());
				}
				event.disallow(PlayerLoginEvent.Result.KICK_BANNED, reason);
			}
		} else if (!playerlist.isWhitelisted(gameprofile)) {
			event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, SpigotConfig.whitelistMessage);
		} else if (playerlist.getIPBans().isBanned(socketaddress)) {
			IpBanEntry ipban = playerlist.getIPBans().get(socketaddress);
			if (!hasExpired(ipban)) {
				String reason = "Your IP address is banned from this server!\nReason: " + ipban.getReason();
				if (ipban.getExpires() != null) {
					reason = reason + "\nYour ban will be removed on " + banDateFormat.format(ipban.getExpires());
				}
				event.disallow(PlayerLoginEvent.Result.KICK_BANNED, reason);
			}
		} else if ((playerlist.players.size() >= playerlist.getMaxPlayers()) && !playerlist.f(gameprofile)) {
			event.disallow(PlayerLoginEvent.Result.KICK_FULL, SpigotConfig.serverFullMessage);
		}
		Bukkit.getServer().getPluginManager().callEvent(event);
		if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
			disconnect(event.getKickMessage());
			return null;
		}

		return entity;
	}

	private static boolean hasExpired(ExpirableListEntry<?> entry) {
		Date expireDate = entry.getExpires();
		return (expireDate != null) && expireDate.before(new Date());
	}

	@Override
	public void a(final IChatBaseComponent ichatbasecomponent) {
		logger.info(getConnectionRepr() + " lost connection: " + ichatbasecomponent.getText());
	}

	private String getConnectionRepr() {
		return profile + " (" + networkManager.getSocketAddress() + ")";
	}

	public void disconnect(final String s) {
		try {
			logger.info("Disconnecting " + getConnectionRepr() + ": " + s);
			if (ConnectionImpl.getFromChannel(networkManager.channel).getVersion().isBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_7_10)) {
				// first send join game that will make client actually switch to game state
				networkManager.sendPacket(new PacketPlayOutLogin(0, EnumGamemode.NOT_SET, false, 0, EnumDifficulty.EASY, 60, WorldType.NORMAL, false));
				// send disconnect with a little delay
				networkManager.channel.eventLoop().schedule(() -> disconnect0(s), 50, TimeUnit.MILLISECONDS);
			} else {
				disconnect0(s);
			}
		} catch (Exception exception) {
			logger.error("Error whilst disconnecting player", exception);
		}
	}

	@SuppressWarnings("unchecked")
	protected void disconnect0(String s) {
		final ChatComponentText chatcomponenttext = new ChatComponentText(s);
		networkManager.sendPacket(new PacketPlayOutKickDisconnect(chatcomponenttext), new GenericFutureListener<Future<? super Void>>() {
			@Override
			public void operationComplete(Future<? super Void> future) {
				networkManager.close(chatcomponenttext);
			}
		});
	}

	@Override
	public void a(final PacketLoginInStart packetlogininstart) {
	}

	@Override
	public void a(final PacketLoginInEncryptionBegin packetlogininencryptionbegin) {
	}

	@Override
	public void a(PacketPlayInArmAnimation p0) {
	}

	@Override
	public void a(PacketPlayInChat p0) {
	}

	@Override
	public void a(PacketPlayInTabComplete p0) {
	}

	@Override
	public void a(PacketPlayInClientCommand p0) {
	}

	@Override
	public void a(PacketPlayInSettings p0) {
	}

	@Override
	public void a(PacketPlayInTransaction p0) {
	}

	@Override
	public void a(PacketPlayInEnchantItem p0) {
	}

	@Override
	public void a(PacketPlayInWindowClick p0) {
	}

	@Override
	public void a(PacketPlayInCloseWindow p0) {
	}

	@Override
	public void a(PacketPlayInCustomPayload p0) {
	}

	@Override
	public void a(PacketPlayInUseEntity p0) {
	}

	@Override
	public void a(PacketPlayInKeepAlive p0) {
	}

	@Override
	public void a(PacketPlayInFlying p0) {
	}

	@Override
	public void a(PacketPlayInAbilities p0) {
	}

	@Override
	public void a(PacketPlayInBlockDig p0) {
	}

	@Override
	public void a(PacketPlayInEntityAction p0) {
	}

	@Override
	public void a(PacketPlayInSteerVehicle p0) {
	}

	@Override
	public void a(PacketPlayInHeldItemSlot p0) {
	}

	@Override
	public void a(PacketPlayInSetCreativeSlot p0) {
	}

	@Override
	public void a(PacketPlayInUpdateSign p0) {
	}

	@Override
	public void a(PacketPlayInUseItem p0) {
	}

	@Override
	public void a(PacketPlayInBlockPlace p0) {
	}

	@Override
	public void a(PacketPlayInSpectate p0) {
	}

	@Override
	public void a(PacketPlayInResourcePackStatus p0) {
	}

	@Override
	public void a(PacketPlayInBoatMove p0) {
	}

	@Override
	public void a(PacketPlayInVehicleMove p0) {
	}

	@Override
	public void a(PacketPlayInTeleportAccept p0) {
	}

}
