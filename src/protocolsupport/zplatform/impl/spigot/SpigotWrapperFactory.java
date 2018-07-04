package protocolsupport.zplatform.impl.spigot;

import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.zplatform.PlatformWrapperFactory;
import protocolsupport.zplatform.impl.spigot.itemstack.SpigotItemStackWrapper;
import protocolsupport.zplatform.impl.spigot.itemstack.SpigotNBTTagCompoundWrapper;
import protocolsupport.zplatform.impl.spigot.itemstack.SpigotNBTTagListWrapper;
import protocolsupport.zplatform.impl.spigot.network.handler.SpigotHandshakeListener;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class SpigotWrapperFactory implements PlatformWrapperFactory {

	@Override
	public NBTTagListWrapper createEmptyNBTList() {
		return SpigotNBTTagListWrapper.create();
	}

	@Override
	public NBTTagCompoundWrapper createNBTCompoundFromJson(String json) {
		return SpigotNBTTagCompoundWrapper.fromJson(json);
	}

	@Override
	public NBTTagCompoundWrapper createEmptyNBTCompound() {
		return SpigotNBTTagCompoundWrapper.createEmpty();
	}

	@Override
	public ItemStackWrapper createItemStack(int typeId) {
		return SpigotItemStackWrapper.create(typeId);
	}

	@Override
	public ItemStackWrapper createItemStack(ItemStack item) {
		return SpigotItemStackWrapper.create(CraftItemStack.asNMSCopy(item));
	}

	@Override
	public AbstractHandshakeListener createHandshakeListener(NetworkManagerWrapper networkmanager) {
		return new SpigotHandshakeListener(networkmanager);
	}

}
