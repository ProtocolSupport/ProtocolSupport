package protocolsupport.zplatform.impl.spigot.network.handler;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerLoginEvent;
import org.spigotmc.SpigotConfig;

import net.minecraft.server.v1_13_R2.DimensionManager;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.ExpirableListEntry;
import net.minecraft.server.v1_13_R2.GameProfileBanEntry;
import net.minecraft.server.v1_13_R2.IChatBaseComponent;
import net.minecraft.server.v1_13_R2.ITickable;
import net.minecraft.server.v1_13_R2.IpBanEntry;
import net.minecraft.server.v1_13_R2.MinecraftServer;
import net.minecraft.server.v1_13_R2.NetworkManager;
import net.minecraft.server.v1_13_R2.PacketListenerPlayIn;
import net.minecraft.server.v1_13_R2.PacketLoginInCustomPayload;
import net.minecraft.server.v1_13_R2.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_13_R2.PacketLoginInListener;
import net.minecraft.server.v1_13_R2.PacketLoginInStart;
import net.minecraft.server.v1_13_R2.PacketPlayInAbilities;
import net.minecraft.server.v1_13_R2.PacketPlayInAdvancements;
import net.minecraft.server.v1_13_R2.PacketPlayInArmAnimation;
import net.minecraft.server.v1_13_R2.PacketPlayInAutoRecipe;
import net.minecraft.server.v1_13_R2.PacketPlayInBEdit;
import net.minecraft.server.v1_13_R2.PacketPlayInBeacon;
import net.minecraft.server.v1_13_R2.PacketPlayInBlockDig;
import net.minecraft.server.v1_13_R2.PacketPlayInBlockPlace;
import net.minecraft.server.v1_13_R2.PacketPlayInBoatMove;
import net.minecraft.server.v1_13_R2.PacketPlayInChat;
import net.minecraft.server.v1_13_R2.PacketPlayInClientCommand;
import net.minecraft.server.v1_13_R2.PacketPlayInCloseWindow;
import net.minecraft.server.v1_13_R2.PacketPlayInCustomPayload;
import net.minecraft.server.v1_13_R2.PacketPlayInEnchantItem;
import net.minecraft.server.v1_13_R2.PacketPlayInEntityAction;
import net.minecraft.server.v1_13_R2.PacketPlayInEntityNBTQuery;
import net.minecraft.server.v1_13_R2.PacketPlayInFlying;
import net.minecraft.server.v1_13_R2.PacketPlayInHeldItemSlot;
import net.minecraft.server.v1_13_R2.PacketPlayInItemName;
import net.minecraft.server.v1_13_R2.PacketPlayInKeepAlive;
import net.minecraft.server.v1_13_R2.PacketPlayInPickItem;
import net.minecraft.server.v1_13_R2.PacketPlayInRecipeDisplayed;
import net.minecraft.server.v1_13_R2.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_13_R2.PacketPlayInSetCommandBlock;
import net.minecraft.server.v1_13_R2.PacketPlayInSetCommandMinecart;
import net.minecraft.server.v1_13_R2.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_13_R2.PacketPlayInSettings;
import net.minecraft.server.v1_13_R2.PacketPlayInSpectate;
import net.minecraft.server.v1_13_R2.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_13_R2.PacketPlayInStruct;
import net.minecraft.server.v1_13_R2.PacketPlayInTabComplete;
import net.minecraft.server.v1_13_R2.PacketPlayInTeleportAccept;
import net.minecraft.server.v1_13_R2.PacketPlayInTileNBTQuery;
import net.minecraft.server.v1_13_R2.PacketPlayInTrSel;
import net.minecraft.server.v1_13_R2.PacketPlayInTransaction;
import net.minecraft.server.v1_13_R2.PacketPlayInUpdateSign;
import net.minecraft.server.v1_13_R2.PacketPlayInUseEntity;
import net.minecraft.server.v1_13_R2.PacketPlayInUseItem;
import net.minecraft.server.v1_13_R2.PacketPlayInVehicleMove;
import net.minecraft.server.v1_13_R2.PacketPlayInWindowClick;
import net.minecraft.server.v1_13_R2.PlayerInteractManager;
import net.minecraft.server.v1_13_R2.PlayerList;
import protocolsupport.protocol.packet.handler.AbstractLoginListenerPlay;
import protocolsupport.zplatform.impl.spigot.SpigotMiscUtils;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotLoginListenerPlay extends AbstractLoginListenerPlay implements PacketLoginInListener, PacketListenerPlayIn, ITickable {

	protected static final MinecraftServer server = SpigotMiscUtils.getServer();

	public SpigotLoginListenerPlay(NetworkManagerWrapper networkmanager, String hostname) {
		super(networkmanager, hostname);
	}

	@Override
	public void tick() {
		loginTick();
	}

	@Override
	protected JoinData createJoinData() {
		com.mojang.authlib.GameProfile mojangGameProfile = SpigotMiscUtils.toMojangGameProfile(connection.getProfile());
		EntityPlayer entity = new EntityPlayer(
			server,
			server.getWorldServer(DimensionManager.OVERWORLD),
			mojangGameProfile,
			new PlayerInteractManager(server.getWorldServer(DimensionManager.OVERWORLD))
		);
		return new JoinData(entity.getBukkitEntity(), entity) {
			@Override
			protected void close() {
			}
		};
	}

	private static final String BAN_DATE_FORMAT_STRING = "yyyy-MM-dd 'at' HH:mm:ss z";
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
					reason = reason + "\nYour ban will be removed on " +  new SimpleDateFormat(BAN_DATE_FORMAT_STRING).format(profileban.getExpires());
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
					reason = reason + "\nYour ban will be removed on " + new SimpleDateFormat(BAN_DATE_FORMAT_STRING).format(ipban.getExpires());
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

	@Override
	public void a(PacketPlayInSetCommandBlock var1) {
	}

	@Override
	public void a(PacketPlayInSetCommandMinecart var1) {
	}

	@Override
	public void a(PacketPlayInPickItem var1) {
	}

	@Override
	public void a(PacketPlayInItemName var1) {
	}

	@Override
	public void a(PacketPlayInBeacon var1) {
	}

	@Override
	public void a(PacketPlayInStruct var1) {
	}

	@Override
	public void a(PacketPlayInTrSel var1) {
	}

	@Override
	public void a(PacketPlayInBEdit var1) {
	}

	@Override
	public void a(PacketPlayInEntityNBTQuery var1) {
	}

	@Override
	public void a(PacketPlayInTileNBTQuery var1) {
	}

	@Override
	public void a(PacketLoginInCustomPayload var1) {
	}

}
