package protocolsupport.zplatform.impl.glowstone;

import java.security.KeyPair;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.CachedServerIcon;

import com.destroystokyo.paper.profile.ProfileProperty;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import net.glowstone.GlowServer;
import net.glowstone.entity.meta.profile.GlowPlayerProfile;
import net.glowstone.io.nbt.NbtSerialization;
import net.glowstone.net.pipeline.CompressionHandler;
import net.glowstone.net.pipeline.MessageHandler;
import net.glowstone.net.protocol.ProtocolType;
import net.glowstone.util.GlowServerIcon;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPacketPrepender;
import protocolsupport.protocol.pipeline.IPacketSplitter;
import protocolsupport.protocol.pipeline.common.PacketDecrypter;
import protocolsupport.protocol.pipeline.common.PacketEncrypter;
import protocolsupport.protocol.utils.MinecraftEncryption;
import protocolsupport.protocol.utils.authlib.GameProfile;
import protocolsupport.utils.ReflectionUtils;
import protocolsupport.zplatform.PlatformUtils;
import protocolsupport.zplatform.impl.glowstone.injector.GlowStoneNettyInjector;
import protocolsupport.zplatform.impl.glowstone.itemstack.GlowStoneNBTTagCompoundWrapper;
import protocolsupport.zplatform.impl.glowstone.network.GlowStoneChannelHandlers;
import protocolsupport.zplatform.impl.glowstone.network.pipeline.GlowStoneFramingHandler;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class GlowStoneMiscUtils implements PlatformUtils {

	public static GlowServer getServer() {
		return ((GlowServer) Bukkit.getServer());
	}

	public static GlowPlayerProfile toGlowStoneGameProfile(GameProfile profile) {
		return new GlowPlayerProfile(
			profile.getName(), profile.getUUID(),
			profile.getProperties().values().stream()
			.flatMap(Set::stream)
			.map(property -> new ProfileProperty(property.getName(), property.getValue(), property.getSignature()))
			.collect(Collectors.toList()),
		false);
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

	public static MessageHandler getNetworkManager(ChannelPipeline pipeline) {
		return (MessageHandler) pipeline.get(GlowStoneChannelHandlers.NETWORK_MANAGER);
	}

	@Override
	public List<Player> getNearbyPlayers(Location location, double x, double y, double z) {
		return location.getWorld().getPlayers().stream().filter(player -> {
			Location playerLocation = player.getLocation();
			return
				(playerLocation.getX() >= (location.getX() - x)) &&
				(playerLocation.getY() >= (location.getY() - y)) &&
				(playerLocation.getZ() >= (location.getZ() - z)) &&
				(playerLocation.getX() <= (location.getX() + x)) &&
				(playerLocation.getY() <= (location.getY() + y)) &&
				(playerLocation.getZ() <= (location.getZ() + z));
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
	public String getOutdatedClientMessage() {
		return "Outdated client! I\'m running {0}";
	}

	@Override
	public boolean isRunning() {
		return true;
	}

	@Override
	public boolean isProxyEnabled() {
		return getServer().getProxySupport();
	}

	@Override
	public boolean isProxyPreventionEnabled() {
		return getServer().shouldPreventProxy();
	}

	private boolean debug = false;

	@Override
	public boolean isDebugging() {
		return debug;
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
		try {
			return (String) ReflectionUtils.getField(GlowServer.class, "GAME_VERSION").get(null);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException("Unable to determive version name", e);
		}
	}

	@Override
	public String convertBukkitIconToBase64(CachedServerIcon icon) {
		return ((GlowServerIcon) icon).getData();
	}

	@Override
	public String getReadTimeoutHandlerName() {
		return GlowStoneChannelHandlers.READ_TIMEOUT;
	}

	@Override
	public void enableCompression(ChannelPipeline pipeline, int compressionThreshold) {
		pipeline.addAfter(GlowStoneChannelHandlers.FRAMING, "compression", new CompressionHandler(compressionThreshold));
	}

	@Override
	public void enableEncryption(ChannelPipeline pipeline, SecretKey key, boolean fullEncryption) {
		pipeline.addBefore(GlowStoneChannelHandlers.FRAMING, ChannelHandlers.DECRYPT, new PacketDecrypter(MinecraftEncryption.getCipher(Cipher.DECRYPT_MODE, key)));
		if (fullEncryption) {
			pipeline.addBefore(GlowStoneChannelHandlers.FRAMING, ChannelHandlers.ENCRYPT, new PacketEncrypter(MinecraftEncryption.getCipher(Cipher.ENCRYPT_MODE, key)));
		}
	}

	@Override
	public void setFraming(ChannelPipeline pipeline, IPacketSplitter splitter, IPacketPrepender prepender) {
		((GlowStoneFramingHandler) pipeline.get(GlowStoneChannelHandlers.FRAMING)).setRealFraming(prepender, splitter);
	}

	@Override
	public EventLoopGroup getServerEventLoop() {
		return GlowStoneNettyInjector.getServerEventLoop();
	}

}
