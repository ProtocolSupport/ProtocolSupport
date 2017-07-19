package protocolsupport.zplatform.impl.glowstone;

import java.security.KeyPair;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.CachedServerIcon;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.glowstone.GlowServer;
import net.glowstone.entity.meta.profile.PlayerProfile;
import net.glowstone.entity.meta.profile.PlayerProperty;
import net.glowstone.io.nbt.NbtSerialization;
import net.glowstone.net.protocol.ProtocolType;
import net.glowstone.util.GlowServerIcon;
import protocolsupport.protocol.pipeline.IPacketPrepender;
import protocolsupport.protocol.pipeline.IPacketSplitter;
import protocolsupport.protocol.utils.authlib.GameProfile;
import protocolsupport.zplatform.PlatformUtils;
import protocolsupport.zplatform.impl.glowstone.itemstack.GlowStoneNBTTagCompoundWrapper;
import protocolsupport.zplatform.impl.glowstone.network.GlowStoneChannelHandlers;
import protocolsupport.zplatform.impl.glowstone.network.GlowStoneNetworkManagerWrapper;
import protocolsupport.zplatform.impl.glowstone.network.pipeline.GlowStoneFramingHandler;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.network.NetworkManagerWrapper;
import protocolsupport.zplatform.network.NetworkState;

public class GlowStoneMiscUtils implements PlatformUtils {

	public static GlowServer getServer() {
		return ((GlowServer) Bukkit.getServer());
	}

	public static PlayerProfile toGlowStoneGameProfile(GameProfile profile) {
		return new PlayerProfile(
			profile.getName(), profile.getUUID(),
			profile.getProperties().values()
			.stream()
			.map(property -> new PlayerProperty(property.getName(), property.getValue(), property.getSignature()))
			.collect(Collectors.toList())
		);
	}

	public static ProtocolType netStateToProtocol(NetworkState type) {
		switch (type) {
			case HANDSHAKING: {
				return ProtocolType.HANDSHAKE;
			}
			case PLAY: {
				return ProtocolType.PLAY;
			}
			case LOGIN: {
				return ProtocolType.LOGIN;
			}
			case STATUS: {
				return ProtocolType.STATUS;
			}
			default: {
				throw new IllegalArgumentException(MessageFormat.format("Unknown state {0}", type));
			}
		}
	}

	public static NetworkState protocolToNetState(ProtocolType type) {
		switch (type) {
			case HANDSHAKE: {
				return NetworkState.HANDSHAKING;
			}
			case LOGIN: {
				return NetworkState.LOGIN;
			}
			case STATUS: {
				return NetworkState.STATUS;
			}
			case PLAY: {
				return NetworkState.PLAY;
			}
			default: {
				throw new IllegalArgumentException(MessageFormat.format("Unknown protocol {0}", type));
			}
		}
	}

	@Override
	public List<Player> getNearbyPlayers(Location location, double x, double y, double z) {
		return location.getWorld().getPlayers().stream().filter(player -> {
			Location playerLocation = player.getLocation();
			return
				playerLocation.getX() >= location.getX() - x &&
				playerLocation.getY() >= location.getY() - y &&
				playerLocation.getZ() >= location.getZ() - z &&
				playerLocation.getX() <= location.getX() + x &&
				playerLocation.getY() <= location.getY() + y &&
				playerLocation.getZ() <= location.getZ() + z;
		}).collect(Collectors.toList());
	}

	@Override
	public ItemStack createItemStackFromNBTTag(NBTTagCompoundWrapper tag) {
		return NbtSerialization.readItem(((GlowStoneNBTTagCompoundWrapper) tag).unwrap());
	}

	@Override
	public NBTTagCompoundWrapper createNBTTagFromItemStack(ItemStack itemstack) {
		return GlowStoneNBTTagCompoundWrapper.wrap(NbtSerialization.writeItem(itemstack, 0));
	}

	@Override
	public String getOutdatedServerMessage() {
		return "Outdated server! I\'m running {0}";
	}

	@Override
	public boolean isRunning() {
		return true;
	}

	@Override
	public boolean isProxyEnabled() {
		return getServer().getProxySupport();
	}

	private boolean debug = false;

	@Override
	public boolean isDebugging() {
		return debug == true;
	}

	@Override
	public void enableDebug() {
		debug = true;
	}

	@Override
	public void disableDebug() {
		debug = false;
	}

	@Override
	public int getCompressionThreshold() {
		return getServer().getCompressionThreshold();
	}

	@Override
	public KeyPair getEncryptionKeyPair() {
		return getServer().getKeyPair();
	}

	@Override
	public <V> FutureTask<V> callSyncTask(Callable<V> call) {
		FutureTask<V> task = new FutureTask<>(call);
		Bukkit.getScheduler().scheduleSyncDelayedTask(null, task);
		return task;
	}

	@Override
	public String getModName() {
		return "GlowStone";
	}

	@Override
	public String getVersionName() {
		return GlowServer.GAME_VERSION;
	}

	@Override
	public String convertBukkitIconToBase64(CachedServerIcon icon) {
		return ((GlowServerIcon) icon).getData();
	}

	@Override
	public NetworkState getNetworkStateFromChannel(Channel channel) {
		return GlowStoneNetworkManagerWrapper.getFromChannel(channel).getProtocol();
	}

	@Override
	public NetworkManagerWrapper getNetworkManagerFromChannel(Channel channel) {
		return GlowStoneNetworkManagerWrapper.getFromChannel(channel);
	}

	@Override
	public String getReadTimeoutHandlerName() {
		return GlowStoneChannelHandlers.READ_TIMEOUT;
	}

	@Override
	public String getSplitterHandlerName() {
		return GlowStoneChannelHandlers.FRAMING;
	}

	@Override
	public String getPrependerHandlerName() {
		return GlowStoneChannelHandlers.FRAMING;
	}

	@Override
	public void setFraming(ChannelPipeline pipeline, IPacketSplitter splitter, IPacketPrepender prepender) {
		((GlowStoneFramingHandler) pipeline.get(GlowStoneChannelHandlers.FRAMING)).setRealFraming(prepender, splitter);
	}

}
