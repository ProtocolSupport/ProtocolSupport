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
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_14_R1.util.CraftIconCache;
import org.bukkit.craftbukkit.v1_14_R1.util.CraftMagicNumbers;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.CachedServerIcon;
import org.spigotmc.SpigotConfig;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.mojang.authlib.properties.Property;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import net.minecraft.server.v1_14_R1.AxisAlignedBB;
import net.minecraft.server.v1_14_R1.Block;
import net.minecraft.server.v1_14_R1.DedicatedServer;
import net.minecraft.server.v1_14_R1.DedicatedServerProperties;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.EnumProtocol;
import net.minecraft.server.v1_14_R1.IRegistry;
import net.minecraft.server.v1_14_R1.Item;
import net.minecraft.server.v1_14_R1.MinecraftServer;
import net.minecraft.server.v1_14_R1.MojangsonParser;
import net.minecraft.server.v1_14_R1.NBTCompressedStreamTools;
import net.minecraft.server.v1_14_R1.NBTReadLimiter;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.WorldServer;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPacketPrepender;
import protocolsupport.protocol.pipeline.IPacketSplitter;
import protocolsupport.protocol.pipeline.common.PacketDecrypter;
import protocolsupport.protocol.pipeline.common.PacketEncrypter;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.serializer.DefaultNBTSerializer;
import protocolsupport.protocol.utils.MinecraftEncryption;
import protocolsupport.protocol.utils.authlib.GameProfile;
import protocolsupport.utils.ReflectionUtils;
import protocolsupport.zplatform.PlatformUtils;
import protocolsupport.zplatform.impl.spigot.injector.network.SpigotNettyInjector;
import protocolsupport.zplatform.impl.spigot.network.SpigotChannelHandlers;
import protocolsupport.zplatform.impl.spigot.network.handler.SpigotHandshakeListener;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketCompressor;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotPacketDecompressor;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotWrappedPrepender;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotWrappedSplitter;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

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
	public AbstractHandshakeListener createHandshakeListener(NetworkManagerWrapper networkmanager) {
		return new SpigotHandshakeListener(networkmanager);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getEntityTypeNetworkId(EntityType type) {
		return IRegistry.ENTITY_TYPE.a(EntityTypes.a(type.getName()).get());
	}

	@Override
	public int getItemNetworkId(Material material) {
		Item item = CraftMagicNumbers.getItem(material);
		return item != null ? Item.getId(item) : -1;
	}

	@Override
	public int getBlockDataNetworkId(BlockData blockdata) {
		return Block.getCombinedId(((CraftBlockData) blockdata).getState());
	}

	@Override
	public int getBlockNetworkId(Material material) {
		return IRegistry.BLOCK.a(CraftMagicNumbers.getBlock(material));
	}

	@Override
	public BlockData getBlockDataByNetworkId(int id) {
		return CraftBlockData.fromData(Block.getByCombinedId(id));
	}

	@Override
	public Material getBlockByNetworkId(int id) {
		return CraftMagicNumbers.getMaterial(IRegistry.BLOCK.fromId(id));
	}

	@Override
	public List<BlockData> getBlockDataList(Material material) {
		return
			CraftMagicNumbers.getBlock(material).getStates().a().stream()
			.map(CraftBlockData::fromData)
			.collect(Collectors.toList());
	}

	@Override
	public ItemStack createItemStackFromNetwork(NetworkItemStack stack) {
		net.minecraft.server.v1_14_R1.ItemStack nmsitemstack = new net.minecraft.server.v1_14_R1.ItemStack(Item.getById(stack.getTypeId()), stack.getAmount());
		NBTCompound rootTag = stack.getNBT();
		if (rootTag != null) {
			//TODO: a faster way to do that
			ByteBuf buffer = Unpooled.buffer();
			try {
				DefaultNBTSerializer.INSTANCE.serializeTag(new ByteBufOutputStream(buffer), rootTag);
				nmsitemstack.setTag(NBTCompressedStreamTools.a(new ByteBufInputStream(buffer), NBTReadLimiter.a));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return CraftItemStack.asCraftMirror(nmsitemstack);
	}

	@Override
	public ItemStack deserializeItemStackFromNBTJson(String json) {
		try {
			return CraftItemStack.asCraftMirror(net.minecraft.server.v1_14_R1.ItemStack.a(MojangsonParser.parse(json)));
		} catch (CommandSyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String serializeItemStackToNBTJson(ItemStack itemstack) {
		net.minecraft.server.v1_14_R1.ItemStack nmsitemstack = CraftItemStack.asNMSCopy(itemstack);
		NBTTagCompound compound = new NBTTagCompound();
		nmsitemstack.save(compound);
		return compound.toString();
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
		return getServer().T();
	}

	@Override
	public boolean isDebugging() {
		return getServer().isDebugging();
	}

	@Override
	public void enableDebug() {
		try {
			ReflectionUtils.getField(DedicatedServerProperties.class, "debug").set(((DedicatedServer)getServer()).getDedicatedServerProperties(), true);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException("Exception occured while enabled debug");
		}
	}

	@Override
	public void disableDebug() {
		try {
			ReflectionUtils.getField(DedicatedServerProperties.class, "debug").set(((DedicatedServer)getServer()).getDedicatedServerProperties(), false);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException("Exception occured while disabling debug");
		}
	}

	@Override
	public int getCompressionThreshold() {
		return getServer().ay();
	}

	@Override
	public KeyPair getEncryptionKeyPair() {
		return getServer().getKeyPair();
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
