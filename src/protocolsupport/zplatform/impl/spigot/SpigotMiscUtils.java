package protocolsupport.zplatform.impl.spigot;

import java.security.KeyPair;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_13_R2.CraftServer;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_13_R2.util.CraftIconCache;
import org.bukkit.craftbukkit.v1_13_R2.util.CraftMagicNumbers;
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
import net.minecraft.server.v1_13_R2.AxisAlignedBB;
import net.minecraft.server.v1_13_R2.Block;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.EntityTypes;
import net.minecraft.server.v1_13_R2.EnumProtocol;
import net.minecraft.server.v1_13_R2.IRegistry;
import net.minecraft.server.v1_13_R2.Item;
import net.minecraft.server.v1_13_R2.MinecraftServer;
import net.minecraft.server.v1_13_R2.MojangsonParser;
import net.minecraft.server.v1_13_R2.NBTCompressedStreamTools;
import net.minecraft.server.v1_13_R2.NBTReadLimiter;
import net.minecraft.server.v1_13_R2.NBTTagCompound;
import net.minecraft.server.v1_13_R2.WorldServer;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.modifiers.HoverAction.EntityInfo;
import protocolsupport.api.utils.NetworkState;
import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.protocol.pipeline.ChannelHandlers;
import protocolsupport.protocol.pipeline.IPacketPrepender;
import protocolsupport.protocol.pipeline.IPacketSplitter;
import protocolsupport.protocol.pipeline.common.PacketDecrypter;
import protocolsupport.protocol.pipeline.common.PacketEncrypter;
import protocolsupport.protocol.typeremapper.itemstack.LegacyItemType;
import protocolsupport.protocol.typeremapper.itemstack.PreFlatteningItemIdData;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.MinecraftEncryption;
import protocolsupport.protocol.utils.authlib.GameProfile;
import protocolsupport.protocol.utils.types.NetworkItemStack;
import protocolsupport.protocol.utils.types.nbt.NBTCompound;
import protocolsupport.protocol.utils.types.nbt.serializer.DefaultNBTSerializer;
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
	public AbstractHandshakeListener createHandshakeListener(NetworkManagerWrapper networkmanager) {
		return new SpigotHandshakeListener(networkmanager);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getMobTypeNetworkId(EntityType type) {
		return IRegistry.ENTITY_TYPE.a(EntityTypes.a(type.getName()));
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
	public BlockData getBlockDataByNetworkId(int id) {
		return CraftBlockData.fromData(Block.getByCombinedId(id));
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
		net.minecraft.server.v1_13_R2.ItemStack nmsitemstack = new net.minecraft.server.v1_13_R2.ItemStack(Item.getById(stack.getTypeId()), stack.getAmount());
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
		return CraftItemStack.asBukkitCopy(nmsitemstack);
	}

	@Override
	public ItemStack deserializeItemStackFromNBTJson(String json) {
		try {
			return CraftItemStack.asCraftMirror(net.minecraft.server.v1_13_R2.ItemStack.a(MojangsonParser.parse(json)));
		} catch (CommandSyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String serializeItemStackToNBTJson(ItemStack itemstack) {
		net.minecraft.server.v1_13_R2.ItemStack nmsitemstack = CraftItemStack.asNMSCopy(itemstack);
		NBTTagCompound compound = new NBTTagCompound();
		nmsitemstack.save(compound);
		return compound.toString();
	}

	@SuppressWarnings("deprecation")
	@Override
	public EntityInfo parseEntityInfo(String json) {
		try {
			NBTTagCompound compound = MojangsonParser.parse(json);
			return new EntityInfo(
				EntityType.fromName(compound.getString("type")),
				UUID.fromString(compound.getString("id")),
				compound.getString("name")
			);
		} catch (CommandSyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String fixHoverShowItem(ProtocolVersion version, String locale, String json) {
		try {
			NBTTagCompound compound = MojangsonParser.parse(json);
			Material material = ItemMaterialLookup.getByKey(compound.getString("id"));
			if (material != null) {
				int materialRuntimeId = LegacyItemType.REGISTRY.getTable(version).getRemap(ItemMaterialLookup.getRuntimeId(material));
				if (version.isBefore(ProtocolVersion.MINECRAFT_1_8)) {
					compound.setInt("id", PreFlatteningItemIdData.getIdFromLegacyCombinedId(PreFlatteningItemIdData.getLegacyCombinedIdByModernId(materialRuntimeId)));
				} else {
					compound.setString("id", ItemMaterialLookup.getByRuntimeId(materialRuntimeId).getKey().toString());
				}
			}
			return compound.toString();
		} catch (CommandSyntaxException e) {
			throw new RuntimeException(e);
		}
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
		return getServer().isRunning();
	}

	@Override
	public boolean isProxyEnabled() {
		return SpigotConfig.bungee;
	}

	@Override
	public boolean isProxyPreventionEnabled() {
		return getServer().S();
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
		return getServer().aw();
	}

	@Override
	public KeyPair getEncryptionKeyPair() {
		return getServer().E();
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
