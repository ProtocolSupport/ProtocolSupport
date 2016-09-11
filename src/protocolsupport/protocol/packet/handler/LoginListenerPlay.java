package protocolsupport.protocol.packet.handler;

import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;

import com.mojang.authlib.GameProfile;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.server.v1_10_R1.ChatComponentText;
import net.minecraft.server.v1_10_R1.EntityPlayer;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.ITickable;
import net.minecraft.server.v1_10_R1.LoginListener;
import net.minecraft.server.v1_10_R1.MinecraftServer;
import net.minecraft.server.v1_10_R1.NetworkManager;
import net.minecraft.server.v1_10_R1.PacketListenerPlayIn;
import net.minecraft.server.v1_10_R1.PacketLoginInEncryptionBegin;
import net.minecraft.server.v1_10_R1.PacketLoginInStart;
import net.minecraft.server.v1_10_R1.PacketLoginOutSuccess;
import net.minecraft.server.v1_10_R1.PacketPlayInAbilities;
import net.minecraft.server.v1_10_R1.PacketPlayInArmAnimation;
import net.minecraft.server.v1_10_R1.PacketPlayInBlockDig;
import net.minecraft.server.v1_10_R1.PacketPlayInBlockPlace;
import net.minecraft.server.v1_10_R1.PacketPlayInBoatMove;
import net.minecraft.server.v1_10_R1.PacketPlayInChat;
import net.minecraft.server.v1_10_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_10_R1.PacketPlayInCloseWindow;
import net.minecraft.server.v1_10_R1.PacketPlayInCustomPayload;
import net.minecraft.server.v1_10_R1.PacketPlayInEnchantItem;
import net.minecraft.server.v1_10_R1.PacketPlayInEntityAction;
import net.minecraft.server.v1_10_R1.PacketPlayInFlying;
import net.minecraft.server.v1_10_R1.PacketPlayInHeldItemSlot;
import net.minecraft.server.v1_10_R1.PacketPlayInKeepAlive;
import net.minecraft.server.v1_10_R1.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_10_R1.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_10_R1.PacketPlayInSettings;
import net.minecraft.server.v1_10_R1.PacketPlayInSpectate;
import net.minecraft.server.v1_10_R1.PacketPlayInSteerVehicle;
import net.minecraft.server.v1_10_R1.PacketPlayInTabComplete;
import net.minecraft.server.v1_10_R1.PacketPlayInTeleportAccept;
import net.minecraft.server.v1_10_R1.PacketPlayInTransaction;
import net.minecraft.server.v1_10_R1.PacketPlayInUpdateSign;
import net.minecraft.server.v1_10_R1.PacketPlayInUseEntity;
import net.minecraft.server.v1_10_R1.PacketPlayInUseItem;
import net.minecraft.server.v1_10_R1.PacketPlayInVehicleMove;
import net.minecraft.server.v1_10_R1.PacketPlayInWindowClick;
import net.minecraft.server.v1_10_R1.PacketPlayOutKeepAlive;
import net.minecraft.server.v1_10_R1.PacketPlayOutKickDisconnect;
import protocolsupport.api.events.PlayerLoginFinishEvent;
import protocolsupport.utils.Utils;

public class LoginListenerPlay extends LoginListener implements PacketListenerPlayIn, ITickable {

	protected static final Logger logger = LogManager.getLogger();
	protected static final MinecraftServer server = Utils.getServer();

	private final GameProfile profile;

	protected boolean ready;

	public LoginListenerPlay(NetworkManager networkmanager, GameProfile profile, String hostname) {
		super(server, networkmanager);
		this.profile = profile;
		this.hostname = hostname;
	}

	public void finishLogin() {
		networkManager.sendPacket(new PacketLoginOutSuccess(profile));

		PlayerLoginFinishEvent event = new PlayerLoginFinishEvent((InetSocketAddress) networkManager.getSocketAddress(), profile.getName(), profile.getId());
		Bukkit.getPluginManager().callEvent(event);
		if (event.isLoginDenied()) {
			disconnect(event.getDenyLoginMessage());
			return;
		}
		ready = true;
	}

	protected int keepAliveTicks = 0;

	@Override
	public void E_() {
		if (keepAliveTicks++ % 20 == 0) {
			networkManager.sendPacket(new PacketPlayOutKeepAlive(0));
		}
		if (ready) {
			ready = false;
			b();
		}
	}

	@Override
	public void b() {
		EntityPlayer loginplayer = server.getPlayerList().attemptLogin(this, profile, hostname);
		if (loginplayer != null) {
			server.getPlayerList().a(this.networkManager, loginplayer);
			ready = false;
		}
	}

	@Override
	public void a(final IChatBaseComponent ichatbasecomponent) {
		logger.info(d() + " lost connection: " + ichatbasecomponent.getText());
	}

	@Override
	public String d() {
		return profile + " (" + networkManager.getSocketAddress() + ")";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void disconnect(final String s) {
		try {
			logger.info("Disconnecting " + d() + ": " + s);
			final ChatComponentText chatcomponenttext = new ChatComponentText(s);
			networkManager.sendPacket(new PacketPlayOutKickDisconnect(chatcomponenttext), new GenericFutureListener<Future<? super Void>>() {
				@Override
				public void operationComplete(Future<? super Void> future) throws Exception {
					networkManager.close(chatcomponenttext);
				}
			});
		} catch (Exception exception) {
			logger.error("Error whilst disconnecting player", exception);
		}
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
