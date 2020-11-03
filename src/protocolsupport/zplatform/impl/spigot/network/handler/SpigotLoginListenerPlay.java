package protocolsupport.zplatform.impl.spigot.network.handler;

import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerLoginEvent;
import org.spigotmc.SpigotConfig;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.ExpirableListEntry;
import net.minecraft.server.v1_16_R3.GameProfileBanEntry;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.IpBanEntry;
import net.minecraft.server.v1_16_R3.MinecraftServer;
import net.minecraft.server.v1_16_R3.NetworkManager;
import net.minecraft.server.v1_16_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_16_R3.PacketLoginInCustomPayload;
import net.minecraft.server.v1_16_R3.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_16_R3.PacketLoginInListener;
import net.minecraft.server.v1_16_R3.PacketLoginInStart;
import net.minecraft.server.v1_16_R3.PacketPlayInAbilities;
import net.minecraft.server.v1_16_R3.PacketPlayInAdvancements;
import net.minecraft.server.v1_16_R3.PacketPlayInArmAnimation;
import net.minecraft.server.v1_16_R3.PacketPlayInAutoRecipe;
import net.minecraft.server.v1_16_R3.PacketPlayInBEdit;
import net.minecraft.server.v1_16_R3.PacketPlayInBeacon;
import net.minecraft.server.v1_16_R3.PacketPlayInBlockDig;
import net.minecraft.server.v1_16_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_16_R3.PacketPlayInBoatMove;
import net.minecraft.server.v1_16_R3.PacketPlayInChat;
import net.minecraft.server.v1_16_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_16_R3.PacketPlayInCloseWindow;
import net.minecraft.server.v1_16_R3.PacketPlayInCustomPayload;
import net.minecraft.server.v1_16_R3.PacketPlayInDifficultyChange;
import net.minecraft.server.v1_16_R3.PacketPlayInDifficultyLock;
import net.minecraft.server.v1_16_R3.PacketPlayInEnchantItem;
import net.minecraft.server.v1_16_R3.PacketPlayInEntityAction;
import net.minecraft.server.v1_16_R3.PacketPlayInEntityNBTQuery;
import net.minecraft.server.v1_16_R3.PacketPlayInFlying;
import net.minecraft.server.v1_16_R3.PacketPlayInHeldItemSlot;
import net.minecraft.server.v1_16_R3.PacketPlayInItemName;
import net.minecraft.server.v1_16_R3.PacketPlayInJigsawGenerate;
import net.minecraft.server.v1_16_R3.PacketPlayInKeepAlive;
import net.minecraft.server.v1_16_R3.PacketPlayInPickItem;
import net.minecraft.server.v1_16_R3.PacketPlayInRecipeDisplayed;
import net.minecraft.server.v1_16_R3.PacketPlayInRecipeSettings;
import net.minecraft.server.v1_16_R3.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_16_R3.PacketPlayInSetCommandBlock;
import net.minecraft.server.v1_16_R3.PacketPlayInSetCommandMinecart;
import net.minecraft.server.v1_16_R3.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_16_R3.PacketPlayInSetJigsaw;
import net.minecraft.server.v1_16_R3.PacketPlayInSettings;
import net.minecraft.server.v1_16_R3.PacketPlayInSpectate;
import net.minecraft.server.v1_16_R3.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_16_R3.PacketPlayInStruct;
import net.minecraft.server.v1_16_R3.PacketPlayInTabComplete;
import net.minecraft.server.v1_16_R3.PacketPlayInTeleportAccept;
import net.minecraft.server.v1_16_R3.PacketPlayInTileNBTQuery;
import net.minecraft.server.v1_16_R3.PacketPlayInTrSel;
import net.minecraft.server.v1_16_R3.PacketPlayInTransaction;
import net.minecraft.server.v1_16_R3.PacketPlayInUpdateSign;
import net.minecraft.server.v1_16_R3.PacketPlayInUseEntity;
import net.minecraft.server.v1_16_R3.PacketPlayInUseItem;
import net.minecraft.server.v1_16_R3.PacketPlayInVehicleMove;
import net.minecraft.server.v1_16_R3.PacketPlayInWindowClick;
import net.minecraft.server.v1_16_R3.PlayerInteractManager;
import net.minecraft.server.v1_16_R3.PlayerList;
import net.minecraft.server.v1_16_R3.World;
import net.minecraft.server.v1_16_R3.WorldServer;
import protocolsupport.protocol.packet.handler.AbstractLoginListenerPlay;
import protocolsupport.zplatform.impl.spigot.SpigotMiscUtils;
import protocolsupport.zplatform.impl.spigot.network.SpigotNetworkManagerWrapper;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotLoginListenerPlay extends AbstractLoginListenerPlay implements PacketLoginInListener, PacketListenerPlayIn {

	protected static final MinecraftServer server = SpigotMiscUtils.SERVER;

	public SpigotLoginListenerPlay(NetworkManagerWrapper networkmanager) {
		super(networkmanager);
	}

	@Override
	protected JoinData createJoinData() {
		WorldServer worldserver = server.getWorldServer(World.OVERWORLD);
		EntityPlayer entity = new EntityPlayer(
			server,
			worldserver,
			SpigotMiscUtils.toMojangGameProfile(connection.getLoginProfile()),
			new PlayerInteractManager(worldserver)
		);
		return new JoinData(entity.getBukkitEntity(), entity) {
			@Override
			protected void close() {
			}
		};
	}

	protected static final String BAN_DATE_FORMAT_STRING = "yyyy-MM-dd 'at' HH:mm:ss z";
	@Override
	protected void checkBans(PlayerLoginEvent event, Object[] data) {
		PlayerList playerlist = server.getPlayerList();

		GameProfile mojangGameProfile = ((EntityPlayer) data[0]).getProfile();
		SocketAddress address = networkManager.getAddress();

		if (playerlist.getProfileBans().isBanned(mojangGameProfile)) {
			GameProfileBanEntry profileban = playerlist.getProfileBans().get(mojangGameProfile);
			if (isBanned(profileban)) {
				String reason = "You are banned from this server!\nReason: " + profileban.getReason();
				if (profileban.getExpires() != null) {
					reason = reason + "\nYour ban will be removed on " +  new SimpleDateFormat(BAN_DATE_FORMAT_STRING).format(profileban.getExpires());
				}
				event.disallow(PlayerLoginEvent.Result.KICK_BANNED, reason);
			}
		} else if (!playerlist.isWhitelisted(mojangGameProfile)) {
			event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, SpigotConfig.whitelistMessage);
		} else if (playerlist.getIPBans().isBanned(address)) {
			IpBanEntry ipban = playerlist.getIPBans().get(address);
			if (isBanned(ipban)) {
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

	protected static boolean isBanned(ExpirableListEntry<?> entry) {
		if (entry == null) {
			return false;
		}
		Date expireDate = entry.getExpires();
		if (expireDate == null) {
			return true;
		}
		return expireDate.after(new Date());
	}

	@Override
	protected void joinGame(Object[] data) {
		server.getPlayerList().a((NetworkManager) networkManager.unwrap(), (EntityPlayer) data[0]);
	}

	@Override
	public void a(IChatBaseComponent ichatbasecomponent) {
		Bukkit.getLogger().info(getConnectionRepr() + " lost connection: " + ichatbasecomponent.getText());
	}

	@Override
	public void a(PacketLoginInStart packetlogininstart) {
	}

	@Override
	public void a(PacketLoginInEncryptionBegin packetlogininencryptionbegin) {
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

	@Override
	public void a(PacketPlayInSetJigsaw arg0) {
	}

	@Override
	public void a(PacketPlayInDifficultyChange arg0) {
	}

	@Override
	public void a(PacketPlayInDifficultyLock arg0) {
	}

	@Override
	public void a(PacketPlayInJigsawGenerate var1) {
	}

	@Override
	public void a(PacketPlayInRecipeSettings var1) {
	}

	@Override
	public NetworkManager a() {
		return ((SpigotNetworkManagerWrapper) this.networkManager).unwrap();
	}

}
