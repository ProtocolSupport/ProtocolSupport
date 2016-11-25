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
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
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
import protocolsupport.utils.nms.MinecraftServerWrapper;
import protocolsupport.utils.nms.NetworkManagerWrapper;

public class StatusListener implements PacketStatusInListener {

	private final NetworkManagerWrapper networkManager;
	public StatusListener(NetworkManagerWrapper networkmanager) {
		this.networkManager = networkmanager;
	}

	private static final UUID profileUUID = UUID.randomUUID();

	private boolean sentInfo = false;

	@Override
	public void a(PacketStatusInStart packetstatusinstart) {
		if (sentInfo) {
			networkManager.close("Status request has already been handled.");
		}
		sentInfo = true;

		InetSocketAddress addr = networkManager.getAddress();

		ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

		String motd = Bukkit.getMotd();
		int maxPlayers = Bukkit.getMaxPlayers();

		InternalServerListPingEvent bevent = new InternalServerListPingEvent(addr.getAddress(), motd, maxPlayers, players);
		bevent.setServerIcon(Bukkit.getServerIcon());
		Bukkit.getPluginManager().callEvent(bevent);

		String icon = bevent.getIcon() != null ? bevent.getIcon().value : null;
		motd = bevent.getMotd();
		maxPlayers = bevent.getMaxPlayers();

		List<String> profiles = new ArrayList<>(players.size());
		for (Player player : players) {
			profiles.add(player.getName());
		}

		ServerPingResponseEvent revent = new ServerPingResponseEvent(
			ConnectionImpl.getFromChannel(networkManager.getChannel()), new ProtocolInfo(ProtocolVersion.getLatest(), MinecraftServerWrapper.getModName() + " " + MinecraftServerWrapper.getVersionName()),
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

		private List<Player> players;
		protected InternalServerListPingEvent(InetAddress address, String motd, int maxPlayers, List<Player> players) {
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
			return players.iterator();
		}

	}

}
