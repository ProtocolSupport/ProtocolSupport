package protocolsupport.zplatform;

import java.io.DataInput;
import java.io.IOException;

import org.bukkit.Material;

import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public interface PlatformWrapperFactory {

	public NBTTagListWrapper createEmptyNBTList();

	public NBTTagCompoundWrapper createNBTCompoundFromJson(String json);

	public NBTTagCompoundWrapper createNBTCompoundFromStream(DataInput datainput) throws IOException;

	public NBTTagCompoundWrapper createEmptyNBTCompound();

	public NBTTagCompoundWrapper createNullNBTCompound();

	public ItemStackWrapper createNullItemStack();

	public ItemStackWrapper createItemStack(Material material);

	public ItemStackWrapper createItemStack(int typeId);

	public AbstractHandshakeListener createModernHandshakeListener(NetworkManagerWrapper networkmanager, boolean hasCompression);

	public AbstractHandshakeListener createLegacyHandshakeListener(NetworkManagerWrapper networkmanager);

}
