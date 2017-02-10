package protocolsupport.zplatform;

import java.security.KeyPair;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.bukkit.Achievement;
import org.bukkit.Statistic;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.CachedServerIcon;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import protocolsupport.protocol.pipeline.IPacketPrepender;
import protocolsupport.protocol.pipeline.IPacketSplitter;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.network.NetworkManagerWrapper;
import protocolsupport.zplatform.network.NetworkState;

public interface PlatformUtils {

	public ItemStack createItemStackFromNBTTag(NBTTagCompoundWrapper tag);

	public NBTTagCompoundWrapper createNBTTagFromItemStack(ItemStack itemstack);

	public String getOutdatedServerMessage();

	public boolean isBungeeEnabled();

	public boolean isDebugging();

	public void enableDebug();

	public void disableDebug();

	public int getCompressionThreshold();

	public KeyPair getEncryptionKeyPair();

	public <V> FutureTask<V> callSyncTask(Callable<V> call);

	public String getModName();

	public String getVersionName();

	public Statistic getStatisticByName(String value);

	public String getStatisticName(Statistic stat);

	public Achievement getAchievmentByName(String value);

	public String getAchievmentName(Achievement achievement);

	public String convertBukkitIconToBase64(CachedServerIcon icon);

	public NetworkState getNetworkStateFromChannel(Channel channel);

	public NetworkManagerWrapper getNetworkManagerFromChannel(Channel channel);

	public String getReadTimeoutHandlerName();

	public String getSplitterHandlerName();

	public String getPrependerHandlerName();

	public void setFraming(ChannelPipeline pipeline, IPacketSplitter splitter, IPacketPrepender prepender);

}
