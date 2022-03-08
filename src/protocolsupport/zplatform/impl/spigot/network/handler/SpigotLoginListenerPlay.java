package protocolsupport.zplatform.impl.spigot.network.handler;

import java.net.SocketAddress;
import java.text.SimpleDateFormat;

import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerLoginEvent;
import org.spigotmc.SpigotConfig;

import com.mojang.authlib.GameProfile;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.protocol.game.PacketListenerPlayIn;
import net.minecraft.network.protocol.game.PacketPlayInAbilities;
import net.minecraft.network.protocol.game.PacketPlayInAdvancements;
import net.minecraft.network.protocol.game.PacketPlayInArmAnimation;
import net.minecraft.network.protocol.game.PacketPlayInAutoRecipe;
import net.minecraft.network.protocol.game.PacketPlayInBEdit;
import net.minecraft.network.protocol.game.PacketPlayInBeacon;
import net.minecraft.network.protocol.game.PacketPlayInBlockDig;
import net.minecraft.network.protocol.game.PacketPlayInBlockPlace;
import net.minecraft.network.protocol.game.PacketPlayInBoatMove;
import net.minecraft.network.protocol.game.PacketPlayInChat;
import net.minecraft.network.protocol.game.PacketPlayInClientCommand;
import net.minecraft.network.protocol.game.PacketPlayInCloseWindow;
import net.minecraft.network.protocol.game.PacketPlayInCustomPayload;
import net.minecraft.network.protocol.game.PacketPlayInDifficultyChange;
import net.minecraft.network.protocol.game.PacketPlayInDifficultyLock;
import net.minecraft.network.protocol.game.PacketPlayInEnchantItem;
import net.minecraft.network.protocol.game.PacketPlayInEntityAction;
import net.minecraft.network.protocol.game.PacketPlayInEntityNBTQuery;
import net.minecraft.network.protocol.game.PacketPlayInFlying;
import net.minecraft.network.protocol.game.PacketPlayInHeldItemSlot;
import net.minecraft.network.protocol.game.PacketPlayInItemName;
import net.minecraft.network.protocol.game.PacketPlayInJigsawGenerate;
import net.minecraft.network.protocol.game.PacketPlayInKeepAlive;
import net.minecraft.network.protocol.game.PacketPlayInPickItem;
import net.minecraft.network.protocol.game.PacketPlayInRecipeDisplayed;
import net.minecraft.network.protocol.game.PacketPlayInRecipeSettings;
import net.minecraft.network.protocol.game.PacketPlayInResourcePackStatus;
import net.minecraft.network.protocol.game.PacketPlayInSetCommandBlock;
import net.minecraft.network.protocol.game.PacketPlayInSetCommandMinecart;
import net.minecraft.network.protocol.game.PacketPlayInSetCreativeSlot;
import net.minecraft.network.protocol.game.PacketPlayInSetJigsaw;
import net.minecraft.network.protocol.game.PacketPlayInSettings;
import net.minecraft.network.protocol.game.PacketPlayInSpectate;
import net.minecraft.network.protocol.game.PacketPlayInSteerVehicle;
import net.minecraft.network.protocol.game.PacketPlayInStruct;
import net.minecraft.network.protocol.game.PacketPlayInTabComplete;
import net.minecraft.network.protocol.game.PacketPlayInTeleportAccept;
import net.minecraft.network.protocol.game.PacketPlayInTileNBTQuery;
import net.minecraft.network.protocol.game.PacketPlayInTrSel;
import net.minecraft.network.protocol.game.PacketPlayInUpdateSign;
import net.minecraft.network.protocol.game.PacketPlayInUseEntity;
import net.minecraft.network.protocol.game.PacketPlayInUseItem;
import net.minecraft.network.protocol.game.PacketPlayInVehicleMove;
import net.minecraft.network.protocol.game.PacketPlayInWindowClick;
import net.minecraft.network.protocol.game.ServerboundPongPacket;
import net.minecraft.network.protocol.login.PacketLoginInCustomPayload;
import net.minecraft.network.protocol.login.PacketLoginInEncryptionBegin;
import net.minecraft.network.protocol.login.PacketLoginInListener;
import net.minecraft.network.protocol.login.PacketLoginInStart;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.players.GameProfileBanEntry;
import net.minecraft.server.players.IpBanEntry;
import net.minecraft.server.players.JsonList;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.World;
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
		WorldServer worldserver = server.a(World.f);
		EntityPlayer entity = new EntityPlayer(
			server,
			worldserver,
			SpigotMiscUtils.toMojangGameProfile(connection.getLoginProfile())
		);
		return new JoinData(entity.getBukkitEntity(), entity) {
			@Override
			protected void close() {
			}
		};
	}

	protected static final String BAN_DATE_FORMAT_STRING = "yyyy-MM-dd 'at' HH:mm:ss z";
	@SuppressWarnings("deprecation")
	@Override
	protected void checkBans(PlayerLoginEvent event, Object[] data) {
		PlayerList playerlist = server.ac();

		GameProfile mojangGameProfile = ((EntityPlayer) data[0]).fq();
		SocketAddress address = networkManager.getAddress();

		GameProfileBanEntry profileban = ((JsonList<GameProfile, GameProfileBanEntry>) playerlist.f()).b(mojangGameProfile);
		if (profileban != null) {
			String reason = "You are banned from this server!\nReason: " + profileban.d();
			if (profileban.c() != null) {
				reason = reason + "\nYour ban will be removed on " +  new SimpleDateFormat(BAN_DATE_FORMAT_STRING).format(profileban.c());
			}
			event.disallow(PlayerLoginEvent.Result.KICK_BANNED, reason);
			return;
		}

		if (!playerlist.c(mojangGameProfile)) {
			event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, SpigotConfig.whitelistMessage);
			return;
		}

		IpBanEntry ipban = playerlist.g().b(address);
		if (ipban != null) {
			String reason = "Your IP address is banned from this server!\nReason: " + ipban.d();
			if (ipban.c() != null) {
				reason = reason + "\nYour ban will be removed on " + new SimpleDateFormat(BAN_DATE_FORMAT_STRING).format(ipban.c());
			}
			event.disallow(PlayerLoginEvent.Result.KICK_BANNED, reason);
			return;
		}

		if ((playerlist.j.size() >= playerlist.n()) && !playerlist.f(mojangGameProfile)) {
			event.disallow(PlayerLoginEvent.Result.KICK_FULL, SpigotConfig.serverFullMessage);
		}
	}

	@Override
	protected void joinGame(Object[] data) {
		server.ac().a((NetworkManager) networkManager.unwrap(), (EntityPlayer) data[0]);
	}

	@Override
	public void a(IChatBaseComponent ichatbasecomponent) {
		Bukkit.getLogger().info(getConnectionRepr() + " lost connection: " + ichatbasecomponent.a());
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
	public void a(ServerboundPongPacket p0) {
	}

	@Override
	public NetworkManager a() {
		return ((SpigotNetworkManagerWrapper) this.networkManager).unwrap();
	}

}
