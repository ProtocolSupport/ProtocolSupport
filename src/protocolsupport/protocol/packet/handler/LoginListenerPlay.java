package protocolsupport.protocol.packet.handler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;

import com.mojang.authlib.GameProfile;

import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.server.v1_10_R1.ChatComponentText;
import net.minecraft.server.v1_10_R1.EntityPlayer;
import net.minecraft.server.v1_10_R1.EnumDifficulty;
import net.minecraft.server.v1_10_R1.EnumGamemode;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.ITickable;
import net.minecraft.server.v1_10_R1.LoginListener;
import net.minecraft.server.v1_10_R1.MinecraftServer;
import net.minecraft.server.v1_10_R1.NetworkManager;
import net.minecraft.server.v1_10_R1.PacketDataSerializer;
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
import net.minecraft.server.v1_10_R1.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_10_R1.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_10_R1.PacketPlayOutLogin;
import net.minecraft.server.v1_10_R1.WorldType;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.PlayerLoginFinishEvent;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.utils.Utils;

public class LoginListenerPlay extends LoginListener implements PacketListenerPlayIn, ITickable {

	protected static final Logger logger = LogManager.getLogger();
	protected static final MinecraftServer server = Utils.getServer();

	private final GameProfile profile;
	private final boolean onlineMode;

	protected boolean ready;

	public LoginListenerPlay(NetworkManager networkmanager, GameProfile profile, boolean onlineMode, String hostname) {
		super(server, networkmanager);
		this.profile = profile;
		this.onlineMode = onlineMode;
		this.hostname = hostname;
	}

	public void finishLogin() {
		//send login success
		networkManager.sendPacket(new PacketLoginOutSuccess(profile));
		//tick connection keep now
		keepConnection();
		//now fire login event
		PlayerLoginFinishEvent event = new PlayerLoginFinishEvent((InetSocketAddress) networkManager.getSocketAddress(), profile.getName(), profile.getId(), onlineMode);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isLoginDenied()) {
			disconnect(event.getDenyLoginMessage());
			return;
		}
		ready = true;
	}

	protected int keepAliveTicks = 1;

	@Override
	public void E_() {
		if (keepAliveTicks++ % 80 == 0) {
			keepConnection();
		}
		if (ready) {
			ready = false;
			b();
		}
	}

	private static final PacketDataSerializer fake = new PacketDataSerializer(Unpooled.EMPTY_BUFFER);
	private void keepConnection() {
		//custom payload does nothing on a client when sent with invalid tag, but it resets client readtimeouthandler, and that is exactly what we need
		networkManager.sendPacket(new PacketPlayOutCustomPayload("PSFake", fake));
		//we also need to reset server readtimeouthandler
		ChannelHandlers.getTimeoutHandler(networkManager.channel.pipeline()).setLastRead();
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

	@Override
	public void disconnect(final String s) {
		try {
			logger.info("Disconnecting " + d() + ": " + s);
			if (ProtocolSupportAPI.getProtocolVersion(networkManager.getSocketAddress()).isBetween(ProtocolVersion.MINECRAFT_1_7_5, ProtocolVersion.MINECRAFT_1_10)) {
				//first send join game that will make client actually switch to game state
				networkManager.sendPacket(new PacketPlayOutLogin(0, EnumGamemode.NOT_SET, false, 0, EnumDifficulty.EASY, 60, WorldType.NORMAL, false));
				//send disconnect with a little delay
				networkManager.channel.eventLoop().schedule(new Runnable() {
					@Override
					public void run() {
						disconnect0(s);
					}
				}, 50, TimeUnit.MILLISECONDS);
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
			public void operationComplete(Future<? super Void> future) throws Exception {
				networkManager.close(chatcomponenttext);
			}
		});
	}

	public GameProfile getProfile() {
		return profile;
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
