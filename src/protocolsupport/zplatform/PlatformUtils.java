package protocolsupport.zplatform;

import java.security.KeyPair;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.CachedServerIcon;

import com.google.common.base.Predicate;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import protocolsupport.api.Connection;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.pipeline.IPacketPrepender;
import protocolsupport.protocol.pipeline.IPacketSplitter;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.network.NetworkManagerWrapper;
import protocolsupport.zplatform.network.NetworkState;

public interface PlatformUtils {

	public ItemStack createItemStackFromNBTTag(NBTTagCompoundWrapper tag);

	public NBTTagCompoundWrapper createNBTTagFromItemStack(ItemStack itemstack);

	public String getOutdatedServerMessage();

	public boolean isRunning();

	public boolean isProxyEnabled();

	public boolean isDebugging();

	public void enableDebug();

	public void disableDebug();

	public int getCompressionThreshold();

	public KeyPair getEncryptionKeyPair();

	public <V> FutureTask<V> callSyncTask(Callable<V> call);

	public String getModName();

	public String getVersionName();

	public String convertBukkitIconToBase64(CachedServerIcon icon);

	public NetworkState getNetworkStateFromChannel(Channel channel);

	public NetworkManagerWrapper getNetworkManagerFromChannel(Channel channel);

	public String getReadTimeoutHandlerName();

	public String getSplitterHandlerName();

	public String getPrependerHandlerName();

	public void setFraming(ChannelPipeline pipeline, IPacketSplitter splitter, IPacketPrepender prepender);
	
	public List<Connection> getNearbyConnections(Location loc, double deltaX, double deltaY, double deltaZ, ProtocolVersion... versions);

	public List<Player> getNearbyPlayers(Location loc, double deltaX, double deltaY, double deltaZ);
	
	public List<Entity> getNearbyEntities(Location loc, double deltaX, double deltaY, double deltaZ);

	static Predicate<Connection> legacyConnectionFilter(ProtocolVersion... versions) {
		return e -> e != null && Arrays.asList(versions).contains(e.getVersion());
	}
}
