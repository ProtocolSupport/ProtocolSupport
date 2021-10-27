package protocolsupport.zplatform;

import java.security.KeyPair;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.crypto.SecretKey;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.CachedServerIcon;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.MultithreadEventLoopGroup;
import protocolsupport.api.utils.Profile;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.protocol.pipeline.IPacketPrepender;
import protocolsupport.protocol.pipeline.IPacketSplitter;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.utils.authlib.LoginProfile;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public interface PlatformUtils {

	public ConnectionImpl getConnection(Player player);

	public void updatePlayerInventorySlot(Player player, int slot);

	public ItemStack createBukkitItemStackFromNetwork(NetworkItemStack stack);

	public NetworkItemStack createNetworkItemStackFromBukkit(ItemStack itemstack);

	public NamespacedKey getParticleKey(Particle particle);

	public List<Player> getNearbyPlayers(Location location, double rX, double rY, double rZ);

	public String getOutdatedServerMessage();

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

	public MultithreadEventLoopGroup getServerIOEventLoopGroup();

	public String getReadTimeoutHandlerName();

	public void enableCompression(ChannelPipeline pipeline, int compressionThreshold);

	public void enableEncryption(ChannelPipeline pipeline, SecretKey key, boolean fullEncryption);

	public void setFraming(ChannelPipeline pipeline, IPacketSplitter splitter, IPacketPrepender prepender);

	public AbstractHandshakeListener createHandshakeListener(NetworkManagerWrapper networkmanager);

	public Profile createWrappedProfile(LoginProfile loginProfile, Player player);

}