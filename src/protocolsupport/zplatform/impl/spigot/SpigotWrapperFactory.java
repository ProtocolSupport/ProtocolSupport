package protocolsupport.zplatform.impl.spigot;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.Material;

import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
import protocolsupport.zplatform.PlatformWrapperFactory;
import protocolsupport.zplatform.impl.spigot.itemstack.SpigotItemStackWrapper;
import protocolsupport.zplatform.impl.spigot.itemstack.SpigotNBTTagCompoundWrapper;
import protocolsupport.zplatform.impl.spigot.itemstack.SpigotNBTTagListWrapper;
import protocolsupport.zplatform.impl.spigot.network.handler.SpigotLegacyHandshakeListener;
import protocolsupport.zplatform.impl.spigot.network.handler.SpigotModernHandshakeListener;
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
	public NBTTagCompoundWrapper createNBTCompoundFromStream(InputStream in) throws IOException {
		return SpigotNBTTagCompoundWrapper.fromStream(new DataInputStream(in));
	}

	@Override
	public NBTTagCompoundWrapper createEmptyNBTCompound() {
		return SpigotNBTTagCompoundWrapper.createEmpty();
	}

	@Override
	public NBTTagCompoundWrapper createNullNBTCompound() {
		return SpigotNBTTagCompoundWrapper.createNull();
	}

	@Override
	public ItemStackWrapper createNullItemStack() {
		return SpigotItemStackWrapper.createNull();
	}

	@SuppressWarnings("deprecation")
	@Override
	public ItemStackWrapper createItemStack(Material material) {
		return SpigotItemStackWrapper.create(material.getId());
	}

	@Override
	public ItemStackWrapper createItemStack(int typeId) {
		return SpigotItemStackWrapper.create(typeId);
	}

	@Override
	public AbstractHandshakeListener createModernHandshakeListener(NetworkManagerWrapper networkmanager, boolean hasCompression) {
		return new SpigotModernHandshakeListener(networkmanager, hasCompression);
	}

	@Override
	public AbstractHandshakeListener createLegacyHandshakeListener(NetworkManagerWrapper networkmanager) {
		return new SpigotLegacyHandshakeListener(networkmanager);
	}

}
