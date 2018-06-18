package protocolsupport.zplatform;

import java.security.KeyPair;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.crypto.SecretKey;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.CachedServerIcon;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import protocolsupport.protocol.pipeline.IPacketPrepender;
import protocolsupport.protocol.pipeline.IPacketSplitter;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;

public interface PlatformUtils {

	public ItemStack createItemStackFromNBTTag(NBTTagCompoundWrapper tag);

	public NBTTagCompoundWrapper createNBTTagFromItemStack(ItemStack itemstack);

	public List<Player> getNearbyPlayers(Location location, double rX, double rY, double rZ);

	public String getOutdatedServerMessage();

	public String getOutdatedClientMessage();

	public boolean isRunning();

	public boolean isProxyEnabled();

	public boolean isProxyPreventionEnabled();

	public boolean isDebugging();

	public void enableDebug();

	public void disableDebug();

	public int getCompressionThreshold();

	public KeyPair getEncryptionKeyPair();

	public <V> FutureTask<V> callSyncTask(Callable<V> call);

	public String getModName();

	public String getVersionName();

	public String convertBukkitIconToBase64(CachedServerIcon icon);

	public String getReadTimeoutHandlerName();

	public void enableCompression(ChannelPipeline pipeline, int compressionThreshold);

	public void enableEncryption(ChannelPipeline pipeline, SecretKey key, boolean fullEncryption);

	public void setFraming(ChannelPipeline pipeline, IPacketSplitter splitter, IPacketPrepender prepender);

	public EventLoopGroup getServerEventLoop();

}
