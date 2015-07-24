package protocolsupport.protocol.transformer.mcpe.handler;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.util.Waitable;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import com.mojang.authlib.GameProfile;

import protocolsupport.protocol.transformer.mcpe.UDPNetworkManager;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.LoginStatusPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.PongPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.ServerHandshakePacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.LoginStatusPacket.Status;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound.ClientConnectPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound.LoginPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound.PingPacket;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.LoginListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketListener;

@SuppressWarnings("deprecation")
public class PELoginListener implements PacketListener {

	private UDPNetworkManager nm;
	public PELoginListener(UDPNetworkManager nm) {
		this.nm = nm;
	}

	private State state = State.INITIAL;

	@Override
	public void a(IChatBaseComponent msg) {
		nm.close(msg);
	}

	public void handleKeepALive(PingPacket pingpacket) {
		try {
			nm.sendPEPacket(new PongPacket(pingpacket.getKeepAliveId()));
		} catch (Exception e) {
			nm.close(new ChatComponentText(e.getMessage()));
		}
	}

	public void handleClientConnect(ClientConnectPacket packet) {
		Validate.isTrue(state == State.INITIAL, "Unexpected state, got: "+state+", expected: "+State.INITIAL);
		state = State.CONNECT;
		try {
			nm.sendPEPacket(new ServerHandshakePacket(nm.getClientAddress(), packet.getClientId(), packet.getClientId() + 1000L));
		} catch (Exception e) {
			nm.close(new ChatComponentText(e.getMessage()));
		}
	}

	public void handleHandshake() {
		Validate.isTrue(state == State.CONNECT, "Unexpected state, got: "+state+", expected: "+State.CONNECT);
		state = State.HANDSHAKE;
	}

	public void handleLoginPacket(final LoginPacket loginPacket) {
		Validate.isTrue(state == State.HANDSHAKE, "Unexpected state, got: "+state+", expected: "+State.HANDSHAKE);
		state = State.FINISH;
		new Thread() {
			@Override
			public void run() {
				try {
					final String username = loginPacket.getUserName();
					final UUID uuid = UUID.nameUUIDFromBytes(("MCPE:"+loginPacket.getUserName()).getBytes(StandardCharsets.UTF_8));
					AsyncPlayerPreLoginEvent asyncEvent = new AsyncPlayerPreLoginEvent(username, nm.getClientAddress().getAddress(), uuid);
					Bukkit.getPluginManager().callEvent(asyncEvent);
					if (asyncEvent.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) {
						nm.close(new ChatComponentText(asyncEvent.getKickMessage()));
					}
					if (PlayerPreLoginEvent.getHandlerList().getRegisteredListeners().length != 0) {
						final PlayerPreLoginEvent event = new PlayerPreLoginEvent(username, nm.getClientAddress().getAddress(), uuid);
						if (asyncEvent.getResult() != PlayerPreLoginEvent.Result.ALLOWED) {
							event.disallow(asyncEvent.getResult(), asyncEvent.getKickMessage());
						}
						final Waitable<PlayerPreLoginEvent.Result> waitable = new Waitable<PlayerPreLoginEvent.Result>() {
							@Override
							protected PlayerPreLoginEvent.Result evaluate() {
								Bukkit.getServer().getPluginManager().callEvent(event);
								return event.getResult();
							}
						};
						MinecraftServer.getServer().processQueue.add(waitable);
						if (waitable.get() != PlayerPreLoginEvent.Result.ALLOWED) {
							nm.close(new ChatComponentText(event.getKickMessage()));
							return;
						}
					}
					nm.sendPEPacket(new LoginStatusPacket(Status.LOGIN_SUCCESS));
					MinecraftServer.getServer().processQueue.add(new Runnable() {
						@Override
						public void run() {
							GameProfile profile = new GameProfile(uuid, username);
							EntityPlayer player = MinecraftServer.getServer().getPlayerList().attemptLogin(
								new LoginListener(MinecraftServer.getServer(), nm) {
									@Override
									public void disconnect(String message) {
										nm.close(new ChatComponentText(message));
									}
								},
								profile,
								""
							);
							if (player != null) {
								MinecraftServer.getServer().getPlayerList().a(nm, MinecraftServer.getServer().getPlayerList().processLogin(profile, player));
								new KickRoutePlayerConnection(MinecraftServer.getServer(), nm, player);
							}
						}
					});
				} catch (Throwable t) {
					nm.close(new ChatComponentText(t.getMessage()));
				}
			}
		}.start();
	}


	private static enum State {
		INITIAL, CONNECT, HANDSHAKE, FINISH;
	}

}
