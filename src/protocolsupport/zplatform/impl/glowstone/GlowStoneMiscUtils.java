package protocolsupport.zplatform.impl.glowstone;

import java.security.KeyPair;
import java.text.MessageFormat;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.bukkit.Achievement;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.CachedServerIcon;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.glowstone.GlowServer;
import net.glowstone.net.protocol.ProtocolType;
import net.glowstone.util.GlowServerIcon;
import protocolsupport.protocol.pipeline.IPacketPrepender;
import protocolsupport.protocol.pipeline.IPacketSplitter;
import protocolsupport.zplatform.PlatformUtils;
import protocolsupport.zplatform.impl.glowstone.network.GlowStoneChannelHandlers;
import protocolsupport.zplatform.impl.glowstone.network.GlowStoneNetworkManagerWrapper;
import protocolsupport.zplatform.impl.glowstone.network.pipeline.GlowStoneFramingHandler;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.network.NetworkManagerWrapper;
import protocolsupport.zplatform.network.NetworkState;

public class GlowStoneMiscUtils implements PlatformUtils {

	public static GlowServer getServer() {
		return ((GlowServer) Bukkit.getServer());
	}

	public static ProtocolType netStateToProtocol(NetworkState type) {
		switch (type) {
			case HANDSHAKING: {
				return ProtocolType.HANDSHAKE;
			}
			case PLAY: {
				return ProtocolType.PLAY;
			}
			case LOGIN: {
				return ProtocolType.LOGIN;
			}
			case STATUS: {
				return ProtocolType.STATUS;
			}
			default: {
				throw new IllegalArgumentException(MessageFormat.format("Unknown state {0}", type));
			}
		}
	}

	public static NetworkState protocolToNetState(ProtocolType type) {
		switch (type) {
			case HANDSHAKE: {
				return NetworkState.HANDSHAKING;
			}
			case LOGIN: {
				return NetworkState.LOGIN;
			}
			case STATUS: {
				return NetworkState.STATUS;
			}
			case PLAY: {
				return NetworkState.PLAY;
			}
			default: {
				throw new IllegalArgumentException(MessageFormat.format("Unknown protocol {0}", type));
			}
		}
	}

	@Override
	public String localize(String key, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack createItemStackFromNBTTag(NBTTagCompoundWrapper tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NBTTagCompoundWrapper createNBTTagFromItemStack(ItemStack itemstack) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getItemIdByName(String registryname) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOutdatedServerMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBungeeEnabled() {
		return getServer().getProxySupport();
	}

	@Override
	public boolean isDebugging() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void enableDebug() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disableDebug() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCompressionThreshold() {
		return getServer().getCompressionThreshold();
	}

	@Override
	public KeyPair getEncryptionKeyPair() {
		return getServer().getKeyPair();
	}

	@Override
	public <V> FutureTask<V> callSyncTask(Callable<V> call) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getModName() {
		return "GlowStone";
	}

	@Override
	public String getVersionName() {
		return GlowServer.GAME_VERSION;
	}

	@Override
	public Statistic getStatisticByName(String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStatisticName(Statistic stat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Achievement getAchievmentByName(String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAchievmentName(Achievement achievement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String convertBukkitIconToBase64(CachedServerIcon icon) {
		return ((GlowServerIcon) icon).getData();
	}

	@Override
	public String getSoundNameById(int soundId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPotionEffectNameById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NetworkState getNetworkStateFromChannel(Channel channel) {
		return GlowStoneNetworkManagerWrapper.getFromChannel(channel).getProtocol();
	}

	@Override
	public NetworkManagerWrapper getNetworkManagerFromChannel(Channel channel) {
		return GlowStoneNetworkManagerWrapper.getFromChannel(channel);
	}

	@Override
	public String getReadTimeoutHandlerName() {
		return GlowStoneChannelHandlers.READ_TIMEOUT;
	}

	@Override
	public String getSplitterHandlerName() {
		return GlowStoneChannelHandlers.FRAMING;
	}

	@Override
	public String getPrependerHandlerName() {
		return GlowStoneChannelHandlers.FRAMING;
	}

	@Override
	public void setFraming(ChannelPipeline pipeline, IPacketSplitter splitter, IPacketPrepender prepender) {
		((GlowStoneFramingHandler) pipeline.get(GlowStoneChannelHandlers.FRAMING)).setRealFraming(prepender, splitter);
	}

}
