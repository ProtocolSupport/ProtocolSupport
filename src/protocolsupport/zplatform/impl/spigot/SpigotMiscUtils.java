package protocolsupport.zplatform.impl.spigot;

import java.security.KeyPair;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_12_R1.util.CraftIconCache;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.CachedServerIcon;
import org.spigotmc.SpigotConfig;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.mojang.authlib.properties.Property;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import net.minecraft.server.v1_12_R1.AxisAlignedBB;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.EnumProtocol;
import net.minecraft.server.v1_12_R1.MinecraftServer;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.WorldServer;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPacketPrepender;
import protocolsupport.protocol.pipeline.IPacketSplitter;
import protocolsupport.protocol.pipeline.common.PacketDecrypter;
import protocolsupport.protocol.pipeline.common.PacketEncrypter;
import protocolsupport.protocol.utils.MinecraftEncryption;
import protocolsupport.protocol.utils.authlib.GameProfile;
import protocolsupport.zplatform.PlatformUtils;
import protocolsupport.zplatform.impl.spigot.injector.network.SpigotNettyInjector;
import protocolsupport.zplatform.impl.spigot.itemstack.SpigotNBTTagCompoundWrapper;
import protocolsupport.zplatform.impl.spigot.network.SpigotChannelHandlers;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketCompressor;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketDecompressor;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotWrappedPrepender;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotWrappedSplitter;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public class SpigotMiscUtils implements PlatformUtils {

	public static NetworkState protocolToNetState(EnumProtocol state) {
		switch (state) {
			case HANDSHAKING: {
				return NetworkState.HANDSHAKING;
			}
			case PLAY: {
				return NetworkState.PLAY;
			}
			case LOGIN: {
				return NetworkState.LOGIN;
			}
			case STATUS: {
				return NetworkState.STATUS;
			}
			default: {
				throw new IllegalArgumentException("Unknown state " + state);
			}
		}
	}

	public static EnumProtocol netStateToProtocol(NetworkState state)  {
		switch (state) {
			case HANDSHAKING: {
				return EnumProtocol.HANDSHAKING;
			}
			case PLAY: {
				return EnumProtocol.PLAY;
			}
			case LOGIN: {
				return EnumProtocol.LOGIN;
			}
			case STATUS: {
				return EnumProtocol.STATUS;
			}
			default: {
				throw new IllegalArgumentException("Unknown state " + state);
			}
		}
	}

	public static MinecraftServer getServer() {
		return ((CraftServer) Bukkit.getServer()).getServer();
	}

	public static com.mojang.authlib.GameProfile toMojangGameProfile(GameProfile profile) {
		com.mojang.authlib.GameProfile mojangGameProfile = new com.mojang.authlib.GameProfile(profile.getUUID(), profile.getName());
		com.mojang.authlib.properties.PropertyMap mojangProperties = mojangGameProfile.getProperties();
		profile.getProperties().entrySet().forEach(entry -> mojangProperties.putAll(
			entry.getKey(),
			entry.getValue().stream()
			.map(p -> new Property(p.getName(), p.getValue(), p.getSignature()))
			.collect(Collectors.toList()))
		);
		return mojangGameProfile;
	}

	@Override
	public ItemStack createItemStackFromNBTTag(NBTTagCompoundWrapper tag) {
		return CraftItemStack.asCraftMirror(new net.minecraft.server.v1_12_R1.ItemStack(((SpigotNBTTagCompoundWrapper) tag).unwrap()));
	}

	@Override
	public NBTTagCompoundWrapper createNBTTagFromItemStack(ItemStack itemstack) {
		net.minecraft.server.v1_12_R1.ItemStack nmsitemstack = CraftItemStack.asNMSCopy(itemstack);
		NBTTagCompound compound = new NBTTagCompound();
		nmsitemstack.save(compound);
		return SpigotNBTTagCompoundWrapper.wrap(compound);
	}

	@Override
	public List<Player> getNearbyPlayers(Location location, double rX, double rY, double rZ) {
		WorldServer nmsWorld = ((CraftWorld) location.getWorld()).getHandle();
		double locX = location.getX();
		double locY = location.getY();
		double locZ = location.getZ();
		List<EntityPlayer> nmsPlayers = nmsWorld.a(EntityPlayer.class, new AxisAlignedBB(locX - rX, locY - rY, locZ - rZ, locX + rX, locY + rY, locZ + rZ), Predicates.alwaysTrue());
		return Lists.transform(nmsPlayers, EntityPlayer::getBukkitEntity);
	}

	@Override
	public String getOutdatedServerMessage() {
		return SpigotConfig.outdatedServerMessage;
	}

	@Override
	public String getOutdatedClientMessage() {
		return SpigotConfig.outdatedClientMessage;
	}

	@Override
	public boolean isRunning() {
		return getServer().isRunning();
	}

	@Override
	public boolean isProxyEnabled() {
		return SpigotConfig.bungee;
	}

	@Override
	public boolean isProxyPreventionEnabled() {
		return getServer().ac();
	}

	@Override
	public boolean isDebugging() {
		return getServer().isDebugging();
	}

	@Override
	public void enableDebug() {
		getServer().getPropertyManager().setProperty("debug", Boolean.TRUE);
	}

	@Override
	public void disableDebug() {
		getServer().getPropertyManager().setProperty("debug", Boolean.FALSE);
	}

	@Override
	public int getCompressionThreshold() {
		return getServer().aG();
	}

	@Override
	public KeyPair getEncryptionKeyPair() {
		return getServer().O();
	}

	@Override
	public <V> FutureTask<V> callSyncTask(Callable<V> call) {
		FutureTask<V> task = new FutureTask<>(call);
		getServer().processQueue.add(task);
		return task;
	}

	@Override
	public String getModName() {
		return getServer().getServerModName();
	}

	@Override
	public String getVersionName() {
		return getServer().getVersion();
	}

	@Override
	public String convertBukkitIconToBase64(CachedServerIcon icon) {
		if (icon == null) {
			return null;
		}
		if (!(icon instanceof CraftIconCache)) {
			throw new IllegalArgumentException(icon + " was not created by " + CraftServer.class);
		}
		return ((CraftIconCache) icon).value;
	}

	@Override
	public String getReadTimeoutHandlerName() {
		return SpigotChannelHandlers.READ_TIMEOUT;
	}

	@Override
	public void enableCompression(ChannelPipeline pipeline, int compressionThreshold) {
		pipeline
		.addAfter(SpigotChannelHandlers.SPLITTER, "decompress", new SpigotPacketDecompressor(compressionThreshold))
		.addAfter(SpigotChannelHandlers.PREPENDER, "compress", new SpigotPacketCompressor(compressionThreshold));
	}

	@Override
	public void enableEncryption(ChannelPipeline pipeline, SecretKey key, boolean fullEncryption) {
		pipeline.addBefore(SpigotChannelHandlers.SPLITTER, ChannelHandlers.DECRYPT, new PacketDecrypter(MinecraftEncryption.getCipher(Cipher.DECRYPT_MODE, key)));
		if (fullEncryption) {
			pipeline.addBefore(SpigotChannelHandlers.PREPENDER, ChannelHandlers.ENCRYPT, new PacketEncrypter(MinecraftEncryption.getCipher(Cipher.ENCRYPT_MODE, key)));
		}
	}

	@Override
	public void setFraming(ChannelPipeline pipeline, IPacketSplitter splitter, IPacketPrepender prepender) {
		((SpigotWrappedSplitter) pipeline.get(SpigotChannelHandlers.SPLITTER)).setRealSplitter(splitter);
		((SpigotWrappedPrepender) pipeline.get(SpigotChannelHandlers.PREPENDER)).setRealPrepender(prepender);
	}

	@Override
	public EventLoopGroup getServerEventLoop() {
		return SpigotNettyInjector.getServerEventLoop();
	}

}
