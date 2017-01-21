package protocolsupport.zplatform;

import java.security.KeyPair;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.bukkit.Achievement;
import org.bukkit.Statistic;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.CachedServerIcon;

import com.mojang.authlib.minecraft.MinecraftSessionService;

import io.netty.channel.Channel;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.network.NetworkManagerWrapper;
import protocolsupport.zplatform.network.NetworkState;

public interface PlatformUtils {

	public String localize(String key, Object... args);

	public ItemStack createItemStackFromNBTTag(NBTTagCompoundWrapper tag);

	public NBTTagCompoundWrapper createNBTTagFromItemStack(ItemStack itemstack);

	public Integer getItemIdByName(String registryname);

	public String getOutdatedServerMessage();

	public boolean isBungeeEnabled();

	public boolean isDebugging();

	public void enableDebug();

	public void disableDebug();

	public int getCompressionThreshold();

	public KeyPair getEncryptionKeyPair();

	public MinecraftSessionService getSessionService();

	public <V> FutureTask<V> callSyncTask(Callable<V> call);

	public String getModName();

	public String getVersionName();

	public Statistic getStatisticByName(String value);

	public String getStatisticName(Statistic stat);

	public Achievement getAchievmentByName(String value);

	public String getAchievmentName(Achievement achievement);

	public String convertBukkitIconToBase64(CachedServerIcon icon);

	public String getSoundNameById(int soundId);

	public String getPotionEffectNameById(int id);

	public NetworkState getNetworkStateFromChannel(Channel channel);

	public NetworkManagerWrapper getNetworkManagerFromChannel(Channel channel);

}
