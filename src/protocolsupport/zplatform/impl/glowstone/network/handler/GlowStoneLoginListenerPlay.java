package protocolsupport.zplatform.impl.glowstone.network.handler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.BanList;
import org.bukkit.event.player.PlayerLoginEvent;

import com.flowpowered.network.Message;

import net.glowstone.EventFactory;
import net.glowstone.GlowServer;
import net.glowstone.entity.GlowPlayer;
import net.glowstone.entity.meta.profile.GlowPlayerProfile;
import net.glowstone.io.PlayerDataService.PlayerReader;
import net.glowstone.net.GlowSession;
import net.glowstone.net.message.play.game.UserListItemMessage;
import protocolsupport.protocol.packet.handler.AbstractLoginListenerPlay;
import protocolsupport.protocol.utils.authlib.GameProfile;
import protocolsupport.utils.ReflectionUtils;
import protocolsupport.zplatform.impl.glowstone.GlowStoneMiscUtils;
import protocolsupport.zplatform.impl.glowstone.network.GlowStoneNetworkManagerWrapper;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class GlowStoneLoginListenerPlay extends AbstractLoginListenerPlay implements GlowStoneTickableListener {

	protected static final GlowServer server = GlowStoneMiscUtils.getServer();

	protected GlowStoneLoginListenerPlay(NetworkManagerWrapper networkmanager, GameProfile profile, boolean onlineMode, String hostname) {
		super(networkmanager, profile, onlineMode, hostname);
	}

	@Override
	protected JoinData createJoinData() {
		GlowPlayerProfile glowstoneProfile = GlowStoneMiscUtils.toGlowStoneGameProfile(profile);
		PlayerReader reader = server.getPlayerDataService().beginReadingData(glowstoneProfile.getUniqueId());
		GlowPlayer player = new GlowPlayer(((GlowStoneNetworkManagerWrapper) networkManager).getSession(), glowstoneProfile, reader);
		return new JoinData(player, player, reader) {
			@Override
			protected void close() {
				reader.close();
			}
		};
	}

	@Override
	protected void checkBans(PlayerLoginEvent event, Object[] data) {
		String name = event.getPlayer().getName();
		String hostaddr = event.getAddress().getHostAddress();
		final BanList nameBans = server.getBanList(BanList.Type.NAME);
		final BanList ipBans = server.getBanList(BanList.Type.IP);
		if (nameBans.isBanned(name)) {
			event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "Banned: " + nameBans.getBanEntry(name).getReason());
		} else if (ipBans.isBanned(hostaddr)) {
			event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "Banned: " + ipBans.getBanEntry(hostaddr).getReason());
		} else if (server.hasWhitelist() && !event.getPlayer().isWhitelisted()) {
			event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, "You are not whitelisted on this server.");
		} else if (server.getOnlinePlayers().size() >= server.getMaxPlayers()) {
			event.disallow(PlayerLoginEvent.Result.KICK_FULL, "The server is full (" + server.getMaxPlayers() + " players).");
		}
	}

	private static final Field playerField = ReflectionUtils.getField(GlowSession.class, "player");
	private static final Field onlineField = ReflectionUtils.getField(GlowSession.class, "online");

	@Override
	protected void joinGame(Object[] data) {
		networkManager.setPacketListener(null);

		GlowPlayer glowplayer = (GlowPlayer) data[0];
		PlayerReader reader = (PlayerReader) data[1];
		GlowSession session = ((GlowStoneNetworkManagerWrapper) networkManager).getSession();

		try {
			playerField.set(session, glowplayer);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalStateException("Unable to set GlowSession player field", e);
		}

		glowplayer.join(session, reader);
		glowplayer.getWorld().getRawPlayers().add(glowplayer);

		try {
			onlineField.set(session, true);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalStateException("Unable to set GlowSession online field", e);
		}

		GlowServer.logger.info(glowplayer.getName() + " [" + networkManager.getAddress() + "] connected, UUID: " + glowplayer.getUniqueId());

		String message = EventFactory.onPlayerJoin(glowplayer).getJoinMessage();
		if ((message != null) && !message.isEmpty()) {
			server.broadcastMessage(message);
		}
		Message addMessage = new UserListItemMessage(UserListItemMessage.Action.ADD_PLAYER, glowplayer.getUserListEntry());
		List<UserListItemMessage.Entry> entries = new ArrayList<UserListItemMessage.Entry>();
		for (GlowPlayer other : server.getRawOnlinePlayers()) {
			if (!other.equals(glowplayer) && other.canSee(glowplayer)) {
				other.getSession().send(addMessage);
			}
			if (glowplayer.canSee(other)) {
				entries.add(other.getUserListEntry());
			}
		}
		session.send(new UserListItemMessage(UserListItemMessage.Action.ADD_PLAYER, entries));
	}

}
