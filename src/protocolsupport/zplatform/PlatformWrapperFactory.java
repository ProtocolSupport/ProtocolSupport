package protocolsupport.zplatform;

import org.bukkit.inventory.ItemStack;

import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public interface PlatformWrapperFactory {

	public NBTTagListWrapper createEmptyNBTList();

	public NBTTagCompoundWrapper createNBTCompoundFromJson(String json);

	public NBTTagCompoundWrapper createEmptyNBTCompound();

	public ItemStackWrapper createItemStack(int typeId);
	
	public ItemStackWrapper createItemStack(ItemStack item);

	public AbstractHandshakeListener createHandshakeListener(NetworkManagerWrapper networkmanager);

}
