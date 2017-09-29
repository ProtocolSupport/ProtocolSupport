package protocolsupport.zplatform;

import org.bukkit.Material;

import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public interface PlatformWrapperFactory {

	public NBTTagListWrapper createEmptyNBTList();

	public NBTTagCompoundWrapper createNBTCompoundFromJson(String json);

	public NBTTagCompoundWrapper createEmptyNBTCompound();

	public ItemStackWrapper createNullItemStack();

	public ItemStackWrapper createItemStack(Material material);

	public ItemStackWrapper createItemStack(int typeId);

	public AbstractHandshakeListener createHandshakeListener(NetworkManagerWrapper networkmanager);

}
