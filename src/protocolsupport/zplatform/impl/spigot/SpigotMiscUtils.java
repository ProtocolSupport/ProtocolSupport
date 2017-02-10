package protocolsupport.zplatform.impl.spigot;

import java.security.KeyPair;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.craftbukkit.v1_11_R1.CraftServer;
import org.bukkit.craftbukkit.v1_11_R1.CraftStatistic;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_11_R1.util.CraftIconCache;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.CachedServerIcon;
import org.spigotmc.SpigotConfig;

import com.mojang.authlib.properties.Property;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_11_R1.EnumProtocol;
import net.minecraft.server.v1_11_R1.MinecraftServer;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NetworkManager;
import protocolsupport.api.events.PlayerPropertiesResolveEvent.ProfileProperty;
import protocolsupport.protocol.pipeline.IPacketPrepender;
import protocolsupport.protocol.pipeline.IPacketSplitter;
import protocolsupport.protocol.utils.authlib.GameProfile;
import protocolsupport.zplatform.PlatformUtils;
import protocolsupport.zplatform.impl.spigot.itemstack.SpigotNBTTagCompoundWrapper;
import protocolsupport.zplatform.impl.spigot.network.SpigotChannelHandlers;
import protocolsupport.zplatform.impl.spigot.network.SpigotNetworkManagerWrapper;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotWrappedPrepender;
import protocolsupport.zplatform.impl.spigot.network.pipeline.SpigotWrappedSplitter;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.network.NetworkManagerWrapper;
import protocolsupport.zplatform.network.NetworkState;

public class SpigotMiscUtils implements PlatformUtils {

	public static NetworkState netStateFromEnumProtocol(EnumProtocol state) {
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

	public static MinecraftServer getServer() {
		return ((CraftServer) Bukkit.getServer()).getServer();
	}

	public static com.mojang.authlib.GameProfile toMojangGameProfile(GameProfile profile) {
		com.mojang.authlib.GameProfile mojangGameProfile = new com.mojang.authlib.GameProfile(profile.getUUID(), profile.getName());
		for (Entry<String, ProfileProperty> entry : profile.getProperties().entrySet()) {
			ProfileProperty property = entry.getValue();
			mojangGameProfile.getProperties().put(entry.getKey(), new Property(property.getName(), property.getValue(), property.getSignature()));
		}
		return mojangGameProfile;
	}

	@Override
	public ItemStack createItemStackFromNBTTag(NBTTagCompoundWrapper tag) {
		return CraftItemStack.asCraftMirror(new net.minecraft.server.v1_11_R1.ItemStack(((SpigotNBTTagCompoundWrapper) tag).unwrap()));
	}

	@Override
	public NBTTagCompoundWrapper createNBTTagFromItemStack(ItemStack itemstack) {
		net.minecraft.server.v1_11_R1.ItemStack nmsitemstack = CraftItemStack.asNMSCopy(itemstack);
		NBTTagCompound compound = new NBTTagCompound();
		nmsitemstack.save(compound);
		return SpigotNBTTagCompoundWrapper.wrap(compound);
	}

	@Override
	public String getOutdatedServerMessage() {
		return SpigotConfig.outdatedServerMessage;
	}

	@Override
	public boolean isBungeeEnabled() {
		return SpigotConfig.bungee;
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
	public Statistic getStatisticByName(String value) {
		return CraftStatistic.getBukkitStatisticByName(value);
	}

	@Override
	public String getStatisticName(Statistic stat) {
		return CraftStatistic.getNMSStatistic(stat).name;
	}

	@Override
	public Achievement getAchievmentByName(String value) {
		return CraftStatistic.getBukkitAchievementByName(value);
	}

	@Override
	public String getAchievmentName(Achievement achievement) {
		return CraftStatistic.getNMSAchievement(achievement).name;
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
	public NetworkState getNetworkStateFromChannel(Channel channel) {
		return netStateFromEnumProtocol(channel.attr(NetworkManager.c).get());
	}

	@Override
	public NetworkManagerWrapper getNetworkManagerFromChannel(Channel channel) {
		return SpigotNetworkManagerWrapper.getFromChannel(channel);
	}

	@Override
	public String getReadTimeoutHandlerName() {
		return SpigotChannelHandlers.READ_TIMEOUT;
	}

	@Override
	public String getSplitterHandlerName() {
		return SpigotChannelHandlers.SPLITTER;
	}

	@Override
	public String getPrependerHandlerName() {
		return SpigotChannelHandlers.PREPENDER;
	}

	@Override
	public void setFraming(ChannelPipeline pipeline, IPacketSplitter splitter, IPacketPrepender prepender) {
		((SpigotWrappedSplitter) pipeline.get(SpigotChannelHandlers.SPLITTER)).setRealSplitter(splitter);
		((SpigotWrappedPrepender) pipeline.get(SpigotChannelHandlers.PREPENDER)).setRealPrepender(prepender);
	}

}
