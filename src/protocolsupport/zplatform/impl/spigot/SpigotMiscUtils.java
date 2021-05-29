package protocolsupport.zplatform.impl.spigot;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.KeyPair;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftIconCache;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.CachedServerIcon;
import org.spigotmc.SpigotConfig;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.MultithreadEventLoopGroup;
import io.netty.channel.epoll.Epoll;
import net.minecraft.server.v1_16_R3.AxisAlignedBB;
import net.minecraft.server.v1_16_R3.DedicatedServer;
import net.minecraft.server.v1_16_R3.DedicatedServerProperties;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EnumProtocol;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_16_R3.Item;
import net.minecraft.server.v1_16_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_16_R3.NBTReadLimiter;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import net.minecraft.server.v1_16_R3.ServerConnection;
import net.minecraft.server.v1_16_R3.WorldServer;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.api.utils.Profile;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPacketPrepender;
import protocolsupport.protocol.pipeline.IPacketSplitter;
import protocolsupport.protocol.pipeline.common.PacketDecrypter;
import protocolsupport.protocol.pipeline.common.PacketEncrypter;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.serializer.DefaultNBTSerializer;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.MinecraftEncryption;
import protocolsupport.protocol.utils.authlib.LoginProfile;
import protocolsupport.utils.ReflectionUtils;
import protocolsupport.utils.UnchekedReflectionException;
import protocolsupport.zplatform.PlatformUtils;
import protocolsupport.zplatform.impl.spigot.network.SpigotChannelHandlers;
import protocolsupport.zplatform.impl.spigot.network.handler.SpigotHandshakeListener;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketCompressor;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketDecompressor;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotWrappedPrepender;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotWrappedSplitter;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotMiscUtils implements PlatformUtils {

	public static final DedicatedServer SERVER = ((CraftServer) Bukkit.getServer()).getServer();

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

	public static EnumProtocol netStateToProtocol(NetworkState state) {
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

	public static GameProfile toMojangGameProfile(LoginProfile profile) {
		GameProfile mojangGameProfile = new GameProfile(profile.getUUID(), profile.getName());
		PropertyMap mojangProperties = mojangGameProfile.getProperties();
		profile.getProperties().entrySet().forEach(entry -> mojangProperties.putAll(
			entry.getKey(),
			entry.getValue().stream()
			.map(p -> new Property(p.getName(), p.getValue(), p.getSignature()))
			.collect(Collectors.toList()))
		);
		return mojangGameProfile;
	}

	public static IChatBaseComponent toPlatformMessage(BaseComponent message) {
		return ChatSerializer.a(ChatAPI.toJSON(message));
	}

	@Override
	public ConnectionImpl getConnection(Player player) {
		if (player instanceof CraftPlayer) {
			PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
			if (connection != null) {
				Channel channel = connection.networkManager.channel;
				if (channel != null) {
					return ConnectionImpl.getFromChannel(channel);
				}
			}
		}
		return null;
	}

	@Override
	public AbstractHandshakeListener createHandshakeListener(NetworkManagerWrapper networkmanager) {
		return new SpigotHandshakeListener(networkmanager);
	}

	@Override
	public Profile createWrappedProfile(LoginProfile loginProfile, Player player) {
		return new SpigotWrappedGameProfile(loginProfile, ((CraftPlayer) player).getHandle().getProfile());
	}

	@Override
	public ItemStack createBukkitItemStackFromNetwork(NetworkItemStack stack) {
		net.minecraft.server.v1_16_R3.ItemStack nmsitemstack = new net.minecraft.server.v1_16_R3.ItemStack(Item.getById(stack.getTypeId()), stack.getAmount());
		NBTCompound rootTag = stack.getNBT();
		if (rootTag != null) {
			//TODO: a faster way to do that
			ByteBuf buffer = Unpooled.buffer();
			try {
				DefaultNBTSerializer.INSTANCE.serializeTag(new ByteBufOutputStream(buffer), rootTag);
				nmsitemstack.setTag(NBTCompressedStreamTools.a(new ByteBufInputStream(buffer), NBTReadLimiter.a));
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		return CraftItemStack.asCraftMirror(nmsitemstack);
	}

	@Override
	public NetworkItemStack createNetworkItemStackFromBukkit(ItemStack itemstack) {
		if ((itemstack == null) || (itemstack.getType() == Material.AIR)) {
			return NetworkItemStack.NULL;
		}
		NetworkItemStack networkItemStack = new NetworkItemStack();
		networkItemStack.setTypeId(ItemMaterialLookup.getRuntimeId(itemstack.getType()));
		networkItemStack.setAmount(itemstack.getAmount());
		if (itemstack.hasItemMeta()) {
			//TODO: a faster way to do that
			net.minecraft.server.v1_16_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemstack);
			ByteBuf buffer = Unpooled.buffer();
			try {
				NBTCompressedStreamTools.a(nmsItemStack.getTag(), (DataOutput) new ByteBufOutputStream(buffer));
				networkItemStack.setNBT((NBTCompound) DefaultNBTSerializer.INSTANCE.deserializeTag(new ByteBufInputStream(buffer)));
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
		return networkItemStack;
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
	public boolean isRunning() {
		return SERVER.isRunning();
	}

	@Override
	public boolean isProxyEnabled() {
		return SpigotConfig.bungee;
	}

	@Override
	public boolean isProxyPreventionEnabled() {
		return SERVER.W();
	}

	@Override
	public boolean isDebugging() {
		return SERVER.isDebugging();
	}

	@Override
	public void enableDebug() {
		try {
			ReflectionUtils.getField(DedicatedServerProperties.class, "debug").set(SERVER.getDedicatedServerProperties(), true);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new UnchekedReflectionException("Exception occured while enabled debug", e);
		}
	}

	@Override
	public void disableDebug() {
		try {
			ReflectionUtils.getField(DedicatedServerProperties.class, "debug").set(SERVER.getDedicatedServerProperties(), false);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new UnchekedReflectionException("Exception occured while disabling debug", e);
		}
	}

	@Override
	public int getCompressionThreshold() {
		return SERVER.ax();
	}

	@Override
	public KeyPair getEncryptionKeyPair() {
		return SERVER.getKeyPair();
	}

	@Override
	public <V> FutureTask<V> callSyncTask(Callable<V> call) {
		FutureTask<V> task = new FutureTask<>(call);
		SERVER.processQueue.add(task);
		return task;
	}

	@Override
	public String getModName() {
		return SERVER.getServerModName();
	}

	@Override
	public String getVersionName() {
		return SERVER.getVersion();
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
	public MultithreadEventLoopGroup getServerIOEventLoopGroup() {
		if (Epoll.isAvailable() && SERVER.l()) {
			return ServerConnection.b.a();
		} else {
			return ServerConnection.a.a();
		}
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

}
