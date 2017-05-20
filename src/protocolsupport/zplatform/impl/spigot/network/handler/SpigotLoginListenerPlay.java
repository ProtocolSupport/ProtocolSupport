package protocolsupport.zplatform.impl.spigot.network.handler;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerLoginEvent;
import org.spigotmc.SpigotConfig;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.ExpirableListEntry;
import net.minecraft.server.v1_12_R1.GameProfileBanEntry;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.ITickable;
import net.minecraft.server.v1_12_R1.IpBanEntry;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import net.minecraft.server.v1_12_R1.NetworkManager;
import net.minecraft.server.v1_12_R1.PacketListenerPlayIn;
import net.minecraft.server.v1_12_R1.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_12_R1.PacketLoginInListener;
import net.minecraft.server.v1_12_R1.PacketLoginInStart;
import net.minecraft.server.v1_12_R1.PacketPlayInAbilities;
import net.minecraft.server.v1_12_R1.PacketPlayInAdvancements;
import net.minecraft.server.v1_12_R1.PacketPlayInArmAnimation;
import net.minecraft.server.v1_12_R1.PacketPlayInAutoRecipe;
import net.minecraft.server.v1_12_R1.PacketPlayInBlockDig;
import net.minecraft.server.v1_12_R1.PacketPlayInBlockPlace;
import net.minecraft.server.v1_12_R1.PacketPlayInBoatMove;
import net.minecraft.server.v1_12_R1.PacketPlayInChat;
import net.minecraft.server.v1_12_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_12_R1.PacketPlayInCloseWindow;
import net.minecraft.server.v1_12_R1.PacketPlayInCustomPayload;
import net.minecraft.server.v1_12_R1.PacketPlayInEnchantItem;
import net.minecraft.server.v1_12_R1.PacketPlayInEntityAction;
import net.minecraft.server.v1_12_R1.PacketPlayInFlying;
import net.minecraft.server.v1_12_R1.PacketPlayInHeldItemSlot;
import net.minecraft.server.v1_12_R1.PacketPlayInKeepAlive;
import net.minecraft.server.v1_12_R1.PacketPlayInRecipeDisplayed;
import net.minecraft.server.v1_12_R1.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_12_R1.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_12_R1.PacketPlayInSettings;
import net.minecraft.server.v1_12_R1.PacketPlayInSpectate;
import net.minecraft.server.v1_12_R1.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_12_R1.PacketPlayInTabComplete;
import net.minecraft.server.v1_12_R1.PacketPlayInTeleportAccept;
import net.minecraft.server.v1_12_R1.PacketPlayInTransaction;
import net.minecraft.server.v1_12_R1.PacketPlayInUpdateSign;
import net.minecraft.server.v1_12_R1.PacketPlayInUseEntity;
import net.minecraft.server.v1_12_R1.PacketPlayInUseItem;
import net.minecraft.server.v1_12_R1.PacketPlayInVehicleMove;
import net.minecraft.server.v1_12_R1.PacketPlayInWindowClick;
import net.minecraft.server.v1_12_R1.PlayerInteractManager;
import net.minecraft.server.v1_12_R1.PlayerList;
import protocolsupport.protocol.packet.handler.AbstractLoginListenerPlay;
import protocolsupport.protocol.utils.authlib.GameProfile;
import protocolsupport.zplatform.impl.spigot.SpigotMiscUtils;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotLoginListenerPlay extends AbstractLoginListenerPlay implements PacketLoginInListener, PacketListenerPlayIn, ITickable {

	protected static final MinecraftServer server = SpigotMiscUtils.getServer();

	public SpigotLoginListenerPlay(NetworkManagerWrapper networkmanager, GameProfile profile, boolean onlineMode, String hostname) {
		super(networkmanager, profile, onlineMode, hostname);
	}

	@Override
	public void e() {
		tick();
	}

	@Override
	protected JoinData createJoinData() {
		com.mojang.authlib.GameProfile mojangGameProfile = SpigotMiscUtils.toMojangGameProfile(profile);
		EntityPlayer entity = new EntityPlayer(server, server.getWorldServer(0), mojangGameProfile, new PlayerInteractManager(server.getWorldServer(0)));
		return new JoinData(entity.getBukkitEntity(), entity) {
			@Override
			protected void close() {
			}
		};
	}

	private static final SimpleDateFormat banDateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
	@Override
	protected void checkBans(PlayerLoginEvent event, Object[] data) {
		PlayerList playerlist = server.getPlayerList();

		com.mojang.authlib.GameProfile mojangGameProfile = ((EntityPlayer) data[0]).getProfile();

		InetSocketAddress socketaddress = networkManager.getAddress();
		if (playerlist.getProfileBans().isBanned(mojangGameProfile)) {
			GameProfileBanEntry profileban = playerlist.getProfileBans().get(mojangGameProfile);
			if (!hasExpired(profileban)) {
				String reason = "You are banned from this server!\nReason: " + profileban.getReason();
				if (profileban.getExpires() != null) {
					reason = reason + "\nYour ban will be removed on " + banDateFormat.format(profileban.getExpires());
				}
				event.disallow(PlayerLoginEvent.Result.KICK_BANNED, reason);
			}
		} else if (!playerlist.isWhitelisted(mojangGameProfile)) {
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
		} else if ((playerlist.players.size() >= playerlist.getMaxPlayers()) && !playerlist.f(mojangGameProfile)) {
			event.disallow(PlayerLoginEvent.Result.KICK_FULL, SpigotConfig.serverFullMessage);
		}
	}

	private static boolean hasExpired(ExpirableListEntry<?> entry) {
		Date expireDate = entry.getExpires();
		return (expireDate != null) && expireDate.before(new Date());
	}

	@Override
	protected void joinGame(Object[] data) {
		server.getPlayerList().a((NetworkManager) networkManager.unwrap(), (EntityPlayer) data[0]);
	}

	@Override
	public void a(final IChatBaseComponent ichatbasecomponent) {
		Bukkit.getLogger().info(getConnectionRepr() + " lost connection: " + ichatbasecomponent.getText());
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

	@Override
	public void a(PacketPlayInAutoRecipe p0) {
	}

	@Override
	public void a(PacketPlayInRecipeDisplayed p0) {
	}

	@Override
	public void a(PacketPlayInAdvancements arg0) {
	}

}
