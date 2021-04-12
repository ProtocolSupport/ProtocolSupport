package protocolsupport.protocol.types;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.zplatform.ServerPlatform;

public class NetworkBukkitItemStack extends ItemStack {

	public static NetworkItemStack create(ItemStack itemstack) {
		return
			(itemstack instanceof NetworkBukkitItemStack) ?
			((NetworkBukkitItemStack) itemstack).getNetworkItemStack() :
			ServerPlatform.get().getMiscUtils().createNetworkItemStackFromBukkit(itemstack);
	}

	protected NetworkItemStack networkItemStack;

	protected boolean bukkitMeta = false;

	public NetworkBukkitItemStack(NetworkItemStack networkItemStack) {
		this.networkItemStack = networkItemStack;
		setType(ItemMaterialLookup.getByRuntimeId(networkItemStack.getTypeId()));
		setAmount(networkItemStack.getAmount());
	}

	public NetworkItemStack getNetworkItemStack() {
		if (bukkitMeta) {
			networkItemStack = ServerPlatform.get().getMiscUtils().createNetworkItemStackFromBukkit(this);
		} else {
			Material type = getType();
			if (type == Material.AIR) {
				networkItemStack = NetworkItemStack.NULL;
			} else {
				if (networkItemStack.isNull()) {
					networkItemStack = new NetworkItemStack();
				}
				networkItemStack.setTypeId(ItemMaterialLookup.getRuntimeId(getType()));
				networkItemStack.setAmount(getAmount());
			}
		}
		return networkItemStack;
	}

	@Override
	public boolean hasItemMeta() {
		if (bukkitMeta) {
			return super.hasItemMeta();
		} else {
			return networkItemStack.getNBT() != null;
		}
	}

	@Override
	public ItemMeta getItemMeta() {
		if (!bukkitMeta && (networkItemStack.getNBT() != null)) {
			bukkitMeta = true;
			setItemMeta(ServerPlatform.get().getMiscUtils().createBukkitItemStackFromNetwork(networkItemStack).getItemMeta());
		}
		return super.getItemMeta();
	}

	@Override
	public boolean setItemMeta(ItemMeta itemMeta) {
		boolean result = super.setItemMeta(itemMeta);
		if (result) {
			bukkitMeta = true;
		}
		return result;
	}

	@Override
	public ItemStack clone() {
		ItemStack clone = new ItemStack(getType(), getAmount());
		if (hasItemMeta()) {
			clone.setItemMeta(getItemMeta());
		}
		return clone;
	}

}
