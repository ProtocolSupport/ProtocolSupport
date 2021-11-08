package protocolsupport.zplatform.impl.spigot;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.KeyPair;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_17_R1.CraftParticle;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_17_R1.util.CraftIconCache;
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
import net.minecraft.nbt.NBTCompressedStreamTools;
import net.minecraft.nbt.NBTReadLimiter;
import net.minecraft.network.EnumProtocol;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatBaseComponent.ChatSerializer;
import net.minecraft.network.protocol.game.PacketPlayOutSetSlot;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.server.network.ServerConnection;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.inventory.ContainerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.AxisAlignedBB;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.api.utils.Profile;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPacketFrameDecoder;
import protocolsupport.protocol.pipeline.IPacketFrameEncoder;
import protocolsupport.protocol.pipeline.common.PacketDecrypter;
import protocolsupport.protocol.pipeline.common.PacketEncrypter;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.serializer.DefaultNBTSerializer;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.MinecraftEncryption;
import protocolsupport.protocol.utils.authlib.LoginProfile;
import protocolsupport.utils.reflection.ReflectionUtils;
import protocolsupport.utils.reflection.UncheckedReflectionException;
import protocolsupport.zplatform.PlatformUtils;
import protocolsupport.zplatform.impl.spigot.network.SpigotChannelHandlers;
import protocolsupport.zplatform.impl.spigot.network.handler.SpigotHandshakeListener;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketCompressor;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketDecompressor;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotWrappedFrameDecoder;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotWrappedFrameEncoder;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotMiscUtils implements PlatformUtils {

	public static final DedicatedServer SERVER = ((CraftServer) Bukkit.getServer()).getServer();

	public static NetworkState protocolToNetState(EnumProtocol state) {
		switch (state) {
			case a: {
				return NetworkState.HANDSHAKING;
			}
			case b: {
				return NetworkState.PLAY;
			}
			case c: {
				return NetworkState.STATUS;
			}
			case d: {
				return NetworkState.LOGIN;
			}
			default: {
				throw new IllegalArgumentException("Unknown state " + state);
			}
		}
	}

	public static EnumProtocol netStateToProtocol(NetworkState state) {
		switch (state) {
			case HANDSHAKING: {
				return EnumProtocol.a;
			}
			case PLAY: {
				return EnumProtocol.b;
			}
			case STATUS: {
				return EnumProtocol.c;
			}
			case LOGIN: {
				return EnumProtocol.d;
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
			.toList()
		));
		return mojangGameProfile;
	}

	public static IChatBaseComponent toPlatformMessage(BaseComponent message) {
		return ChatSerializer.a(ChatAPI.toJSON(message));
	}

	@Override
	public ConnectionImpl getConnection(Player player) {
		if (player instanceof CraftPlayer craftPlayer) {
			PlayerConnection connection = craftPlayer.getHandle().b;
			if (connection != null) {
				Channel channel = connection.a.k;
				if (channel != null) {
					return ConnectionImpl.getFromChannel(channel);
				}
			}
		}
		return null;
	}

	@Override
	public void updatePlayerInventorySlot(Player player, int slot) {
		if (slot < PlayerInventory.getHotbarSize()) {
			slot += 36;
		} else if (slot > 39) {
			slot += 5;
		} else if (slot > 35) {
			slot = 8 - (slot - 36);
		}
		EntityPlayer platformPlayer = ((CraftPlayer) player).getHandle();
		ContainerPlayer platformPlayerContainer = platformPlayer.bU;
		platformPlayer.b.sendPacket(new PacketPlayOutSetSlot(platformPlayerContainer.j, platformPlayerContainer.incrementStateId(), slot, platformPlayerContainer.getSlot(slot).getItem()));
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
		net.minecraft.world.item.ItemStack nmsitemstack = new net.minecraft.world.item.ItemStack(Item.getById(stack.getTypeId()), stack.getAmount());
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
			net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemstack);
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

	protected static final Map<Particle, MinecraftKey> particleKeyMap = getParticleKeyMap();

	@SuppressWarnings("unchecked")
	protected static final Map<Particle, MinecraftKey> getParticleKeyMap() {
		try {
			return (Map<Particle, MinecraftKey>) ReflectionUtils.findField(CraftParticle.class, "particles").get(null);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new UncheckedReflectionException(e);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public NamespacedKey getParticleKey(Particle particle) {
		MinecraftKey key = particleKeyMap.get(particle);
		if (key == null) {
			return null;
		}
		return new NamespacedKey(key.getNamespace(), key.getKey());
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
		return SERVER.getDedicatedServerProperties().b;
	}

	@Override
	public boolean isDebugging() {
		return SERVER.isDebugging();
	}

	@Override
	public void enableDebug() {
		try {
			ReflectionUtils.findField(DedicatedServerProperties.class, "debug").set(SERVER.getDedicatedServerProperties(), true);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new UncheckedReflectionException("Exception occured while enabling debug", e);
		}
	}

	@Override
	public void disableDebug() {
		try {
			ReflectionUtils.findField(DedicatedServerProperties.class, "debug").set(SERVER.getDedicatedServerProperties(), false);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new UncheckedReflectionException("Exception occured while disabling debug", e);
		}
	}

	@Override
	public int getCompressionThreshold() {
		return SERVER.av();
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
		if (Epoll.isAvailable() && SERVER.m()) {
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
	public void setFraming(ChannelPipeline pipeline, IPacketFrameDecoder splitter, IPacketFrameEncoder prepender) {
		((SpigotWrappedFrameDecoder) pipeline.get(SpigotChannelHandlers.SPLITTER)).setDecoder(splitter);
		((SpigotWrappedFrameEncoder) pipeline.get(SpigotChannelHandlers.PREPENDER)).setEncoder(prepender);
	}

}
