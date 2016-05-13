package protocolsupport.protocol.transformer.mcpe.handler;

import java.lang.invoke.MethodHandle;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftInventoryPlayer;
import org.bukkit.craftbukkit.v1_9_R2.util.Waitable;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import com.mojang.authlib.GameProfile;

import protocolsupport.protocol.transformer.mcpe.PEPlayerInteractManager;
import protocolsupport.protocol.transformer.mcpe.PEPlayerInventory;
import protocolsupport.protocol.transformer.mcpe.UDPNetworkManager;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.PingPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.both.PongPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.LoginStatusPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.ServerHandshakePacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound.LoginStatusPacket.Status;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound.ClientConnectPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound.LoginPacket;
import protocolsupport.utils.Utils;

import net.minecraft.server.v1_9_R2.ChatComponentText;
import net.minecraft.server.v1_9_R2.ContainerPlayer;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.LoginListener;
import net.minecraft.server.v1_9_R2.MinecraftServer;
import net.minecraft.server.v1_9_R2.PacketListener;
import net.minecraft.server.v1_9_R2.PlayerInteractManager;

@SuppressWarnings("deprecation")
public class PELoginListener implements PacketListener {

	private static final MethodHandle playerInventoryFieldSetter = Utils.getFieldSetter(CraftHumanEntity.class, "inventory");
	private static final MethodHandle playerInteractManagerFieldSetter = Utils.getFieldSetter(EntityPlayer.class, "playerInteractManager");

	private UDPNetworkManager networkManager;
	public PELoginListener(UDPNetworkManager nm) {
		this.networkManager = nm;
	}

	private State state = State.INITIAL;

	@Override
	public void a(IChatBaseComponent msg) {
		networkManager.close(msg);
	}

	public void handleKeepALive(PingPacket pingpacket) {
		try {
			networkManager.sendPEPacket(new PongPacket(pingpacket.getKeepAliveId()));
		} catch (Exception e) {
			networkManager.close(new ChatComponentText(e.getMessage()));
			if (MinecraftServer.getServer().isDebugging()) {
				e.printStackTrace();
			}
		}
	}

	public void handleClientConnect(ClientConnectPacket packet) {
		Validate.isTrue(state == State.INITIAL, "Unexpected state, got: "+state+", expected: "+State.INITIAL);
		state = State.CONNECT;
		try {
			networkManager.sendPEPacket(new ServerHandshakePacket(networkManager.getClientAddress(), packet.getClientId(), packet.getClientId() + 1000L));
		} catch (Exception e) {
			networkManager.close(new ChatComponentText(e.getMessage()));
			if (MinecraftServer.getServer().isDebugging()) {
				e.printStackTrace();
			}
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
					final UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:"+loginPacket.getUserName()).getBytes(StandardCharsets.UTF_8));
					AsyncPlayerPreLoginEvent asyncEvent = new AsyncPlayerPreLoginEvent(username, networkManager.getClientAddress().getAddress(), uuid);
					Bukkit.getPluginManager().callEvent(asyncEvent);
					if (asyncEvent.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) {
						networkManager.close(new ChatComponentText(asyncEvent.getKickMessage()));
					}
					if (PlayerPreLoginEvent.getHandlerList().getRegisteredListeners().length != 0) {
						final PlayerPreLoginEvent event = new PlayerPreLoginEvent(username, networkManager.getClientAddress().getAddress(), uuid);
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
							networkManager.close(new ChatComponentText(event.getKickMessage()));
							return;
						}
					}
					networkManager.sendPEPacket(new LoginStatusPacket(Status.LOGIN_SUCCESS));
					MinecraftServer.getServer().processQueue.add(new Runnable() {
						@Override
						public void run() {
							GameProfile profile = new GameProfile(uuid, username);
							EntityPlayer player = MinecraftServer.getServer().getPlayerList().attemptLogin(
								new LoginListener(MinecraftServer.getServer(), networkManager) {
									@Override
									public void disconnect(String message) {
										networkManager.close(new ChatComponentText(message));
									}
								},
								profile,
								""
							);
							if (player != null) {
								try {
									player.inventory = new PEPlayerInventory(player);
									player.activeContainer = player.defaultContainer = new ContainerPlayer(player.inventory, true, player);
									playerInteractManagerFieldSetter.invokeExact(player, (PlayerInteractManager) new PEPlayerInteractManager(player));
									playerInventoryFieldSetter.invokeExact((CraftHumanEntity) player.getBukkitEntity(), new CraftInventoryPlayer(player.inventory));
									MinecraftServer.getServer().getPlayerList().a(networkManager, player);
									new PEPlayerConnection(MinecraftServer.getServer(), networkManager, player);
								} catch (Throwable t) {
									player.playerConnection.disconnect("Exception while logging in: "+t.getMessage());
									if (MinecraftServer.getServer().isDebugging()) {
										t.printStackTrace();
									}
								}
							}
						}
					});
				} catch (Throwable t) {
					networkManager.close(new ChatComponentText(t.getMessage()));
					if (MinecraftServer.getServer().isDebugging()) {
						t.printStackTrace();
					}
				}
			}
		}.start();
	}


	private static enum State {
		INITIAL, CONNECT, HANDSHAKE, FINISH;
	}

}
