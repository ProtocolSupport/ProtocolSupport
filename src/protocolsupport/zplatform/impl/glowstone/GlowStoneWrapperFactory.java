package protocolsupport.zplatform.impl.glowstone;

import java.io.DataInput;
import java.io.IOException;

import org.bukkit.Material;

import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.protocol.packet.handler.AbstractLoginListener;
import protocolsupport.protocol.packet.handler.AbstractStatusListener;
import protocolsupport.zplatform.PlatformWrapperFactory;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;
import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
import protocolsupport.zplatform.network.NetworkManagerWrapper;

public class GlowStoneWrapperFactory implements PlatformWrapperFactory {

	@Override
	public NBTTagListWrapper createEmptyNBTList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NBTTagCompoundWrapper createNBTCompoundFromJson(String json) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NBTTagCompoundWrapper createNBTCompoundFromStream(DataInput datainput) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NBTTagCompoundWrapper createEmptyNBTCompound() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NBTTagCompoundWrapper createNullNBTCompound() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStackWrapper createNullItemStack() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStackWrapper createItemStack(Material material) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStackWrapper createItemStack(int typeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractHandshakeListener createModernHandshakeListener(NetworkManagerWrapper networkmanager, boolean hasCompression) {
		return new AbstractHandshakeListener(networkmanager) {
			@Override
			protected AbstractStatusListener getStatusListener(NetworkManagerWrapper networkManager) {
				return new AbstractStatusListener(networkmanager) {
				};
			}
			@Override
			protected AbstractLoginListener getLoginListener(NetworkManagerWrapper networkManager, String hostname) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

	@Override
	public AbstractHandshakeListener createLegacyHandshakeListener(NetworkManagerWrapper networkmanager) {
		return new AbstractHandshakeListener(networkmanager) {
			@Override
			protected AbstractStatusListener getStatusListener(NetworkManagerWrapper networkManager) {
				return new AbstractStatusListener(networkmanager) {
				};
			}
			@Override
			protected AbstractLoginListener getLoginListener(NetworkManagerWrapper networkManager, String hostname) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}

}
