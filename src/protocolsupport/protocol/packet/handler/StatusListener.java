package protocolsupport.protocol.packet.handler;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_11_R1.CraftServer;
import org.bukkit.craftbukkit.v1_11_R1.util.CraftIconCache;
import org.bukkit.entity.Player;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;
import org.spigotmc.SpigotConfig;

import com.mojang.authlib.GameProfile;

import io.netty.channel.ChannelFutureListener;
import net.minecraft.server.v1_11_R1.ChatComponentText;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.MinecraftServer;
import net.minecraft.server.v1_11_R1.NetworkManager;
import net.minecraft.server.v1_11_R1.PacketStatusInListener;
import net.minecraft.server.v1_11_R1.PacketStatusInPing;
import net.minecraft.server.v1_11_R1.PacketStatusInStart;
import net.minecraft.server.v1_11_R1.PacketStatusOutPong;
import net.minecraft.server.v1_11_R1.PacketStatusOutServerInfo;
import net.minecraft.server.v1_11_R1.ServerPing;
import net.minecraft.server.v1_11_R1.ServerPing.ServerData;
import net.minecraft.server.v1_11_R1.ServerPing.ServerPingPlayerSample;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.events.ServerPingResponseEvent;
import protocolsupport.api.events.ServerPingResponseEvent.ProtocolInfo;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.utils.ServerPlatformUtils;

public class StatusListener implements PacketStatusInListener {

	private static final IChatBaseComponent infoAlreadySent = new ChatComponentText("Status request has already been handled.");
	private static final MinecraftServer server = ServerPlatformUtils.getServer();

	private final NetworkManager networkManager;

	public StatusListener(NetworkManager networkmanager) {
		this.networkManager = networkmanager;
	}

	private static final UUID profileUUID = UUID.randomUUID();

	private boolean sentInfo = false;

	@Override
	public void a(PacketStatusInStart packetstatusinstart) {
		if (sentInfo) {
			networkManager.close(infoAlreadySent);
		}
		sentInfo = true;

		InetSocketAddress addr = (InetSocketAddress) networkManager.getSocketAddress();

		ArrayList<EntityPlayer> players = new ArrayList<>(server.getPlayerList().players);

		String motd = server.getMotd();
		int maxPlayers = server.getPlayerList().getMaxPlayers();

		InternalServerListPingEvent bevent = new InternalServerListPingEvent(addr.getAddress(), motd, maxPlayers, players);
		bevent.setServerIcon(Bukkit.getServerIcon());
		Bukkit.getPluginManager().callEvent(bevent);

		String icon = bevent.getIcon() != null ? bevent.getIcon().value : null;
		motd = bevent.getMotd();
		maxPlayers = bevent.getMaxPlayers();

		List<String> profiles = new ArrayList<>(players.size());
		for (EntityPlayer player : players) {
			profiles.add(player.getProfile().getName());
		}

		ServerPingResponseEvent revent = new ServerPingResponseEvent(
			ConnectionImpl.getFromChannel(networkManager.channel), new ProtocolInfo(ProtocolVersion.getLatest(), server.getServerModName() + " " + server.getVersion()),
			icon, motd, maxPlayers, profiles
		);
		Bukkit.getPluginManager().callEvent(revent);

		profiles = revent.getPlayers();
		icon = revent.getIcon();
		motd = revent.getMotd();
		maxPlayers = revent.getMaxPlayers();
		ProtocolInfo info = revent.getProtocolInfo();

		ServerPingPlayerSample playerSample = new ServerPingPlayerSample(maxPlayers, profiles.size());

		Collections.shuffle(profiles);
		GameProfile[] gprofiles = new GameProfile[profiles.size()];
		for (int i = 0; i < profiles.size(); i++) {
			gprofiles[i] = new GameProfile(profileUUID, profiles.get(i));
		}
		gprofiles = Arrays.copyOfRange(gprofiles, 0, Math.min(gprofiles.length, SpigotConfig.playerSample));
		playerSample.a(gprofiles);

		ServerPing serverping = new ServerPing();
		serverping.setFavicon(icon);
		serverping.setMOTD(new ChatComponentText(motd));
		serverping.setPlayerSample(playerSample);
		serverping.setServerInfo(new ServerData(info.getName(), info.getId()));

		networkManager.sendPacket(new PacketStatusOutServerInfo(serverping));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void a(PacketStatusInPing packetstatusinping) {
		networkManager.sendPacket(new PacketStatusOutPong(packetstatusinping.a()), ChannelFutureListener.CLOSE);
	}

	@Override
	public void a(IChatBaseComponent msg) {
	}

	public static class InternalServerListPingEvent extends ServerListPingEvent {

		private List<EntityPlayer> players;
		protected InternalServerListPingEvent(InetAddress address, String motd, int maxPlayers, List<EntityPlayer> players) {
			super(address, motd, maxPlayers);
			this.players = players;
		}

		protected CraftIconCache icon;

		public CraftIconCache getIcon() {
			return icon;
		}

		@Override
		public void setServerIcon(CachedServerIcon icon) {
			if ((icon != null) && !(icon instanceof CraftIconCache)) {
				throw new IllegalArgumentException(icon + " was not created by " + CraftServer.class);
			}
			this.icon = ((CraftIconCache) icon);
		}

		@Override
		public Iterator<Player> iterator() throws UnsupportedOperationException {
			return new Iterator<Player>() {
				Iterator<EntityPlayer> iterator = players.iterator();
				@Override
				public boolean hasNext() {
					return iterator.hasNext();
				}

				@Override
				public Player next() {
					return iterator.next().getBukkitEntity();
				}

				@Override
				public void remove() {
					iterator.remove();
				}
			};
		}

	}

}
