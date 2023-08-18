package protocolsupport.protocol.utils.pingresponse;

import java.net.InetSocketAddress;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.bukkit.Bukkit;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spigotmc.SpigotConfig;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import com.destroystokyo.paper.network.StatusClient;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;

import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import protocolsupport.api.Connection;
import protocolsupport.api.events.ServerPingResponseEvent;
import protocolsupport.api.events.ServerPingResponseEvent.ProtocolInfo;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.zplatform.ServerPlatform;

public class PaperPingResponseHandler extends PingResponseHandler {

	static {
		try {
			Class.forName(PaperServerListPingEvent.class.getName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Init error, not paper", e);
		}
	}

	@Override
	public ServerPingResponseEvent createResponse(Connection connection) {
		PaperServerListPingEvent bevent = new PaperServerListPingEvent(
			new StatusClientImpl(connection),
			Bukkit.motd(),
			Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers(),
			createServerVersionString(), connection.getVersion().getId(),
			Bukkit.getServerIcon()
		);
		List<PlayerProfile> playerSample = bevent.getPlayerSample();
		Bukkit.getOnlinePlayers().stream()
		.limit(SpigotConfig.playerSample)
		.map(player -> new NameUUIDPlayerProfile(player.getUniqueId(), player.getName()))
		.forEach(playerSample::add);
		Bukkit.getPluginManager().callEvent(bevent);

		ServerPingResponseEvent revent = new ServerPingResponseEvent(
			connection,
			new ProtocolInfo(bevent.getProtocolVersion(), bevent.getVersion()),
			bevent.getServerIcon() != null ? ServerPlatform.get().getMiscUtils().convertBukkitIconToBase64(bevent.getServerIcon()) : null,
			ChatCodec.deserializeTree(GsonComponentSerializer.gson().serializeToTree(bevent.motd())),
			bevent.getNumPlayers(), bevent.getMaxPlayers(),
			bevent.getPlayerSample().stream().map(PlayerProfile::getName).toList()
		);
		Bukkit.getPluginManager().callEvent(revent);

		return revent;
	}

	protected static class StatusClientImpl implements StatusClient {

		protected final Connection connection;
		public StatusClientImpl(Connection connection) {
			this.connection = connection;
		}

		@Override
		public InetSocketAddress getAddress() {
			return connection.getAddress();
		}

		@Override
		public int getProtocolVersion() {
			return connection.getVersion().getId();
		}

		@Override
		public InetSocketAddress getVirtualHost() {
			return connection.getVirtualHost();
		}

	}

	protected static class NameUUIDPlayerProfile implements PlayerProfile {

		protected UUID uuid;
		protected String name;

		protected NameUUIDPlayerProfile(UUID uuid, String name) {
			this.uuid = uuid;
			this.name = name;
		}

		@Override
		public UUID getUniqueId() {
			return uuid;
		}

		@Override
		public UUID getId() {
			return uuid;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String setName(String name) {
			String currentName = this.name;
			this.name = name;
			return currentName;
		}

		@Override
		public UUID setId(UUID uuid) {
			UUID currentId = this.uuid;
			this.uuid = uuid;
			return currentId;
		}

		@Override
		public @NotNull CompletableFuture<PlayerProfile> update() {
			return CompletableFuture.completedFuture(this);
		}

		@Override
		public boolean isComplete() {
			return false;
		}

		@Override
		public boolean completeFromCache() {
			return false;
		}

		@Override
		public boolean complete(boolean arg0) {
			return false;
		}

		@Override
		public boolean complete(boolean arg0, boolean arg1) {
			return false;
		}

		@Override
		public boolean completeFromCache(boolean arg0) {
			return false;
		}

		@Override
		public boolean completeFromCache(boolean arg0, boolean arg1) {
			return false;
		}

		@Override
		public Set<ProfileProperty> getProperties() {
			return Collections.emptySet();
		}

		@Override
		public boolean hasProperty(String propertyName) {
			return false;
		}

		@Override
		public boolean removeProperty(String propertyName) {
			return false;
		}

		protected static final PlayerTextures TEXTURES_NOOP = new PlayerTextures() {
			@Override
			public void setSkin(@Nullable URL skinUrl, @Nullable SkinModel skinModel) {
			}

			@Override
			public void setSkin(@Nullable URL skinUrl) {
			}

			@Override
			public void setCape(@Nullable URL capeUrl) {
			}

			@Override
			public boolean isSigned() {
				return false;
			}

			@Override
			public boolean isEmpty() {
				return true;
			}

			@Override
			public long getTimestamp() {
				return 0;
			}

			@Override
			public SkinModel getSkinModel() {
				return SkinModel.CLASSIC;
			}

			@Override
			public URL getSkin() {
				return null;
			}

			@Override
			public URL getCape() {
				return null;
			}

			@Override
			public void clear() {
			}
		};

		@Override
		public PlayerTextures getTextures() {
			return TEXTURES_NOOP;
		}

		@Override
		public void setTextures(PlayerTextures textures) {
		}

		@Override
		public void clearProperties() {
		}

		@Override
		public void setProperties(Collection<ProfileProperty> arg0) {
		}

		@Override
		public void setProperty(ProfileProperty arg0) {
		}

		@Override
		public Map<String, Object> serialize() {
			return Collections.emptyMap();
		}

		@Override
		public NameUUIDPlayerProfile clone() {
			return new NameUUIDPlayerProfile(uuid, name);
		}

	}

}
